package com.yiruantong.composite.rabbitReceiver.in;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.*;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InFinishedInvalidateOutReceiver implements IRabbitReceiver {
  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IInEnterService inEnterService;
  private final IInEnterDetailService inEnterDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutSortingRuleService outSortingRuleService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;


  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_FINISHED_INVALIDATE_OUT); // 接收哪几种类型的数据
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
      InEnter enterInfo = inEnterService.getById(rabbitReceiverDto.getBillId());
      if (ObjectUtil.isNull(enterInfo)) {
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到入库单");
        transactionManager.rollback(transaction);
        return R.fail();
      }
      var enterDetailList = (List<LinkedHashMap<String, Object>>) rabbitReceiverDto.getOtherField().get("data");
      if (ObjectUtil.isNull(enterDetailList) || B.isEqual(enterDetailList.size())) {
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "明细没有【报废出库】商品属性！");
        transactionManager.rollback(transaction);
        return R.fail();
      }
      //#region 创建出库单
      OutOrder outOrderInfo = new OutOrder();
      BeanUtil.copyProperties(enterInfo, outOrderInfo);
      outOrderInfo.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671, LoginHelper.getLoginUser().getTenantId()));
      outOrderInfo.setTotalQuantityOrder(enterInfo.getTotalEnterQuantity());
      outOrderInfo.setOrderType(OutOrderTypeEnum.INVALIDATE_OUT.getName());
      outOrderInfo.setOrderStatus(OutOrderStatusEnum.NEWED.getName());
      outOrderInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
      outOrderInfo.setSourceType("入库单");
      outOrderInfo.setSourceId("" + enterInfo.getEnterId());
      outOrderInfo.setSourceCode(enterInfo.getEnterCode());
      outOrderInfo.setOrderId(null);
      outOrderInfo.setAuditor(LoginHelper.getLoginUser().getNickname());
      outOrderInfo.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      outOrderInfo.setAuditDate(new Date());
      outOrderInfo.setConsignorNameSale("上海");
      outOrderInfo.setConsignorIdSale(483L);
      outOrderInfo.setRemark("报废出库");

      outOrderInfo.setTotalQuantityOrder(BigDecimal.ZERO);
      outOrderInfo.setTotalWeight(BigDecimal.ZERO);
      outOrderInfo.setTotalCube(BigDecimal.ZERO);
      outOrderInfo.setTotalNetWeight(BigDecimal.ZERO);
      outOrderService.save(outOrderInfo);
      outOrderStatusHistoryService.AddHistory(outOrderInfo, OutOperationTypeEnum.PC_INVALIDATE_OUT, OutOrderStatusEnum.AUDIT_SUCCESS);
      outOrderInfo.setOrderStatus(OutOrderStatusEnum.AUDIT_SUCCESS.getName());
      //#endregion

      //#region 创建出库单 明细
      for (var enterDetail : enterDetailList) {
        OutOrderDetail outOrderDetail = new OutOrderDetail();
        BeanUtil.copyProperties(enterDetail, outOrderDetail);
        outOrderDetail.setOrderId(outOrderInfo.getOrderId());
        outOrderDetail.setSourceDetailId("" + enterDetail.get("orderDetailId"));
        outOrderDetail.setSourceMainId("" + enterDetail.get("orderId"));
        outOrderDetail.setQuantityOrder(Convert.toBigDecimal(enterDetail.get("enterQuantity")));
        outOrderDetail.setSourceType("入库单");
        outOrderDetail.setRemark("报废出库");
        outOrderDetail.setConsignorNameSale("上海");
        outOrderDetail.setConsignorIdSale(483L);
        outOrderDetail.setOrderDetailId(null);
        outOrderDetailService.save(outOrderDetail);

        //查询库存Id
        LambdaQueryWrapper<CoreInventory> coreInventoryLqw = new LambdaQueryWrapper<>();
        coreInventoryLqw.select(CoreInventory::getInventoryId)
          .eq(CoreInventory::getBillCode, enterInfo.getEnterCode())
          .eq(CoreInventory::getProductAttribute, "报废出库")
          .eq(CoreInventory::getProductId, outOrderDetail.getProductId())
          .eq(CoreInventory::getStorageId, enterInfo.getStorageId())
          .eq(CoreInventory::getValidStorage, outOrderDetail.getQuantityOrder())
          .last("limit 1");
        CoreInventory info = coreInventoryService.getOne(coreInventoryLqw);
        if (ObjectUtil.isNotEmpty(info)) {
          OutSortingRule outSortingRule = new OutSortingRule();
          outSortingRule.setInventoryId(info.getInventoryId());
          outSortingRule.setOrderId(outOrderInfo.getOrderId());
          outSortingRule.setOrderDetailId(outOrderDetail.getOrderDetailId());
          outSortingRule.setProductId(outOrderDetail.getProductId());
          outSortingRule.setProductCode(outOrderDetail.getProductCode());
          outSortingRuleService.save(outSortingRule);
        }

        outOrderInfo.setTotalQuantityOrder(B.add(outOrderInfo.getTotalQuantityOrder(), outOrderDetail.getQuantityOrder()));
        outOrderInfo.setTotalWeight(B.add(outOrderInfo.getTotalWeight(), outOrderDetail.getRowWeight()));
        outOrderInfo.setTotalCube(B.add(outOrderInfo.getTotalCube(), outOrderDetail.getRowCube()));
        outOrderInfo.setTotalNetWeight(B.add(outOrderInfo.getTotalNetWeight(), outOrderDetail.getRowNetWeight()));

      }
      outOrderService.updateById(outOrderInfo);
      //#endregion

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务

      IOutOrderSortingService sortingBean = SpringUtils.getBean(IOutOrderSortingService.class); // 需要动态获取bean，否则循环冲突
      sortingBean.sorting(outOrderInfo.getOrderId(), false);// 更新任务状态为完成

      //#region 出库单自动出库
      OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(outOrderInfo, OutScanMainBo.class);
      newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.INVALIDATE_OUT);
      newOutScanOrderBo.setHolderSourceTypeEnum(HolderSourceTypeEnum.INVALIDATE_OUT);
      List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(outOrderInfo.getOrderId()); // 订单明细集合
      newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细
      for (var outOrderDetail : outOrderDetailList) {
        OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
        outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
        newOutScanOrderBo.getDataList().add(outScanDetailBo);
      }
      IOutScanOrderService outScanOrderService = SpringUtils.getBean(IOutScanOrderService.class);
      outScanOrderService.normalOutSave(newOutScanOrderBo);
      //#endregion
    } catch (Exception e) {
      // 更新任务状态为失败
      transactionManager.rollback(transaction); // 手动回滚事务
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
    }
    return R.ok();
  }

//#endregion
}
