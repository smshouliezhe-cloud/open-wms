package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
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
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.inbound.domain.api.ApiInOrderPlanBo;
import com.yiruantong.inbound.domain.in.InOrderPlan;
import com.yiruantong.inbound.domain.in.InOrderPlanDetail;
import com.yiruantong.inbound.service.in.IInOrderPlanApiService;
import com.yiruantong.inbound.service.in.IInOrderPlanDetailService;
import com.yiruantong.inbound.service.in.IInOrderPlanService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InOrderPlanApiServiceImpl implements IInOrderPlanApiService {
  private final IBaseConsignorService baseConsignorService;
  private final IBaseProviderService baseProviderService;
  private final IBaseProductService baseProductService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageService baseStorageService;
  private final IInOrderPlanService inOrderPlanService;
  private final IInOrderPlanDetailService inOrderPlanDetailService;

  //#region 新增数据

  /**
   * 新增数据
   */
  @Override
  public R<Map<String, Object>> add(ApiInOrderPlanBo bo) {

    // 验证是否存在
    InOrderPlan sourceInfo = inOrderPlanService.getBySourceCode(bo.getSourceCode());
    if (!bo.getPlanType().equals("自产材")) {
//      Assert.isFalse(ObjectUtil.isNotNull(sourceInfo), bo.getSourceCode() + "已存在");
    }

    // 验证货主
    BaseConsignor consignorInfo = baseConsignorService.getByCode(bo.getConsignorCode());
    Assert.isFalse(ObjectUtil.isNull(consignorInfo), bo.getConsignorCode() + "货主不存在");
    bo.setConsignorId(consignorInfo.getConsignorId());
    bo.setConsignorName(consignorInfo.getConsignorName());

    if (ObjectUtil.isNotNull(bo.getProviderShortName())) {
      // 验证供应商，如果不存在自动增加供应商
      BaseProvider providerInfo = baseProviderService.getByShortName(bo.getProviderShortName());
      if (ObjectUtil.isNull(providerInfo)) {

        providerInfo = new BaseProvider();
        if (ObjectUtil.isNotNull(bo.getProviderCode())) {
          providerInfo.setProviderCode(bo.getProviderCode());
        } else {
          providerInfo.setProviderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1762));
        }
        providerInfo.setProviderShortName(bo.getProviderShortName());
        providerInfo.setProviderName(bo.getProviderShortName());
        baseProviderService.save(providerInfo);
      }
      bo.setProviderId(providerInfo.getProviderId());
      bo.setProviderCode(providerInfo.getProviderCode());
    } else {
      // 没有传供应商使用默认供应商
      boolean erp_provider_Id = sysConfigService.getConfigBool("erp_provider_Id");

      if (erp_provider_Id) {
        BaseProvider providerInfo = baseProviderService.getById(erp_provider_Id);
        if (ObjectUtil.isNotNull(providerInfo)) {
          bo.setProviderId(providerInfo.getProviderId());
          bo.setProviderCode(providerInfo.getProviderCode());
          bo.setProviderShortName(providerInfo.getProviderShortName());
        }
      }
      Assert.isFalse(ObjectUtil.isNull(bo.getProviderShortName()), "请联系管理员设置默认供应商");
    }
    // 验证仓库
    BaseStorage storageInfo = baseStorageService.getByCode(bo.getStorageCode());
    Assert.isFalse(ObjectUtil.isNull(storageInfo), bo.getStorageCode() + "仓库不存在");
    bo.setStorageName(storageInfo.getStorageName());
    bo.setStorageCode(storageInfo.getStorageCode());
    bo.setStorageId(storageInfo.getStorageId());

    // 校验单位重量
//    for (var detailInfo : bo.getDetailList()) {
//      var weight = detailInfo.getWeight();
//      BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
//      if (ObjectUtil.isNotNull(prodInfo) && ObjectUtil.equals(weight, prodInfo.getWeight())) {
//        return R.fail("单位重量和商品信息中不一致");
//      }
//    }

    InOrderPlan inOrderPlan = new InOrderPlan();
    BeanUtil.copyProperties(bo, inOrderPlan);

    String code = DBUtils.getCodeRegular(MenuEnum.MENU_1003);
    inOrderPlan.setPlanCode(code);
    if (ObjectUtil.isNotNull(bo.getPlanStatus())) {
      inOrderPlan.setPlanStatus(bo.getPlanStatus());
    } else {
      inOrderPlan.setPlanStatus(InOrderPlanStatusEnum.NEWED.getName());
    }
    if (ObjectUtil.isNotNull(bo.getPlanStatus())) {
      inOrderPlan.setPlanType(bo.getPlanType());
    } else {
      inOrderPlan.setPlanType(InOrderPlanTypeEnum.ORDINARY_ORDER.getName());
    }
    Object crossDocking = bo.getCrossDocking();
    Map<String, Object> expandFields = inOrderPlan.getExpandFields();
    if (expandFields == null) {
      expandFields = new HashMap<>();
      inOrderPlan.setExpandFields(expandFields);
    }
    if (crossDocking != null) {
      expandFields.put("crossDocking", crossDocking);
    }
    inOrderPlanService.save(inOrderPlan);


    //处理明细;
    for (var detailInfo : bo.getDetailList()) {
      BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
      Assert.isFalse(ObjectUtil.isNull(prodInfo), detailInfo.getProductCode() + "商品编号不存在");

      BaseConsignor consignor = baseConsignorService.getByCode(detailInfo.getConsignorCode());
      Assert.isFalse(ObjectUtil.isNull(consignor), detailInfo.getConsignorCode() + "明细货主不存在");

      BaseStorage storage = baseStorageService.getByCode(detailInfo.getStorageCode());
      Assert.isFalse(ObjectUtil.isNull(storageInfo), detailInfo.getStorageCode() + "明细仓库不存在");

      // 新建明细
      InOrderPlanDetail detail = new InOrderPlanDetail();
      BeanUtil.copyProperties(detailInfo, detail);
//      BeanUtil.copyProperties(prodInfo, detail);
      detail.setProductId(prodInfo.getProductId());
      detail.setProductCode(prodInfo.getProductCode());
      detail.setProductName(prodInfo.getProductName());
      detail.setProductModel(prodInfo.getProductModel());
      detail.setPlanId(inOrderPlan.getPlanId());
      detail.setStorageId(storage.getStorageId());
      detail.setStorageName(storage.getStorageName());
      detail.setConsignorId(consignor.getConsignorId());
      detail.setConsignorCode(consignor.getConsignorCode());
      detail.setConsignorName(consignor.getConsignorName());
      detail.setExpandFields(detailInfo.getExpandFields());
      detail.setUnitConvert(detailInfo.getOvercharges()); // 允差
      detail.setTotalPackageQty(detailInfo.getQuantityOrder());
      Object isChecking = detailInfo.getIsChecking();
      Object rowTareWeight = detailInfo.getRowTareWeight();
      Map<String, Object> detailExpandFields = inOrderPlan.getExpandFields();
      if (detailExpandFields == null) {
        detailExpandFields = new HashMap<>();
        inOrderPlan.setExpandFields(detailExpandFields);
      }
      if (isChecking != null) {
        detailExpandFields.put("isChecking", isChecking);
      }

      inOrderPlanDetailService.save(detail);
    }
    Map<String, Object> result = new HashMap<>();
    result.put("planId", inOrderPlan.getPlanId());
    result.put("planCode", inOrderPlan.getPlanCode());
    return R.ok("订单保存成功", result);
  }

  //#endregion
}
