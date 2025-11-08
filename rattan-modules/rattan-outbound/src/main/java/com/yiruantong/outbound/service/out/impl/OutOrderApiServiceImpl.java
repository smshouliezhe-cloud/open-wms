package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.base.BaseExpressCorp;
import com.yiruantong.basic.domain.client.BaseClient;
import com.yiruantong.basic.domain.client.BaseClientAddress;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.basic.service.base.IBaseExpressCorpService;
import com.yiruantong.basic.service.client.IBaseClientAddressService;
import com.yiruantong.basic.service.client.IBaseClientService;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.api.ApiOutOrderBo;
import com.yiruantong.outbound.domain.api.ApiOutOrderDetailBo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.service.out.IOutOrderApiService;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutSortingRuleService;
import com.yiruantong.outbound.service.service.IOutReturnDetailService;
import com.yiruantong.outbound.service.service.IOutReturnService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * 出库订单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@RequiredArgsConstructor
@Service
public class OutOrderApiServiceImpl implements IOutOrderApiService {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IBaseConsignorService baseConsignorService;
  private final IBaseClientService baseClientService;
  private final IBaseStorageService baseStorageService;
  private final ISysConfigService sysConfigService;
  private final IBaseProductService baseProductService;
  private final IBaseExpressCorpService baseExpressCorpService;
  private final IBaseClientAddressService baseClientAddressService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IOutReturnService outReturnService;
  private final IOutReturnDetailService outReturnDetailService;
  private final IOutSortingRuleService outSortingRuleService;

