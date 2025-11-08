package com.yiruantong.outbound.liteflow.OutScanCmp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlateProduct;
import com.yiruantong.basic.service.storage.IBasePlateProductService;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.liteflow.Context.OutScanContext;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;

import java.util.List;

@LiteflowComponent(id = "outScanGenPlateCmp", name = "5.生成容器组件")
@RequiredArgsConstructor
public class OutScanGenPlateCmp extends NodeComponent {
  private final IOutOrderDetailService outOrderDetailService;
  private final IBasePlateProductService basePlateProductService;
  private final IOutOrderService outOrderService;

  @Override
  public void process() {
    OutScanContext ctx = this.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder(); // 出库单主表信息
    List<OutOrderDetail> outOrderDetailList = ctx.getOutOrderDetailList(); // 出库单明细集合

    for (var item : outOrderDetailList) {
      // 查询商品容器数据
      LambdaQueryWrapper<BasePlateProduct> plateProductLambdaQueryWrapper = new LambdaQueryWrapper<>();
      plateProductLambdaQueryWrapper
        .eq(BasePlateProduct::getProductId, item.getProductId());
      var basePlateProduct = basePlateProductService.getOne(plateProductLambdaQueryWrapper);

      // 给出库单明细记录一个标识，代表此条单据是容器商品
      if (ObjectUtil.isNotNull(basePlateProduct)) {
        //修改明细是否容器商品
        LambdaUpdateWrapper<OutOrderDetail> lambda = new UpdateWrapper<OutOrderDetail>().lambda();
        lambda.set(OutOrderDetail::getIsPlate, EnableEnum.ENABLE.getId()) // 是容器商品
          .eq(OutOrderDetail::getOrderDetailId, item.getOrderDetailId());
        outOrderDetailService.update(lambda);//提交

        //修改主表是否容器商品
        LambdaUpdateWrapper<OutOrder> outOrderLambda = new LambdaUpdateWrapper<>();
        outOrderLambda.set(OutOrder::getIsPlate, EnableEnum.ENABLE.getId())
          .eq(OutOrder::getOrderId, outOrder.getOrderId());
        outOrderService.update(outOrderLambda);
      }
    }
  }
}
