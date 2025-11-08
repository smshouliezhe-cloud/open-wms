package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.inventory.StorageAdjustStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageValidAdjustStatusEnum;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.operation.StorageValidAdjust;
import com.yiruantong.inventory.domain.operation.StorageValidAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageValidAdjustBo;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustVo;
import com.yiruantong.inventory.mapper.operation.StorageValidAdjustMapper;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStorageValidAdjustDetailService;
import com.yiruantong.inventory.service.operation.IStorageValidAdjustService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 效期信息调整Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageValidAdjustServiceImpl extends ServiceImplPlus<StorageValidAdjustMapper, StorageValidAdjust, StorageValidAdjustVo, StorageValidAdjustBo> implements IStorageValidAdjustService {
  private final IStorageValidAdjustDetailService storageValidAdjustDetailService;
  private final ICoreInventoryService coreInventoryService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      StorageValidAdjust mainInfo = this.getById(id);
      Assert.isFalse(ObjectUtil.equal(mainInfo.getAdjustStatus(), StorageAdjustStatusEnum.FINISHED.getName()),
        mainInfo.getValidAdjustCode() + "出库完成的，不允许重复操作");
      List<StorageValidAdjustDetail> detailList = storageValidAdjustDetailService.selectListByMainId(id);
      for (var detail : detailList) {
        Assert.isFalse(!ObjectUtil.isNotNull(detail.getBatchNumberTarget()) && !ObjectUtil.isNotNull(detail.getProduceDateTarget()),
          mainInfo.getValidAdjustCode() + "明细中目标生产日期,目标批次号不能同时为空");
      }

      for (var detail : detailList) {
        LambdaUpdateWrapper<CoreInventory> inventoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        inventoryLambdaUpdateWrapper
          .set(ObjectUtil.isNotNull(detail.getProduceDateTarget()), CoreInventory::getProduceDate, detail.getProduceDateTarget())
          .set(CoreInventory::getLimitDate, detail.getLimitDateTarget())
          .set(CoreInventory::getShelfLifeDay, detail.getShelfLifeDay())
          .set(ObjectUtil.isNotNull(detail.getBatchNumberTarget()), CoreInventory::getBatchNumber, detail.getBatchNumberTarget())
          .eq(CoreInventory::getStorageId, mainInfo.getStorageId())
          .eq(CoreInventory::getConsignorId, mainInfo.getConsignorId())
          .eq(CoreInventory::getProductId, detail.getProductId())
          .eq(CoreInventory::getPositionName, detail.getPositionName())
          .eq(CoreInventory::getInventoryId, detail.getInventoryId())
          .isNull(ObjectUtil.isEmpty(detail.getProduceDate()), CoreInventory::getProduceDate)
          .eq(ObjectUtil.isNotEmpty(detail.getProduceDate()), CoreInventory::getProduceDate, detail.getProduceDate());
        coreInventoryService.update(inventoryLambdaUpdateWrapper);
      }

      //#region 更新状态
      StorageValidAdjust updateStorageOuter = new StorageValidAdjust();
      updateStorageOuter.setAuditor(LoginHelper.getNickname());
      updateStorageOuter.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      updateStorageOuter.setAuditDate(DateUtil.date());
      updateStorageOuter.setAdjustStatus(StorageValidAdjustStatusEnum.FINISHED.getName());
      updateStorageOuter.setValidAdjustId(mainInfo.getValidAdjustId()); // 设置主键

      this.updateById(updateStorageOuter);
      //#endregion
    }
    return R.ok("调整完成");
  }
}
