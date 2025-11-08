package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.out.*;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.base.IInventoryBaseService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.operation.OutOrderMatching;
import com.yiruantong.outbound.domain.operation.OutOrderMatchingDetail;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingDetailVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailPickingVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.operation.IOutOrderMatchingDetailService;
import com.yiruantong.outbound.service.operation.IOutOrderMatchingService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yiruantong.common.satoken.utils.LoginHelper.getLoginUser;

@RequiredArgsConstructor
@Service
public class OutScanMatchService implements IOutScanMatchService, IInventoryBaseService {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService iOutOrderDetailService;
  private final IOutOrderWaveService outOrderWaveService;
  private final IOutOrderWaveDetailService outOrderWaveDetailService;
  private final IOutOrderMatchingService outOrderMatchingService;
  private final IOutOrderMatchingDetailService outOrderMatchingDetailService;
  private final IOrderScanSendBatchService orderScanSendBatchService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IOutOrderWaveStatusHistoryService outOrderWaveStatusHistoryService;
  private final IBaseProductService baseProductService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final ICoreInventoryService coreInventoryService;

  //#region 获取波次扫描数据
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> getMatchData(Map<String, Object> map) {
    String orderWaveCode = Convert.toStr(map.get("orderWaveCode"));
    Assert.isFalse(ObjectUtil.isEmpty(orderWaveCode), "波次单号不能为空！");

    LoginUser loginUser = getLoginUser();
    Assert.isFalse(ObjectUtil.isNull(loginUser), "当前用户不存在！");

    String mainWaveCode = orderWaveCode; // 主波次号
    //区分主波次 子波次
    if (orderWaveCode.contains("-")) {
      var arr = orderWaveCode.split("-");
      mainWaveCode = arr[0];
    }
    var orderWave = outOrderWaveService.getByCode(mainWaveCode);
    if (ObjectUtil.isEmpty(orderWave)) {
      throw new ServiceException("波次不存在！");
    }
    List<OutOrderStatusEnum> statusEnumList = CollUtil.newArrayList(OutOrderStatusEnum.WAVE_FINISHED, OutOrderStatusEnum.PICKING, OutOrderStatusEnum.PICKING_PARTIAL, OutOrderStatusEnum.PICKED);
    Assert.isFalse(statusEnumList.stream().noneMatch(n -> ObjectUtil.equal(orderWave.getWaveStatus(), n.getName())), "波次单状态状态不允许操作配货！");

    // 查询字段
    String selectFields = "productId,productCode,productName,productModel,productSpec,batchNumber,produceDate,bigUnit,smallUnit,clientId,clientCode,clientShortName," +
      "quantityOrderOrigin,pickQuantity,holderStorage,salePrice,remark,orderId,orderDetailId,orderWaveDetailId,plateCode,positionName,weight,rowWeight," +
      "storageId,storageName,middleBarcode,middleUnitConvert,bigBarcode,unitConvert,singleSignCode,allotPositionName,orderCode," +
      "brandName,relationCode,relationCode2,relationCode3,relationCode4,relationCode5,middleUnitConvert,bigBarcode,unitConvert,isManageSn";

    // 求和字段
    String sumFields = "quantityOrder,quantityOrderOrigin,matchedQuantity,holderStorage,rowWeight";

    // 分组字段
    String groupFields = "storageId,storageName,consignorId,consignorCode,consignorName,allotPositionName,productId,productCode,productName,productModel,orderCode";

    // 构建联表查询
    MPJLambdaWrapper<OutOrderWaveDetail> wrapper = new MPJLambdaWrapper<>();
    wrapper
      .innerJoin(OutOrderWave.class, OutOrderWave::getOrderWaveId, OutOrderWaveDetail::getOrderWaveId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, OutOrderWaveDetail::getProductId)
      .innerJoin(CoreInventoryHolder.class, CoreInventoryHolder::getHolderId, OutOrderWaveDetail::getHolderId)
      .eq(OutOrderWave::getOrderWaveId, orderWave.getOrderWaveId())
      .gt(OutOrderWaveDetail::getQuantityOrder, OutOrderWaveDetail::getMatchedQuantity)
      .isNotNull(OutOrderWaveDetail::getPositionType)
      .eq(OutOrderWaveDetail::getPositionName, PositionTypeEnum.UNLOADING.getName())
      .orderByAsc(OutOrderWaveDetail::getAllotPositionName);

    // 构建分组查询
    BuildWrapperHelper.mpjWrapperGroup(selectFields, sumFields, groupFields, wrapper, OutOrderWaveDetail.class, OutOrderWave.class, BaseProduct.class, CoreInventoryHolder.class);
    List<OutOrderWaveDetailPickingVo> inventoryComposeVoList = outOrderWaveDetailService.selectJoinList(OutOrderWaveDetailPickingVo.class, wrapper);
    Assert.isFalse(inventoryComposeVoList.isEmpty(), "没有获取到可配货数据！");
    Map<String, Object> result = new HashMap<>();
    result.put("dataList", inventoryComposeVoList);

    // 创建配货单
    outOrderMatchingService.createMatch(mainWaveCode, orderWave);

    return R.ok(result);
  }
  //#endregion

