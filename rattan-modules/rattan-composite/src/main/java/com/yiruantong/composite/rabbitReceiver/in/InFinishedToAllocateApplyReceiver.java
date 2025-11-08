package com.yiruantong.composite.rabbitReceiver.in;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyActionEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyDetailService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyStatusHistoryService;
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
import java.util.List;

@RequiredArgsConstructor
@Service
public class InFinishedToAllocateApplyReceiver implements IRabbitReceiver {
  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IStorageAllocateApplyStatusHistoryService storageAllocateApplyStatusHistoryService;
  private final IStorageAllocateApplyService storageAllocateApplyService;
  private final IInOrderService inOrderService;
  private final IStorageAllocateApplyDetailService storageAllocateApplyDetailService;
  private final IInOrderDetailService inOrderDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutSortingRuleService outSortingRuleService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_FINISHED_TO_ALLOCATE_APPLY); // 接收哪几种类型的数据
  }

  //#region 实现MQ 方法

  /**
   * 1、更新调拨申请单的状态、状态轨迹，以及明细入库数量；
   * 2、生成在途虚拟仓的出库订单，并自动分配上该调拨单对应的调拨在途库存，然后自动出库完成
   *
   * @return 结果
   */
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
      //#region  获取数据并且校验数据
      InOrder inOrderInfo = inOrderService.getByCode(rabbitReceiverDto.getBillCode());
      Assert.isTrue(ObjectUtil.isNotEmpty(inOrderInfo), "未找到对应的预到货单！");

      //查询当前单据对应的  调拨在途虚拟入库 并自动出库
      LambdaQueryWrapper<InOrder> inOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      inOrderLambdaQueryWrapper.eq(InOrder::getSourceType, StorageAllocateApplyStatusEnum.ALLOCATE_ROUTE_VIRTUAL_IN.getName())
        .eq(InOrder::getSourceCode, inOrderInfo.getSourceCode())
        .eq(InOrder::getOrderType, inOrderInfo.getOrderType());
      InOrder inOrderVirtual = inOrderService.getOne(inOrderLambdaQueryWrapper);

      //获取数据并且校验数据
      LambdaQueryWrapper<StorageAllocateApply> storageAllocateApplyLqw = new LambdaQueryWrapper<>();

      storageAllocateApplyLqw.eq(StorageAllocateApply::getAllocateApplyId, inOrderVirtual.getSourceId())
        .eq(StorageAllocateApply::getAllocateApplyCode, inOrderVirtual.getSourceCode());
      StorageAllocateApply allocateApplyInfo = storageAllocateApplyService.getOne(storageAllocateApplyLqw);
      if (ObjectUtil.isNull(allocateApplyInfo)) {
        transactionManager.commit(transaction); // 手动回滚事务
        R.ok("未找到对应的调拨单！");
      }
      if (StrUtil.equals(allocateApplyInfo.getApplyStatus(), StorageAllocateApplyStatusEnum.ALLOCATE_FINISHED.getName())) {
        transactionManager.commit(transaction); // 手动回滚事务
        R.ok("调拨单已经完成不允许再完成！");
      }
      //#endregion

      // 1、更新调拨申请单的状态、状态轨迹，以及明细入库数量；
      //#region 调拨单状态更新 以及明细数据回写
      StorageAllocateApplyStatusEnum fromStatus = StorageAllocateApplyStatusEnum.matchingEnum(allocateApplyInfo.getApplyStatus());
      //默认调拨完成
      StorageAllocateApplyStatusEnum toStatus = StorageAllocateApplyStatusEnum.ALLOCATE_FINISHED;

      //调拨单添加轨迹
      storageAllocateApplyStatusHistoryService.addHistoryInfo(allocateApplyInfo, StorageAllocateApplyActionEnum.PC_SCAN_IN, fromStatus, toStatus);
      //修改调拨单状态
      allocateApplyInfo.setApplyStatus(toStatus.getName());
      storageAllocateApplyService.updateById(allocateApplyInfo);
      List<InOrderDetail> orderDetails = inOrderDetailService.selectListByMainId(inOrderVirtual.getOrderId());
      for (InOrderDetail detailInfo : orderDetails) {
        //更新出库数量
        LambdaUpdateWrapper<StorageAllocateApplyDetail> storageAllocateApplyDetailLuq = new LambdaUpdateWrapper<>();
        storageAllocateApplyDetailLuq.setSql("in_quantity =in_quantity +" + detailInfo.getEnterQuantity())
          .eq(StorageAllocateApplyDetail::getAllocateApplyDetailId, detailInfo.getSourceDetailId())
          .eq(StorageAllocateApplyDetail::getAllocateApplyId, detailInfo.getSourceMainId());
        storageAllocateApplyDetailService.update(storageAllocateApplyDetailLuq);
      }
      //#endregion

