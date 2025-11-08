package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;
import com.yiruantong.inbound.domain.api.ApiInOrderDetailBo;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.service.in.IInOrderApiService;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.system.domain.permission.SysDept;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.permission.ISysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class InOrderApiServiceImpl implements IInOrderApiService {
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseProviderService baseProviderService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageService baseStorageService;
  private final ISysDeptService sysDeptService;
  private final IBaseProductService baseProductService;

  @Override
  public R<Map<String, Object>> add(ApiInOrderBo bo) {

    // 判断来源单号是否重复
    LambdaQueryWrapper<InOrder> inOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    inOrderLambdaQueryWrapper.eq(InOrder::getTrackingNumber, bo.getTrackingNumber());
    // 如果Id不为空
    if (ObjectUtil.isNotEmpty(bo.getOrderId())) {
      inOrderLambdaQueryWrapper.eq(InOrder::getTrackingNumber, bo.getTrackingNumber())
        .ne(InOrder::getOrderId, bo.getOrderId());
    }

    long count = inOrderService.count(inOrderLambdaQueryWrapper);
    if (count > 0) {
      return R.fail("来源单号【" + bo.getTrackingNumber() + "】已存在，不允许重复添加！！");
    }

    if (bo.getDetailList().isEmpty()) {
      return R.fail("入库明细不能为空");
    }


    // 验证单号是否已推送
    LambdaQueryWrapper<InOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(InOrder::getTrackingNumber, bo.getTrackingNumber());
    InOrder storeOrderInfo = inOrderService.getOne(lambdaQueryWrapper);
    if (ObjectUtil.isNotNull(storeOrderInfo)) {
      Map<String, Object> result = new HashMap<>();
      result.put("orderId", storeOrderInfo.getOrderId());
      result.put("orderCode", storeOrderInfo.getOrderCode());
      return R.ok(bo.getTrackingNumber() + "已存在，不允许重复推送", result);
    }

    // 验证货主
    BaseConsignor consignorInfo = baseConsignorService.getByName(bo.getConsignorName());
    if (ObjectUtil.isNull(consignorInfo)) {
      return R.fail("货主不存在");
    }
    bo.setConsignorId(consignorInfo.getConsignorId());
    bo.setConsignorCode(consignorInfo.getConsignorCode());


    if (ObjectUtil.isNotNull(bo.getProviderShortName())) {
      // 验证供应商，如果不存在自动增加供应商
      BaseProvider providerInfo = baseProviderService.getByShortName(bo.getProviderShortName());
      if (ObjectUtil.isNull(providerInfo)) {
        providerInfo = new BaseProvider();
        providerInfo.setProviderCode(bo.getProviderCode());
        providerInfo.setProviderShortName(bo.getProviderShortName());
        providerInfo.setProviderName(bo.getProviderName());

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
      if (ObjectUtil.isNull(bo.getProviderShortName())) {
        bo.setProviderId(null);
        bo.setProviderCode(null);
        return R.fail("请联系管理员设置默认供应商");
      }
    }
    // 验证仓库
    BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
    if (ObjectUtil.isNull(storageInfo)) {
      return R.fail("仓库不存在");
    }
    bo.setStorageId(storageInfo.getStorageId());

    // 根据“部门名”带入“部门ID”
    if (ObjectUtil.isNotNull(bo.getNickName())) {
      SysDept deptInfo = sysDeptService.getByName(bo.getDeptName());
      if (ObjectUtil.isNotNull(deptInfo)) {
        bo.setDeptId(deptInfo.getDeptId());
        bo.setDeptName(deptInfo.getDeptName());
      }
    }
    for (var detailInfo : bo.getDetailList()) {
      BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
      if (ObjectUtil.isNull(prodInfo)) {
        return R.fail(detailInfo.getProductCode() + "商品编号不存在");
      }
      detailInfo.setQuantity(detailInfo.getQuantity());
      detailInfo.setProductId(prodInfo.getProductId());
      detailInfo.setProductModel(prodInfo.getProductModel());
      detailInfo.setProductName(prodInfo.getProductName());
      detailInfo.setProductSpec(prodInfo.getProductSpec());

      if (ObjectUtil.isNull(detailInfo.getQuantity()) || B.isLessOrEqual(detailInfo.getQuantity(), BigDecimal.ZERO)) {
        return R.fail(detailInfo.getProductModel() + "数量必须大于0");
      }
    }

    // 合计数量
    BigDecimal totalQuantityOrder = bo.getDetailList().stream().map(ApiInOrderDetailBo::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    // 合计重量

    // 接受预到货单时状态为审核成功
    boolean api_purchaseOrderStatusSuccess = sysConfigService.getConfigBool("api_purchaseOrderStatusSuccess");

    InOrder dataInfo = new InOrder();
    BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("orderId"));
    dataInfo.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1001));

    if (api_purchaseOrderStatusSuccess) {
      dataInfo.setAuditor(LoginHelper.getLoginUser().getNickname());
      dataInfo.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
      dataInfo.setAuditDate(new Date());
    } else {
      dataInfo.setOrderStatus(InOrderStatusEnum.NEWED.getName());
    }
    dataInfo.setTotalQuantity(totalQuantityOrder);
    dataInfo.setTotalWeight(BigDecimal.ZERO);
    dataInfo.setUserId(LoginHelper.getUserId());
    dataInfo.setNickName(LoginHelper.getNickname());
    dataInfo.setDeptId(LoginHelper.getDeptId());
    dataInfo.setDeptName(LoginHelper.getDeptName());
    dataInfo.setAuditing(InOrderStatusEnum.PENDING.getId());
    dataInfo.setCreateByName(bo.getCreateByName());
    dataInfo.setCreateTime(new Date());

    inOrderService.save(dataInfo);

    for (var detailInfo : bo.getDetailList()) {
      InOrderDetail detail = new InOrderDetail();
      BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("orderDetailId"));

      detail.setPurchaseAmount(B.mul(detail.getPurchasePrice(), detail.getQuantity()));

      // 计算大单位单价
      if (ObjectUtil.isNotNull(detail.getUnitConvert())) {
        BigDecimal bigUnitPrice = B.div(detailInfo.getPurchasePrice(), detailInfo.getUnitConvert());
//          this.ctx.helper.setExpandFields(detail, "bigUnitPrice", bigUnitPrice);
      }
      detail.setOrderId(dataInfo.getOrderId());
      // 需要根据主表把供应商数据带入
      detail.setProviderId(dataInfo.getProviderId());
      detail.setProviderShortName(dataInfo.getProviderShortName());
      inOrderDetailService.save(detail);
    }

    LambdaUpdateWrapper<InOrder> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(InOrder::getCreateBy, bo.getCreateBy())
      .set(InOrder::getCreateByName, bo.getCreateByName())
      .eq(InOrder::getOrderId, dataInfo.getOrderId());
    inOrderService.update(updateWrapper);

    Map<String, Object> result = new HashMap<>();
    result.put("orderId", dataInfo.getOrderId());
    result.put("orderCode", dataInfo.getOrderCode());
    return R.ok("订单保存成功", result);
  }


  @Override
  public R<Map<String, Object>> updateInOrder(ApiInOrderBo bo) {

    // 判断来源单号是否重复
    LambdaQueryWrapper<InOrder> inOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    inOrderLambdaQueryWrapper.eq(InOrder::getOrderId, bo.getOrderId());
    var inOrderServiceOnly = inOrderService.getOnly(inOrderLambdaQueryWrapper);

    if (ObjectUtil.isNull(inOrderServiceOnly)) {
      return R.fail("入库单不存在");
    }
    if (bo.getDetailList().isEmpty()) {
      return R.fail("入库明细不能为空");
    }

    try {
      // 验证货主
      BaseConsignor consignorInfo = baseConsignorService.getByName(bo.getConsignorName());
      if (ObjectUtil.isNull(consignorInfo)) {
        return R.fail("货主不存在");
      }
      bo.setConsignorId(consignorInfo.getConsignorId());
      bo.setConsignorCode(consignorInfo.getConsignorCode());


      if (ObjectUtil.isNotNull(bo.getProviderShortName())) {
        // 验证供应商，如果不存在自动增加供应商
        BaseProvider providerInfo = baseProviderService.getByShortName(bo.getProviderShortName());
        if (ObjectUtil.isNull(providerInfo)) {
          providerInfo = new BaseProvider();
          providerInfo.setProviderCode(bo.getProviderCode());
          providerInfo.setProviderShortName(bo.getProviderShortName());
          providerInfo.setProviderName(bo.getProviderName());

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
        if (ObjectUtil.isNull(bo.getProviderShortName())) {
          bo.setProviderId(null);
          bo.setProviderCode(null);
          return R.fail("请联系管理员设置默认供应商");
        }
      }
      // 验证仓库
      BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
      if (ObjectUtil.isNull(storageInfo)) {
        return R.fail("仓库不存在");
      }
      bo.setStorageId(storageInfo.getStorageId());

      // 根据“部门名”带入“部门ID”
      if (ObjectUtil.isNotNull(bo.getNickName())) {
        SysDept deptInfo = sysDeptService.getByName(bo.getDeptName());
        if (ObjectUtil.isNotNull(deptInfo)) {
          bo.setDeptId(deptInfo.getDeptId());
          bo.setDeptName(deptInfo.getDeptName());
        }
      }
      for (var detailInfo : bo.getDetailList()) {
        BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
        if (ObjectUtil.isNull(prodInfo)) {
          return R.fail(detailInfo.getProductCode() + "商品编号不存在");
        }
        detailInfo.setQuantity(detailInfo.getQuantity());
        detailInfo.setProductId(prodInfo.getProductId());
        detailInfo.setProductModel(prodInfo.getProductModel());
        detailInfo.setProductName(prodInfo.getProductName());
        detailInfo.setProductSpec(prodInfo.getProductSpec());

        if (ObjectUtil.isNull(detailInfo.getQuantity()) || B.isLessOrEqual(detailInfo.getQuantity(), BigDecimal.ZERO)) {
          return R.fail(detailInfo.getProductModel() + "数量必须大于0");
        }
      }

      // 合计数量
      BigDecimal totalQuantityOrder = bo.getDetailList().stream().map(ApiInOrderDetailBo::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合计重量

      // 接受预到货单时状态为审核成功
      boolean api_purchaseOrderStatusSuccess = sysConfigService.getConfigBool("api_purchaseOrderStatusSuccess");

      LambdaQueryWrapper<InOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(InOrder::getOrderId, bo.getOrderId());
      var dataInfo = inOrderService.getOnly(lambdaQueryWrapper);

      BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("orderId"));

      inOrderService.saveOrUpdate(dataInfo);

      for (var detailInfo : bo.getDetailList()) {

        LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper.eq(InOrderDetail::getOrderDetailId, detailInfo.getOrderDetailId());
        var detail = inOrderDetailService.getOnly(detailLambdaQueryWrapper);
        BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("orderDetailId"));

        detail.setPurchaseAmount(B.mul(detail.getPurchasePrice(), detail.getQuantity()));

        // 计算大单位单价
        if (ObjectUtil.isNotNull(detail.getUnitConvert())) {
          BigDecimal bigUnitPrice = B.div(detailInfo.getPurchasePrice(), detailInfo.getUnitConvert());
//          this.ctx.helper.setExpandFields(detail, "bigUnitPrice", bigUnitPrice);
        }
        detail.setOrderId(dataInfo.getOrderId());
        inOrderDetailService.saveOrUpdate(detail);
      }

      Map<String, Object> result = new HashMap<>();
      result.put("orderId", dataInfo.getOrderId());
      result.put("orderCode", dataInfo.getOrderCode());
      return R.ok("订单修改成功", result);
    } catch (Exception error) {
      return R.fail("推送订单失败，" + error.getMessage());
    }
  }
}
