package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutWaveOperationTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailSendBatchVo;
import com.yiruantong.outbound.domain.order.OutSendBill;
import com.yiruantong.outbound.domain.out.*;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.domain.out.vo.OutPackageDetailComposeVo;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.order.IOutSendBillService;
import com.yiruantong.outbound.service.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderScanSendBatchService implements IOrderScanSendBatchService {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderWaveService outOrderWaveService;
  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final IOutSendBillService sendBillService;
  private final IOutPackageDetailService outPackageDetailService;
  private final IOutPackageService outPackageService;

  //#region 闪电发货效验 - 获取波次下的订单数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> getSendBatchData(Map<String, Object> map) {
    String orderPrintCode = Convert.toStr(map.get("orderPrintCode"));
    Assert.isFalse(ObjectUtil.isEmpty(orderPrintCode), "波次单号不能为空！");

    if (StringUtils.isEmpty(orderPrintCode)) {
      throw new ServiceException("波次不能为空！");
    }
    String mainWaveCode = orderPrintCode; // 主波次号
    //区分主波次 子波次
    if (orderPrintCode.contains("-")) {
      var arr = orderPrintCode.split("-");
      mainWaveCode = arr[0];
    }
    var orderWave = outOrderWaveService.getByCode(mainWaveCode);
    if (ObjectUtil.isEmpty(orderWave)) {
      throw new ServiceException("波次不存在！");
    }

    // 查询字段
    String selectFields = "bigBarcode,unitConvert,productName,productCode,productModel,quantityOrder,smallUnit,bigUnit,isManageSn";
    // 求和字段
    String sumFields = "quantityOrder";
    // 分组字段
    String groupFields = "productName,productCode,productModel,orderId";

    // 构建联表查询
    MPJLambdaWrapper<OutOrderWaveDetail> wrapper = new MPJLambdaWrapper<>();
    wrapper
      .innerJoin(OutOrderWave.class, OutOrderWave::getOrderWaveId, OutOrderWaveDetail::getOrderWaveId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderWaveDetail::getProductId)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderWaveDetail::getOrderId)
      .innerJoin(OutOrderDetail.class, OutOrderDetail::getOrderDetailId, OutOrderWaveDetail::getOrderDetailId)
      .eq(OutOrderWave::getOrderWaveCode, orderPrintCode);

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, OutOrderWaveDetail.class, OutOrderWave.class, BaseProduct.class, CoreInventoryHolder.class, BasePosition.class);
    List<OutOrderWaveDetailSendBatchVo> inventoryComposeVoList = outOrderWaveDetailService.selectJoinList(OutOrderWaveDetailSendBatchVo.class, wrapper);
    String productCode = inventoryComposeVoList.stream().filter(f -> B.isEqual(f.getIsManageSn(), EnableEnum.ENABLE.getId())).findFirst().map(OutOrderWaveDetailSendBatchVo::getProductCode).orElse(null);
    if (ObjectUtil.isNotNull(productCode)) {
      return R.fail(productCode + "存在SN不允许闪电发货");
    }

    Map<String, Object> result = new HashMap<>();
    result.put("dataList", inventoryComposeVoList);

    return R.ok(result);
  }
  //endregion

  //#region 闪电发货效验 - 保存波次下的订单数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> saveSendBatchData(OutScanMainBo outScanMainBo) {
    String orderWaveCode = outScanMainBo.getOrderWaveCode();
    OutOrderWave outOrderWave = outOrderWaveService.getByCode(orderWaveCode);
    Assert.isFalse(ObjectUtil.isNull(outOrderWave), outOrderWave.getOrderWaveCode() + "波次单不存在！");
    List<OutOrder> outOrderList = outOrderService.selectByOrderWaveId(outOrderWave.getOrderWaveId());

    for (OutOrder orderOrder : outOrderList) {
      OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(orderOrder, OutScanMainBo.class);
      newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.PC_SEND_BATCH);
      newOutScanOrderBo.setHolderSourceTypeEnum(HolderSourceTypeEnum.OUT_ORDER_NORMAL);

      List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(orderOrder.getOrderId()); // 订单明细集合
      newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细
      for (var outOrderDetail : outOrderDetailList) {
        OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
        outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
        newOutScanOrderBo.getDataList().add(outScanDetailBo);
      }
      IOutScanOrderService outScanOrderService = SpringUtils.getBean(IOutScanOrderService.class);
      R<OutOrder> outResult = outScanOrderService.normalOutSave(newOutScanOrderBo);

      // 打包完成后，执行发运完成操作
      if (ObjectUtil.equals(outResult.getData().getOrderStatus(), OutOrderStatusEnum.PACKAGE_FINISHED.getName())) {
        // 生成发货单

        IOutOrderStatusHistoryService outOrderStatusHistoryService = SpringUtils.getBean(IOutOrderStatusHistoryService.class);
        IOutOrderWaveStatusHistoryService outOrderWaveStatusHistoryService = SpringUtils.getBean(IOutOrderWaveStatusHistoryService.class);
        // 生成出库单
        OutSendBill outSendBill = new OutSendBill();
        BeanUtil.copyProperties(orderOrder, outSendBill);
        sendBillService.save(outSendBill);

        // 发运完成
        outOrderService.updateOrderStatus(orderOrder.getOrderId(), OutOrderStatusEnum.SHIPMENT_FINISHED);
        // 生成发运完成轨迹
        orderOrder.setOrderStatus(OutOrderStatusEnum.PACKAGE_FINISHED.getName());
        outOrderStatusHistoryService.AddHistory(orderOrder, OutOperationTypeEnum.PC_SEND_BATCH, OutOrderStatusEnum.SHIPMENT_FINISHED);


        OutOrderWave orderWave = outOrderWaveService.getById(orderOrder.getOrderWaveId());
        if (ObjectUtil.isNotEmpty(orderWave)) {

          // 发运完成
          orderWave.setWaveStatus(OutOrderStatusEnum.SHIPMENT_FINISHED.getName());
          // 生成发运完成轨迹
          outOrderWave.setWaveStatus(OutOrderStatusEnum.PACKAGE_FINISHED.getName());
          // 生成波次的轨迹
          outOrderWaveStatusHistoryService.AddHistory(outOrderWave, OutWaveOperationTypeEnum.PC_SEND_BATCH, OutOrderStatusEnum.SHIPMENT_FINISHED);
          outOrderWaveService.getBaseMapper().updateById(orderWave);

        }
      }


      LambdaUpdateWrapper<OutPackage> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutPackage::getTotalPackage, outScanMainBo.getOrderNumber())
        .eq(OutPackage::getOrderCode, orderOrder.getOrderCode());
      outPackageService.update(updateWrapper);

      // 更新状态
      LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper.set(OutOrder::getFactWeight, outScanMainBo.getFactWeight())
        .eq(OutOrder::getOrderCode, orderOrder.getOrderCode());
      outOrderService.update(orderLambdaUpdateWrapper);
    }

    return R.ok("闪电发货完成");
  }
  //#endregion


  //#region 发货校验 - 获取订单数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> getCheckExpressCode(Map<String, Object> map) {
    String expressCode = Convert.toStr(map.get("expressCode"));
    Assert.isFalse(ObjectUtil.isEmpty(expressCode), map.get("scanType") + "单号不能为空！");

    // 构建联表查询
    MPJLambdaWrapper<OutPackageDetail> wrapper = new MPJLambdaWrapper<>();
    wrapper
      .selectAll(OutPackage.class)
      .innerJoin(OutPackage.class, OutPackage::getPackageId, OutPackageDetail::getPackageId)
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutPackage::getOrderId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutPackageDetail::getProductId)
      .eq(OutOrder::getOrderStatus, OutOrderStatusEnum.PACKAGE_FINISHED.getName());

    if (Convert.toBool(map.get("isNoCheckExpressCorp"))) {
      //不校验快递公司
      //#region
      if (B.isEqual(Convert.toStr(map.get("scanType")), "快递单号")) {
        //快递单号
        wrapper.eq(OutOrder::getExpressCode, expressCode);
      } else if (B.isEqual(Convert.toStr(map.get("scanType")), "出库单号")) {
        //出库单号
        wrapper.eq(OutOrder::getOrderCode, expressCode);
      } else if (B.isEqual(Convert.toStr(map.get("scanType")), "波次号")) {
        //波次号
        wrapper.eq(OutOrder::getOrderWaveCode, expressCode);
      }
      //#endregion
    } else {
      //不校验快递公司
      //#region
      if (B.isEqual(Convert.toStr(map.get("scanType")), "快递单号")) {
        LambdaQueryWrapper<OutOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OutOrder::getExpressCode, expressCode).last("limit 1");
        OutOrder outOrder = outOrderService.getOne(lambdaQueryWrapper);

        if (ObjectUtil.isNotEmpty(outOrder)) {
          if (!ObjectUtil.equals(Convert.toLong(map.get("expressCorpId")), outOrder.getExpressCorpId())) {
            throw new ServiceException("扫描的出库单快递不一致！");
          }
        }
        //快递单号
        wrapper.eq(OutOrder::getExpressCode, expressCode);
      } else if (B.isEqual(Convert.toStr(map.get("scanType")), "出库单号")) {

        OutOrder outOrder = outOrderService.getByCode(expressCode);
        if (ObjectUtil.isNotEmpty(outOrder)) {
          if (!ObjectUtil.equals(Convert.toLong(map.get("expressCorpId")), outOrder.getExpressCorpId())) {
            throw new ServiceException("扫描的出库单快递不一致！");
          }
        }
        //出库单号
        wrapper.eq(OutOrder::getOrderCode, expressCode);
      } else if (B.isEqual(Convert.toStr(map.get("scanType")), "波次号")) {
        OutOrderWave outOrderWave = outOrderWaveService.getByCode(expressCode);
        if (ObjectUtil.isNotEmpty(outOrderWave)) {
          if (!ObjectUtil.equals(Convert.toLong(map.get("expressCorpId")), outOrderWave.getExpressCorpId())) {
            throw new ServiceException("扫描的出库单快递不一致！");
          }
        }
        //波次号
        wrapper.eq(OutOrder::getOrderWaveCode, expressCode);
      }
      //#endregion
    }

    List<OutPackageDetailComposeVo> inventoryComposeVoList = outPackageDetailService.selectJoinList(OutPackageDetailComposeVo.class, wrapper);
    Assert.isFalse(B.isEqual(inventoryComposeVoList.size()), "没有可操作的数据！");
    Map<String, Object> result = new HashMap<>();
    result.put("dataList", inventoryComposeVoList);


    return R.ok(result);
  }
  //endregion


  //#region 发货校验 - 效验订单
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> orderSave(Map<String, Object> map) {
    IOutOrderWaveStatusHistoryService outOrderWaveStatusHistoryService = SpringUtils.getBean(IOutOrderWaveStatusHistoryService.class);

    String expressCode = Convert.toStr(map.get("expressCode"));
    OutPackage orderOrder = outPackageService.getById(Convert.toLong(map.get("packageId")));
    OutOrder outOrder = outOrderService.getByCode((String) map.get("orderCode"));

    //#region
    if (B.isEqual(Convert.toStr(map.get("scanType")), "快递单号")) {
      this.updateOutOrder(outOrder);
      //修改波次明细数据
      OutOrderWave outOrderWave = outOrderWaveService.getByCode(outOrder.getOrderWaveCode());
      if (ObjectUtil.isEmpty(outOrderWave)) {
        throw new ServiceException("未生成波次单！");
      }
      this.updateOutOrderWaveDetail(outOrderWave, outOrder.getOrderCode());

      // 更新状态
      LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrder::getFactWeight, Convert.toBigDecimal(map.get("weight")))
        .eq(OutOrder::getOrderCode, outOrder.getOrderCode());
      outOrderService.update(updateWrapper);
    } else if (B.isEqual(Convert.toStr(map.get("scanType")), "出库单号")) {
      this.updateOutOrder(outOrder);
      //修改波次明细数据
      OutOrderWave outOrderWave = outOrderWaveService.getByCode(outOrder.getOrderWaveCode());
      if (ObjectUtil.isEmpty(outOrderWave)) {
        throw new ServiceException("未生成波次单！");
      }
      this.updateOutOrderWaveDetail(outOrderWave, outOrder.getOrderCode());

      // 更新状态
      LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrder::getFactWeight, Convert.toBigDecimal(map.get("weight")))
        .eq(OutOrder::getOrderCode, outOrder.getOrderCode());
      outOrderService.update(updateWrapper);
    } else if (B.isEqual(Convert.toStr(map.get("scanType")), "波次号")) {
      // 扫描的单号
      if (ObjectUtil.isNotEmpty(expressCode)) {
        // 更新波次单的状态为发运完成
        OutOrderWave outOrderWave = outOrderWaveService.getByCode(expressCode);
        if (ObjectUtil.isEmpty(outOrderWave)) {
          throw new ServiceException("未生成波次单！");
        }
        // 生成波次的轨迹
        // outOrderWaveStatusHistoryService.AddHistory(outOrderWave, OutWaveOperationTypeEnum.PC_SEND_GOODS_CHECK, OutOrderStatusEnum.SHIPMENT_FINISHED);
        outOrderWave.setWaveStatus(OutOrderStatusEnum.SHIPMENT_FINISHED.getName());
        outOrderWaveService.getBaseMapper().updateById(outOrderWave);
        //修改波次明细数据
        this.updateOutOrderWaveDetail(outOrderWave, "");

        // 查询波次单根据出库单分组的明细
        MPJLambdaWrapper<OutOrderWaveDetail> detailLambdaQueryWrapper = new MPJLambdaWrapper<>();
        detailLambdaQueryWrapper.select(OutOrderWaveDetail::getOrderCode)
          .groupBy("order_code")
          .eq(OutOrderWaveDetail::getOrderWaveId, outOrderWave.getOrderWaveId());

        List<OutOrderWaveDetail> waveDetailList = outOrderWaveDetailService.list(detailLambdaQueryWrapper);

        // 根据分组获取到的出库单号把对应的单号的数据进行修改
        for (var item : waveDetailList) {
          OutOrder outOrder1 = outOrderService.getByCode(item.getOrderCode());
          this.updateOutOrder(outOrder1);
        }
      }
    }

    // 波次状态计算
    OutOrderWave outOrderWave = outOrderWaveService.getById(outOrder.getOrderWaveId());
    if (B.isEqual(Convert.toStr(map.get("scanType")), "波次号")) {
      outOrderWave = outOrderWaveService.getByCode(expressCode);
    }
    if (ObjectUtil.isNotEmpty(outOrderWave)) {
      // 更新波次单状态
      List<OutOrderWaveDetail> outOrderWaveDetails = outOrderWaveDetailService.selectListByMainId(outOrder.getOrderWaveId());


      // 已出货数量
      BigDecimal outQuantityOuted = outOrderWaveDetails.stream().filter(item -> B.isGreater(item.getQuantityShipped())).map(OutOrderWaveDetail::getQuantityShipped).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 原单数量
      BigDecimal outQuantityOrder = outOrderWaveDetails.stream().filter(item -> B.isGreater(item.getQuantityOrder())).map(OutOrderWaveDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);

      // 找到最新一条的状态轨迹
      LambdaQueryWrapper<OutOrderWaveStatusHistory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(OutOrderWaveStatusHistory::getBillId, outOrderWave.getOrderWaveId())
        .orderByDesc(OutOrderWaveStatusHistory::getHistoryId)
        .last("limit 1");
      var orderStatusHistory = outOrderWaveStatusHistoryService.getOne(lambdaQueryWrapper);

      if (ObjectUtil.isNotEmpty(orderStatusHistory.getToStatus())) {
        outOrderWave.setWaveStatus(orderStatusHistory.getToStatus());
      }

      if (B.isGreaterOrEqual(outQuantityOuted, outQuantityOrder)) {
        // 生成波次的轨迹
        outOrderWaveStatusHistoryService.AddHistory(outOrderWave, OutWaveOperationTypeEnum.PC_SEND_GOODS_CHECK, OutOrderStatusEnum.SHIPMENT_FINISHED);

        // 如果已发货数量的合计>=原单数量 就是 发运完成
        outOrderWave.setWaveStatus(OutOrderStatusEnum.SHIPMENT_FINISHED.getName());

      } else if (B.isGreater(outQuantityOuted) && B.isLess(outQuantityOuted, outQuantityOrder)) {
        // 生成波次的轨迹
        outOrderWaveStatusHistoryService.AddHistory(outOrderWave, OutWaveOperationTypeEnum.PC_SEND_GOODS_CHECK, OutOrderStatusEnum.SHIPMENT_PARTIAL);
        // 部分发运
        outOrderWave.setWaveStatus(OutOrderStatusEnum.SHIPMENT_PARTIAL.getName());
      }

      outOrderWaveService.getBaseMapper().updateById(outOrderWave);
    }


    if (B.isEqual(Convert.toStr(map.get("scanType")), "波次号")) {

      // 获取波次信息
      OutOrderWave outOrderWaveInfo = outOrderWaveService.getByCode(expressCode);
      if (ObjectUtil.isNotEmpty(outOrderWave)) {
        // 获取波次明细

        LambdaQueryWrapper<OutOrderWaveDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
          .select(OutOrderWaveDetail::getOrderCode)
          .eq(OutOrderWaveDetail::getOrderWaveId, outOrderWaveInfo.getOrderWaveId())
          .groupBy(OutOrderWaveDetail::getOrderCode);
        var waveDetails = outOrderWaveDetailService.list(lambdaQueryWrapper);

        for (var item : waveDetails) {
          // 获取出库信息
          OutOrder outOrderMain = outOrderService.getByCode(item.getOrderCode());
          OutSendBill outSendBill = new OutSendBill();
          BeanUtil.copyProperties(outOrderMain, outSendBill);
          outSendBill.setApplyDate(outOrderMain.getCreateTime());
          sendBillService.save(outSendBill);
        }
      }
    } else {
      // 生成发货记录单
      OutSendBill outSendBill = new OutSendBill();
      BeanUtil.copyProperties(orderOrder, outSendBill);
      outSendBill.setApplyDate(outOrder.getCreateTime());
      sendBillService.save(outSendBill);
    }

    return R.ok("发运完成");
  }
  //endregion


  //#region 修改出库单
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> updateOutOrder(OutOrder outOrder) {
    IOutOrderStatusHistoryService outOrderStatusHistoryService = SpringUtils.getBean(IOutOrderStatusHistoryService.class);
    // 更新状态
    LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(OutOrder::getOrderStatus, OutOrderStatusEnum.SHIPMENT_FINISHED.getName())
      .eq(OutOrder::getOrderCode, outOrder.getOrderCode());
    outOrderService.update(updateWrapper);
    // 因为单据都是打包完成的  直接更新出库单的已发货数量
    LambdaUpdateWrapper<OutOrderDetail> updateWrapperDetail = new LambdaUpdateWrapper<>();
    updateWrapperDetail
      .setSql("quantity_shipped = quantity_order")
      .eq(OutOrderDetail::getOrderId, outOrder.getOrderId());
    outOrderDetailService.update(updateWrapperDetail);
    //出库单添加轨迹
    outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.PC_SEND_GOODS_CHECK, OutOrderStatusEnum.SHIPMENT_FINISHED);
    return R.ok();
  }
  //#endregion

  //#region 修改波次明细
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> updateOutOrderWaveDetail(OutOrderWave outOrderWave, String OrderCode) {
    // 查询波次单的明细
    LambdaQueryWrapper<OutOrderWaveDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(OutOrderWaveDetail::getOrderWaveId, outOrderWave.getOrderWaveId());
    if (ObjectUtil.isNotEmpty(OrderCode)) {
      lambdaQueryWrapper.eq(OutOrderWaveDetail::getOrderCode, OrderCode);
    }

    List<OutOrderWaveDetail> orderWaveDetails = outOrderWaveDetailService.list(lambdaQueryWrapper);

    // 截取货位不是下架理货位的数据
    var waveDetailFilterList = orderWaveDetails.stream()
      .filter(f -> !ObjectUtil.equals(f.getPositionType(), PositionTypeEnum.UNLOADING.getId())).toList();

    for (var item : waveDetailFilterList) {
      // 更新波次单明细的发运数量
      LambdaUpdateWrapper<OutOrderWaveDetail> outOrderWaveWrapper = new LambdaUpdateWrapper<>();
      outOrderWaveWrapper
        .setSql("quantity_shipped = quantity_order_origin")
        .eq(OutOrderWaveDetail::getOrderWaveDetailId, item.getOrderWaveDetailId());
      outOrderWaveDetailService.update(outOrderWaveWrapper);
    }
    return R.ok();
  }
  //#endregion
}
