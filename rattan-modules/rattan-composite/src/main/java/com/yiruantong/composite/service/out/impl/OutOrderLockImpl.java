package com.yiruantong.composite.service.out.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.service.out.IOutOrderLockService;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OrderDetailLackBo;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OutOrderLockImpl implements IOutOrderLockService {
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;
  private final IOutOrderService outOrderService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutOrderWaveService outOrderWaveService;

  //#region 生成预到货单

  /**
   * 生成预到货单
   *
   * @param dataList 数据集合
   * @return 提示信息
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> toPurchaseOrder(List<OrderDetailLackBo> dataList) {
    // 定义一个函数Function，该函数将元素对象映射到一个键的集合里
    Function<OrderDetailLackBo, List<Object>> compositeKey = info -> Arrays.asList(info.getProviderId(), info.getStorageId(), info.getConsignorId());
    // 分组
    Map<List<Object>, List<OrderDetailLackBo>> groupList = dataList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));

    for (var mapStorageItem : groupList.entrySet()) {

      List<OrderDetailLackBo> list = mapStorageItem.getValue();

      InOrder orderInfo = new InOrder();
      OrderDetailLackBo bo = list.get(0);
      BeanUtil.copyProperties(bo, orderInfo);
      String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1001, null);
      orderInfo.setOrderCode(orderCode);
      orderInfo.setOrderType(InOrderTypeEnum.LOCK_TO_ORDER.getName());
      orderInfo.setSourceType(InOrderTypeEnum.LOCK_TO_ORDER.getName());
      orderInfo.setOrderStatus(InOrderStatusEnum.NEWED.getName());
      orderInfo.setTrackingNumber(bo.getOrderCode());
      orderInfo.setOrderId(null);
      orderInfo.setShelveStatus(InOrderStatusEnum.HISTORY_WAITING.getName());
      inOrderService.save(orderInfo);

      BigDecimal totalQuantity = BigDecimal.ZERO;
      BigDecimal totalAmount = BigDecimal.ZERO;
      BigDecimal totalRateAmount = BigDecimal.ZERO;
      BigDecimal totalWeight = BigDecimal.ZERO;
      for (OrderDetailLackBo info : list) {
        OutOrderDetail detail = outOrderDetailService.getBaseMapper().selectById(info.getOrderDetailId());
        InOrderDetail orderDetail = new InOrderDetail();
        BeanUtil.copyProperties(detail, orderDetail);
        BeanUtil.copyProperties(info, orderDetail, CopyOptions.create().setIgnoreNullValue(true));
        orderDetail.setPurchasePrice(info.getPurchasePrice());
        orderDetail.setQuantity(info.getPurchaseStorage());
        orderDetail.setRowWeight(B.mul(orderDetail.getWeight(), orderDetail.getQuantity()));
        orderDetail.setRowWeightTon(B.div(orderDetail.getRowWeight(), new BigDecimal(1000)));
        orderDetail.setRowCube(B.mul(orderDetail.getUnitCube(), orderDetail.getQuantity()));
        orderDetail.setPurchaseAmount(B.mul(orderDetail.getPurchasePrice(), orderDetail.getQuantity()));


        BaseProduct productInfo = baseProductService.getById(orderDetail.getProductId());
        var providerInfo = baseProviderService.getByShortName(orderInfo.getProviderShortName());

        Assert.isFalse(ObjectUtil.isNull(providerInfo), "未找到【" + orderInfo.getProviderShortName() + "】供应商");
        if (ObjectUtil.isNotNull(productInfo)) {
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
      }
      orderInfo.setTotalQuantity(totalQuantity);
      orderInfo.setTotalAmount(totalAmount);
      orderInfo.setTotalRateAmount(totalRateAmount);
      orderInfo.setTotalWeight(totalWeight);
      inOrderService.saveOrUpdate(orderInfo);
      //添加轨迹
      inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.LOCK_TO_ORDER, InOrderStatusEnum.NEWED);


    }
    return R.ok("生成预到货单成功");
  }
  //#endregion

  //#region 生成入库计划单

  /**
   * 生成入库计划单
   *
   * @param dataList 数据集合
   * @return 提示信息
   */
  @Override
  public R<Void> toTmsQuotation(List<OrderDetailLackBo> dataList) {
    return null;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> cancelApply(List<Long> ids) {

    List<OutOrder> outOrders = outOrderService.listByIds(ids);
    var data = outOrders.stream().filter(item -> !B.isEqual(item.getOrderStatus(), OutOrderStatusEnum.PACKAGE_FINISHED.getName())).toList();
    if (B.isGreater(data.size())) {
      throw new ServiceException("只有已出库的订单才允许撤回申请！");
    }
    for (OutOrder info : outOrders) {
      outOrderService.updateOrderStatus(info.getOrderId(), OutOrderStatusEnum.CANCEL_APPLY);
      outOrderStatusHistoryService.AddHistory(info, OutOperationTypeEnum.ORDER_CANCEL, OutOrderStatusEnum.CANCEL_APPLY);
    }


    return R.ok("撤回订单申请成功！");
  }

  /**
   * 标记PDA分拣成功
   *
   * @param ids
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> pdaSorting(List<Long> ids) {
    List<OutOrder> outOrders = outOrderService.listByIds(ids);
    for (OutOrder outOrder : outOrders) {

      // 如果订单紧急订单 则不能标记 PDA分拣
      if (B.isEqual(outOrder.getOrderStatus(), AuditEnum.AUDITED_SUCCESS.getName())) {
        throw new ServiceException(outOrder.getOrderCode() + "：不是待出库订单，不允许标记PDA分拣！");
      }
      Map<String, Object> expandFields = outOrder.getExpandFields();
      if (ObjectUtil.isNull(expandFields)) {
        expandFields = new HashMap<>();
      }
      expandFields.put("pdaSorting", true);

      // 如果订单紧急订单 则不能标记 PDA分拣
      if (ObjectUtil.isNotNull(expandFields.get("release")) && Convert.toBool(expandFields.get("release"))) {
        throw new ServiceException(outOrder.getOrderCode() + "：是紧急订单，不允许标记PDA分拣！");
      }

      LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrder::getExpandFields, JsonUtils.toJsonString(expandFields))
        .eq(OutOrder::getOrderId, outOrder.getOrderId());
      outOrderService.update(updateWrapper);
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.ORDER_OPERATE, OutOrderStatusEnum.AUDIT_SUCCESS, "PDA分拣");
    }
    return R.ok("操作成功");

  }

  //#endregion
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> orderCancel(List<Long> ids) {
    List<OutOrder> outOrders = outOrderService.listByIds(ids);

    var data = outOrders.stream().filter(item -> !B.isEqual(item.getOrderStatus(), OutOrderStatusEnum.CANCEL_APPLY.getName())).toList();
    if (B.isGreater(data.size())) {
      throw new ServiceException("只有状态为撤回申请的单据才允许进行撤回！");
    }

    for (OutOrder outOrder : outOrders) {


      //1：派车单 ，配送单数据 (会更新出库单状态为审核成功)
      OutOrderWave waveInfo = outOrderWaveService.getById(outOrder.getOrderWaveId());
      if (ObjectUtil.isNotNull(waveInfo)) {
        //删除波次单
        outOrderWaveService.deleteById(waveInfo.getOrderWaveId());
      }


      //2：库存数据新增，根据占位原始数据插入库存（新增）
      HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(outOrder.getOrderType());
      List<CoreInventoryHolder> coreInventoryHolders = coreInventoryHolderService.selectHolderList(outOrder.getOrderId(), holderSourceTypeEnum);
      if (ObjectUtil.isNull(coreInventoryHolders) || B.isEqual(coreInventoryHolders.size())) {
        throw new ServiceException(outOrder.getOrderCode() + "未找到占位信息");
      }
      for (CoreInventoryHolder holderInfo : coreInventoryHolders) {
        //添加库存信息
        CoreInventory coreInventory = coreInventoryService.getById(holderInfo.getInventoryId());
        coreInventory.setInventoryId(null);
        coreInventory.setSourceType("订单撤回");
        coreInventory.setProductStorage(holderInfo.getOrignHolderStorage());
        coreInventory.setOriginStorage(holderInfo.getOrignHolderStorage());
        coreInventory.setHolderStorage(BigDecimal.ZERO);
        coreInventory.setValidStorage(holderInfo.getOrignHolderStorage());
        coreInventoryService.save(coreInventory);
      }

      //4：增加出库单轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.ORDER_CANCEL, OutOrderStatusEnum.AUDIT_SUCCESS);

    }
    //修改出库单状态
    LambdaUpdateWrapper<OutOrder> outOrderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    outOrderLambdaUpdateWrapper.set(OutOrder::getOrderStatus, OutOrderStatusEnum.AUDIT_SUCCESS.getName())
      .set(OutOrder::getExpressCode, null)
      .set(OutOrder::getOrderWaveCode, null)
      .set(OutOrder::getOrderWaveId, null)
      .set(OutOrder::getSortingStatus, SortingStatusEnum.NONE.getId())
      .in(OutOrder::getOrderId, ids);
    outOrderService.update(outOrderLambdaUpdateWrapper);


    return R.ok("订单撤回成功！");
  }

}
