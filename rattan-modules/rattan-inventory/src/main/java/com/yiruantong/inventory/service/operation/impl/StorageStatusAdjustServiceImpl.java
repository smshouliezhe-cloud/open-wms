package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.StorageAdjustStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.operation.StorageStatusAdjust;
import com.yiruantong.inventory.domain.operation.StorageStatusAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageStatusAdjustBo;
import com.yiruantong.inventory.domain.operation.vo.StorageStatusAdjustVo;
import com.yiruantong.inventory.mapper.operation.StorageStatusAdjustMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStorageStatusAdjustDetailService;
import com.yiruantong.inventory.service.operation.IStorageStatusAdjustService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 状态属性调整Service业务层处理
 *
 * @author YRT
 * @date 2025-02-14
 */
@RequiredArgsConstructor
@Service
public class StorageStatusAdjustServiceImpl extends ServiceImplPlus<StorageStatusAdjustMapper, StorageStatusAdjust, StorageStatusAdjustVo, StorageStatusAdjustBo> implements IStorageStatusAdjustService, IInventoryBaseService {
  private final IStorageStatusAdjustDetailService storageStatusAdjustDetailService;
  private final IInventoryCommonService inventoryCommonService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final ICoreInventoryService coreInventoryService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseStorageService baseStorageService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      StorageStatusAdjust adjustInfo = this.getById(id);
      Assert.isFalse(ObjectUtil.equal(adjustInfo.getAdjustStatus(), StorageAdjustStatusEnum.FINISHED.getName()),
        adjustInfo.getStatusAdjustCode() + "出库完成的，不允许重复操作");
      List<StorageStatusAdjustDetail> detailList = storageStatusAdjustDetailService.selectListByMainId(id);

      for (StorageStatusAdjustDetail detail : detailList) {
        Assert.isFalse(ObjectUtil.equals(detail.getProductStorage(), null), "调整数量不能为空！");
        Assert.isFalse(ObjectUtil.equals(detail.getStorageStatusTarget(), null), "调整状态不能为空！");
      }

      //#region 通用模块调用
      inventoryCommonService.setBizService(this);

      // 构建DTO数据 - 主表
      CommonMainDto commonMainDto = BeanUtil.copyProperties(adjustInfo, CommonMainDto.class);
      commonMainDto.setMainId(adjustInfo.getStatusAdjustId());
      commonMainDto.setMainCode(adjustInfo.getStatusAdjustCode());

      // 构建DTO数据 - 构建入库数据
      List<CommonDetailDto> commonDetailDtoList = new java.util.ArrayList<>(detailList.stream().map(detail -> {
        CommonDetailDto detailDto = BeanUtil.copyProperties(detail, CommonDetailDto.class);
        detailDto.setMainId(detail.getStatusAdjustId()); // 主表ID
        detailDto.setDetailId(detail.getStatusAdjustDetailId()); // 明细ID
        detailDto.setInventoryId(detail.getInventoryId());//库存ID
        detailDto.setBillCode(adjustInfo.getStatusAdjustCode());

        // 重新入库，修改目标状态和属性
        detailDto.setInQuantity(detail.getProductStorage());  // 入库数量
        detailDto.setOutQuantity(BigDecimal.ZERO); // 出库数量
        detailDto.setPositionNameIn(detail.getPositionName()); // 入库货位
        detailDto.setPositionNameOut(null);  // 出库货位
        detailDto.setStorageStatus("锁定");
        detailDto.setProductAttribute(detail.getProductAttributeTarget());

        return detailDto;
      }).toList());