  //#region 保存扫描配货
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> saveMatchScan(OutScanMainBo outScanMainBo) {
    OutOrderWave outOrderWave = outOrderWaveService.getByCode(outScanMainBo.getOrderWaveCode());
    Assert.isFalse(ObjectUtil.isNull(outOrderWave), outOrderWave.getOrderWaveCode() + "波次单不存在！");
    List<OutOrderStatusEnum> orderStatusEnums = CollUtil.newArrayList(OutOrderStatusEnum.WAVE_FINISHED, OutOrderStatusEnum.PICKING, OutOrderStatusEnum.PICKING_PARTIAL, OutOrderStatusEnum.MATCHING, OutOrderStatusEnum.PICKED);
    Assert.isFalse(orderStatusEnums.stream().noneMatch(a -> Objects.equals(a.getName(), outOrderWave.getWaveStatus())),
      "{}当前状态{}不允许操作配货！", outOrderWave.getOrderWaveCode(), outOrderWave.getWaveStatus());
    // 波次明细
    List<OutOrderWaveDetail> waveDetailList = outOrderWaveDetailService.selectListById(outOrderWave.getOrderWaveId());
    //#region 生成配货单明细
    OutOrderMatching outOrderMatching = outOrderMatchingService.getbyOrderWaveCode(outOrderWave.getOrderWaveCode());
    Assert.isFalse(ObjectUtil.isEmpty(outOrderMatching), "未生成配货单，不允许操作！");

    // 当前扫描的波次明细
    var detailList = outScanMainBo.getDataList();

    for (OutScanDetailBo detailBo : detailList) {
      List<OutOrderWaveDetail> allotList = waveDetailList.stream().filter(f -> ObjectUtil.equal(f.getProductId(), detailBo.getProductId()) && ObjectUtil.equal(f.getAllotPositionName(), detailBo.getAllotPositionName()) && B.isGreater(f.getQuantityOrder(), f.getMatchedQuantity())
        && B.isEqual(f.getPositionName(), PositionTypeEnum.UNLOADING.getName())).toList();

      for (var allotItem : allotList) {

        // 查询商品信息
        var skuInfo = baseProductService.getById(allotItem.getProductId());
        // 出库单明细信息
        OutOrderDetail outOrderDetail = iOutOrderDetailService.getById(allotItem.getOrderDetailId());
        // 获取占位数据
        CoreInventoryHolder coreInventoryHolder = coreInventoryHolderService.getById(allotItem.getHolderId());

        // 获取库存数据
        LambdaQueryWrapper<CoreInventory> inventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        inventoryLambdaQueryWrapper
          .eq(CoreInventory::getInventoryId, coreInventoryHolder.getInventoryId());
        CoreInventory coreInventory = coreInventoryService.getOne(inventoryLambdaQueryWrapper);

        OutOrderMatchingDetail matchingDetail = BeanUtil.copyProperties(detailBo, OutOrderMatchingDetail.class);
        matchingDetail.setMatchingId(outOrderMatching.getMatchingId());

        matchingDetail.setUnitCube(coreInventory.getUnitCube());// 单位体积
        matchingDetail.setRowCube(B.mul(detailBo.getFinishedQuantity(), coreInventory.getUnitCube())); // 小计体积
        matchingDetail.setPlateCode(coreInventory.getPlateCode()); // 托盘号
        matchingDetail.setContainerNo(coreInventory.getContainerNo()); // 集装箱号
        matchingDetail.setThermocline(coreInventory.getThermocLine()); // 温层
        matchingDetail.setBrandName(skuInfo.getBrandName()); // 品牌
        matchingDetail.setTypeName(skuInfo.getTypeName()); // 类别
        matchingDetail.setProductBarCode(skuInfo.getProductBarCode()); // 型号
        matchingDetail.setImages(skuInfo.getImages()); // 图片

        // 波次单明细配货数量计算
        BigDecimal surplusQty = B.sub(allotItem.getQuantityOrder(), allotItem.getMatchedQuantity()); // 剩余可配数量
        if (B.isGreater(surplusQty, detailBo.getFinishedQuantity())) {
          allotItem.setMatchedQuantity(B.add(allotItem.getMatchedQuantity(), detailBo.getFinishedQuantity()));
          matchingDetail.setMatchQuantity(detailBo.getFinishedQuantity()); // 配货单明细数量
        } else {
          allotItem.setMatchedQuantity(B.add(allotItem.getMatchedQuantity(), surplusQty));
          matchingDetail.setMatchQuantity(surplusQty); // 配货单明细数量
        }

        matchingDetail.setRate(outOrderDetail.getRate()); // 税率
        matchingDetail.setRatePrice(B.mul(outOrderDetail.getSalePrice(), B.add(new BigDecimal(1), outOrderDetail.getRate()))); // 含税单价
        matchingDetail.setRateAmount(B.mul(matchingDetail.getRatePrice(), matchingDetail.getMatchQuantity())); // 含税金额

        matchingDetail.setBigQty(B.div(matchingDetail.getMatchQuantity(), skuInfo.getUnitConvert()));
        matchingDetail.setSaleAmount(B.mul(outOrderDetail.getSalePrice(), matchingDetail.getMatchQuantity()));
        matchingDetail.setSalePrice(outOrderDetail.getSalePrice());

        matchingDetail.setRowWeight(B.mul(matchingDetail.getMatchQuantity(), matchingDetail.getWeight())); // 小计毛重
        matchingDetail.setRowCube(B.mul(matchingDetail.getMatchQuantity(), matchingDetail.getUnitCube())); // 小计体积
        outOrderWaveDetailService.updateById(allotItem); // 更新波次配货数量
        outOrderMatchingDetailService.save(matchingDetail); // 保存配货记录
      }
    }

    // 查询明细数据
    LambdaQueryWrapper<OutOrderMatchingDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(OutOrderMatchingDetail::getMatchingId, outOrderMatching.getMatchingId());
    List<OutOrderMatchingDetailVo> orderMatchingDetails = outOrderMatchingDetailService.selectList(detailLambdaQueryWrapper);

    BigDecimal matchQuantity = BigDecimal.ZERO; // 配货数
    BigDecimal totalWeight = BigDecimal.ZERO; // 合计重量
    BigDecimal totalCube = BigDecimal.ZERO; // 合计体积
    BigDecimal bigQtyTotal = BigDecimal.ZERO; // 大单位数量
    for (OutOrderMatchingDetailVo detailVo : orderMatchingDetails) {
      // 查询商品信息
      var skuInfo = baseProductService.getById(detailVo.getProductId());

      matchQuantity = matchQuantity.add(detailVo.getMatchQuantity());
      totalWeight = totalWeight.add(detailVo.getRowWeight());

      BigDecimal zero = new BigDecimal("0");

      totalCube = totalCube.add(ObjectUtil.isNotNull(detailVo.getRowCube()) ? detailVo.getRowCube() : zero);
      bigQtyTotal = bigQtyTotal.add(B.div(detailVo.getQuantityOrder(), skuInfo.getUnitConvert()));
    }

    // 更新主表
    LambdaUpdateWrapper<OutOrderMatching> outOrderMatchingLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    outOrderMatchingLambdaUpdateWrapper
      .set(OutOrderMatching::getOrderCount, outOrderWave.getOrderCount())
      .set(OutOrderMatching::getTotalMatchQuantity, matchQuantity)
      .set(OutOrderMatching::getTotalWeight, totalWeight)
      .set(OutOrderMatching::getBigQtyTotal, bigQtyTotal)
      .set(OutOrderMatching::getTotalCube, totalCube)
      .eq(OutOrderMatching::getMatchingId, outOrderMatching.getMatchingId());
    outOrderMatchingService.update(outOrderMatchingLambdaUpdateWrapper);


    //#region 更新状态
    //出库轨迹动作
    OutOperationTypeEnum outOperationTypeEnum = OutOperationTypeEnum.PC_MATCHING;
    //波次轨迹动作
    OutWaveOperationTypeEnum outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PC_MATCHING;

    // 根据前端传入的入库类型（库存来源类型），转换出库轨迹类型
    switch (outScanMainBo.getScanInType()) {
      case PC_MATCHING_BATCH -> {
        outOperationTypeEnum = OutOperationTypeEnum.PC_MATCHING_BATCH;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PC_MATCHING_BATCH;
      }
      case PDA_MATCHING -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_MATCHING;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_MATCHING;
      }
      case PDA_MATCHING_BATCH -> {
        outOperationTypeEnum = OutOperationTypeEnum.PDA_MATCHING_BATCH;
        outWaveOperationTypeEnum = OutWaveOperationTypeEnum.PDA_MATCHING_BATCH;
      }
    }

