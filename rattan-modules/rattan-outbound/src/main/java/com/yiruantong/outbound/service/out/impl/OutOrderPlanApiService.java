package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
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
import com.yiruantong.outbound.domain.api.ApiOutOrderPlanBo;
import com.yiruantong.outbound.domain.out.OutOrderPlan;
import com.yiruantong.outbound.domain.out.OutOrderPlanDetail;
import com.yiruantong.outbound.service.out.IOutOrderPlanApiService;
import com.yiruantong.outbound.service.out.IOutOrderPlanDetailService;
import com.yiruantong.outbound.service.out.IOutOrderPlanService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 出库计划接口
 */
@RequiredArgsConstructor
@Service
public class OutOrderPlanApiService implements IOutOrderPlanApiService {
  private final IBaseConsignorService baseConsignorService;
  private final IBaseProviderService baseProviderService;
  private final IBaseProductService baseProductService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageService baseStorageService;
  private final IOutOrderPlanService outOrderPlanService;
  private final IOutOrderPlanDetailService outOrderPlanDetailService;

  @Override
  public R<Map<String, Object>> add(ApiOutOrderPlanBo bo) {
    // 验证货主
    BaseConsignor consignorInfo = baseConsignorService.getByCode(bo.getConsignorCode());
    Assert.isFalse(ObjectUtil.isNull(consignorInfo), bo.getConsignorCode() + "货主不存在");
    bo.setConsignorId(consignorInfo.getConsignorId());
    bo.setConsignorName(consignorInfo.getConsignorName());

    // 验证仓库
    BaseStorage storageInfo = baseStorageService.getByCode(bo.getStorageCode());
    Assert.isFalse(ObjectUtil.isNull(storageInfo), bo.getStorageCode() + "仓库不存在");
    bo.setStorageName(storageInfo.getStorageName());
    bo.setStorageCode(storageInfo.getStorageCode());
    bo.setStorageId(storageInfo.getStorageId());


    OutOrderPlan outOrderPlan = new OutOrderPlan();
    BeanUtil.copyProperties(bo, outOrderPlan);

    String code = DBUtils.getCodeRegular(MenuEnum.MENU_1669);
    outOrderPlan.setOrderPlanCode(code);
    if (ObjectUtil.isNotNull(bo.getPlanStatus())) {
      outOrderPlan.setPlanStatus(bo.getPlanStatus());
    } else {
      outOrderPlan.setPlanStatus(InOrderPlanStatusEnum.NEWED.getName());
    }
    if (ObjectUtil.isNotNull(bo.getPlanType())) {
      outOrderPlan.setPlanType(bo.getPlanType());
    } else {
      outOrderPlan.setPlanType(InOrderPlanTypeEnum.ORDINARY_ORDER.getName());
    }
    outOrderPlanService.save(outOrderPlan);


    //处理明细;
    for (var detailInfo : bo.getDetailList()) {
      BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
      Assert.isFalse(ObjectUtil.isNull(prodInfo), detailInfo.getProductCode() + "商品编号不存在");

      // 新建明细
      OutOrderPlanDetail detail = new OutOrderPlanDetail();
      BeanUtil.copyProperties(detailInfo, detail);
      BeanUtil.copyProperties(prodInfo, detail);
      detail.setProductId(prodInfo.getProductId());
      detail.setProductCode(prodInfo.getProductCode());
      detail.setProductName(prodInfo.getProductName());
      detail.setProductModel(prodInfo.getProductModel());
      detail.setOrderPlanId(outOrderPlan.getOrderPlanId());
      detail.setStorageId(storageInfo.getStorageId());
      detail.setStorageName(storageInfo.getStorageName());
      detail.setConsignorId(consignorInfo.getConsignorId());
      detail.setConsignorCode(consignorInfo.getConsignorCode());
      detail.setConsignorName(consignorInfo.getConsignorName());
      detail.setExpandFields(detailInfo.getExpandFields());
      outOrderPlanDetailService.save(detail);
    }
    Map<String, Object> result = new HashMap<>();
    result.put("orderPlanId", outOrderPlan.getOrderPlanId());
    result.put("orderPlanCode", outOrderPlan.getOrderPlanCode());
    return R.ok("订单保存成功", result);
  }

  //#endregion

}
