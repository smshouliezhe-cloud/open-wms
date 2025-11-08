package com.yiruantong.composite.rabbitReceiver.out;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.outbound.service.out.IOutScanOrderService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VirtualOutOrderSortingReceiver implements IRabbitReceiver {

  private final IOutOrderSortingService outOrderSortingService;
  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderService outOrderService;
  private final ITaskQueueService taskQueueService;
  private final IOutOrderDetailService outOrderDetailService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.VIRTUAL_ORDER_SORTING); // 接收哪几种类型的数据
  }


  //#region 实现MQ 方法

  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {
    if (taskQueueService.checkTaskFinished(rabbitReceiverDto.getTaskId())) {
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED, MessageUtils.message("rabbitmq.no.longer.executing"));
      return R.fail(MessageUtils.message("rabbitmq.no.longer.executing"));
    }

    // 手动开启事务  start
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus transaction = transactionManager.getTransaction(definition);
    try {
      OutOrder outOrder = outOrderService.getById(rabbitReceiverDto.getBillId()); // 出库单主表信息
      Assert.isTrue(ObjectUtil.isNotEmpty(outOrder), "未找到对应的出库订单");

      // 如果还没有分配库存 执行分配库存代码
      if (outOrder.getSortingStatus() != SortingStatusEnum.ASSIGNED.getId()) {
        // 设置可分拣的库存状态
        List<InventoryStatusEnum> inventoryStatusEnumList = CollUtil.newArrayList(InventoryStatusEnum.ALLOCATE_ROUTE);
        // 设置可分拣的货位类型
        List<PositionTypeEnum> positionTypeEnumList = CollUtil.newArrayList(PositionTypeEnum.VIRTUAL);
        //执行分拣操作
        outOrderSortingService.sorting(outOrder.getOrderId(), false, inventoryStatusEnumList, positionTypeEnumList);
      }

      //分拣成功 自动出库
      OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(outOrder, OutScanMainBo.class);
      newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.PC_ALLOCATE_ROUTE_VIRTUAL_OUT);
      HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(outOrder.getOrderType());

      Assert.isFalse(holderSourceTypeEnum == null, "未找到出库单的枚举");
      newOutScanOrderBo.setHolderSourceTypeEnum(holderSourceTypeEnum);

      List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(outOrder.getOrderId()); // 订单明细集合
      newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细
      for (var outOrderDetail : outOrderDetailList) {
        OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
        outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
        newOutScanOrderBo.getDataList().add(outScanDetailBo);
      }
      IOutScanOrderService outScanOrderService = SpringUtils.getBean(IOutScanOrderService.class);
      outScanOrderService.normalOutSave(newOutScanOrderBo);

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception e) {
      // 更新任务状态为失败
      transactionManager.rollback(transaction); // 手动回滚事务
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
    }
    return R.ok();
  }
}
