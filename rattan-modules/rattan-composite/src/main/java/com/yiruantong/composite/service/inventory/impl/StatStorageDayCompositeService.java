package com.yiruantong.composite.service.inventory.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.composite.service.inventory.IStatStorageDayCompositeService;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.InEnterDetail;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import com.yiruantong.inbound.service.in.IInShelveDetailService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.operation.*;
import com.yiruantong.inventory.domain.stat.StatStorageDay;
import com.yiruantong.inventory.domain.stat.StatStorageDayDetail;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStorageEnterDetailService;
import com.yiruantong.inventory.service.operation.IStorageOuterDetailService;
import com.yiruantong.inventory.service.operation.IStorageProfitLossDetailService;
import com.yiruantong.inventory.service.stat.IStatStorageDayDetailService;
import com.yiruantong.inventory.service.stat.IStatStorageDayService;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.yiruantong.outbound.domain.service.OutReturn;
import com.yiruantong.outbound.domain.service.OutReturnDetail;
import com.yiruantong.outbound.service.out.IOutPackageDetailService;
import com.yiruantong.outbound.service.service.IOutReturnDetailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: xtb
 * @CreateTime: 2024-03-19
 * @Description: 每日库存快照
 * @Version: 1.0
 */
@RequiredArgsConstructor
@Service
public class StatStorageDayCompositeService implements IStatStorageDayCompositeService {
  private final ICoreInventoryService inventoryService;
  private final IStatStorageDayService statStorageDayService;
  private final IStatStorageDayDetailService statStorageDayDetailService;
  private final IInEnterDetailService inEnterDetailService;
  private final IInShelveDetailService inShelveDetailService;
  private final IStorageEnterDetailService storageEnterDetailService;
  private final IOutReturnDetailService outReturnDetailService;
  private final IStorageProfitLossDetailService storageProfitLossDetailService;
  private final IOutPackageDetailService outPackageDetailService;
  private final IStorageOuterDetailService storageOuterDetailService;

  @Async
  @Override
  public void currentDayStorage(HttpServletRequest request, LoginUser loginUser) {
    LoginHelper.setLoginUser(loginUser);
    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到

    String today = DateUtil.today(); // 开始时间
    String tomorrow = DateUtil.formatDate(DateUtil.offsetDay(DateUtil.date(), 1)); // 结束时间
    Date fromDate = DateUtil.parse(today);
    Date toDate = DateUtil.parse(tomorrow);

    // 清空当天明细
    LambdaQueryWrapper<StatStorageDayDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StatStorageDayDetail::getStorageDay, fromDate);
    statStorageDayDetailService.remove(detailLambdaQueryWrapper);

    // 清空当天数据
    LambdaQueryWrapper<StatStorageDay> dayLambdaQueryWrapper = new LambdaQueryWrapper<>();
    dayLambdaQueryWrapper.eq(StatStorageDay::getStorageDay, fromDate);
    statStorageDayService.remove(dayLambdaQueryWrapper);

    // 获取当日库存
    // 查询字段
    String selectFields = "storageId,storageName,consignorId,consignorCode,consignorName,positionName,productId,productCode,productName,productModel,productSpec,providerId,providerCode,providerShortName,productStorage,originStorage,purchasePrice,purchaseAmount,productAttribute,batchNumber,produceDate,plateCode,relationCode,shelfLifeDay,shelfLifeDate,validShelfLifeDay,storageStatus,limitDate,typeName,brandName,validStorage,rowWeight,rowWeightOrigin,weight,inStorageDate,netWeight,rowNetWeight,rowNetWeightOrigin,remark";

    // 求和字段
    String sumFields = "productStorage,validStorage,originStorage,purchaseAmount,rowWeight,rowWeightOrigin,rowNetWeight,rowNetWeightOrigin";

    // 分组字段
    String groupFields = "storageId,storageName,consignorId,consignorCode,consignorName,positionName,productId,productCode,productName,productModel,productSpec,providerId,providerCode,providerShortName,productAttribute,batchNumber,produceDate,plateCode,relationCode,shelfLifeDay,shelfLifeDate,validShelfLifeDay,storageStatus,limitDate";