    waveDetailList = outOrderWaveDetailService.selectListById(outOrderWave.getOrderWaveId());
    Map<Long, List<OutOrderWaveDetail>> listMap = waveDetailList.stream().collect(Collectors.groupingBy(OutOrderWaveDetail::getOrderId)); // 订单分组
    // 更新订单中的配货状态
    for (var groupItem : listMap.entrySet()) {
      Long orderId = groupItem.getKey();
      List<OutOrderWaveDetail> waveDetails = groupItem.getValue();
      BigDecimal matchedQuantity = StreamUtils.sum(waveDetails, OutOrderWaveDetail::getPickQuantity); // 获取配货数量
      BigDecimal quantityOrder = StreamUtils.sum(waveDetails, OutOrderWaveDetail::getQuantityOrder); // 获取出库单数量

      // 查询出库单
      LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(OutOrder::getOrderId, orderId);
      OutOrder outOrder = outOrderService.getOne(orderLambdaQueryWrapper);
      // outOrder.setOrderStatus(OutOrderStatusEnum.PC_MATCHING.getName());
      if (B.isGreaterOrEqual(matchedQuantity, quantityOrder)) {
        // 下架完成
        outOrderService.updateMatchStatus(orderId, OutMatchStatusEnum.FINISHED);

        // 生成出库单的轨迹
        outOrderStatusHistoryService.AddHistory(outOrder, outOperationTypeEnum, OutOrderStatusEnum.MATCHED);
      } else if (B.isLess(matchedQuantity, quantityOrder) && B.isGreater(matchedQuantity)) {
        // 部分完成
        outOrderService.updateMatchStatus(orderId, OutMatchStatusEnum.MATCHING_PARTIAL);

        // 生成出库单的轨迹
        outOrderStatusHistoryService.AddHistory(outOrder, outOperationTypeEnum, OutOrderStatusEnum.MATCHING_PARTIAL);
      }
    }
    // 更新订单状态，如果所有的都配货完成，将订单状态
    BigDecimal matchedQuantity = StreamUtils.sum(waveDetailList, OutOrderWaveDetail::getMatchedQuantity); // 获取配货数量
    BigDecimal quantityOrder = StreamUtils.sum(waveDetailList, OutOrderWaveDetail::getQuantityOrder); // 获取出库单数量
    if (B.isGreaterOrEqual(matchedQuantity, quantityOrder)) {
      // 更新波次单状态
      outOrderWaveService.updateWaveStatus(outOrderWave.getOrderWaveId(), OutOrderWaveStatusEnum.MATCHED);

      // outOrderWave.setWaveStatus(OutOrderStatusEnum.PC_MATCHING.getName());
      // 生成波次的轨迹
      outOrderWaveStatusHistoryService.AddHistory(outOrderWave, outWaveOperationTypeEnum, OutOrderStatusEnum.MATCHED);

      // 更新出库单配货记录状态
      LambdaUpdateWrapper<OutOrderMatching> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrderMatching::getMatchStatus, OutMatchStatusEnum.FINISHED.getName())
        .set(OutOrderMatching::getEndDate, DateUtil.date())
        .set(OutOrderMatching::getSpanTime, DateUtil.formatBetween(outOrderMatching.getStartDate(), DateUtil.date(), BetweenFormatter.Level.SECOND))
        .eq(OutOrderMatching::getMatchingId, outOrderMatching.getMatchingId());

      outOrderMatchingService.update(updateWrapper);

      for (var groupItem : listMap.entrySet()) {
        Long orderId = groupItem.getKey();
        // 更新订单状态为配货完成
        outOrderService.updateOrderStatus(orderId, OutOrderStatusEnum.MATCHED);
      }
    }
    //endregion

    return R.ok();
  }
  //endregion

  //#region 保存扫描批量配货
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> saveMatchBatchScan(OutScanMainBo outScanMainBo) {
    var scanResult = this.saveMatchScan(outScanMainBo);
    if (!scanResult.isResult()) {
      return scanResult;
    }

    // 执行闪电发货
    orderScanSendBatchService.saveSendBatchData(outScanMainBo);

    return R.ok();
  }
  //endregion

}