      // 构建出库数据
      commonDetailDtoList.addAll(detailList.stream().map(detail -> {
        CommonDetailDto detailDto = BeanUtil.copyProperties(detail, CommonDetailDto.class);
        detailDto.setMainId(detail.getStatusAdjustId()); // 主表ID
        detailDto.setDetailId(detail.getStatusAdjustDetailId()); // 明细ID
        detailDto.setInventoryId(detail.getInventoryId());//库存ID
        detailDto.setBillCode(adjustInfo.getStatusAdjustCode());

        // 将当前库存消减掉
        detailDto.setInQuantity(null);  // 入库数量
        detailDto.setOutQuantity(detail.getProductStorage()); // 出库数量
        detailDto.setPositionNameIn(null); // 入库货位
        detailDto.setPositionNameOut(detail.getPositionName());  // 出库货位

        return detailDto;
      }).toList());

      // 设置可分拣的库存属性:正常，未质检，不合格
      commonMainDto.setProductAttributeEnumList(List.of(InventoryStatusEnum.NORMAL, InventoryStatusEnum.UNCHECKED, InventoryStatusEnum.UNQUALIFIED));
      // 分拣库存
      var sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, HolderSourceTypeEnum.STORAGE_ADJUST);
      Assert.isFalse(!sortingResult.isResult(), sortingResult.getMsg());

      // 出库
      inventoryCommonService.out(commonMainDto, commonDetailDtoList, InventorySourceTypeEnum.PC_STORAGE_STATUS_ADJUST_OUT);

      //重新计算单位体积，小计体积。和获得温层
      List<CommonDetailDto> inResult = commonDetailDtoList.stream().map(detail -> {
        var inventoryInfo = coreInventoryService.getById(detail.getInventoryId());
        detail.setThermocLine(inventoryInfo.getThermocLine());
        detail.setUnitCube(inventoryInfo.getUnitCube());
        detail.setRowCube(B.mul(detail.getInQuantity(), inventoryInfo.getUnitCube()));
        return detail;
      }).toList();

      // 入库
      inventoryCommonService.in(commonMainDto, inResult, InventorySourceTypeEnum.PC_STORAGE_STATUS_ADJUST_IN);
      //#endregion

      //#region 更新状态
      StorageStatusAdjust updateStorageOuter = new StorageStatusAdjust();
      updateStorageOuter.setAuditor(LoginHelper.getNickname());
      updateStorageOuter.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      updateStorageOuter.setAuditDate(DateUtil.date());
      updateStorageOuter.setAdjustStatus(StorageAdjustStatusEnum.FINISHED.getName());
      updateStorageOuter.setStatusAdjustId(adjustInfo.getStatusAdjustId()); // 设置主键

      this.updateById(updateStorageOuter);
      //#endregion
    }
    return R.ok("调整完成");
  }


  //#region 更新缺货数量 updateLackStorage
  @Override
  public void updateLackStorage(List<CommonDetailDto> detailList) {
    for (var detail : detailList) {
      // 获取占位
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getMainId(), detail.getDetailId(), HolderSourceTypeEnum.STORAGE_ADJUST);
      BigDecimal lackStorage = B.sub(detail.getOutQuantity(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<StorageStatusAdjustDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      detailLambdaUpdateWrapper
        .set(StorageStatusAdjustDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), StorageStatusAdjustDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), StorageStatusAdjustDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), StorageStatusAdjustDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(StorageStatusAdjustDetail::getStatusAdjustDetailId, detail.getDetailId());
      storageStatusAdjustDetailService.update(detailLambdaUpdateWrapper);
    }
  }
  //#endregion

  //#region 更新分拣状态 updateSortingStatus

  /**
   * 更新明细分拣状态
   *
   * @param mainID            主表ID
   * @param sortingStatusEnum 分拣状态
   */
  @Override
  public void updateSortingStatus(Long mainID, SortingStatusEnum sortingStatusEnum) {
    LambdaUpdateWrapper<StorageStatusAdjust> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper
      .set(StorageStatusAdjust::getSortingStatus, sortingStatusEnum.getId())
      .set(StorageStatusAdjust::getSortingDate, DateUtil.date())
      .eq(StorageStatusAdjust::getStatusAdjustId, mainID);
    this.update(orderLambdaUpdateWrapper);
  }

  //#endregion

}
