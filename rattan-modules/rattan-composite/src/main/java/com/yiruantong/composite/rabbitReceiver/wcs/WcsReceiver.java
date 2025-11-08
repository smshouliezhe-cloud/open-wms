package com.yiruantong.composite.rabbitReceiver.wcs;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.system.*;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.inbound.domain.in.*;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutPackageDetailService;
import com.yiruantong.outbound.service.out.IOutPackageService;
import com.yiruantong.system.domain.task.WcsTask;
import com.yiruantong.system.service.task.ITaskQueueService;
import com.yiruantong.system.service.task.IWcsTaskService;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RLock;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * WCS任务生成接收器
 */
@RequiredArgsConstructor
@Service
public class WcsReceiver implements IRabbitReceiver {

  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IInEnterService inEnterService;
  private final IInEnterDetailService inEnterDetailService;
  private final IInOrderService inOrderService;
  private final IWcsTaskService wcsTaskService;
  private final IBaseStorageService baseStorageService;
  private final IInShelveService inShelveService;
  private final IInShelveDetailService inShelveDetailService;
  private final IOutOrderService outOrderService;
  private final IOutPackageService outPackageService;
  private final IOutPackageDetailService outPackageDetailService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;

  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_FINISHED_TO_WCS, RabbitmqTypeEnum.IN_SHELVED_TO_WCS, RabbitmqTypeEnum.OUT_FINISHED_TO_WCS); // 接收哪几种类型的数据
  }

  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {// 手动开启事务  start
    if (taskQueueService.checkTaskFinished(rabbitReceiverDto.getTaskId())) {
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED, MessageUtils.message("rabbitmq.no.longer.executing"));
      return R.fail(MessageUtils.message("rabbitmq.no.longer.executing")); // 已经执行成功，不再执行
    }

    RLock lock = RedisUtils.getClient().getLock(LockNameEnum.RABBIT_RECEIVER_IN_FINISHED_TO_WCS.getName());
    try {
      lock.lock(20, TimeUnit.SECONDS);
      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
      definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus transaction = transactionManager.getTransaction(definition);
      try {
        if (rabbitReceiverDto.getRabbitmqType() == RabbitmqTypeEnum.IN_FINISHED_TO_WCS) {
          //#region 直接上架
          InEnter enterInfo = inEnterService.getById(rabbitReceiverDto.getBillId());
          InOrder orderInfo = inOrderService.getById(Convert.toLong(rabbitReceiverDto.getSourceId()));
          //#region 订单校验
          if (ObjectUtil.isNull(enterInfo)) {
            taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到入库单");
            transactionManager.commit(transaction); // 手动提交事务
            return R.fail();
          }
          if (ObjectUtil.isNull(orderInfo)) {
            taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到预到货单");
            transactionManager.commit(transaction); // 手动提交事务
            return R.fail();
          }
          //#endregion

          List<InEnterDetail> inEnterDetailList = inEnterDetailService.selectListByMainId(enterInfo.getEnterId());
          for (var enterDetail : inEnterDetailList) {
            // 生成WCS任务单
            WcsTask wcsTask = getWcsTask(enterDetail, enterInfo);
            wcsTaskService.save(wcsTask);
          }
          //#endregion
        } else if (rabbitReceiverDto.getRabbitmqType() == RabbitmqTypeEnum.IN_SHELVED_TO_WCS) {
          //#region 扫描上架
          InShelve inShelve = inShelveService.getById(rabbitReceiverDto.getBillId());
          InOrder orderInfo = inOrderService.getById(Convert.toLong(rabbitReceiverDto.getSourceId()));
          //#region 订单校验
          if (ObjectUtil.isNull(inShelve)) {
            taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到上架单");
            transactionManager.commit(transaction); // 手动提交事务
            return R.fail();
          }
          if (ObjectUtil.isNull(orderInfo)) {
            taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到预到货单");
            transactionManager.commit(transaction); // 手动提交事务
            return R.fail();
          }
          //#endregion

          List<InShelveDetail> inShelveDetailList = inShelveDetailService.selectListByMainId(rabbitReceiverDto.getBillId());
          for (var shelveDetail : inShelveDetailList) {
            // 生成WCS任务单
            WcsTask wcsTask = getWcsTask(shelveDetail, inShelve);
            wcsTaskService.save(wcsTask);
          }
          //#endregion
        } else if (rabbitReceiverDto.getRabbitmqType() == RabbitmqTypeEnum.OUT_FINISHED_TO_WCS) {
          //#region 扫描出库
          OutPackage outPackage = outPackageService.getById(rabbitReceiverDto.getBillId());
          OutOrder orderInfo = outOrderService.getById(Convert.toLong(rabbitReceiverDto.getSourceId()));
          //#region 订单校验
          if (ObjectUtil.isNull(outPackage)) {
            taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到打包单");
            transactionManager.commit(transaction); // 手动提交事务
            return R.fail();
          }
          if (ObjectUtil.isNull(orderInfo)) {
            taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "未找到出库单");
            transactionManager.commit(transaction); // 手动提交事务
            return R.fail();
          }
          //#endregion

          List<OutPackageDetail> packageDetailList = outPackageDetailService.selectListByMainId(rabbitReceiverDto.getBillId());
          for (var packageDetail : packageDetailList) {
            // 生成WCS任务单
            WcsTask wcsTask = getWcsTask(packageDetail, outPackage);
            wcsTaskService.save(wcsTask);
          }
          //#endregion
        }

        // 更新任务状态为完成
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
        transactionManager.commit(transaction); // 手动提交事务
      } catch (Exception e) {
        // 更新任务状态为失败
        transactionManager.rollback(transaction); // 手动回滚事务
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
      }
    } finally {
      lock.unlock();
    }
    return R.ok();
  }

  @NotNull
  private WcsTask getWcsTask(InEnterDetail enterDetail, InEnter enterInfo) {
    WcsTask wcsTask = new WcsTask();
    wcsTask.setWcsTaskType(WcsTaskTypeEnum.IN_ENTER.getName());
    wcsTask.setMainId(enterDetail.getEnterId());
    wcsTask.setDetailId(enterDetail.getEnterDetailId());

    wcsTask.setBillCode(enterInfo.getEnterCode());
    wcsTask.setSourceCode(enterInfo.getOrderCode());
    wcsTask.setSourceId(enterInfo.getOrderId());
    wcsTask.setStorageId(enterInfo.getStorageId());
    wcsTask.setStorageName(enterInfo.getStorageName());

    // 获取仓库编号
    BaseStorage baseStorage = baseStorageService.getById(enterInfo.getStorageId());
    if (ObjectUtil.isNotNull(baseStorage)) {
      wcsTask.setStorageCode(baseStorage.getStorageCode());
    }

    wcsTask.setProductId(enterDetail.getProductId());
    wcsTask.setProductCode(enterDetail.getProductCode());
    wcsTask.setProductName(enterDetail.getProductName());
    wcsTask.setProductSpec(enterDetail.getProductSpec());
    wcsTask.setBatchNumber(enterDetail.getBatchNumber());
    wcsTask.setProduceDate(enterDetail.getProduceDate());
    wcsTask.setProductModel(enterDetail.getProductModel());
    wcsTask.setPositionName(enterDetail.getPositionName());
    wcsTask.setSingleSignCode(enterDetail.getSingleSignCode());

    wcsTask.setPushStatus(PushStatusEnum.WAIT.getName());
    wcsTask.setQty(enterDetail.getShelvedQuantity());
    wcsTask.setPlateCode(enterDetail.getPlateCode());
    return wcsTask;
  }

  @NotNull
  private WcsTask getWcsTask(InShelveDetail shelveDetail, InShelve inShelve) {
    WcsTask wcsTask = new WcsTask();
    wcsTask.setWcsTaskType(WcsTaskTypeEnum.IN_ENTER.getName());
    wcsTask.setMainId(shelveDetail.getEnterId());
    wcsTask.setDetailId(shelveDetail.getEnterDetailId());

    wcsTask.setBillCode(inShelve.getEnterCode());
    wcsTask.setSourceCode(inShelve.getOrderCode());
    wcsTask.setSourceId(inShelve.getOrderId());
    wcsTask.setStorageId(inShelve.getStorageId());
    wcsTask.setStorageName(inShelve.getStorageName());

    // 获取仓库编号
    BaseStorage baseStorage = baseStorageService.getById(inShelve.getStorageId());
    if (ObjectUtil.isNotNull(baseStorage)) {
      wcsTask.setStorageCode(baseStorage.getStorageCode());
    }

    wcsTask.setProductId(shelveDetail.getProductId());
    wcsTask.setProductCode(shelveDetail.getProductCode());
    wcsTask.setProductName(shelveDetail.getProductName());
    wcsTask.setProductSpec(shelveDetail.getProductSpec());
    wcsTask.setBatchNumber(shelveDetail.getBatchNumber());
    wcsTask.setProduceDate(shelveDetail.getProduceDate());
    wcsTask.setProductModel(shelveDetail.getProductModel());
    wcsTask.setPositionName(shelveDetail.getPositionName());

    wcsTask.setSingleSignCode(shelveDetail.getSingleSignCode());
    wcsTask.setPushStatus(PushStatusEnum.WAIT.getName());
    wcsTask.setQty(shelveDetail.getShelvedQuantity());
    wcsTask.setPlateCode(shelveDetail.getPlateCode());
    return wcsTask;
  }

  /**
   * 打包单转入WCS任务单
   *
   * @param outPackageDetail
   * @param outPackage
   * @return
   */
  @NotNull
  private WcsTask getWcsTask(OutPackageDetail outPackageDetail, OutPackage outPackage) {
    // 获取占位信息
    List<CoreInventoryHolder> coreInventoryHolderList = coreInventoryHolderService.selectHolderList(outPackageDetail.getOrderId(), outPackageDetail.getOrderDetailId(), outPackage.getOrderCode());
    Assert.isFalse(coreInventoryHolderList.isEmpty(), outPackage.getOrderCode() + "未找到占位信息！");

    WcsTask wcsTask = new WcsTask();
    wcsTask.setWcsTaskType(WcsTaskTypeEnum.OUT_PACKAGE.getName());
    wcsTask.setMainId(outPackageDetail.getPackageId());
    wcsTask.setDetailId(outPackageDetail.getPackageDetailId());

    wcsTask.setBillCode(outPackage.getPackageCode());
    wcsTask.setSourceCode(outPackage.getOrderCode());
    wcsTask.setSourceId(outPackage.getOrderId());
    wcsTask.setStorageId(outPackage.getStorageId());
    wcsTask.setStorageName(outPackage.getStorageName());

    // 获取仓库编号
    BaseStorage baseStorage = baseStorageService.getById(outPackage.getStorageId());
    if (ObjectUtil.isNotNull(baseStorage)) {
      wcsTask.setStorageCode(baseStorage.getStorageCode());
    }
    OutOrderDetail outOrderDetail = outOrderDetailService.getById(outPackageDetail.getOrderDetailId());

    if(ObjectUtil.isNotNull(outOrderDetail)){
      wcsTask.setPlateCode(outOrderDetail.getPlateCode());
    }else{
      wcsTask.setPlateCode(coreInventoryHolderList.get(0).getPlateCode());
    }
    wcsTask.setProductId(outPackageDetail.getProductId());
    wcsTask.setProductCode(outPackageDetail.getProductCode());
    wcsTask.setProductName(outPackageDetail.getProductName());
    wcsTask.setProductSpec(outPackageDetail.getProductSpec());
    wcsTask.setBatchNumber(outPackageDetail.getBatchNumber());
    wcsTask.setProduceDate(outPackageDetail.getProduceDate());
    wcsTask.setProductModel(outPackageDetail.getProductModel());
    wcsTask.setSingleSignCode(outPackageDetail.getSingleSignCode());
    wcsTask.setPositionName(coreInventoryHolderList.get(0).getPositionName());

    wcsTask.setPushStatus(PushStatusEnum.WAIT.getName());
    wcsTask.setQty(outPackageDetail.getPackageQuantity());

    return wcsTask;
  }
}