  //#region add API接口新增数据
  @Override
  public R<Map<String, Object>> add(ApiOutOrderBo bo) {

    if (bo.getDetailList().isEmpty()) {
      return R.fail("出库明细不能为空");
    }

    // 验证单号是否已推送
    OutOrder storeOrderInfo = outOrderService.getByStoreOrder(bo.getStoreOrderCode());
    if (ObjectUtil.isNotNull(storeOrderInfo)) {
      Map<String, Object> result = new HashMap<>();
      result.put("orderId", storeOrderInfo.getOrderId());
      result.put("orderCode", storeOrderInfo.getOrderCode());
      return R.ok(bo.getStoreOrderCode() + "已存在，不允许重复推送", result);
    }

    // 验证货主
    BaseConsignor consignorInfo = baseConsignorService.getByCode(bo.getConsignorCode());
    if (ObjectUtil.isNull(consignorInfo)) {
      return R.fail("货主不存在");
    }
    bo.setConsignorId(consignorInfo.getConsignorId());
    bo.setConsignorCode(consignorInfo.getConsignorCode());


    // 验证客户，如果不存在自动增加客户
    if (ObjectUtil.isNotNull(bo.getClientCode()) && ObjectUtil.isNotNull(bo.getClientShortName())) {
      BaseClient clientInfo = baseClientService.getByCode(bo.getClientCode());
      if (ObjectUtil.isNull(clientInfo)) {
        clientInfo = new BaseClient();
        clientInfo.setClientCode(bo.getClientCode());
        clientInfo.setClientShortName(bo.getClientShortName());
        clientInfo.setAddress(bo.getBillingAddress());
        clientInfo.setProvinceName(bo.getProvinceName());
        clientInfo.setCityName(bo.getCityName());
        clientInfo.setRegionName(bo.getRegionName());
        clientInfo.setShippingAddress(bo.getShippingAddress());
        clientInfo.setLineName(bo.getLineName());
        baseClientService.save(clientInfo);
      } else {
        // 修改地址省市区
        clientInfo.setAddress(bo.getBillingAddress());
        clientInfo.setProvinceName(bo.getProvinceName());
        clientInfo.setCityName(bo.getCityName());
        clientInfo.setRegionName(bo.getRegionName());
        baseClientService.saveOrUpdate(clientInfo);
      }
      bo.setClientId(clientInfo.getClientId());
    }

    // 验证仓库信息，如果推送仓库，走指定仓库，没有走系统某人仓库
    if (ObjectUtil.isNotNull(bo.getStorageName())) {
      BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
      if (ObjectUtil.isNotNull(storageInfo)) {
        bo.setStorageId(storageInfo.getStorageId());
      } else {
        // 清掉仓库
        bo.setStorageId(null);
        bo.setStorageName(null);
      }
    } else {
      // 接口推送时需要仓库
      boolean erp_storage_Id = sysConfigService.getConfigBool("erp_storage_Id");
      if (erp_storage_Id) {
        // 验证仓库
        BaseStorage storageInfo = baseStorageService.getByName(bo.getStorageName());
        if (ObjectUtil.isNull(storageInfo)) {
          return R.fail("仓库不存在");
        }
        bo.setStorageId(storageInfo.getStorageId());
      } else {
        // 清掉仓库
        bo.setStorageId(null);
        bo.setStorageName(null);
      }
    }

    // 下单时间
    if (ObjectUtil.isNull(bo.getApplyDate())) bo.setApplyDate(new Date());

//      let detailInfo: SaleOrderList;
    List<ApiOutOrderDetailBo> details = bo.getDetailList();
    for (var detailInfo : details) {
      BaseProduct prodInfo = baseProductService.getByCode(detailInfo.getProductCode());
      if (ObjectUtil.isNull(prodInfo)) {
        return R.fail(detailInfo.getProductCode() + "商品编号不存在");
      }
      detailInfo.setQuantityOrder(detailInfo.getQuantityOrder());
      detailInfo.setProductId(prodInfo.getProductId());
      detailInfo.setProductModel(prodInfo.getProductModel());
      detailInfo.setProductName(prodInfo.getProductName());
      detailInfo.setProductSpec(prodInfo.getProductSpec());
      if (ObjectUtil.isNotNull(detailInfo.getWeight())) {
        detailInfo.setWeight(detailInfo.getWeight());
      } else {
        detailInfo.setWeight(prodInfo.getWeight());
      }
      if (ObjectUtil.isNotNull(detailInfo.getRowWeight())) {
        detailInfo.setRowWeight(detailInfo.getRowWeight());
      } else {
        detailInfo.setRowWeight(B.mul(detailInfo.getWeight(), detailInfo.getQuantityOrder()));
      }

      detailInfo.setUnitCube(prodInfo.getUnitCube());
      detailInfo.setRowCube(B.mul(prodInfo.getUnitCube(), detailInfo.getQuantityOrder()));


//        if (!isNumber(detailInfo.quantityOrder)) {
//          this.info.result = false;
//          this.info.msg = "条码【" + detailInfo.productModel + "】对应的出库数量quantityOrder必须为数字";
//          ctx.body = this.info;
//          return;
//        }
      if (ObjectUtil.isNull(detailInfo.getQuantityOrder()) || B.isLessOrEqual(detailInfo.getQuantityOrder(), BigDecimal.ZERO)) {
        return R.fail(detailInfo.getProductModel() + "数量必须大于0");
      }
    }

    // 主表求和字段计算
    //#endregion
    // 合计数量
    BigDecimal totalQuantityOrder = bo.getDetailList().stream().map(ApiOutOrderDetailBo::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
    // 合计重量
    BigDecimal totalWeight = bo.getDetailList().stream().map(ApiOutOrderDetailBo::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);

    OutOrder dataInfo = new OutOrder();
    BeanUtil.copyProperties(bo, dataInfo, new CopyOptions().setIgnoreProperties("orderId"));
    dataInfo.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671));
    dataInfo.setOrderStatus(OutOrderStatusEnum.AUDIT_WAITING.getName());
    if (ObjectUtil.isNotNull(bo.getTotalQuantityOrder())) {
      dataInfo.setTotalQuantityOrder(bo.getTotalQuantityOrder());
    } else {
      dataInfo.setTotalQuantityOrder(totalQuantityOrder);
    }
    if (ObjectUtil.isNotNull(bo.getTotalWeight())) {
      dataInfo.setTotalWeight(bo.getTotalWeight());
    } else {
      dataInfo.setTotalWeight(totalWeight);
    }
    dataInfo.setTotalAmount(dataInfo.getTotalAmount());


    // 未付金额（应收金额）
    if (ObjectUtil.isNull(dataInfo.getTotalUnpaid())) {
      dataInfo.setTotalUnpaid(dataInfo.getTotalProductAmount());
    }

    // 城配字段
    if (ObjectUtil.isNotNull(bo.getDistributionType())) {
      dataInfo.setDistributionType(bo.getDistributionType());
    } else {
      dataInfo.setDistributionType("城配");
    }


    if (ObjectUtil.isNotNull(dataInfo.getExpressCorpName())) {
      BaseExpressCorp corpInfo = baseExpressCorpService.getByName(dataInfo.getExpressCorpName());
      if (ObjectUtil.isNotNull(corpInfo)) {
        dataInfo.setExpressCorpId(corpInfo.getExpressCorpId());
        dataInfo.setExpressCorpName(corpInfo.getExpressCorpName());
        dataInfo.setExpressCorpType(Convert.toByte(corpInfo.getExpressCorpType()));
      }
    }
    // 接口默认快递类型、快递公司
    boolean erp_expressCorp_Id = sysConfigService.getConfigBool("erp_expressCorp_Id");
    if (erp_expressCorp_Id && ObjectUtil.isNull(dataInfo.getExpressCorpName())) {
      BaseExpressCorp corpInfo = baseExpressCorpService.getById(erp_expressCorp_Id);
      if (ObjectUtil.isNotNull(corpInfo)) {
        dataInfo.setExpressCorpId(Convert.toLong(erp_expressCorp_Id));
        dataInfo.setExpressCorpName(corpInfo.getExpressCorpName());
        dataInfo.setExpressCorpType(Convert.toByte(corpInfo.getExpressCorpType()));
      }
    }

