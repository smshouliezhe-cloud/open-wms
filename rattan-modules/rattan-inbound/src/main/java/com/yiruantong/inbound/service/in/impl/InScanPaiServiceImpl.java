package com.yiruantong.inbound.service.in.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.in.InShelveStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.inventory.domain.helper.RecommendPositionDto;
import com.yiruantong.inventory.service.rule.IRecommendPositionService;
import com.yiruantong.system.service.core.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InScanPaiServiceImpl implements IInScanPaiService {

  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInScanOrderService inScanOrderService;
  private final IInShelveService inShelveService;
  private final IInShelveDetailService inShelveDetailService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageService baseStorageService;
  private final IRecommendPositionService recommendPositionService;

  @Override
  public List<Map<String, Object>> getPlateCodeData(Map<String, Object> map) {
    String plateCode = Convert.toStr(map.get("plateCode"));
    Boolean isOnShelve = Optional.of(map).map(m -> Convert.toBool(m.get("isOnShelve"))).orElse(false); // 是否直接上架

    //#region 校验数据
    if (StringUtils.isEmpty(plateCode)) {
      throw new ServiceException("拍号不能为空！");
    }
    LambdaQueryWrapper<InOrder> inOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    inOrderLambdaQueryWrapper
      .in(InOrder::getOrderStatus, List.of(InOrderStatusEnum.SUCCESS.getName(), InOrderStatusEnum.PARTIAL_FINISHED.getName()))
      .exists("Select 1 from in_order_detail Where plate_code='" + plateCode + "' And order_Id=in_order.order_Id And quantity>IFNULL(enter_quantity,0)")
      .last("limit 1");
    InOrder inOrderInfo = inOrderService.getOne(inOrderLambdaQueryWrapper);
    Assert.isFalse(ObjectUtil.isNull(inOrderInfo), "拍号[" + plateCode + "]未找到可用预到货单数据！");
    Assert.isFalse(ObjectUtil.equals(inOrderInfo.getOrderStatus(), InOrderStatusEnum.FINISHED.getName()), "该订单已经完成交货！");

    // 审核成功允许扫描入库
    var in_auditEnableScan = sysConfigService.getConfigBool("in_auditEnableScan");
    List<InOrderStatusEnum> statusList = new ArrayList<>(Arrays.asList(InOrderStatusEnum.IN_TRANSIT, InOrderStatusEnum.PARTIAL_FINISHED));
    if (in_auditEnableScan) {
      statusList.add(InOrderStatusEnum.SUCCESS);
    }
    if (statusList.stream().noneMatch(m -> m.getName().equals(inOrderInfo.getOrderStatus()))) {
      String statusStr = statusList.stream().map(InOrderStatusEnum::getName).collect(Collectors.joining(","));
      throw new ServiceException("采购订单的订单状态不是" + statusStr + "，不能进行入库！");
    }
    //#endregion

    //#region 获取数据
    MPJLambdaWrapper<InOrderDetail> wrapper = new MPJLambdaWrapper<InOrderDetail>()
      .select(InOrder::getOrderCode, InOrder::getStorageId, InOrder::getStorageName)
      .select(BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getUnitConvert)
      .selectAll(InOrderDetail.class)
      .innerJoin(InOrder.class, InOrder::getOrderId, InOrderDetail::getOrderId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, InOrderDetail::getProductId)
      .eq(InOrder::getOrderId, inOrderInfo.getOrderId())
      .apply("t.quantity > IFNULL(t.enter_quantity ,0)")
      .eq(InOrderDetail::getPlateCode, plateCode);
    List<Map<String, Object>> orderList = inOrderDetailService.selectJoinMaps(wrapper);

    if (ArrayUtil.isEmpty(orderList)) {
      throw new ServiceException("未找到对应的订单明细数据");
    }

    // 仓库信息
    var storageInfo = baseStorageService.selectById(inOrderInfo.getStorageId());
    Assert.isFalse(ObjectUtil.isNull(storageInfo), "预到货单仓库不存在！");
    // 推荐货位规则
    for (var item : orderList) {
      //直接上架时匹配货位推荐规则
      if (isOnShelve) {
        RecommendPositionDto recommendPositionDto = new RecommendPositionDto();// 复制数据
        recommendPositionDto.setInQty(Convert.toBigDecimal(item.get("quantity")));
        recommendPositionDto.setPlateCode(Convert.toStr(item.get("plateCode")));
        recommendPositionDto.setBatchNumber(Convert.toStr(item.get("batchNumber")));
        recommendPositionDto.setProductId(Convert.toLong(item.get("productId")));
        recommendPositionDto.setProductSpec(Convert.toStr(item.get("productSpec")));
        recommendPositionDto.setConsignorId(Convert.toLong(item.get("consignorId")));
        recommendPositionDto.setStorageId(Convert.toLong(item.get("storageId")));
        recommendPositionDto.setProviderId(Convert.toLong(item.get("providerId")));
        recommendPositionDto.setWeight(Convert.toBigDecimal(item.get("weight")));
        recommendPositionDto.setUnitCube(Convert.toBigDecimal(item.get("unitCube")));
        String positionName = recommendPositionService.getRecommendPosition(recommendPositionDto);
        item.put("positionName", positionName);
      }
      // 开启SN管理状态处理
      var isManageSn = Convert.toInt(item.get("isManageSn"));
      var snDisabled = storageInfo.getSnDisabled(); // 仓库设置了不需SN管理
      if (snDisabled == 1) {
        isManageSn = 0; // 不需要SN
      }
      item.put("isManageSn", isManageSn);

      // 如果类型是合金/辅料 需要吧 生产日期 默认为当前时间
      if (B.isEqual(inOrderInfo.getOrderType(), InOrderTypeEnum.AUXILIARY_MATERIAL.getName())) {
        item.put("produceDate", new Date());
      }
      //取出来 商品的扩展字段值
      if (ObjectUtil.isNotEmpty(item.get("expandFields"))) {
        Map<String, Object> jsonData = JsonUtils.toMap(Convert.toStr(item.get("expandFields")));

        item.put("bigTareWeight", jsonData.get("bigTareWeight"));
        item.put("smallTareWeight", jsonData.get("smallTareWeight"));
        item.put("expandFields", jsonData);
      }
    }
    //#endregion

    return orderList;
    //#endregion
  }

  @Override
  public R<Void> enterPaiSave(InScanOrderBo inScanOrderBo) {
    return inScanOrderService.normalScanSave(inScanOrderBo);
  }

  @Override
  public List<Map<String, Object>> getShelvePlateData(Map<String, Object> maps) {
    String plateCode = Convert.toStr(maps.get("plateCode"));
    List<String> arr = new ArrayList<>();
    arr.add(InShelveStatusEnum.SHELVING.getName());
    arr.add(InShelveStatusEnum.WAITING.getName());
    arr.add(InShelveStatusEnum.PARTIAL_FINISHED.getName());
    //根据仓库id
    MPJLambdaWrapper<InShelve> wrapper = new MPJLambdaWrapper<InShelve>()
      .select(InShelve::getShelveId, InShelve::getStartDate)
      .innerJoin(InShelveDetail.class, InShelveDetail::getShelveId, InShelve::getShelveId)
      .eq(InShelveDetail::getPlateCode, plateCode)
      .gt(InShelveDetail::getOnShelveQuantity, BigDecimal.ZERO)
      .in(InShelve::getShelveStatus, arr)
      .last("limit 1");

    List<Map<String, Object>> orderList = inShelveService.selectJoinMaps(wrapper);
    Assert.isTrue(!orderList.isEmpty(), "未找到待上架拍号！");


    wrapper = new MPJLambdaWrapper<InShelve>()
      .selectAll(InShelve.class, InShelve::getExpandFields)
      .selectAll(InShelveDetail.class)
      .select(BaseProduct::getRelationCode, BaseProduct::getRelationCode2, BaseProduct::getRelationCode3, BaseProduct::getRelationCode4, BaseProduct::getRelationCode5, BaseProduct::getMiddleBarcode, BaseProduct::getMiddleUnitConvert, BaseProduct::getBigBarcode)
      .innerJoin(InShelveDetail.class, InShelveDetail::getShelveId, InShelve::getShelveId)
      .innerJoin(BaseProduct.class, BaseProduct::getProductId, InShelveDetail::getProductId)
      .eq(InShelveDetail::getPlateCode, plateCode)
      .eq(InShelve::getShelveId, orderList.get(0).get("shelveId"))
      .in(InShelve::getShelveStatus, List.of(InShelveStatusEnum.WAITING.getName(), InShelveStatusEnum.PARTIAL_FINISHED.getName(), InShelveStatusEnum.SHELVING.getName()));

    orderList = inShelveService.selectJoinMaps(wrapper);
    Assert.isTrue(!orderList.isEmpty(), "未找到待上架拍号！");
    // 推荐货位
    for (var item : orderList) {
      Long productId = Convert.toLong(item.get("productId"));

      String shelveDetailId = Convert.toStr(item.get("shelveDetailId"));

      RecommendPositionDto recommendPsoitionDto = new RecommendPositionDto();// 复制数据
      recommendPsoitionDto.setInQty(Convert.toBigDecimal(item.get("quantity")));
      recommendPsoitionDto.setPlateCode(Convert.toStr(item.get("plateCode")));
      recommendPsoitionDto.setBatchNumber(Convert.toStr(item.get("batchNumber")));
      recommendPsoitionDto.setProductId(productId);
      recommendPsoitionDto.setProductSpec(Convert.toStr(item.get("productSpec")));
      recommendPsoitionDto.setConsignorId(Convert.toLong(item.get("consignorId")));
      recommendPsoitionDto.setStorageId(Convert.toLong(item.get("storageId")));
      recommendPsoitionDto.setProviderId(Convert.toLong(item.get("providerId")));
      recommendPsoitionDto.setWeight(Convert.toBigDecimal(item.get("weight")));
      recommendPsoitionDto.setUnitCube(Convert.toBigDecimal(item.get("unitCube")));
      String positionName = recommendPositionService.getRecommendPosition(recommendPsoitionDto);

      if (StrUtil.isNotEmpty(positionName)) {
        //更新推荐上架货位
        LambdaUpdateWrapper<InShelveDetail> inShelveDetailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        inShelveDetailLambdaUpdateWrapper.set(InShelveDetail::getPositionName, positionName)
          .eq(InShelveDetail::getShelveDetailId, shelveDetailId);
        inShelveDetailService.update(inShelveDetailLambdaUpdateWrapper);
        item.put("positionName", positionName);
      }

    }

    //更新这个上架单为当前用户所有
    LambdaUpdateWrapper<InShelve> shelveLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    shelveLambdaUpdateWrapper.set(InShelve::getUserId, LoginHelper.getUserId())
      .set(InShelve::getNickName, LoginHelper.getNickname())
      .set(InShelve::getShelveStatus, InShelveStatusEnum.SHELVING.getName())
      .eq(InShelve::getShelveId, orderList.get(0).get("shelveId"));
    if (ObjectUtil.isEmpty(orderList.get(0).get("startDate"))) {
      shelveLambdaUpdateWrapper.set(InShelve::getStartDate, DateUtils.getNowDate());
    }
    inShelveService.update(shelveLambdaUpdateWrapper);


    return orderList;
  }
}
