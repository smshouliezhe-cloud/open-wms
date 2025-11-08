package com.yiruantong.composite.rabbitReceiver.in;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.enums.out.OutSourceTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.outbound.service.out.IOutSortingRuleService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 销售订单到出库单
 */
@RequiredArgsConstructor
@Service
public class InOrderToOutOrderReceiver implements IRabbitReceiver {
  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IInEnterService inEnterService;
  private final IInEnterDetailService inEnterDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutSortingRuleService outSortingRuleService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_ORDER_TO_OUT_ORDER, RabbitmqTypeEnum.IN_ORDER_TO_OUT_ORDER_CROSS); // 接收哪几种类型的数据
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

    //调用下一步消息队列
    RabbitReceiverDto nextDto = BeanUtil.copyProperties(rabbitReceiverDto, RabbitReceiverDto.class);
    try {
      InOrder inOrder = inOrderService.getById(rabbitReceiverDto.getBillId());

      if (ObjectUtil.isNull(inOrder)) {
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到销售单");
        transactionManager.rollback(transaction);
        return R.fail();
      }
      List<InOrderDetail> saleOrderDetailList = inOrderDetailService.selectListByMainId(inOrder.getOrderId());

      //#region 创建出库单
      OutOrder outOrder = new OutOrder();
      BeanUtil.copyProperties(inOrder, outOrder);
      outOrder.setOrderId(null);
      outOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671));
      outOrder.setStoreOrderCode(inOrder.getSourceCode());
      outOrder.setTotalQuantityOrder(BigDecimal.ZERO);// 下面会根据明细累加

      OutOrderTypeEnum outOrderTypeEnum;
      if (rabbitReceiverDto.getRabbitmqType() == RabbitmqTypeEnum.IN_ORDER_TO_OUT_ORDER_CROSS) {
        outOrderTypeEnum = OutOrderTypeEnum.CROSS_ORDER;
        outOrder.setDistributionType("城配");
      } else {
        outOrderTypeEnum = OutOrderTypeEnum.NORMAL;
      }
      outOrder.setOrderType(outOrderTypeEnum.getName());

      outOrder.setOrderStatus(OutOrderStatusEnum.NEWED.getName());
      outOrder.setSortingStatus(SortingStatusEnum.NONE.getId());
      outOrder.setSourceType(OutSourceTypeEnum.IN_ORDER.getName());
      outOrder.setSourceId("" + inOrder.getOrderId());
      outOrder.setSourceCode(inOrder.getSourceCode());
      outOrder.setAuditing(AuditEnum.AUDIT.getId());