////      只有完全入库才往下走流程
//      if (!StrUtil.equals(inOrderVirtual.getOrderStatus(), InOrderStatusEnum.FINISHED.getName())) {
//
//        transactionManager.rollback(transaction); // 手动回滚事务
//        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "只有完全入库才生成虚拟出库单");
//        return R.ok("只有完全入库才生成虚拟出库单");
//      }
      //2、生成在途虚拟仓的出库订单，并自动分配上该调拨单对应的调拨在途库存，然后自动出库完成

      //#region 生成出库单，自动出库
      OutOrder outOrderInfo = BeanUtil.copyProperties(inOrderVirtual, OutOrder.class);
      BeanUtil.copyProperties(allocateApplyInfo, outOrderInfo, CopyOptions.create().setIgnoreNullValue(true));
      outOrderInfo.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671, LoginHelper.getLoginUser().getTenantId()));
      outOrderInfo.setOrderStatus(OutOrderStatusEnum.AUDIT_SUCCESS.getName());
      outOrderInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
      outOrderInfo.setTotalQuantityOrder(inOrderVirtual.getTotalQuantity());
      outOrderInfo.setTaxAmount(inOrderVirtual.getTotalRateAmount());
      outOrderInfo.setTotalAmount(inOrderVirtual.getTotalAmount());
      outOrderInfo.setTotalCube(BigDecimal.ZERO);
      outOrderInfo.setBigQtyTotal(BigDecimal.ZERO);
      outOrderInfo.setTotalNetWeight(BigDecimal.ZERO);
      outOrderInfo.setSourceType(StorageAllocateApplyStatusEnum.ALLOCATE_ROUTE_VIRTUAL_OUT.getName());
      outOrderInfo.setAuditing((byte) 2);
      outOrderInfo.setAuditor(LoginHelper.getLoginUser().getNickname());
      outOrderInfo.setAuditDate(DateUtil.date());

      outOrderInfo.setStorageId(inOrderVirtual.getStorageId());
      outOrderInfo.setStorageName(inOrderVirtual.getStorageName());
      outOrderInfo.setConsignorName(inOrderVirtual.getConsignorName());
      outOrderInfo.setConsignorCode(inOrderVirtual.getConsignorCode());
      outOrderInfo.setConsignorId(inOrderVirtual.getConsignorId());
      outOrderInfo.setOrderId(null);
      outOrderService.save(outOrderInfo);

      for (InOrderDetail detailInfo : orderDetails) {
        OutOrderDetail outOrderDetail = BeanUtil.copyProperties(detailInfo, OutOrderDetail.class);
        outOrderDetail.setQuantityOrder(detailInfo.getEnterQuantity());
        outOrderDetail.setSalePrice(detailInfo.getPurchasePrice());
        outOrderDetail.setSaleAmount(detailInfo.getPurchaseAmount());
        outOrderDetail.setOrderId(outOrderInfo.getOrderId());
        outOrderDetail.setSourceDetailId("" + detailInfo.getOrderDetailId());
        outOrderDetail.setSourceMainId("" + detailInfo.getOrderId());
        outOrderDetail.setSourceType(StorageAllocateApplyStatusEnum.ALLOCATE_ROUTE_VIRTUAL_OUT.getName());
        outOrderDetail.setOrderDetailId(null);
        outOrderDetailService.save(outOrderDetail);

        //查询库存Id
        LambdaQueryWrapper<CoreInventory> coreInventoryLqw = new LambdaQueryWrapper<>();
        coreInventoryLqw.select(CoreInventory::getInventoryId)
          .eq(CoreInventory::getSourceCode, allocateApplyInfo.getAllocateApplyCode())
          .eq(CoreInventory::getStorageStatus, InventoryStatusEnum.ALLOCATE_ROUTE.getName())
          .eq(CoreInventory::getProductId, detailInfo.getProductId())
          .eq(CoreInventory::getStorageId, inOrderVirtual.getStorageId())
          .eq(CoreInventory::getValidStorage, detailInfo.getQuantity())
          .last("limit 1");
        CoreInventory info = coreInventoryService.getOne(coreInventoryLqw);
        if (ObjectUtil.isNull(info)) {
          transactionManager.rollback(transaction); // 手动回滚事务
          // 更新任务状态为失败

          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到对应的库存");

          return R.ok();
        }
        OutSortingRule outSortingRule = new OutSortingRule();
        outSortingRule.setInventoryId(info.getInventoryId());
        outSortingRule.setOrderId(outOrderInfo.getOrderId());
        outSortingRule.setOrderDetailId(outOrderDetail.getOrderDetailId());
        outSortingRule.setProductId(outOrderDetail.getProductId());
        outSortingRule.setProductCode(outOrderDetail.getProductCode());
        outSortingRuleService.save(outSortingRule);
        //合计
        outOrderInfo.setTotalCube(B.add(outOrderInfo.getTotalCube(), outOrderDetail.getRowCube()));
        outOrderInfo.setBigQtyTotal(B.add(outOrderInfo.getTotalCube(), outOrderDetail.getRowCube()));
        outOrderInfo.setTotalNetWeight(B.add(outOrderInfo.getTotalNetWeight(), outOrderDetail.getBigQty()));
      }
      outOrderService.updateById(outOrderInfo);
      //添加轨迹信息
      outOrderStatusHistoryService.AddHistory(outOrderInfo, OutOperationTypeEnum.PC_ALLOCATE_APPLY_PLAN, OutOrderStatusEnum.AUDIT_SUCCESS);

      //#endregion
      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务

      //调用RabbitMQ 执行分拣动作
      RabbitReceiverDto receiverDto = new RabbitReceiverDto();
      receiverDto.setRabbitmqType(RabbitmqTypeEnum.VIRTUAL_ORDER_SORTING); //类别
      receiverDto.setBillId(outOrderInfo.getOrderId());
      receiverDto.setBillCode(outOrderInfo.getOrderCode());
      receiverDto.setSourceCode(outOrderInfo.getSourceCode());
      receiverDto.setSourceId(outOrderInfo.getSourceId());
      taskQueueService.createTask(receiverDto);
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
