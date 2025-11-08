package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.QrCodeTypeEnum;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutPrintService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出库打印服务类
 */
@RequiredArgsConstructor
@Service
public class OutPrintService implements IOutPrintService {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;

  @Override
  public R<Map<String, Object>> printOutOrderLabel(List<QueryBo> queryBoList) {
    Long orderId = queryBoList.stream().filter(f -> StringUtils.equals(f.getColumn(), "orderId")).map(m -> Convert.toLong(m.getValues())).findFirst().orElse(null);
    Assert.isFalse(orderId == null, "出库单ID不能为空！");

    Map<String, Object> resultMapList = new HashMap<>(); // 返回结果

    // 主表数据
    LambdaQueryWrapper<OutOrder> waveLambdaQueryWrapper = new LambdaQueryWrapper<>();
    waveLambdaQueryWrapper.eq(OutOrder::getOrderId, orderId);
    Map<String, Object> mainMap = outOrderService.getMap(waveLambdaQueryWrapper);

    // 明细表数据
    List<OutOrderDetail> outOrderDetails = outOrderDetailService.selectListByMainId(orderId);

    // qrCode二维码数据整理
    Map<String, Object> qrCodeMap = new HashMap<>();
    qrCodeMap.put("type", QrCodeTypeEnum.OUT_ORDER);
    qrCodeMap.put("orderCode", mainMap.get("orderCode"));
    mainMap.put("qrCode", JSONUtil.toJsonStr(qrCodeMap)); // 蒋二维码数据放入mainMap

    if (!outOrderDetails.isEmpty()) {
      mainMap.putAll(BeanUtil.beanToMap(outOrderDetails.get(0), false, true));
    }

    resultMapList.put("isCustomData", true); // 自定义数据，必须标记上
    resultMapList.put("dataInfo", mainMap);

    return R.ok(resultMapList);
  }
}