//      outOrder.setAuditor(LoginHelper.getLoginUser().getNickname());
//      outOrder.setAuditDate(new Date());
      outOrder.setConsignorNameSale(inOrder.getConsignorName());
      outOrder.setConsignorIdSale(inOrder.getConsignorId());

      outOrderService.save(outOrder);

      OutOperationTypeEnum operationTypeEnum;
      if (rabbitReceiverDto.getRabbitmqType() == RabbitmqTypeEnum.IN_ORDER_TO_OUT_ORDER_CROSS) {
        operationTypeEnum = OutOperationTypeEnum.IN_ORDER_CROSS;
      } else {
        operationTypeEnum = OutOperationTypeEnum.IN_ORDER;
      }
      outOrderStatusHistoryService.AddHistory(outOrder, operationTypeEnum, OutOrderStatusEnum.AUDIT_WAITING);
      outOrder.setOrderStatus(OutOrderStatusEnum.AUDIT_WAITING.getName());
      //#endregion

      BigDecimal unqualifiedQty = Convert.toBigDecimal(rabbitReceiverDto.getOtherField().get("unqualifiedQty"));

      //#region 创建出库单 明细
      for (var saleDetail : saleOrderDetailList) {
        OutOrderDetail outOrderDetail = new OutOrderDetail();
        BeanUtil.copyProperties(saleDetail, outOrderDetail);
        outOrderDetail.setOrderDetailId(null);
        outOrderDetail.setOrderId(outOrder.getOrderId());
        outOrderDetail.setSourceDetailId("" + saleDetail.getOrderDetailId());
        outOrderDetail.setSourceMainId("" + saleDetail.getOrderId());
        outOrderDetail.setQuantityOrder(B.sub(saleDetail.getEnterQuantity(), unqualifiedQty)); // 减去不合格数量
        outOrderDetail.setSourceType(OutSourceTypeEnum.IN_ORDER.getName());
        outOrderDetail.setSortingStatus(SortingStatusEnum.NONE.getId());
        outOrderDetail.setConsignorNameSale(inOrder.getConsignorName());
        outOrderDetail.setConsignorIdSale(inOrder.getConsignorId());
        outOrderDetailService.save(outOrderDetail);

        // 分拣规则加上项目
        sortingRuleInfo(outOrder, outOrderDetail);

        outOrder.setTotalQuantityOrder(B.add(outOrder.getTotalQuantityOrder(), outOrderDetail.getQuantityOrder()));
        outOrder.setTotalWeight(B.add(outOrder.getTotalWeight(), outOrderDetail.getRowWeight()));
        outOrder.setTotalCube(B.add(outOrder.getTotalCube(), outOrderDetail.getRowCube()));
        outOrder.setTotalNetWeight(B.add(outOrder.getTotalNetWeight(), outOrderDetail.getRowNetWeight()));
      }
      outOrderService.updateById(outOrder);
      //#endregion

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务

      // 下一步消息队列执行，记录当前业务单号
      this.initNextStep(rabbitReceiverDto, outOrder.getOrderId(), outOrder.getOrderCode());
      return R.ok(rabbitReceiverDto);
      //#endregion
    } catch (Exception e) {
      // 更新任务状态为失败
      transactionManager.rollback(transaction); // 手动回滚事务
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
      return R.fail();
    }
  }
  //#endregion

  /**
   * 分拣规则
   *
   * @param outOrder
   * @param outOrderDetail
   */
  private void sortingRuleInfo(OutOrder outOrder, OutOrderDetail outOrderDetail) {
    OutSortingRule outSortingRule = new OutSortingRule();
    outSortingRule.setConsignorId(outOrder.getConsignorId());
    outSortingRule.setConsignorCode(outOrder.getConsignorCode());
    outSortingRule.setConsignorName(outOrder.getConsignorName());
    outSortingRule.setCreateTime(new Date());
    outSortingRule.setOrderId(outOrder.getOrderId());
    outSortingRule.setOrderDetailId(outOrderDetail.getOrderDetailId());
    outSortingRule.setOrderCode(outOrder.getOrderCode());
    outSortingRule.setProductId(outOrderDetail.getProductId());
    outSortingRule.setProductCode(outOrderDetail.getProductCode());
    outSortingRule.setStorageId(outOrder.getStorageId());
    outSortingRule.setStorageName(outOrder.getStorageName());
    if (ObjectUtil.isNotEmpty(outOrderDetail.getPlateCode())) {
      outSortingRule.setPlateCode(outOrderDetail.getPlateCode());
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getProduceDate())) {
      outSortingRule.setProduceDate(Convert.toDate(outOrderDetail.getProduceDate()));
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getPositionName())) {
      outSortingRule.setPositionName(outOrderDetail.getPositionName());
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getBatchNumber())) {
      outSortingRule.setBatchNumber(outOrderDetail.getBatchNumber());
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getProductCode())) {
      outSortingRule.setProductCode(outOrderDetail.getProductCode());
    }
    if (ObjectUtil.isNotEmpty(outOrderDetail.getProjectCode())) {
      outSortingRule.setProjectCode(outOrderDetail.getProjectCode());
    }
    outSortingRuleService.save(outSortingRule);
  }
}
