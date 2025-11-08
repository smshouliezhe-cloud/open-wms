package com.yiruantong.composite.service.out.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutReturnStatusEnum;
import com.yiruantong.common.core.enums.out.OutSourceTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.service.out.IAutoReturnOrderService;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
import com.yiruantong.inbound.service.in.IInScanOrderService;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.api.ApiOutOrderBo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.service.OutReturn;
import com.yiruantong.outbound.domain.service.OutReturnDetail;
import com.yiruantong.outbound.domain.service.vo.OutReturnVo;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.service.IOutReturnDetailService;
import com.yiruantong.outbound.service.service.IOutReturnService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AutoReturnOrderImpl implements IAutoReturnOrderService {
  private final IOutOrderService outOrderService;
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final IOutReturnService outReturnService;
  private final IOutReturnDetailService outReturnDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IInScanOrderService inScanOrderService;


//#region 出库单退货

  /**
   * 出库单退货-自动回退库存
   */
  @Override
  public R<Map<String, Object>> toReturnOrder(ApiOutOrderBo bo) {
    try {
      OutOrder outOrder = outOrderService.getById(bo.getOrderId());
      if (ObjectUtil.isNull(outOrder)) {
        return R.fail("出库单不存在");
      }
      if (!outOrder.getOrderStatus().equals(OutOrderStatusEnum.SHIPMENT_FINISHED.getName())) {
        return R.fail("当前状态为：" + outOrder.getOrderStatus() + "，只有发运完成的才允许做退货操作");
      }

      // 生成出库退货单
      var returnId = this.toOutReturn(outOrder);
      if (ObjectUtil.isNull(returnId)) {
        return R.fail("无生成退货单内容");
      }

      // 更新出库单状态为完全退货
      LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrder::getOrderStatus, OutOrderStatusEnum.RETURN_FINISHED.getName())
        .eq(OutOrder::getOrderId, outOrder.getOrderId());
      outOrderService.update(updateWrapper);
      // 转到预到货单
      var newOrderId = this.createInOrder(returnId);
      if (ObjectUtil.isNull(newOrderId)) {
        return R.fail("无生成预到货单内容");
      }
      // 更新入库单状态为在途中
      LambdaUpdateWrapper<InOrder> inOrderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      inOrderLambdaUpdateWrapper.set(InOrder::getOrderStatus, InOrderStatusEnum.IN_TRANSIT.getName())
        .eq(InOrder::getOrderId, newOrderId);

      // 自动审核退货单
      this.saveCheck(newOrderId, returnId);
      return R.ok("取消成功");
    } catch (Exception error) {
      return R.fail("推送订单失败，" + error.getMessage());
    }
  }
  //#endregion


  //#region 生成出库退货单 toOutReturn

  /**
   * 生成出库退货单
   *
   * @param outOrder 出库单对象
   * @return
   */
  @Override
  public Long toOutReturn(OutOrder outOrder) {
    //#region 生成出库退货单
    OutReturn orderInfo = new OutReturn();
    BeanUtil.copyProperties(outOrder, orderInfo);
    orderInfo.setReturnCode(DBUtils.getCodeRegular(MenuEnum.MENU_1733, null));
    orderInfo.setOrderType(OutSourceTypeEnum.OUT_ORDER_RETURN.getName());
    orderInfo.setSourceType(OutSourceTypeEnum.OUT_ORDER_RETURN.getName());
    orderInfo.setReturnStatus(OutReturnStatusEnum.NEWED.getName());
    orderInfo.setStoreOrderCode(outOrder.getSourceCode());
    orderInfo.setSourceCode(outOrder.getOrderCode());
    orderInfo.setSourceId(Convert.toStr(outOrder.getOrderId()));
    orderInfo.setReturnStatus(OutReturnStatusEnum.NOT_REFUNDED.getName());
    orderInfo.setAuditing(Convert.toLong(AuditEnum.AUDITED_SUCCESS.getId())); // 待审核
    outReturnService.save(orderInfo);

    // 明细数据查询
    List<OutOrderDetail> outOrderDetails = outOrderDetailService.selectListByMainId(outOrder.getOrderId());
    if (ObjectUtil.isNull(outOrderDetails)) {
      throw new ServiceException("出库单明细不存在");
    }

    for (var item : outOrderDetails) {
      OutReturnDetail orderDetail = new OutReturnDetail();
      BeanUtil.copyProperties(item, orderDetail);
      BeanUtil.copyProperties(item, orderDetail, CopyOptions.create().setIgnoreNullValue(true)); // 复制排除空值
      orderDetail.setReturnId(orderInfo.getReturnId());
      orderDetail.setOrderQuantity(item.getQuantityOrder());
      orderDetail.setReturnQuantity(item.getQuantityOrder());
      orderDetail.setActualReturnQuantity(item.getQuantityOrder());
      orderDetail.setEnterQuantity(BigDecimal.ZERO);

      LambdaQueryWrapper<CoreInventoryHolder> holderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      holderLambdaQueryWrapper.eq(CoreInventoryHolder::getDetailId, item.getOrderDetailId());
      holderLambdaQueryWrapper.last("limit 1");
      CoreInventoryHolder coreInventoryHolder = coreInventoryHolderService.getOne(holderLambdaQueryWrapper);

      if (ObjectUtil.isNotNull(coreInventoryHolder)) {
        orderDetail.setProviderId(coreInventoryHolder.getProviderId());
        orderDetail.setProviderCode(coreInventoryHolder.getProviderCode());
        orderDetail.setProviderShortName(coreInventoryHolder.getProviderShortName());
        orderDetail.setPositionName(coreInventoryHolder.getPositionName());
        orderDetail.setBatchNumber(coreInventoryHolder.getBatchNumber());
        orderDetail.setProduceDate(coreInventoryHolder.getProduceDate());
      }
      orderDetail.setSourceMainId(String.valueOf(outOrder.getOrderId()));
      orderDetail.setSourceDetailId(String.valueOf(item.getOrderDetailId()));
      outReturnDetailService.save(orderDetail);

    }

    LambdaQueryWrapper<OutReturnDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(OutReturnDetail::getReturnId, orderInfo.getReturnId());
    List<OutReturnDetail> returnDetails = outReturnDetailService.list(lambdaQueryWrapper);

    BigDecimal totalReturnQuantity = returnDetails.stream().map(OutReturnDetail::getActualReturnQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalWeight = returnDetails.stream().map(OutReturnDetail::getRowWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalCube = returnDetails.stream().map(OutReturnDetail::getRowCube).reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal totalSaleAmount = returnDetails.stream().map(OutReturnDetail::getSaleAmount).reduce(BigDecimal.ZERO, BigDecimal::add);


    orderInfo.setTotalReturnQuantity(totalReturnQuantity);
    orderInfo.setTotalAmount(totalSaleAmount);
    orderInfo.setTotalCube(totalCube);
    orderInfo.setTotalWeight(totalWeight);
    outReturnService.saveOrUpdate(orderInfo);
    //#endregion

    //添加轨迹
//    inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.RETURN_TO_ORDER, InOrderStatusEnum.NEWED);

    return orderInfo.getReturnId();
  }
  //#endregion

  //#region 创建预到货 createInOrder

  /**
   * 创建预到货
   *
   * @param returnId 退货单ID
   * @return
   */
  @Override
  public Long createInOrder(Long returnId) {

    OutReturnVo outReturnVo = outReturnService.selectById(returnId);
    if (ObjectUtil.isEmpty(outReturnVo)) {
      throw new ServiceException("未获取到出库退货单");
    }
    if (outReturnVo.getReturnStatus().equals(OutReturnStatusEnum.OVER_TO_INORDER.getName())) {
      throw new ServiceException("单据已转到预到货单，不允许重复操作！");
    }
    Assert.isTrue(StrUtil.equals(outReturnVo.getReturnStatus(), OutReturnStatusEnum.SUCCESS.getName()), "单据未审核不允许转预到货单！");


    InOrder orderInfo = new InOrder();
    BeanUtil.copyProperties(outReturnVo, orderInfo);
    String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1001, null);
    orderInfo.setOrderCode(orderCode);
    orderInfo.setOrderType(outReturnVo.getOrderType());
    orderInfo.setSourceType(InOrderTypeEnum.RETURN_TO_ORDER.getName());
    orderInfo.setOrderStatus(InOrderStatusEnum.NEWED.getName());
    orderInfo.setTrackingNumber(outReturnVo.getReturnCode());
    orderInfo.setSourceCode(outReturnVo.getReturnCode());
    orderInfo.setSourceId(String.valueOf(outReturnVo.getReturnId()));
    orderInfo.setOrderId(null);
    orderInfo.setShelveStatus(InOrderStatusEnum.HISTORY_WAITING.getName());
    orderInfo.setAuditing(AuditEnum.AUDIT.getId()); // 待审核
    inOrderService.save(orderInfo);

    // 明细数据查询
    LambdaQueryWrapper<OutReturnDetail> outReturnLma = new LambdaQueryWrapper<>();
    outReturnLma.eq(OutReturnDetail::getReturnId, returnId);
    List<OutReturnDetail> outReturnDetails = outReturnDetailService.list(outReturnLma);

    BigDecimal totalQuantity = BigDecimal.ZERO;
    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal totalRateAmount = BigDecimal.ZERO;
    BigDecimal totalWeight = BigDecimal.ZERO;
    for (var item : outReturnDetails) {
      InOrderDetail orderDetail = new InOrderDetail();
      BeanUtil.copyProperties(item, orderDetail);
      BeanUtil.copyProperties(item, orderDetail, CopyOptions.create().setIgnoreNullValue(true));
      orderDetail.setQuantity(item.getActualReturnQuantity());
      orderDetail.setRowWeight(B.mul(orderDetail.getWeight(), orderDetail.getQuantity()));
      orderDetail.setRowWeightTon(B.div(orderDetail.getRowWeight(), new BigDecimal(1000)));
      orderDetail.setRowCube(B.mul(orderDetail.getUnitCube(), orderDetail.getQuantity()));
      orderDetail.setPurchaseAmount(B.mul(orderDetail.getPurchasePrice(), orderDetail.getQuantity()));

      orderDetail.setSourceMainId(String.valueOf(outReturnVo.getReturnId()));
      orderDetail.setSourceDetailId(String.valueOf(item.getReturnDetailId()));

      BaseProduct productInfo = baseProductService.getById(orderDetail.getProductId());
      var providerInfo = baseProviderService.getByShortName(item.getProviderShortName());

      if (ObjectUtil.isNotEmpty(productInfo)) {
        // 默认使用供应商税率，如果没有，则使用商品信息税率
        if (ObjectUtil.isNotNull(providerInfo.getRate())) {
          orderDetail.setRate(providerInfo.getRate());
        } else {
          orderDetail.setRate(productInfo.getRate());
        }
        BigDecimal ratePrice = B.mul(orderDetail.getPurchasePrice(), orderDetail.getRate());
        orderDetail.setRatePrice(B.add(orderDetail.getPurchasePrice(), ratePrice));
        orderDetail.setRateAmount(B.mul(orderDetail.getRatePrice(), orderDetail.getQuantity()));
        orderDetail.setProductSpec(productInfo.getProductSpec());
        orderDetail.setBrandName(productInfo.getBrandName());
        orderDetail.setTypeId(productInfo.getTypeId());
        orderDetail.setTypeName(productInfo.getTypeName());
        orderDetail.setProductBarCode(productInfo.getProductBarCode());
        orderDetail.setOriginPlace(productInfo.getOriginPlace());
        orderDetail.setProductSpec(productInfo.getProductSpec());
        orderDetail.setProductSpec(productInfo.getProductSpec());
      }
      orderDetail.setBigQty(B.div(orderDetail.getQuantity(), orderDetail.getUnitConvert()));
      orderDetail.setRowNetWeight(B.mul(orderDetail.getNetWeight(), orderDetail.getQuantity()));

      orderDetail.setOrderId(orderInfo.getOrderId());
      orderDetail.setOrderDetailId(null);
      inOrderDetailService.save(orderDetail);
      totalQuantity = B.add(totalQuantity, orderDetail.getQuantity());
      totalAmount = B.add(totalAmount, orderDetail.getPurchaseAmount());
      totalRateAmount = B.add(totalRateAmount, orderDetail.getRateAmount());
      totalWeight = B.add(totalWeight, orderDetail.getRowWeight());

      orderInfo.setProviderId(item.getProviderId());
      orderInfo.setProviderCode(item.getProviderCode());
//      orderInfo.setProviderName(item.getProviderShortName());
      orderInfo.setProviderShortName(item.getProviderShortName());
    }
    orderInfo.setTotalQuantity(totalQuantity);
    orderInfo.setTotalAmount(totalAmount);
    orderInfo.setTotalRateAmount(totalRateAmount);
    orderInfo.setTotalWeight(totalWeight);
    inOrderService.saveOrUpdate(orderInfo);
    //添加轨迹
    inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.RETURN_TO_ORDER, InOrderStatusEnum.NEWED);

    return orderInfo.getOrderId();
  }
  //#endregion


  //#region 确认入库

  /**
   * 确认入库
   *
   * @param newOrderId 预到货单Id
   * @return
   */
  @Override
  public R<Void> saveCheck(Long newOrderId, Long returnId) {
    try {

      LambdaQueryWrapper<InOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
      orderLambdaQueryWrapper.eq(InOrder::getOrderId, newOrderId);
      var inOrder = inOrderService.getOne(orderLambdaQueryWrapper);

      LambdaQueryWrapper<InOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(InOrderDetail::getOrderId, newOrderId);
      List<InOrderDetail> outReturnDetails = inOrderDetailService.list(queryWrapper);


      List<InScanOrderDetailBo> orderDetailBos = new ArrayList<>();
      for (var item : outReturnDetails) {
        InScanOrderDetailBo orderDetail = new InScanOrderDetailBo();
        BeanUtil.copyProperties(item, orderDetail);
        orderDetail.setFinishedQuantity(item.getQuantity());
        orderDetailBos.add(orderDetail);
      }

      InScanOrderBo inScanOrderBo = new InScanOrderBo();
      inScanOrderBo.setOrderCode(inOrder.getOrderCode());
      inScanOrderBo.setOrderId(inOrder.getOrderId());
      inScanOrderBo.setDataList(orderDetailBos);
      inScanOrderBo.setScanInType(InventorySourceTypeEnum.PC_OUT_RETURN_CONFIRM);
      inScanOrderService.normalScanSave(inScanOrderBo);


      LambdaQueryWrapper<InOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(InOrder::getOrderId, newOrderId);
      var inOrder1 = inOrderService.getOne(lambdaQueryWrapper);
      if (B.isEqual(inOrder1.getOrderStatus(), InOrderStatusEnum.FINISHED.getName())) {
        // 修改入库计划单状态
        LambdaUpdateWrapper<OutReturn> lambda1 = new UpdateWrapper<OutReturn>().lambda();
        lambda1.set(OutReturn::getReturnStatus, OutReturnStatusEnum.FINISHED.getName()) // 已转预到货单
          .eq(OutReturn::getReturnId, returnId);
        outReturnService.update(lambda1);
      } else if (B.isEqual(inOrder1.getOrderStatus(), InOrderStatusEnum.PARTIAL_FINISHED.getName())) {

        // 修改入库计划单状态
        LambdaUpdateWrapper<OutReturn> lambda2 = new UpdateWrapper<OutReturn>().lambda();
        lambda2.set(OutReturn::getReturnStatus, OutReturnStatusEnum.PARTIAL_FINISHED.getName()) // 已转预到货单
          .eq(OutReturn::getReturnId, returnId);
        outReturnService.update(lambda2);
      } else {
        // 修改入库计划单状态
        LambdaUpdateWrapper<OutReturn> lambda = new UpdateWrapper<OutReturn>().lambda();
        lambda.set(OutReturn::getReturnStatus, OutReturnStatusEnum.CONFIRM_IN.getName()) // 已转预到货单
          .eq(OutReturn::getReturnId, returnId);
        outReturnService.update(lambda);
      }

      LambdaQueryWrapper<OutReturnDetail> outReturnDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
      outReturnDetailLambdaQueryWrapper.eq(OutReturnDetail::getReturnId, returnId);
      var outReturnDetail = outReturnDetailService.list(outReturnDetailLambdaQueryWrapper);


      OutReturn outReturn = outReturnService.getById(returnId);
      for (var item : outReturnDetail) {
        //出库退货单对应的明细
        LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailLambdaQueryWrapper.eq(InOrderDetail::getSourceDetailId, item.getReturnDetailId());
        var orderDetailVo = inOrderDetailService.selectOne(detailLambdaQueryWrapper);
        // 修改退货单明细已收货数量
        LambdaUpdateWrapper<OutReturnDetail> lambdaUpdateWrapper = new UpdateWrapper<OutReturnDetail>().lambda();
        lambdaUpdateWrapper.set(OutReturnDetail::getEnterQuantity, orderDetailVo.getEnterQuantity()) // 已收货数量
          .eq(OutReturnDetail::getReturnDetailId, item.getReturnDetailId());
        outReturnDetailService.update(lambdaUpdateWrapper);

        if (ObjectUtil.isNotEmpty(outReturn.getOrderCode())) {
          if (ObjectUtil.isNotEmpty(item.getOrderDetailId())) {
            LambdaUpdateWrapper<OutOrderDetail> detailLambdaUpdateWrapper = new UpdateWrapper<OutOrderDetail>().lambda();
            detailLambdaUpdateWrapper.set(OutOrderDetail::getQuantityRefunded, orderDetailVo.getEnterQuantity()) // 退货数量
              .eq(OutOrderDetail::getOrderDetailId, item.getOrderDetailId());
            outOrderDetailService.update(detailLambdaUpdateWrapper);
          }
        }
      }


      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion
}
