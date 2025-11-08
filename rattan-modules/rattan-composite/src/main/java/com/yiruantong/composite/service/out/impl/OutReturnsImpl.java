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
import com.yiruantong.common.core.enums.out.OutReturnStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.service.out.IOutReturnsService;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
import com.yiruantong.inbound.service.in.IInScanOrderService;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.service.OutReturn;
import com.yiruantong.outbound.domain.service.OutReturnDetail;
import com.yiruantong.outbound.domain.service.vo.OutReturnVo;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.service.IOutReturnDetailService;
import com.yiruantong.outbound.service.service.IOutReturnService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.context.NoSuchMessageException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OutReturnsImpl implements IOutReturnsService {

  private final IOutReturnService outReturnService;
  private final IInOrderService inOrderService;
  private final IOutReturnDetailService outReturnDetailService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IInScanOrderService inScanOrderService;
  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderDetailService outOrderDetailService;


  //#region 生成预到货单

  /**
   * 出库退货单转预到货
   *
   * @param map 前端参数
   * @return
   */
  public R<Void> toInOrder(Map<String, Object> map) {
    try {
      String[] ids = StringUtils.split(map.get("ids").toString(), ",");
      Long[] _ids = Convert.toLongArray(ids);

      for (Long returnId : _ids) {
        this.createOrderCode(returnId);

        // 修改入库计划单状态
        LambdaUpdateWrapper<OutReturn> lambda = new UpdateWrapper<OutReturn>().lambda();
        lambda.set(OutReturn::getReturnStatus, OutReturnStatusEnum.OVER_TO_INORDER.getName()) // 已转预到货单
          .eq(OutReturn::getReturnId, returnId);
        outReturnService.update(lambda);
      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion


  //#region 创建预到货 createOrderCode

  /**
   * 创建预到货
   *
   * @param orderId 库存ID
   * @return
   */
  @Override
  public Long createOrderCode(Long orderId) {

    OutReturnVo outReturnVo = outReturnService.selectById(orderId);
    if (ObjectUtil.isEmpty(outReturnVo)) {
      throw new ServiceException("未获取到入库计划单");
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
    outReturnLma.eq(OutReturnDetail::getReturnId, orderId);
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
   * @param ids 前端参数
   * @return
   */
  public R<Void> saveCheck(List<Long> ids) {
    try {
      for (Long returnId : ids) {
        Long orderId = this.createOrderCode(returnId);

        LambdaQueryWrapper<InOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(InOrder::getOrderId, orderId);
        var inOrder = inOrderService.getOne(orderLambdaQueryWrapper);

        LambdaQueryWrapper<InOrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InOrderDetail::getOrderId, orderId);
        List<InOrderDetail> outReturnDetails = inOrderDetailService.list(queryWrapper);


        List<InScanOrderDetailBo> orderDetailBos = new ArrayList<>();
        for (var item : outReturnDetails) {
          InScanOrderDetailBo orderDetail = new InScanOrderDetailBo();
          BeanUtil.copyProperties(item, orderDetail);
          orderDetail.setFinishedQuantity(item.getReturnQuantity());
          orderDetailBos.add(orderDetail);
        }

        InScanOrderBo inScanOrderBo = new InScanOrderBo();
        inScanOrderBo.setOrderCode(inOrder.getOrderCode());
        inScanOrderBo.setOrderId(inOrder.getOrderId());
        inScanOrderBo.setDataList(orderDetailBos);
        inScanOrderBo.setScanInType(InventorySourceTypeEnum.PC_OUT_RETURN_CONFIRM);
        inScanOrderService.normalScanSave(inScanOrderBo);


        LambdaQueryWrapper<InOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(InOrder::getOrderId, orderId);
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

      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }


}
