package com.yiruantong.inventory.service.operation.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.*;
import com.yiruantong.common.core.enums.inventory.StorageAdjustStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageEnterStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.operation.StorageAdjust;
import com.yiruantong.inventory.domain.operation.StorageAdjustDetail;
import com.yiruantong.inventory.domain.operation.api.ApiStorageAdjustBo;
import com.yiruantong.inventory.domain.operation.bo.StorageAdjustBo;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustVo;
import com.yiruantong.inventory.mapper.operation.StorageAdjustMapper;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.base.IInventoryCommonService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStorageAdjustDetailService;
import com.yiruantong.inventory.service.operation.IStorageAdjustService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存调整单Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageAdjustServiceImpl extends ServiceImplPlus<StorageAdjustMapper, StorageAdjust, StorageAdjustVo, StorageAdjustBo> implements IStorageAdjustService, IInventoryBaseService {
  private final IStorageAdjustDetailService storageAdjustDetailService;
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
      StorageAdjust adjustInfo = this.getById(id);
      Assert.isFalse(ObjectUtil.equal(adjustInfo.getAdjustStatus(), StorageAdjustStatusEnum.FINISHED.getName()),
        adjustInfo.getAdjustCode() + "出库完成的，不允许重复操作");
      List<StorageAdjustDetail> detailList = storageAdjustDetailService.selectListByMainId(id);

      for (StorageAdjustDetail detail : detailList) {
        Assert.isFalse(ObjectUtil.equals(detail.getCheckQuantity(), null), "盘点数量不能为空！");
      }

      //#region 通用模块调用
      inventoryCommonService.setBizService(this);

      // 构建DTO数据 - 主表
      CommonMainDto commonMainDto = BeanUtil.copyProperties(adjustInfo, CommonMainDto.class);
      commonMainDto.setMainId(adjustInfo.getAdjustId());
      commonMainDto.setMainCode(adjustInfo.getAdjustCode());

      // 构建DTO数据 - 明细集合
      List<CommonDetailDto> commonDetailDtoList = detailList.stream().map(detail -> {
        CommonDetailDto detailDto = BeanUtil.copyProperties(detail, CommonDetailDto.class);
        detailDto.setMainId(detail.getAdjustId()); // 主表ID
        detailDto.setDetailId(detail.getAdjustDetailId()); // 明细ID
        detailDto.setInventoryId(detail.getInventoryId());//库存ID
        detailDto.setBillCode(adjustInfo.getAdjustCode());
        if (B.isGreater(detail.getProfitQuantity())) {
          // 盘盈
          detailDto.setInQuantity(detail.getProfitQuantity());  // 入库数量
          detailDto.setOutQuantity(BigDecimal.ZERO); // 出库数量
          detailDto.setPositionNameIn(detail.getPositionName()); // 入库货位
          detailDto.setPositionNameOut(null);  // 出库货位
        } else {
          // 盘亏
          detailDto.setInQuantity(null);  // 入库数量
          detailDto.setOutQuantity(detail.getLossQuantity()); // 出库数量
          detailDto.setPositionNameIn(null); // 入库货位
          detailDto.setPositionNameOut(detail.getPositionName());  // 出库货位
        }
        return detailDto;
      }).toList();

      // 分拣库存
      var sortingResult = inventoryCommonService.sorting(commonMainDto, commonDetailDtoList, HolderSourceTypeEnum.STORAGE_ADJUST);
      Assert.isFalse(!sortingResult.isResult(), sortingResult.getMsg());

      // 出库
      inventoryCommonService.out(commonMainDto, commonDetailDtoList, InventorySourceTypeEnum.PC_STORAGE_ADJUST_OUT);

      //重新计算单位体积，小计体积。和获得温层
      List<CommonDetailDto> inResult = commonDetailDtoList.stream().map(detail -> {
        var inventoryInfo = coreInventoryService.getById(detail.getInventoryId());
        detail.setThermocLine(inventoryInfo.getThermocLine());
        detail.setUnitCube(inventoryInfo.getUnitCube());
        detail.setRowCube(B.mul(detail.getInQuantity(), inventoryInfo.getUnitCube()));
        return detail;
      }).toList();

      // 入库
      inventoryCommonService.in(commonMainDto, inResult, InventorySourceTypeEnum.PC_STORAGE_ADJUST_IN);
      //#endregion

      //#region 更新状态
      StorageAdjust updateStorageOuter = new StorageAdjust();
      updateStorageOuter.setAuditor(LoginHelper.getNickname());
      updateStorageOuter.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      updateStorageOuter.setAuditDate(DateUtil.date());
      updateStorageOuter.setAdjustStatus(StorageAdjustStatusEnum.FINISHED.getName());
      updateStorageOuter.setAdjustId(adjustInfo.getAdjustId()); // 设置主键

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
      LambdaUpdateWrapper<StorageAdjustDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      detailLambdaUpdateWrapper
        .set(StorageAdjustDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), StorageAdjustDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getOutQuantity(), lackStorage), StorageAdjustDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getOutQuantity(), lackStorage), StorageAdjustDetail::getSortingStatus, SortingStatusEnum.LACK.getId())
        .eq(StorageAdjustDetail::getAdjustDetailId, detail.getDetailId());
      storageAdjustDetailService.update(detailLambdaUpdateWrapper);
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
    LambdaUpdateWrapper<StorageAdjust> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    orderLambdaUpdateWrapper
      .set(StorageAdjust::getSortingStatus, sortingStatusEnum.getId())
      .set(StorageAdjust::getSortingDate, DateUtil.date())
      .eq(StorageAdjust::getAdjustId, mainID);
    this.update(orderLambdaUpdateWrapper);
  }

  //#endregion

  @Override
  public R<Map<String, Object>> add(ApiStorageAdjustBo bo) {
    try {
      // 验证单号是否已推送

      LambdaQueryWrapper<StorageAdjust> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(StorageAdjust::getSourceCode, bo.getSourceCode());
      StorageAdjust storeOrderInfo = this.getOnly(queryWrapper);

      if (ObjectUtil.isNotNull(storeOrderInfo)) {
        Map<String, Object> result = new HashMap<>();
        result.put("adjustId", storeOrderInfo.getAdjustId());
        result.put("adjustCode", storeOrderInfo.getAdjustCode());
        return R.ok(bo.getSourceCode() + "已存在，不允许重复推送", result);
      }

      // 验证货主
      BaseConsignor consignorInfo = baseConsignorService.getByName(bo.getConsignorName());
      Assert.isFalse(ObjectUtil.isNull(consignorInfo), "货主" + bo.getConsignorName() + "不存在！");

      bo.setConsignorId(consignorInfo.getConsignorId());
      bo.setConsignorCode(consignorInfo.getConsignorCode());

      // 验证供应商，如果不存在自动增加供应商
      BaseProvider providerInfo = baseProviderService.getByShortName(bo.getProviderShortName());
      if (ObjectUtil.isNull(providerInfo)) {
        providerInfo = new BaseProvider();
        providerInfo.setProviderCode(bo.getProviderCode());
        providerInfo.setProviderShortName(bo.getProviderShortName());
        providerInfo.setProviderName(bo.getProviderShortName());
        baseProviderService.save(providerInfo);
      }
      bo.setProviderId(providerInfo.getProviderId());

      // 验证调出仓库
      BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
      Assert.isFalse(ObjectUtil.isNull(storageInfo), "仓库不存在！");

      bo.setStorageId(storageInfo.getStorageId());
      bo.setStorageName(storageInfo.getStorageName());

      for (var detailInfo : bo.getDetailList()) {
        BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
        Assert.isFalse(ObjectUtil.isNull(prodInfo), detailInfo.getProductCode() + "商品编号不存在");

        detailInfo.setProductId(prodInfo.getProductId());
        detailInfo.setProductModel(prodInfo.getProductModel());
        detailInfo.setProductName(prodInfo.getProductName());
        detailInfo.setProductSpec(prodInfo.getProductSpec());
      }

      StorageAdjust dataInfo = new StorageAdjust();
      BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("adjustId"));
      dataInfo.setAdjustCode(DBUtils.getCodeRegular(MenuEnum.MENU_1785));
      dataInfo.setAdjustStatus(StorageEnterStatusEnum.NEWED.getName());

      if (ObjectUtil.isNotNull(bo.getCheckType())) {
        dataInfo.setCheckType(bo.getCheckType());
      } else {
        dataInfo.setCheckType("常规调整");
      }
      this.save(dataInfo);

      for (var detailInfo : bo.getDetailList()) {
        StorageAdjustDetail detail = new StorageAdjustDetail();
        BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("adjustDetailId"));
        detail.setAdjustId(dataInfo.getAdjustId());

        storageAdjustDetailService.save(detail);
      }

      // 合计
      List<StorageAdjustDetail> details = storageAdjustDetailService.selectListByMainId(dataInfo.getAdjustId());

      BigDecimal totalProductStorage = details.stream().map(StorageAdjustDetail::getProductStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalCheckQuantity = details.stream().map(StorageAdjustDetail::getCheckQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalProfitQuantity = details.stream().map(StorageAdjustDetail::getProfitQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalProfitAmount = details.stream().map(StorageAdjustDetail::getProfitAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalLossQuantity = details.stream().map(StorageAdjustDetail::getLossQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal totalLossAmount = details.stream().map(StorageAdjustDetail::getLossAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

      LambdaUpdateWrapper<StorageAdjust> lambda = new LambdaUpdateWrapper<>();
      lambda.set(StorageAdjust::getTotalProductStorage, totalProductStorage)
        .set(StorageAdjust::getTotalCheckQuantity, totalCheckQuantity)
        .set(StorageAdjust::getTotalProfitQuantity, totalProfitQuantity)
        .set(StorageAdjust::getTotalProfitAmount, totalProfitAmount)
        .set(StorageAdjust::getTotalLossQuantity, totalLossQuantity)
        .set(StorageAdjust::getTotalLossAmount, totalLossAmount)
        .eq(StorageAdjust::getAdjustId, dataInfo.getAdjustId());

      Map<String, Object> result = new HashMap<>();
      result.put("adjustId", dataInfo.getAdjustId());
      result.put("adjustCode", dataInfo.getAdjustCode());
      return R.ok("调整单保存成功", result);

    } catch (Exception error) {
      return R.fail("推送订单失败，" + error.getMessage());
    }
  }
}
