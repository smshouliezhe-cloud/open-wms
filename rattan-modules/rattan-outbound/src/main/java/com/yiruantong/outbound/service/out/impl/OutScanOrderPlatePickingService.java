package com.yiruantong.outbound.service.out.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import com.yiruantong.outbound.service.out.IOutScanOrderPlatePickingService;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class OutScanOrderPlatePickingService implements IOutScanOrderPlatePickingService {


  private final IOutOrderWaveDetailService OutOrderWaveDetailService;

  /**
   * 按拍下架 - 获取扫描数据
   *
   * @param map 前端参数
   */
  @Override
  public R<Map<String, Object>> getOutOrderWaveList(Map<String, Object> map) {

    //#region 获取需要扫描的数据
//    MPJLambdaWrapper<OutOrderWaveDetail> detailWrapper = new MPJLambdaWrapper<>();
//    detailWrapper
//      .select(OutOrderWaveDetail::getOrderId)
//      .select(InOrder::getStorageId)
//      .select(BaseProduct::getImages)
//      .innerJoin(OutOrderWaveDetail.class, InOrder::orderWaveId, InOrderDetail::orderWaveId)
//      .innerJoin(BaseProduct.class, BaseProduct::getProductId, InOrderDetail::getProductId)
//      .eq(InOrder::getOrderId, orderInfo.getOrderId());
//    List<Map<String, Object>> orderList = OutOrderWaveDetailService.selectJoinMaps(detailWrapper);
//
//    if (ArrayUtil.isEmpty(orderList)) {
//      throw new ServiceException("未找到对应的订单明细数据");
//    }





    // 返回数据
    Map<String, Object> resultData = new HashMap<>();
    resultData.put("orderList", "");


    return R.ok(resultData);
  }
  //endregion
}
