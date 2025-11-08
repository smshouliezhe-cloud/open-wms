package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.tool.MpjWrapperHelper;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 无单出库单扫描service
 *
 * @author xieti
 */
@RequiredArgsConstructor
@Service
public class OutScanNoBillService implements IOutScanNoBillService {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryService coreInventoryService;
  private final IBaseProductService baseProductService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IOutScanOrderService outScanOrderService;
  private final IOutOrderSortingService outOrderSortingService;
  private final MpjWrapperHelper mpjWrapperHelper;
  private final IOutSortingRuleService outSortingRuleService;

  @Override
  public R<List<CoreInventoryComposeVo>> getNoBillProduct(Map<String, Object> map) {
    String productModel = Convert.toStr(map.get("productModel"));
    Long storageId = Convert.toLong(map.get("storageId"));
    Long consignorId = Convert.toLong(map.get("consignorId"));
    String positionName = Convert.toStr(map.get("positionName"));

    List<CoreInventoryComposeVo> inventoryComposeVoList;
    if (StringUtils.isNotEmpty(productModel)) {
      LambdaQueryWrapper<BaseProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
      productLambdaQueryWrapper.like(BaseProduct::getProductModel, productModel)
        .or()
        .like(BaseProduct::getProductCode, productModel)
        .last("limit 1");
      BaseProduct productInfo = baseProductService.getOne(productLambdaQueryWrapper);
      Assert.isFalse(ObjectUtil.isNull(productInfo), "扫描的条码在商品信息中不存在，请完善好基础数据！");
      if (!B.isGreater(storageId)) {
        storageId = productInfo.getStorageId();
      }
      if (StrUtil.isEmpty(positionName) || B.isEqual(positionName, "")) {
        positionName = productInfo.getPositionName();
      }
      inventoryComposeVoList = coreInventoryService.selectValidInventoryList(storageId, consignorId, productInfo.getProductId(), positionName);
    } else {
      inventoryComposeVoList = coreInventoryService.selectValidInventoryList(storageId, positionName);
    }

    List<CoreInventoryComposeVo> resultList = new ArrayList<>();
    //先根据id分组
    Map<Long, List<CoreInventoryComposeVo>> groupMap = inventoryComposeVoList.stream().collect(Collectors.groupingBy(CoreInventory::getProductId));
    //分组后：有效库求和
    groupMap.keySet().forEach(key -> {
      CoreInventoryComposeVo single = new CoreInventoryComposeVo();
      List<CoreInventoryComposeVo> listInfo = groupMap.get(key);
      BeanUtil.copyProperties(listInfo.get(0), single);
      BaseProduct baseProduct = baseProductService.getById(single.getProductId());
      single.setBigUnit(baseProduct.getBigUnit());
      single.setUnitConvert(baseProduct.getUnitConvert());

      //有效库求和
      BigDecimal totalValidStorage = listInfo.stream().map(CoreInventoryComposeVo::getValidStorage).reduce(BigDecimal.ZERO, BigDecimal::add);
      if (B.isGreater(totalValidStorage)) {
        single.setValidStorage(totalValidStorage);
        resultList.add(single);
      }
    });

    return R.ok(resultList);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<OutOrder> noBillOutSave(OutScanMainBo outScanMainBo) {
    Assert.isFalse(ObjectUtil.isNull(outScanMainBo.getStorageId()), "请选择仓库！");
    Long consignorId = outScanMainBo.getConsignorId();

    if (ObjectUtil.isEmpty(consignorId)) {
      consignorId = outScanMainBo.getDataList().get(0).getConsignorId();
      outScanMainBo.setConsignorId(outScanMainBo.getDataList().get(0).getConsignorId());
      outScanMainBo.setConsignorCode(outScanMainBo.getDataList().get(0).getConsignorCode());
      outScanMainBo.setConsignorName(outScanMainBo.getDataList().get(0).getConsignorName());
    }
    Assert.isFalse(ObjectUtil.isNull(consignorId), "货主不能为空！");

    //#region 主表处理
    OutOrder outOrder = new OutOrder();
    // 对象数据的拷贝
    BeanUtil.copyProperties(outScanMainBo, outOrder);
    outOrder.setOrderCode(DBUtils.getCodeRegular(MenuEnum.MENU_1671));
    if (ObjectUtil.isNotEmpty(outScanMainBo.getHolderSourceTypeEnum()) && outScanMainBo.getHolderSourceTypeEnum().getName().equals(HolderSourceTypeEnum.PC_STACKING_IN.getName())) {
      outOrder.setOrderType(OutOrderTypeEnum.STOREHOUSE_STACKING.getName());
      outOrder.setSourceType(OutOrderTypeEnum.STOREHOUSE_STACKING.getName());
    } else {
      outOrder.setOrderType(OutOrderTypeEnum.NO_BILL.getName());
      outOrder.setSourceType(OutOrderTypeEnum.NO_BILL.getName());
    }

    outOrder.setSortingStatus(SortingStatusEnum.NONE.getId());
    outOrder.setOrderStatus(OutOrderStatusEnum.AUDIT_WAITING.getName());
    outOrder.setAuditing(AuditEnum.AUDIT.getId());

    outOrder.setNickName(LoginHelper.getNickname());
    outOrder.setUserId(LoginHelper.getUserId());
    outOrderService.save(outOrder);
    outScanMainBo.setOrderId(outOrder.getOrderId());
    outScanMainBo.setOrderCode(outOrder.getOrderCode());
    //#endregion

    //#region 明细处理
    List<OutOrderDetail> outOrderDetailList = new ArrayList<>();
    for (OutScanDetailBo detail : outScanMainBo.getDataList()) {
      Long productId = detail.getProductId();
      BigDecimal finishedQuantity = detail.getFinishedQuantity(); // 扫描完成的数量

      OutOrderDetail detailInfo = new OutOrderDetail();
      BaseProduct baseProduct = baseProductService.getById(productId);
      // 对象数据的拷贝
      BeanUtil.copyProperties(baseProduct, detailInfo);
      BeanUtil.copyProperties(detail, detailInfo);

      detailInfo.setOrderId(outOrder.getOrderId());
      detailInfo.setSortingStatus(SortingStatusEnum.NONE.getId());
      detailInfo.setQuantityOrder(finishedQuantity);

      BigDecimal hundred = new BigDecimal(100);
      BigDecimal thousand = new BigDecimal(1000);

      BigDecimal rate = B.div(baseProduct.getRate(), hundred); // 税率小数点除100
      BigDecimal ratePrice = B.mul(baseProduct.getSalePrice(), rate);
      detailInfo.setRatePrice(B.add(ratePrice, baseProduct.getSalePrice())); // 税价（含税金额）
      detailInfo.setRateAmount(B.mul(detailInfo.getRatePrice(), finishedQuantity)); // 含税金额

      BigDecimal rowWeight = B.mul(detailInfo.getWeight(), finishedQuantity);
      detailInfo.setRowWeight(rowWeight); // 小计重量
//      detailInfo.setRowWeightTon(B.div(rowWeight, thousand)); // 小计重量（吨）
      detailInfo.setRowCube(B.mul(detailInfo.getQuantityOrder(), detailInfo.getUnitCube())); // 小计体积
      detailInfo.setRowNetWeight(rowWeight); // 小计重量
      detailInfo.setBigQty(B.ceiling(B.div(detailInfo.getQuantityOrder(), baseProduct.getUnitConvert()))); // 大单位数量
      detailInfo.setSaleAmount(B.mul(finishedQuantity, detailInfo.getSalePrice()));

      outOrderDetailService.save(detailInfo);
      outOrderDetailList.add(detailInfo);

      // 增加货位规则
      OutSortingRule outSortingRule = new OutSortingRule();
      // outSortingRule.setBatchNumber(detailInfo.getBatchNumber());
      outSortingRule.setConsignorId(detailInfo.getConsignorId());
      outSortingRule.setConsignorCode(Convert.toStr(detailInfo.getConsignorCode()));
      outSortingRule.setConsignorName(Convert.toStr(detailInfo.getConsignorName()));
      outSortingRule.setCreateTime(new Date());
      outSortingRule.setOrderId(Convert.toLong(detailInfo.getOrderId()));
      outSortingRule.setOrderDetailId(Convert.toLong(detailInfo.getOrderDetailId()));
      outSortingRule.setOrderCode(Convert.toStr(outOrder.getOrderCode()));
      // outSortingRule.setPlateCode(Convert.toStr(detailInfo.getPlateCode()));
      outSortingRule.setPositionName(Convert.toStr(detailInfo.getPositionName()));
      outSortingRule.setProductId(Convert.toLong(detailInfo.getProductId()));
      outSortingRule.setProductCode(Convert.toStr(detailInfo.getProductCode()));
      // outSortingRule.setSingleSignCode(Convert.toStr(detailInfo.getSingleSignCode()));
      //      outSortingRule.setInventoryId(Convert.toLong(detailInfo.getInv()));
      //      outSortingRule.setStorageId(Convert.toLong(detailInfo.getStorageId()));
      //      outSortingRule.setStorageName(Convert.toStr(map.get("storageName")));
      outSortingRuleService.save(outSortingRule);
      // 复制扫描明细
      detail.setOrderId(detailInfo.getOrderId());
      detail.setOrderDetailId(detailInfo.getOrderDetailId());
    }
    //#endregion

    //#region  主表求和
    outOrder.setTotalQuantityOrder(StreamUtils.sum(outOrderDetailList, OutOrderDetail::getQuantityOrder));
    outOrder.setTotalRateAmount(StreamUtils.sum(outOrderDetailList, OutOrderDetail::getRateAmount));
    outOrder.setTotalWeight(StreamUtils.sum(outOrderDetailList, OutOrderDetail::getRowWeight));
    outOrder.setTotalAmount(StreamUtils.sum(outOrderDetailList, OutOrderDetail::getSaleAmount));
    outOrder.setTotalNetWeight(StreamUtils.sum(outOrderDetailList, OutOrderDetail::getNetWeight));
    outOrder.setTotalCube(StreamUtils.sum(outOrderDetailList, OutOrderDetail::getRowCube));
    outOrderService.getBaseMapper().updateById(outOrder);
    //#endregion

    // 生成新建出库单的轨迹
    outOrder.setOrderStatus(null);
    outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.PC_NO_BILL, OutOrderStatusEnum.AUDIT_WAITING);

    outOrder.setOrderStatus(OutOrderStatusEnum.AUDIT_SUCCESS.getName());
    outOrder.setAuditing(AuditEnum.AUDITED_SUCCESS.getId());
    outOrder.setAuditDate(DateUtil.date());
    outOrder.setAuditor(LoginHelper.getNickname());
    outOrderService.updateById(outOrder);

    // 分拣出库单
    R<Void> sorting = outOrderSortingService.sorting(outOrder.getOrderId(), false);
    if (!sorting.isResult()) {
      throw new ServiceException(sorting.getMsg());
    }

    // 调用通用出库操作
    return outScanOrderService.normalOutSave(outScanMainBo);
  }
}
