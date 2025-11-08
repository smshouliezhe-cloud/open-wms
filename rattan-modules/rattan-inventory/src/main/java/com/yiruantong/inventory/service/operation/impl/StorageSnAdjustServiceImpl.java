package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.inventory.StorageSnAdjustStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveDataDetailBo;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.domain.core.bo.CoreInventorySnBo;
import com.yiruantong.inventory.domain.operation.StorageSnAdjust;
import com.yiruantong.inventory.domain.operation.StorageSnAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageSnAdjustBo;
import com.yiruantong.inventory.domain.operation.vo.StorageSnAdjustVo;
import com.yiruantong.inventory.mapper.operation.StorageSnAdjustMapper;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import com.yiruantong.inventory.service.operation.IStorageSnAdjustDetailService;
import com.yiruantong.inventory.service.operation.IStorageSnAdjustService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SN调整Service业务层处理
 *
 * @author YRT
 * @date 2024-09-05
 */
@RequiredArgsConstructor
@Service
public class StorageSnAdjustServiceImpl extends ServiceImplPlus<StorageSnAdjustMapper, StorageSnAdjust, StorageSnAdjustVo, StorageSnAdjustBo> implements IStorageSnAdjustService {
  private final IStorageSnAdjustDetailService storageSnAdjustDetailService;
  private final ICoreInventorySnService coreInventorySnService;

  @Override
  public void beforeSaveEditor(SaveEditorBo<StorageSnAdjustBo> saveEditorBo) {
    SaveDataDetailBo saveDataDetailBo = saveEditorBo.getData().getDetailList().get(0);
    for (var detail : saveDataDetailBo.getRows()) {
      Long storageId = Convert.toLong(detail.get("storageId"));
      byte enableTarget = Convert.toByte(detail.get("enableTarget"));
      String snNoTarget = Convert.toStr(detail.get("snNoTarget"));

      // 在可用的时候才需要做校验目标SN已经存在
      if (B.isEqual(enableTarget, EnableEnum.ENABLE.getId())) {
        List<CoreInventorySn> validSnList = coreInventorySnService.getValidSnList(storageId, snNoTarget);
        Assert.isFalse(!validSnList.isEmpty(), "SN[" + snNoTarget + "]已存在不允许重复输入！");
      }
    }
    super.beforeSaveEditor(saveEditorBo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      StorageSnAdjust storageSnAdjust = this.getById(id);
      Assert.isFalse(ObjectUtil.isNull(storageSnAdjust), "调整单不存在！");
      Assert.isFalse(B.isEqual(storageSnAdjust.getAuditing(), AuditEnum.AUDITED_SUCCESS.getId()), "调整单已调整，不允许重复操作！");

      List<StorageSnAdjustDetail> detailList = storageSnAdjustDetailService.selectListByMainId(id);
      Assert.isFalse(detailList.isEmpty(), "明细不能为空！");

      for (var detail : detailList) {
        Assert.isFalse(ObjectUtil.isEmpty(detail.getProductCode()), detail.getProductCode() + "商品编号不能为空！");
        Assert.isFalse(ObjectUtil.isEmpty(detail.getConsignorName()), detail.getProductCode() + "货主不能为空！");
        Assert.isFalse(ObjectUtil.isEmpty(detail.getStorageName()), detail.getProductCode() + "仓库不能为空！");
        Assert.isFalse(ObjectUtil.isEmpty(detail.getPositionName()), detail.getProductCode() + "货位不能为空！");

        if (StringUtils.equals(detail.getSnNo(), detail.getSnNoTarget())) {
          // 原SN和目标SN相同时直接修改状态
          if (B.isEqual(detail.getEnableTarget(), EnableEnum.ENABLE.getId())) {
            coreInventorySnService.updateValid(detail.getStorageId(), List.of(detail.getSnNo()), detail.getInventoryId());
          } else {
            coreInventorySnService.updateInvalid(detail.getStorageId(), List.of(detail.getSnNo()), detail.getInventoryId());
          }
        } else if (StringUtils.isNotEmpty(detail.getSnNo()) && StringUtils.isNotEmpty(detail.getSnNoTarget())) {
          // 将原SN改为不可用
          coreInventorySnService.updateInvalid(detail.getStorageId(), List.of(detail.getSnNo()), detail.getInventoryId());

          // 新增目标SN
          CoreInventorySnBo bo = BeanUtil.copyProperties(detail, CoreInventorySnBo.class);
          bo.setSnNo(detail.getSnNoTarget());
          bo.setEnable(detail.getEnableTarget());
          coreInventorySnService.insert(bo);
        } else if (StringUtils.isNotEmpty(detail.getSnNo()) && StringUtils.isEmpty(detail.getSnNoTarget())) {
          // 原SN不为空和目标SN为空
          if (B.isEqual(detail.getEnableTarget(), EnableEnum.ENABLE.getId())) {
            coreInventorySnService.updateValid(detail.getStorageId(), List.of(detail.getSnNo()), detail.getInventoryId());
          } else {
            coreInventorySnService.updateInvalid(detail.getStorageId(), List.of(detail.getSnNo()), detail.getInventoryId());
          }
        } else if (StringUtils.isEmpty(detail.getSnNo()) && StringUtils.isNotEmpty(detail.getSnNoTarget())) {
          // 原SN为空和目标SN不为空
          // 新增目标SN
          CoreInventorySnBo bo = BeanUtil.copyProperties(detail, CoreInventorySnBo.class);
          bo.setSnNo(detail.getSnNoTarget());
          bo.setEnable(detail.getEnableTarget());
          coreInventorySnService.insert(bo);
        }
      }

      // 更新主表状态
      LambdaUpdateWrapper<StorageSnAdjust> snAdjustLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      snAdjustLambdaUpdateWrapper
        .set(StorageSnAdjust::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
        .set(StorageSnAdjust::getAuditDate, DateUtils.getDate())
        .set(StorageSnAdjust::getAuditor, LoginHelper.getNickname())
        .set(StorageSnAdjust::getAdjustStatus, StorageSnAdjustStatusEnum.FINSISHED.getName())
        .eq(StorageSnAdjust::getSnAdjustId, id);
      this.update(snAdjustLambdaUpdateWrapper);
    }

    return R.ok("SN调整成功！");
  }
}