    // 验证客户，如果不存在自动增加客户
    if (ObjectUtil.isNotNull(bo.getClientCode()) && ObjectUtil.isNotNull(bo.getClientShortName())) {
      BaseClient clientInfo = baseClientService.getByCode(bo.getClientCode());
      if (ObjectUtil.isNotNull(clientInfo)) {
//          BeanUtil.copyProperties(clientInfo, dataInfo); // 复制

        // 负责人
        if (ObjectUtil.isNotNull(dataInfo.getShippingName())) {
          dataInfo.setShippingName(dataInfo.getShippingName());
        } else {
          dataInfo.setShippingName(clientInfo.getShippingName());
        }
        // 门店地址
        if (ObjectUtil.isNotNull(dataInfo.getShippingAddress())) {
          dataInfo.setShippingAddress(dataInfo.getShippingAddress());
        } else {
          dataInfo.setShippingAddress(clientInfo.getShippingAddress());
        }
        // 联系人
        if (ObjectUtil.isNotNull(dataInfo.getMobile())) {
          dataInfo.setMobile(dataInfo.getMobile());
        } else {
          dataInfo.setMobile(clientInfo.getMobile());
        }
        // 电话
        if (ObjectUtil.isNotNull(dataInfo.getTelephone())) {
          dataInfo.setTelephone(dataInfo.getTelephone());
        } else {
          dataInfo.setTelephone(clientInfo.getTelephone());
        }
        // 省份
        if (ObjectUtil.isNotNull(dataInfo.getProvinceName())) {
          dataInfo.setProvinceName(dataInfo.getProvinceName());
        } else {
          dataInfo.setProvinceName(clientInfo.getProvinceName());
        }
        // 城市
        if (ObjectUtil.isNotNull(dataInfo.getCityName())) {
          dataInfo.setCityName(dataInfo.getCityName());
        } else {
          dataInfo.setCityName(clientInfo.getCityName());
        }
        // 区县
        if (ObjectUtil.isNotNull(dataInfo.getRegionName())) {
          dataInfo.setRegionName(dataInfo.getRegionName());
        } else {
          dataInfo.setRegionName(clientInfo.getRegionName());
        }
        // email
        if (ObjectUtil.isNotNull(dataInfo.getEmail())) {
          dataInfo.setEmail(dataInfo.getEmail());
        } else {
          dataInfo.setEmail(clientInfo.getEmail());
        }
        if (ObjectUtil.isNotNull(clientInfo.getShippingName())
          && ObjectUtil.isNotNull(clientInfo.getProvinceName())
          && ObjectUtil.isNotNull(clientInfo.getCityName())
          && ObjectUtil.isNotNull(clientInfo.getRegionName())
          && ObjectUtil.isNotNull(clientInfo.getMobile())
          && ObjectUtil.isNotNull(clientInfo.getShippingAddress())
        ) {
          LambdaQueryWrapper<BaseClientAddress> queryWrapper = new LambdaQueryWrapper<>();
          queryWrapper.eq(BaseClientAddress::getName, clientInfo.getShippingAddress())
            .eq(BaseClientAddress::getProvinceName, clientInfo.getProvinceName())
            .eq(BaseClientAddress::getCityName, clientInfo.getCityName())
            .eq(BaseClientAddress::getRegionName, clientInfo.getRegionName())
            .eq(BaseClientAddress::getMobile, clientInfo.getMobile())
            .eq(BaseClientAddress::getAddress, clientInfo.getShippingAddress());
          BaseClientAddress clientAddress = baseClientAddressService.getOnly(queryWrapper);
          if (ObjectUtil.isNull(clientAddress)) {
            //不存在保存
            clientAddress = new BaseClientAddress();
            clientAddress.setProvinceName(clientInfo.getProvinceName());
            clientAddress.setCityName(clientInfo.getCityName());
            clientAddress.setRegionName(clientInfo.getRegionName());
            clientAddress.setMobile(clientInfo.getMobile());
            clientAddress.setAddress(clientInfo.getShippingAddress());
            clientAddress.setEmail(clientInfo.getEmail());
            baseClientAddressService.save(clientAddress);
          }
        }
      }
    }

    outOrderService.save(dataInfo);

