package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.other.XgOrderTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 湘钢超收
 */
@LiteflowComponent(id = "inScanOverchargeCmp", name = "2.入库扫描超收校验组件")
@RequiredArgsConstructor
public class InScanOverchargeCmp extends NodeComponent {
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;

  @Override
  public void process() throws Exception {
    InScanContext ctx = this.getContextBean(InScanContext.class);
    InScanOrderBo inScanOrderBo = ctx.getInScanOrderBo();
    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList(); // 明细JSON集合数据

    if (B.isEqual(Convert.toBigDecimal(dataList.stream().filter(item -> B.isGreater(item.getFinishedQuantity())).toList().size()))) {
      throw new RuntimeException("入库数量必须大于0");
    }
    if (B.isGreater(Convert.toBigDecimal(dataList.stream().filter(item -> B.isGreater(item.getFinishedQuantity()) && StrUtil.isEmpty(item.getPositionName())).toList().size()))) {
      throw new RuntimeException("入库数量必须大于0");
    }
    //#region 对扫描明细循环验证
    for (InScanOrderDetailBo inScanOrderDetailBo : dataList) {
      InOrder inOrder = inOrderService.getByCode(inScanOrderBo.getOrderCode());
      if (inOrder.getOrderType().equals(XgOrderTypeEnum.ALLOY_AUXILIARY_MATERIAL.getName())) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = sdf1.format(new Date());
        LambdaUpdateWrapper<InOrderDetail> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(InOrderDetail::getQuantity, inScanOrderDetailBo.getQuantity())
          .setSql("expand_fields = json_set(expand_fields,'$.applyDate', '" + today + "')")
          .eq(InOrderDetail::getOrderDetailId, inScanOrderDetailBo.getOrderDetailId());
        inOrderDetailService.update(updateWrapper);
        Map<String, Object> expandFields = inScanOrderDetailBo.getExpandFields();
        expandFields.put("applyDate", new Date());
        inScanOrderDetailBo.setExpandFields(expandFields);
      }


      Long orderDetailId = inScanOrderDetailBo.getOrderDetailId();
      String productCode = inScanOrderDetailBo.getProductCode();
      BigDecimal finishedQuantity = inScanOrderDetailBo.getFinishedQuantity(); // 扫描完成的数量
      var inOrderDetail = inOrderDetailService.getById(orderDetailId);
      var quantity = inOrderDetail.getQuantity();

      // 接收量最大值 不能超过订单量-订单预已接收量+送货量+（订单量*允差）
      if (B.isGreater(inOrderDetail.getOvercharges(), BigDecimal.ZERO)) {
        BigDecimal orderQty = Convert.toBigDecimal(inOrderDetail.getExpandFields().get("orderQty")); // 订单量
        BigDecimal shippedTopQty = Convert.toBigDecimal(inOrderDetail.getExpandFields().get("shippedTopQty")); // 订单预已接收量
        BigDecimal yunchaliang = B.mul(quantity, inOrderDetail.getOvercharges());
        BigDecimal max = B.adds(B.sub(orderQty, shippedTopQty), quantity, yunchaliang);

        if (B.isGreater(finishedQuantity, max)) {
          throw new RuntimeException("产品编号：" + productCode + "已扫描数量超出允许范围！");
        }

      }
      inScanOrderDetailBo.setIsAccomplish(true);
    }
    //#endregion

    //计算磅单均重
    // 预到货数量  /  包数之和
    BigDecimal totalQuantity = dataList.stream().map(InScanOrderDetailBo::getFinishedQuantity).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);


    BigDecimal totalParcelQuantity = dataList.stream().map(InScanOrderDetailBo::getParcelQuantity).filter(B::isGreater).reduce(BigDecimal.ZERO, BigDecimal::add);

    if (B.isGreater(totalParcelQuantity) && B.isGreater(totalQuantity)) {

      BigDecimal div = B.div(totalQuantity, totalParcelQuantity);
      LambdaUpdateWrapper<InOrder> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper
        .setSql("expand_fields = json_set(expand_fields,'$.poundAvg', " + div + ")") //磅单均重 =  合计数量/ 包数之和

        .eq(InOrder::getOrderId, inScanOrderBo.getOrderId());
      ctx.getInOrder().getExpandFields().put("poundAvg", div);

      for (var item : ctx.getInScanOrderBo().getDataList()) {
        item.getExpandFields().put("poundAvg", div);
      }
      inOrderService.update(updateWrapper);
    }


  }
}