    // 构建联表查询
    MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, CoreInventory::getProductId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .gt(CoreInventory::getProductStorage, BigDecimal.ZERO); // 大于0的库存

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, CoreInventory.class, BaseProduct.class, BasePosition.class);
//    wrapper.eq(CoreInventory::getProductModel, "ZRP24000010L");

    List<StatStorageDayDetail> detailList = inventoryService.selectJoinList(StatStorageDayDetail.class, wrapper);
    detailList.forEach(dayDetail -> {
      dayDetail.setPurchasePrice(B.div(dayDetail.getPurchaseAmount(), dayDetail.getProductStorage())); // 计算平均单价
      dayDetail.setValidWeight(dayDetail.getRowWeight()); // 有效重量
      dayDetail.setStorageDay(fromDate);
    });
    List<StatStorageDayDetail> saveDetailList = new ArrayList<>();
    for (int index = 0; index < detailList.size(); index++) {
      saveDetailList.add(detailList.get(index));
      if (index % 50 == 0) {
        statStorageDayDetailService.saveBatch(saveDetailList); // 批量保存明细
        saveDetailList.clear();
      }
    }
    if (!saveDetailList.isEmpty()) {
      statStorageDayDetailService.saveBatch(saveDetailList); // 批量保存明细
      saveDetailList.clear();
    }

    // 封存每日库存
    List<StatStorageDay> statStorageDayList = new ArrayList<>();
    // 定义一个函数Function，该函数将元素对象映射到一个键的集合里
    Function<StatStorageDayDetail, List<Object>> compositeKey = item -> Arrays.asList(item.getStorageDay(), item.getStorageId(), item.getConsignorId(), item.getProductId(), item.getProductSpec());

    //#region 提前查询出各项合计值
    // 扫描入库
    MPJLambdaWrapper<InEnterDetail> enterDetailLambdaQueryWrapper = new MPJLambdaWrapper<>();
    enterDetailLambdaQueryWrapper.innerJoin(InEnter.class, InEnter::getEnterId, InEnterDetail::getEnterId)
      .select(InEnter::getStorageId, InEnter::getConsignorId)
      .select(InEnterDetail::getProductId, InEnterDetail::getProductSpec)
      .selectSum(InEnterDetail::getShelvedQuantity)
      .selectSum(InEnterDetail::getEnterQuantity)
      .selectSum(InEnterDetail::getRowWeight)
      .eq(InEnter::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .groupBy(InEnter::getStorageId)
      .groupBy(InEnter::getConsignorId)
      .groupBy(InEnterDetail::getProductId)
      .groupBy(InEnterDetail::getProductSpec)
      .between(InEnter::getAuditDate, fromDate, toDate);
    List<Map<String, Object>> enterDetailMapList = inEnterDetailService.selectJoinMaps(enterDetailLambdaQueryWrapper);

    // 上架数量
    MPJLambdaWrapper<InShelveDetail> shelveDetailLambdaQueryWrapper = new MPJLambdaWrapper<>();
    shelveDetailLambdaQueryWrapper.innerJoin(InShelve.class, InShelve::getShelveId, InShelveDetail::getShelveId)
      .select(InShelve::getStorageId, InShelve::getConsignorId)
      .select(InShelveDetail::getProductId, InShelveDetail::getProductSpec)
      .selectSum(InShelveDetail::getShelvedQuantity)
      .eq(InShelve::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .groupBy(InShelve::getStorageId)
      .groupBy(InShelve::getConsignorId)
      .groupBy(InShelveDetail::getProductId)
      .groupBy(InShelveDetail::getProductSpec)
      .between(InShelve::getAuditDate, fromDate, toDate);
    List<Map<String, Object>> shelveDetailMapList = inShelveDetailService.selectJoinMaps(shelveDetailLambdaQueryWrapper);

    // 其他入库数量
    MPJLambdaWrapper<StorageEnterDetail> storageEnterDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    storageEnterDetailMPJLambdaWrapper.innerJoin(StorageEnter.class, StorageEnter::getEnterId, StorageEnterDetail::getEnterId)
      .select(StorageEnter::getStorageId, StorageEnter::getConsignorId)
      .select(StorageEnterDetail::getProductId, StorageEnterDetail::getProductSpec)
      .selectSum(StorageEnterDetail::getEnterQuantity)
      .eq(StorageEnter::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .groupBy(StorageEnter::getStorageId)
      .groupBy(StorageEnter::getConsignorId)
      .groupBy(StorageEnter::getAuditDate)
      .groupBy(StorageEnterDetail::getProductId)
      .groupBy(StorageEnterDetail::getProductSpec)
      .between(StorageEnter::getAuditDate, fromDate, toDate);
    List<Map<String, Object>> storageEnterDetailMapList = storageEnterDetailService.selectJoinMaps(storageEnterDetailMPJLambdaWrapper);

    // 销售退货数量
    MPJLambdaWrapper<OutReturnDetail> outReturnDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    outReturnDetailMPJLambdaWrapper.innerJoin(OutReturn.class, OutReturn::getReturnId, OutReturnDetail::getReturnId)
      .select(OutReturn::getStorageId, OutReturn::getConsignorId)
      .select(OutReturnDetail::getProductId, OutReturnDetail::getProductSpec)
      .selectSum(OutReturnDetail::getReturnQuantity)
      .eq(OutReturn::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .groupBy(OutReturn::getStorageId)
      .groupBy(OutReturn::getConsignorId)
      .groupBy(OutReturnDetail::getProductId)
      .groupBy(OutReturnDetail::getProductSpec)
      .between(OutReturn::getAuditDate, fromDate, toDate);
    List<Map<String, Object>> outReturnDetailMapList = outReturnDetailService.selectJoinMaps(outReturnDetailMPJLambdaWrapper);

    // 盘点入库数量
    MPJLambdaWrapper<StorageProfitLossDetail> profitLossDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    profitLossDetailMPJLambdaWrapper.innerJoin(StorageProfitLoss.class, StorageProfitLoss::getProfitLossId, StorageProfitLossDetail::getProfitLossId)
      .select(StorageProfitLoss::getStorageId, StorageProfitLoss::getConsignorId)
      .select(StorageProfitLossDetail::getProductId, StorageProfitLossDetail::getProductSpec)
      .selectSum(StorageProfitLossDetail::getProfitQuantity)
      .eq(StorageProfitLoss::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .groupBy(StorageProfitLoss::getStorageId)
      .groupBy(StorageProfitLoss::getConsignorId)
      .groupBy(StorageProfitLossDetail::getProductId)
      .groupBy(StorageProfitLossDetail::getProductSpec)
      .between(StorageProfitLoss::getAuditDate, fromDate, toDate);
    List<Map<String, Object>> profitLossDetailMapList = storageProfitLossDetailService.selectJoinMaps(profitLossDetailMPJLambdaWrapper);

    // 扫描出库数量
    MPJLambdaWrapper<OutPackageDetail> packageDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    packageDetailMPJLambdaWrapper.innerJoin(OutPackage.class, OutPackage::getPackageId, OutPackageDetail::getPackageId)
      .select(OutPackage::getStorageId, OutPackage::getConsignorId)
      .select(OutPackageDetail::getProductId, OutPackageDetail::getProductSpec)
      .selectSum(OutPackageDetail::getPackageQuantity)
      .selectSum(OutPackageDetail::getRowWeight)
      .eq(OutPackage::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .groupBy(OutPackage::getStorageId)
      .groupBy(OutPackage::getConsignorId)
      .groupBy(OutPackageDetail::getProductId)
      .groupBy(OutPackageDetail::getProductSpec)
      .between(OutPackage::getAuditDate, fromDate, toDate);
    List<Map<String, Object>> packageDetailMapList = outPackageDetailService.selectJoinMaps(packageDetailMPJLambdaWrapper);

    // 其他出库数量
    MPJLambdaWrapper<StorageOuterDetail> storageOuterDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    storageOuterDetailMPJLambdaWrapper.innerJoin(StorageOuter.class, StorageOuter::getOuterId, StorageOuterDetail::getOuterId)
      .select(StorageOuter::getStorageId, StorageOuter::getConsignorId)
      .select(StorageOuterDetail::getProductId, StorageOuterDetail::getProductSpec)
      .selectSum(StorageOuterDetail::getOuterQuantity)
      .eq(StorageOuter::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .groupBy(StorageOuter::getStorageId)
      .groupBy(StorageOuter::getConsignorId)
      .groupBy(StorageOuterDetail::getProductId)
      .groupBy(StorageOuterDetail::getProductSpec)
      .between(StorageOuter::getAuditDate, fromDate, toDate);
    List<Map<String, Object>> storageOuterDetailMapList = storageOuterDetailService.selectJoinMaps(storageOuterDetailMPJLambdaWrapper);

    // 盘点出库数量
    profitLossDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    profitLossDetailMPJLambdaWrapper.innerJoin(StorageProfitLoss.class, StorageProfitLoss::getProfitLossId, StorageProfitLossDetail::getProfitLossId)
      .select(StorageProfitLoss::getStorageId, StorageProfitLoss::getConsignorId)
      .select(StorageProfitLossDetail::getProductId, StorageProfitLossDetail::getProductSpec)
      .eq(StorageProfitLoss::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
      .selectSum(StorageProfitLossDetail::getLossQuantity)
      .groupBy(StorageProfitLoss::getStorageId)
      .groupBy(StorageProfitLoss::getConsignorId)
      .groupBy(StorageProfitLossDetail::getProductId)
      .groupBy(StorageProfitLossDetail::getProductSpec)
      .between(StorageProfitLoss::getAuditDate, fromDate, toDate);
    List<Map<String, Object>> storageProfitLossDetailMapList = storageProfitLossDetailService.selectJoinMaps(profitLossDetailMPJLambdaWrapper);
    //#endregion

    // 分组
    Map<List<Object>, List<StatStorageDayDetail>> groupList = detailList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));
    for (var mapStorageItem : groupList.entrySet()) {
      List<StatStorageDayDetail> subList = mapStorageItem.getValue();
      StatStorageDayDetail statStorageDayDetail = subList.get(0);
      StatStorageDay statStorageDay = BeanUtil.copyProperties(statStorageDayDetail, StatStorageDay.class);

      // 求和
      BigDecimal purchaseAmount = subList.stream().map(m -> B.ifNullDefaultZero(m.getPurchaseAmount())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal productStorage = subList.stream().map(m -> B.ifNullDefaultZero(m.getProductStorage())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal validStorage = subList.stream().map(m -> B.ifNullDefaultZero(m.getValidStorage())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal holderStorage = subList.stream().map(m -> B.ifNullDefaultZero(m.getHolderStorage())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal originStorage = subList.stream().map(m -> B.ifNullDefaultZero(m.getOriginStorage())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal rowWeight = subList.stream().map(m -> B.ifNullDefaultZero(m.getRowWeight())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal rowNetWeight = subList.stream().map(m -> B.ifNullDefaultZero(m.getRowNetWeight())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal rowWeightOrigin = subList.stream().map(m -> B.ifNullDefaultZero(m.getRowWeightOrigin())).reduce(BigDecimal.ZERO, BigDecimal::add);
      BigDecimal rowNetWeightOrigin = subList.stream().map(m -> B.ifNullDefaultZero(m.getRowNetWeightOrigin())).reduce(BigDecimal.ZERO, BigDecimal::add);

      statStorageDay.setPurchasePrice(B.div(purchaseAmount, productStorage));
      statStorageDay.setPurchaseAmount(purchaseAmount);
      statStorageDay.setRowWeight(rowWeight);
      statStorageDay.setRowNetWeight(rowNetWeight);
      statStorageDay.setRowWeightOrigin(rowWeightOrigin);
      statStorageDay.setRowNetWeightTon(B.div(rowNetWeight, 1000L));
      statStorageDay.setRowNetWeightOrigin(rowNetWeightOrigin);

      statStorageDay.setValidStorage(validStorage);
      statStorageDay.setHolderStorage(holderStorage);
      statStorageDay.setOriginStorage(originStorage);
      statStorageDay.setProductStorage(productStorage);

      // 扫描入库
      Map<String, Object> enterDetailMap = enterDetailMapList.stream().filter(f ->
        B.isEqual(f.get("storageId"), statStorageDayDetail.getStorageId())
          && B.isEqual(f.get("consignorId"), statStorageDayDetail.getConsignorId())
          && B.isEqual(f.get("productId"), statStorageDayDetail.getProductId())
          && StringUtils.equals(Convert.toStr(f.get("productSpec")), statStorageDayDetail.getProductSpec())
      ).findFirst().orElse(null);
      var enterQuantity = Optional.ofNullable(enterDetailMap).map(m -> Convert.toBigDecimal(m.get("enterQuantity"))).orElse(BigDecimal.ZERO);
      rowWeight = Optional.ofNullable(enterDetailMap).map(m -> Convert.toBigDecimal(m.get("rowWeight"))).orElse(BigDecimal.ZERO);
      statStorageDay.setScanInQuantity(enterQuantity);
      statStorageDay.setScanInWeight(rowWeight);

      // 上架数量
      Map<String, Object> shelveDetailMap = shelveDetailMapList.stream().filter(f ->
        B.isEqual(f.get("storageId"), statStorageDayDetail.getStorageId())
          && B.isEqual(f.get("consignorId"), statStorageDayDetail.getConsignorId())
          && B.isEqual(f.get("productId"), statStorageDayDetail.getProductId())
          && StringUtils.equals(Convert.toStr(f.get("productSpec")), statStorageDayDetail.getProductSpec())
      ).findFirst().orElse(null);
      var shelvedQuantity = Optional.ofNullable(shelveDetailMap).map(m -> Convert.toBigDecimal(m.get("shelvedQuantity"))).orElse(BigDecimal.ZERO);
      statStorageDay.setScanShelveQuantity(shelvedQuantity);

      // 其他入库数量
      Map<String, Object> storageEnterDetailMap = storageEnterDetailMapList.stream().filter(f ->
        B.isEqual(f.get("storageId"), statStorageDayDetail.getStorageId())
          && B.isEqual(f.get("consignorId"), statStorageDayDetail.getConsignorId())
          && B.isEqual(f.get("productId"), statStorageDayDetail.getProductId())
          && StringUtils.equals(Convert.toStr(f.get("productSpec")), statStorageDayDetail.getProductSpec())
      ).findFirst().orElse(null);
      var storageEnterQuantity = Optional.ofNullable(storageEnterDetailMap).map(m -> Convert.toBigDecimal(m.get("enterQuantity"))).orElse(BigDecimal.ZERO);
      statStorageDay.setOtherInQuantity(storageEnterQuantity);

      // 销售退货数量
      Map<String, Object> outReturnDetailMap = outReturnDetailMapList.stream().filter(f ->
        B.isEqual(f.get("storageId"), statStorageDayDetail.getStorageId())
          && B.isEqual(f.get("consignorId"), statStorageDayDetail.getConsignorId())
          && B.isEqual(f.get("productId"), statStorageDayDetail.getProductId())
          && StringUtils.equals(Convert.toStr(f.get("productSpec")), statStorageDayDetail.getProductSpec())
      ).findFirst().orElse(null);
      var returnQuantity = Optional.ofNullable(outReturnDetailMap).map(m -> Convert.toBigDecimal(m.get("returnQuantity"))).orElse(BigDecimal.ZERO);
      statStorageDay.setReturnQuantity(returnQuantity);

      // 盘点入库数量
      Map<String, Object> profitLossDetailMap = profitLossDetailMapList.stream().filter(f ->
        B.isEqual(f.get("storageId"), statStorageDayDetail.getStorageId())
          && B.isEqual(f.get("consignorId"), statStorageDayDetail.getConsignorId())
          && B.isEqual(f.get("productId"), statStorageDayDetail.getProductId())
          && StringUtils.equals(Convert.toStr(f.get("productSpec")), statStorageDayDetail.getProductSpec())
      ).findFirst().orElse(null);
      var profitQuantity = Optional.ofNullable(profitLossDetailMap).map(m -> Convert.toBigDecimal(m.get("profitQuantity"))).orElse(BigDecimal.ZERO);
      statStorageDay.setCheckInQuantity(profitQuantity);

      // 扫描出库数量
      Map<String, Object> packageDetailMap = packageDetailMapList.stream().filter(f ->
        B.isEqual(f.get("storageId"), statStorageDayDetail.getStorageId())
          && B.isEqual(f.get("consignorId"), statStorageDayDetail.getConsignorId())
          && B.isEqual(f.get("productId"), statStorageDayDetail.getProductId())
          && StringUtils.equals(Convert.toStr(f.get("productSpec")), statStorageDayDetail.getProductSpec())
      ).findFirst().orElse(null);
      var packageQuantity = Optional.ofNullable(packageDetailMap).map(m -> Convert.toBigDecimal(m.get("packageQuantity"))).orElse(BigDecimal.ZERO);
      rowWeight = Optional.ofNullable(packageDetailMap).map(m -> Convert.toBigDecimal(m.get("rowWeight"))).orElse(BigDecimal.ZERO);
      statStorageDay.setScanOutQuantity(packageQuantity);
      statStorageDay.setScanOutWeight(rowWeight);

      // 其他出库数量
      Map<String, Object> storageOuterDetailMap = storageOuterDetailMapList.stream().filter(f ->
        B.isEqual(f.get("storageId"), statStorageDayDetail.getStorageId())
          && B.isEqual(f.get("consignorId"), statStorageDayDetail.getConsignorId())
          && B.isEqual(f.get("productId"), statStorageDayDetail.getProductId())
          && StringUtils.equals(Convert.toStr(f.get("productSpec")), statStorageDayDetail.getProductSpec())
      ).findFirst().orElse(null);
      var outerQuantity = Optional.ofNullable(storageOuterDetailMap).map(m -> Convert.toBigDecimal(m.get("outerQuantity"))).orElse(BigDecimal.ZERO);
      statStorageDay.setOtherOutQuantity(outerQuantity);

      // 盘点出库数量
      profitLossDetailMap = storageProfitLossDetailMapList.stream().filter(f ->
        B.isEqual(f.get("storageId"), statStorageDayDetail.getStorageId())
          && B.isEqual(f.get("consignorId"), statStorageDayDetail.getConsignorId())
          && B.isEqual(f.get("productId"), statStorageDayDetail.getProductId())
          && StringUtils.equals(Convert.toStr(f.get("productSpec")), statStorageDayDetail.getProductSpec())
      ).findFirst().orElse(null);
      var lossQuantity = Optional.ofNullable(profitLossDetailMap).map(m -> Convert.toBigDecimal(m.get("lossQuantity"))).orElse(BigDecimal.ZERO);
      statStorageDay.setCheckOutQuantity(lossQuantity);

      statStorageDayList.add(statStorageDay);
    }
    List<StatStorageDay> saveStatStorageDayList = new ArrayList<>();
    for (int index = 0; index < statStorageDayList.size(); index++) {
      saveStatStorageDayList.add(statStorageDayList.get(index));
      if (index % 50 == 0) {
        statStorageDayService.saveBatch(saveStatStorageDayList); // 批量保存每日库存
        saveStatStorageDayList.clear();
      }
    }
    if (!saveStatStorageDayList.isEmpty()) {
      statStorageDayService.saveBatch(saveStatStorageDayList); // 批量保存每日库存
      saveStatStorageDayList.clear();
    }
  }
}
