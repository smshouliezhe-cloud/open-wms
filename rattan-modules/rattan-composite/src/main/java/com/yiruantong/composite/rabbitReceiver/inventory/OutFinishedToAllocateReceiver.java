package com.yiruantong.composite.rabbitReceiver.inventory;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyActionEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
import com.yiruantong.inbound.service.in.IInScanOrderService;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyDetailService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyService;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyStatusHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 调拨单转到出库单，出库单出库后续操作
 * 1、更新调拨申请单的状态、状态轨迹，以及明细出库数量；
 * 2、生成预到货单；
 * 3、生成在途虚拟仓的预到货单，并自动入库完成（库存状态为“调拨在途”）
 */
@RequiredArgsConstructor
@Service
public class OutFinishedToAllocateReceiver implements IRabbitReceiver {
  private final DataSourceTransactionManager transactionManager;
  private final IStorageAllocateApplyStatusHistoryService storageAllocateApplyStatusHistoryService;
  private final IStorageAllocateApplyDetailService storageAllocateApplyDetailService;
  private final IStorageAllocateApplyService storageAllocateApplyService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final ICoreInventoryHolderService coreInventoryHolderService;

  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IBaseProductService baseProductService;
  private final IInScanOrderService inScanOrderService;
  private final ITaskQueueService taskQueueService;
  private final IBasePositionService basePositionService;