    BigDecimal bigQtyTotal = BigDecimal.ZERO;
    for (var detailInfo : bo.getDetailList()) {
      OutOrderDetail detail = new OutOrderDetail();
      BeanUtil.copyProperties(detailInfo, detail, new CopyOptions().setIgnoreProperties("orderDetailId")); // 复制忽略ID
      detail.setOrderId(dataInfo.getOrderId());


      if (ObjectUtil.isNotNull(detailInfo.getBigQty())) {
        bigQtyTotal = B.adds(detailInfo.getBigQty());
        detail.setBigQty(detailInfo.getBigQty());
      }
      if (ObjectUtil.isNull(detail.getSaleAmount())) {
        detail.setSaleAmount(B.mul(detailInfo.getQuantityOrder(), detailInfo.getSalePrice()));
      }
      outOrderDetailService.save(detail);

      // 新增一条规则
      OutSortingRule outSortingRule = new OutSortingRule();
      outSortingRule.setOrderId(dataInfo.getOrderId());
      outSortingRule.setOrderCode(dataInfo.getOrderCode());
      outSortingRule.setOrderDetailId(detail.getOrderDetailId());
      outSortingRule.setProductId(detail.getProductId());
      outSortingRule.setProductCode(detail.getProductCode());
      if (ObjectUtil.isNotNull(detail.getPositionName())) {
        outSortingRule.setPositionName(detail.getPositionName());
      }
      if (ObjectUtil.isNotNull(detail.getBatchNumber())) {
        outSortingRule.setBatchNumber(detail.getBatchNumber());
      }
      if (ObjectUtil.isNotNull(detail.getProduceDate())) {
        outSortingRule.setProduceDate(detail.getProduceDate());
      }
      outSortingRuleService.save(outSortingRule);
    }
    if (ObjectUtil.isNotNull(dataInfo.getOrderId())) {
      dataInfo.setBigQtyTotal(bigQtyTotal);
      outOrderService.saveOrUpdate(dataInfo);
    }


    Map<String, Object> result = new HashMap<>();
    result.put("orderId", dataInfo.getOrderId());
    result.put("orderCode", dataInfo.getOrderCode());
    result.put("createTime", dataInfo.getCreateTime());
    return R.ok("订单保存成功", result);

  }

  //#endregion


  //#region 取消出库单

  /**
   * 取消出库单
   *
   * @param bo
   * @return
   */
  @Override
  public R<Map<String, Object>> cancel(ApiOutOrderBo bo) {
    try {
      //#region 校验数据
      LambdaQueryWrapper<OutOrder> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(OutOrder::getStoreOrderCode, bo.getStoreOrderCode())
        .eq(OutOrder::getConsignorCode, bo.getConsignorCode());
      OutOrder orderInfo = outOrderService.getOnly(queryWrapper);
      if (ObjectUtil.isNull(orderInfo)) {
        return R.fail("出库单不存在");
      }


      List<String> statusList = Arrays.asList(OutOrderStatusEnum.PACKAGE_PARTIAL.getName(), OutOrderStatusEnum.PACKAGE_FINISHED.getName(), OutOrderStatusEnum.SHIPMENT_PARTIAL.getName(), OutOrderStatusEnum.SHIPMENT_FINISHED.getName());
      if (statusList.contains(orderInfo.getOrderStatus())) {
        throw new ServiceException(orderInfo.getOrderStatus() + "不允许取消！");
      }
      //#endregion

      //#region 取消操作
      // 清除占位
      coreInventoryHolderService.clearHolder(List.of(HolderSourceTypeEnum.OUT_ORDER_NORMAL, HolderSourceTypeEnum.PC_OUT_PLAN, HolderSourceTypeEnum.OUT_PICKING), orderInfo.getOrderId());

      // 更新订单状态为：用户取消
      LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrder::getOrderStatus, OutOrderStatusEnum.CANCEL.getName()) // 用户取消
        .eq(OutOrder::getOrderId, orderInfo.getOrderId());
      outOrderService.update(queryWrapper);
      //#endregion

      return R.ok("取消成功");
    } catch (Exception error) {
      return R.fail("推送订单失败，" + error.getMessage());
    }
  }
  //#endregion


  //#region 取消出库单

  /**
   * 更新出库单订单状态
   *
   * @param bo
   * @return
   */
  @Override
  public R<Map<String, Object>> updateOrderStatus(ApiOutOrderBo bo) {
    try {
      //#region 校验数据
      LambdaQueryWrapper<OutOrder> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(OutOrder::getOrderId, bo.getOrderId());
      OutOrder orderInfo = outOrderService.getOnly(queryWrapper);
      if (ObjectUtil.isNull(orderInfo)) {
        return R.fail("出库单不存在");
      }

      // 更新订单状态
      LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrder::getOrderStatus, bo.getOrderStatus()) //
        .eq(OutOrder::getOrderId, orderInfo.getOrderId());
      outOrderService.update(updateWrapper);
      //#endregion

      return R.ok("状态修改成功");
    } catch (Exception error) {
      return R.fail("修改订单状态失败，" + error.getMessage());
    }
  }
  //#endregion


}
