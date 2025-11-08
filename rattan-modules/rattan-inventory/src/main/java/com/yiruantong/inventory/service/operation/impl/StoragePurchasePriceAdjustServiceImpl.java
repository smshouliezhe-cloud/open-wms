package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.inventory.StoragePurchasePriceAdjustStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjust;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjustDetail;
import com.yiruantong.inventory.domain.operation.bo.StoragePurchasePriceAdjustBo;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustVo;
import com.yiruantong.inventory.mapper.operation.StoragePurchasePriceAdjustMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.operation.IStoragePurchasePriceAdjustDetailService;
import com.yiruantong.inventory.service.operation.IStoragePurchasePriceAdjustService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存成本价调整Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StoragePurchasePriceAdjustServiceImpl extends ServiceImplPlus<StoragePurchasePriceAdjustMapper, StoragePurchasePriceAdjust, StoragePurchasePriceAdjustVo, StoragePurchasePriceAdjustBo> implements IStoragePurchasePriceAdjustService, IInventoryBaseService {
  private final IStoragePurchasePriceAdjustDetailService storagePurchasePriceAdjustDetailService;
  private final IInventoryCommonService inventoryCommonService;
  private final ICoreInventoryHolderService coreInventoryHolderService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> multiAuditing(List<Long> ids) {
    for (Long id : ids) {
      StoragePurchasePriceAdjust mainInfo = this.getById(id);
      Assert.isFalse(ObjectUtil.equal(mainInfo.getAdjustStatus(), StoragePurchasePriceAdjustStatusEnum.FINISHED.getName()),
        mainInfo.getPurchasePriceAdjustCode() + "出库完成的，不允许重复操作");
      List<StoragePurchasePriceAdjustDetail> detailList = storagePurchasePriceAdjustDetailService.selectListByMainId(id);

      //#region 通用模块调用
      inventoryCommonService.setBizService(this);

      // 构建DTO数据 - 主表
      CommonMainDto commonMainDto = BeanUtil.copyProperties(mainInfo, CommonMainDto.class);
      commonMainDto.setMainId(mainInfo.getPurchasePriceAdjustId());
      commonMainDto.setMainCode(mainInfo.getPurchasePriceAdjustCode());

      // 构建DTO数据 - 明细集合
      List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(m -> {
        CommonDetailDto detailDto = BeanUtil.copyProperties(m, CommonDetailDto.class);
        detailDto.setMainId(m.getPurchasePriceAdjustId()); // 主表ID
        detailDto.setDetailId(m.getPurchasePriceAdjustDetailId()); // 明细ID

        detailDto.setInQuantity(m.getProductStorage());  // 入库数量
        detailDto.setOutQuantity(m.getProductStorage()); // 出库数量
        detailDto.setPositionNameIn(m.getPositionName()); // 入库货位
        detailDto.setPositionNameOut(m.getPositionName());  // 出库货位

        detailDto.setPurchasePrice(m.getAdjustPrice());
        detailDto.setPurchaseAmount(B.mul(m.getAdjustPrice(), m.getProductStorage()));
        detailDto.setRatePrice(B.mul(m.getAdjustPrice(), B.add(BigDecimal.ONE, m.getRate())));
        detailDto.setRateAmount(B.mul(m.getRatePrice(), m.getProductStorage()));
        detailDto.setBillCode(mainInfo.getPurchasePriceAdjustCode());
        return detailDto;
      }).toList();

      // 分拣库存
      var sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, HolderSourceTypeEnum.STORAGE_PURCHASE_PRICE_ADJUST);
      Assert.isFalse(!sortingResult.isResult(), sortingResult.getMsg());

      // 出库
      R<List<CommonDetailDto>> outResult = inventoryCommonService.out(commonMainDto, commonDetailDtoList, InventorySourceTypeEnum.PC_STORAGE_PURCHASE_PRICE_ADJUST_OUT);

      List<CommonDetailDto> inResult = outResult.getData().stream().map(m -> {
        m.setBillCode(mainInfo.getPurchasePriceAdjustCode());
        return m;
      }).toList();

      // 入库
      inventoryCommonService.in(commonMainDto, inResult, InventorySourceTypeEnum.PC_STORAGE_PURCHASE_PRICE_ADJUST_IN);
      //#endregion

      //#region 更新状态
      StoragePurchasePriceAdjust updateStorageOuter = new StoragePurchasePriceAdjust();
      updateStorageOuter.setAuditor(LoginHelper.getNickname());
      updateStorageOuter.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      updateStorageOuter.setAuditDate(DateUtil.date());
      updateStorageOuter.setAdjustStatus(StoragePurchasePriceAdjustStatusEnum.FINISHED.getName());
      updateStorageOuter.setPurchasePriceAdjustId(mainInfo.getPurchasePriceAdjustId()); // 设置主键

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
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getMainId(), detail.getDetailId(), HolderSourceTypeEnum.STORAGE_PURCHASE_PRICE_ADJUST);
      BigDecimal lackStorage = B.sub(detail.getOutQuantity(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<StoragePurchasePriceAdjustDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      detailLambdaUpdateWrapper
        .set(StoragePurchasePriceAdjustDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), StoragePurchasePriceAdjustDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), StoragePurchasePriceAdjustDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), StoragePurchasePriceAdjustDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(StoragePurchasePriceAdjustDetail::getPurchasePriceAdjustId, detail.getDetailId());
      storagePurchasePriceAdjustDetailService.update(detailLambdaUpdateWrapper);
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
    LambdaUpdateWrapper<StoragePurchasePriceAdjust> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper
      .set(StoragePurchasePriceAdjust::getSortingStatus, sortingStatusEnum.getId())
      .set(StoragePurchasePriceAdjust::getSortingDate, DateUtil.date())
      .eq(StoragePurchasePriceAdjust::getPurchasePriceAdjustId, mainID);
    this.update(orderLambdaUpdateWrapper);
  }
  //#endregion
}