  /**
   * 标注接收的数据类型
   *
   * @return
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_FINISHED_TO_ALLOCATE); // 接收哪几种类型的数据
  }

  /**
   * 调拨单转到出库单，出库单出库后续操作
   * 1、更新调拨申请单的状态、状态轨迹，以及明细出库数量；
   * 2、生成预到货单；
   * 3、生成在途虚拟仓的预到货单，并自动入库完成（库存状态为“调拨在途”）
   *
   * @param rabbitReceiverDto 数据信息
   * @return 返回R
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
      System.out.println("接收的数据：" + JsonUtils.toJsonString(rabbitReceiverDto));
      String orderCode = rabbitReceiverDto.getBillCode();

      //#region  获取数据并且校验数据
      OutOrder outOrderInfo = outOrderService.getByCode(orderCode);
      if (ObjectUtil.isNull(outOrderInfo)) {
        transactionManager.commit(transaction); // 手动回滚事务
        R.ok("未找到对应的出库单！");
      }
      //如果不是调拨单传入 则返回
      if (!StrUtil.equals(outOrderInfo.getSourceType(), StorageAllocateApplyStatusEnum.ROUTINE_ALLOCATE_OUT.getName())) {
        transactionManager.commit(transaction); // 手动提交事务
        return R.ok();
      }
      //获取数据并且校验数据
      LambdaQueryWrapper<StorageAllocateApply> storageAllocateApplyLqw = new LambdaQueryWrapper<>();
      storageAllocateApplyLqw.eq(StorageAllocateApply::getAllocateApplyId, outOrderInfo.getSourceId())
        .eq(StorageAllocateApply::getAllocateApplyCode, outOrderInfo.getSourceCode());

      StorageAllocateApply allocateApplyInfo = storageAllocateApplyService.getOne(storageAllocateApplyLqw);
      if (ObjectUtil.isNull(allocateApplyInfo)) {
        transactionManager.commit(transaction); // 手动回滚事务
        R.ok("未找到对应的调拨单！");
      }
      if (StrUtil.equals(allocateApplyInfo.getApplyStatus(), StorageAllocateApplyStatusEnum.OUT_FINISHED.getName())) {
        transactionManager.commit(transaction); // 手动回滚事务
        R.ok("调拨单已经完成不允许再完成！");
      }
      //#endregion

      // 1、更新调拨申请单的状态、状态轨迹，以及明细的出库数量；
      //#region  更新调拨单明细回写
      List<OutOrderDetail> orderDetails = outOrderDetailService.selectListByMainId(outOrderInfo.getOrderId());
      for (OutOrderDetail detailInfo : orderDetails) {
        //更新出库数量
        LambdaUpdateWrapper<StorageAllocateApplyDetail> storageAllocateApplyDetailLuq = new LambdaUpdateWrapper<>();
        storageAllocateApplyDetailLuq.set(StorageAllocateApplyDetail::getOutQuantity, detailInfo.getQuantityOuted())
          .eq(StorageAllocateApplyDetail::getAllocateApplyDetailId, detailInfo.getSourceDetailId())
          .eq(StorageAllocateApplyDetail::getAllocateApplyId, detailInfo.getSourceMainId());
        storageAllocateApplyDetailService.update(storageAllocateApplyDetailLuq);
      }
      //#endregion
      //#region 调拨单状态更新
      StorageAllocateApplyStatusEnum fromStatus = StorageAllocateApplyStatusEnum.matchingEnum(allocateApplyInfo.getApplyStatus());
      //出库完成
      StorageAllocateApplyStatusEnum toStatus = StorageAllocateApplyStatusEnum.OUT_FINISHED;
      //调拨单添加轨迹
      storageAllocateApplyStatusHistoryService.addHistoryInfo(allocateApplyInfo, StorageAllocateApplyActionEnum.PC_SCAN_OUT, fromStatus, toStatus);
      //修改调拨单状态
      allocateApplyInfo.setApplyStatus(toStatus.getName());
      storageAllocateApplyService.updateById(allocateApplyInfo);
      //#endregion

//      //出库单只有全部打包完成 才执行以下操作
//      if (StrUtil.equals(outOrderInfo.getOrderStatus(), OutOrderStatusEnum.PACKAGE_FINISHED.getName()) ) {
//        transactionManager.rollback(transaction); // 手动回滚事务
//        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL,"出库单只有全部打包完成");
//        return R.ok();
//      }

      //2、生成预到货单信息
      //#region  2、生成预到货单信息
      InOrder inOrderInfo = new InOrder();
      inOrderInfo.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1001, LoginHelper.getLoginUser().getTenantId()));
      // 对象数据的拷贝
      BeanUtil.copyProperties(allocateApplyInfo, inOrderInfo);

      inOrderInfo.setStorageName(allocateApplyInfo.getStorageNameIn());
      inOrderInfo.setStorageId(allocateApplyInfo.getStorageIdIn());
      inOrderInfo.setConsignorName(allocateApplyInfo.getConsignorNameTarget());
      inOrderInfo.setConsignorCode(allocateApplyInfo.getConsignorCodeTarget());
      inOrderInfo.setConsignorId(Convert.toLong(allocateApplyInfo.getConsignorIdTarget()));
      inOrderInfo.setSourceCode(allocateApplyInfo.getAllocateApplyCode());
      inOrderInfo.setSourceId("" + allocateApplyInfo.getAllocateApplyId());
      inOrderInfo.setSourceType(StorageAllocateApplyStatusEnum.ROUTINE_ALLOCATE_IN.getName());
      inOrderInfo.setOrderStatus(InOrderStatusEnum.SUCCESS.getName());
      inOrderInfo.setShelveStatus(InOrderStatusEnum.HISTORY_WAITING.getName());
      inOrderInfo.setStorageIdOut(allocateApplyInfo.getStorageId());
      inOrderInfo.setStorageNameOut(allocateApplyInfo.getStorageName());

      inOrderInfo.setConsignorIdOut(allocateApplyInfo.getConsignorId());
      inOrderInfo.setConsignorCodeOut(allocateApplyInfo.getConsignorCode());
      inOrderInfo.setConsignorNameOut(allocateApplyInfo.getConsignorName());
      inOrderInfo.setOrderId(null);
      inOrderService.save(inOrderInfo);

      //预到货单明细信息从 占位 赋值
      LambdaQueryWrapper<CoreInventoryHolder> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(CoreInventoryHolder::getBillCode, outOrderInfo.getOrderCode())
        .eq(CoreInventoryHolder::getMainId, outOrderInfo.getOrderId())
        .eq(CoreInventoryHolder::getSourceType, outOrderInfo.getOrderType());
      List<CoreInventoryHolder> holderList = coreInventoryHolderService.list(queryWrapper);

      BigDecimal totalQuantity = BigDecimal.ZERO;
      BigDecimal totalAmount = BigDecimal.ZERO;
      BigDecimal totalRateAmount = BigDecimal.ZERO;
      BigDecimal totalWeight = BigDecimal.ZERO;
      for (CoreInventoryHolder info : holderList) {
        BaseProduct baseProduct = baseProductService.getById(info.getProductId());
        InOrderDetail orderDetail = new InOrderDetail();
        OutOrderDetail outOrderDetail = outOrderDetailService.getById(info.getDetailId());

        BeanUtil.copyProperties(baseProduct, orderDetail);
        BeanUtil.copyProperties(info, orderDetail);
        orderDetail.setOrderId(inOrderInfo.getOrderId());
        orderDetail.setQuantity(info.getOrignHolderStorage());
        orderDetail.setRowWeightTon(B.div(orderDetail.getRowWeight(), BigDecimal.valueOf(1000)));
        orderDetail.setRowCube(B.mul(orderDetail.getUnitCube(), orderDetail.getQuantity()));
        orderDetail.setPurchaseAmount(B.mul(orderDetail.getPurchasePrice(), orderDetail.getQuantity()));
        orderDetail.setRateAmount(B.mul(orderDetail.getRatePrice(), orderDetail.getQuantity()));
        orderDetail.setBigQty(B.div(orderDetail.getQuantity(), orderDetail.getUnitConvert()));
        orderDetail.setPositionName(null);


        orderDetail.setSourceDetailId(outOrderDetail.getSourceDetailId());
        orderDetail.setSourceMainId(outOrderDetail.getSourceMainId());
        inOrderDetailService.save(orderDetail);
        inOrderInfo.setProviderShortName(info.getProviderShortName());
        inOrderInfo.setProviderCode(info.getProviderCode());
        inOrderInfo.setProviderId(info.getProviderId());

        totalQuantity = B.add(totalQuantity, orderDetail.getQuantity());
        totalAmount = B.add(totalAmount, orderDetail.getPurchaseAmount());
        totalRateAmount = B.add(totalRateAmount, orderDetail.getRateAmount());
        totalWeight = B.add(totalWeight, orderDetail.getRowNetWeight());
      }
      inOrderInfo.setTotalQuantity(totalQuantity);
      inOrderInfo.setTotalAmount(totalAmount);
      inOrderInfo.setTotalRateAmount(totalRateAmount);
      inOrderInfo.setTotalWeight(totalWeight);
      inOrderService.updateById(inOrderInfo);
      //添加轨迹信息
      inOrderStatusHistoryService.addHistoryInfo(inOrderInfo, InOrderActionEnum.PC_ALLOCATE_APPLY_PLAN, InOrderStatusEnum.SUCCESS);
      //#endregion

      //3、生成在途虚拟仓的预到货单，并自动入库完成（库存状态为“调拨在途”）
      //#region 3、生成在途虚拟仓的预到货单，并自动入库完成（库存状态为“调拨在途”）


      InOrder orderInfoRoute = new InOrder();
      // 对象数据的拷贝
      BeanUtil.copyProperties(inOrderInfo, orderInfoRoute);
      orderInfoRoute.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1001, LoginHelper.getLoginUser().getTenantId()));
      orderInfoRoute.setStorageName(allocateApplyInfo.getVirtualStorageName());
      orderInfoRoute.setStorageId(allocateApplyInfo.getVirtualStorageId());
      orderInfoRoute.setSourceType(StorageAllocateApplyStatusEnum.ALLOCATE_ROUTE_VIRTUAL_IN.getName());
      orderInfoRoute.setOrderId(null);

      inOrderService.save(orderInfoRoute);
      List<InOrderDetail> orderDetailList = inOrderDetailService.selectListByMainId(inOrderInfo.getOrderId());
      orderDetailList.forEach(item -> {
        item.setOrderId(orderInfoRoute.getOrderId());
        item.setOrderDetailId(null);
        item.setPositionName("在途货位");
      });
      inOrderDetailService.saveBatch(orderDetailList);

      inOrderStatusHistoryService.addHistoryInfo(orderInfoRoute, InOrderActionEnum.PC_ALLOCATE_APPLY_PLAN, InOrderStatusEnum.SUCCESS, "系统自动");
      // 重新查询预到货明细
      orderDetailList = inOrderDetailService.selectListByMainId(orderInfoRoute.getOrderId());
      //自动入库

      List<InScanOrderDetailBo> orderDetailBos = new ArrayList<>();
      orderDetailList.forEach(item -> {
        InScanOrderDetailBo orderDetail = new InScanOrderDetailBo();
        BeanUtil.copyProperties(item, orderDetail);
        orderDetail.setFinishedQuantity(item.getQuantity());
        orderDetailBos.add(orderDetail);
      });
      InScanOrderBo inScanOrderBo = new InScanOrderBo();
      inScanOrderBo.setOrderCode(orderInfoRoute.getOrderCode());
      inScanOrderBo.setOrderId(orderInfoRoute.getOrderId());
      inScanOrderBo.setDataList(orderDetailBos);
      inScanOrderBo.setScanInType(InventorySourceTypeEnum.PC_ALLOCATE_ROUTE_VIRTUAL_IN);
      inScanOrderService.normalScanSave(inScanOrderBo);
      //#endregion

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
  //endregion
}
