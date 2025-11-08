package com.yiruantong.composite.rabbitReceiver.out;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.system.service.task.ITaskQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OutOrderQuickOutReceiver implements IRabbitReceiver {
  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
    private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderSortingService outOrderSortingService;


    /**
   * 质检结果返回后需要将标记一键出库的单据进行一键出库 （湘钢）
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.QUALITY_TESTING_RESULT_OUT_ORDER_QUICK_OUT); // 接收哪几种类型的数据
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
      LambdaQueryWrapper<OutOrder> outOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      outOrderLambdaQueryWrapper.apply("JSON_EXTRACT(expand_fields, '$.isQuickOut') = true")
        .eq(OutOrder::getOrderStatus, OutOrderStatusEnum.NOT_OUT_ACCOMPLISH.getName())
        .eq(OutOrder::getSortingStatus, SortingStatusEnum.NONE.getId());

      List<OutOrder> outOrders = outOrderService.list(outOrderLambdaQueryWrapper);


        for (OutOrder orderInfo : outOrders) {
          //修改出库单状态为 审核成功  因为 现在出库单状态是 未出库完成 不能进行分拣
          outOrderService.updateOrderStatus(orderInfo.getOrderId(),OutOrderStatusEnum.AUDIT_WAITING);
          orderInfo.setOrderStatus(OutOrderStatusEnum.AUDIT_WAITING.getName());

          //#region 出库单自动出库
          OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(orderInfo, OutScanMainBo.class);
          newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.PC_QUALITY_TESTING_RETURN_OUT);
          newOutScanOrderBo.setHolderSourceTypeEnum(HolderSourceTypeEnum.ALLOY_AUXILIARY_MATERIALS);
          newOutScanOrderBo.setSpillover(EnableEnum.ENABLE.getId()); // 标记 生成已出单
          Map<String, Object> expandFields = orderInfo.getExpandFields();
          expandFields.put("isQuickOut",true);
          orderInfo.setExpandFields(expandFields);
          List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(orderInfo.getOrderId()); // 订单明细集合
          newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细
          for (var outOrderDetail : outOrderDetailList) {
            OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
            outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
            newOutScanOrderBo.getDataList().add(outScanDetailBo);
          }
          // 执行一键出库功能
          R<Void> voidR = outOrderService.quickOut(newOutScanOrderBo);
          if(!voidR.isResult()){
            // 更新任务状态为完成
            taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL,voidR.getMsg());
            transactionManager.commit(transaction); // 手动提交事务
            return R.fail();

          }
          //#endregion
        }

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务

      return R.ok();
    } catch (Exception e) {
      // 更新任务状态为失败
      transactionManager.rollback(transaction); // 手动回滚事务
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
      return R.fail();
    }
  }
  //#endregion
}
