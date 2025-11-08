package com.yiruantong.composite.service.inventory.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.BaseProvider;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.product.IBaseProviderService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.composite.service.inventory.IInventoryAdviseService;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InventoryAdviseServiceImpl implements IInventoryAdviseService {

  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IBaseProductService baseProductService;
  private final IBaseProviderService baseProviderService;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> toPurchaseOrder(List<CoreInventoryAdvise> dataList) {

    // 定义一个函数Function，该函数将元素对象映射到一个键的集合里
    Function<CoreInventoryAdvise, List<Object>> compositeKey = info -> Arrays.asList(info.getProviderId(), info.getStorageId(), info.getConsignorId());
    // 分组
    Map<List<Object>, List<CoreInventoryAdvise>> groupList = dataList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));

    for (var mapStorageItem : groupList.entrySet()) {

      List<CoreInventoryAdvise> list = mapStorageItem.getValue();

      InOrder orderInfo = new InOrder();
      CoreInventoryAdvise bo = list.get(0);
      BeanUtil.copyProperties(bo, orderInfo);
      String orderCode = DBUtils.getCodeRegular(MenuEnum.MENU_1001, null);
      orderInfo.setOrderCode(orderCode);
      orderInfo.setOrderType(InOrderTypeEnum.SUGGESTION.getName());
      orderInfo.setSourceType(InOrderTypeEnum.SUGGESTION_SHIFT_TO.getName());
      orderInfo.setOrderStatus(InOrderStatusEnum.NEWED.getName());
      orderInfo.setShelveStatus(InOrderStatusEnum.HISTORY_WAITING.getName());
      inOrderService.save(orderInfo);

      // 获取供应商
      BaseProvider baseProvider = baseProviderService.getByShortName(bo.getProviderShortName());


      BigDecimal totalQuantity = BigDecimal.ZERO;
      BigDecimal totalAmount = BigDecimal.ZERO;
      BigDecimal totalRateAmount = BigDecimal.ZERO;
      BigDecimal totalWeight = BigDecimal.ZERO;
      for (CoreInventoryAdvise info : list) {
        BaseProduct productInfo = baseProductService.getById(info.getProductId());
        InOrderDetail orderDetail = new InOrderDetail();

        BeanUtil.copyProperties(productInfo, orderDetail);
        BeanUtil.copyProperties(info, orderDetail, CopyOptions.create().setIgnoreNullValue(true));



        orderDetail.setQuantity(info.getAdviseQty());
        orderDetail.setRowWeight(B.mul(orderDetail.getWeight(), orderDetail.getQuantity()));
        orderDetail.setRowWeightTon(B.div(orderDetail.getRowWeight(), new BigDecimal(1000)));
        orderDetail.setRowCube(B.mul(orderDetail.getUnitCube(), orderDetail.getQuantity()));
        orderDetail.setPurchaseAmount(B.mul(orderDetail.getPurchasePrice(), orderDetail.getQuantity()));

        orderDetail.setRate(productInfo.getRate());
        if (ObjectUtil.isNotEmpty(baseProvider)) {
          orderDetail.setRate(baseProvider.getRate());
        }
        BigDecimal ratePrice = B.mul(orderDetail.getPurchasePrice(), orderDetail.getRate());
        orderDetail.setRatePrice(B.add(orderDetail.getPurchasePrice(), ratePrice));
        orderDetail.setRateAmount(B.mul(orderDetail.getRatePrice(), orderDetail.getQuantity()));

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
      inOrderStatusHistoryService.addHistoryInfo(orderInfo, InOrderActionEnum.SUGGESTION_TO, InOrderStatusEnum.NEWED);
    }

    return R.ok("操作成功");
  }

  @Override
  public R<Void> toTmsQuotation(List<CoreInventoryAdvise> dataList) {
    return R.ok("涉及到TMS 还未实现");
  }
}
