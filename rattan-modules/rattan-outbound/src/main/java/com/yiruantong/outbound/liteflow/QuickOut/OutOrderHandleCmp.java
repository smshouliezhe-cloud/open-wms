package com.yiruantong.outbound.liteflow.QuickOut;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.inventory.domain.core.CoreSortingRule;
import com.yiruantong.inventory.service.core.ICoreSortingRuleService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.QuickOutContext;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutSortingRuleService;
import com.yiruantong.system.service.task.ITaskQueueService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LiteflowComponent(id = "qutOrderHandleCmp", name = "2.出库单处理(湘钢)")
@RequiredArgsConstructor
public class OutOrderHandleCmp extends NodeComponent {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;

  private final ITaskQueueService taskQueueService;

  /**
   * 如果出库单已经占位了 一键出库时，修改了货位或者批次号 则需要释放库存重新占位，并在出库
   */
  @Override
  public void process() {

    QuickOutContext ctx = this.getContextBean(QuickOutContext.class);
    OutScanMainBo outScanMainBo = ctx.getOutScanMainBo();
    OutOrder outOrder = ctx.getOutOrder();

    //如果是已分配的单据需要 释放库存
    if (B.isEqual(outOrder.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId())) {
      Map<String, Object> maps = new HashMap<>();
      maps.put("ids", outOrder.getOrderId());
      //单据终止开启一下 重新分拣
      outOrderService.stop(maps);
      outOrderService.open(maps);
    }

    List<Map<String, Object>> orderList = new ArrayList<>();
    for (OutScanDetailBo item : outScanMainBo.getDataList()) {
      // 湘钢  根据  实际出库数量去  更新单据， 然后占位
      LambdaUpdateWrapper<OutOrderDetail> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrderDetail::getUnitCube, item.getUnitCube())
        .set(OutOrderDetail::getBigQty, item.getBigQty())
        .setSql("expand_fields = json_set(expand_fields,'$.ifOver', '" + item.getExpandFields().get("ifOver") + "')") //签收数量等于  数量 - 拒收数量
        .setSql("expand_fields = json_set(expand_fields,'$.finishedQuantity', '" +  item.getFinishedQuantity()+ "')") //记录扫描数量
        .set(OutOrderDetail::getBatchNumber, item.getBatchNumber())
        .set(OutOrderDetail::getQuantityOrder,item.getFinishedQuantity())
        .set(OutOrderDetail::getPositionName, item.getPositionName())
        .set(OutOrderDetail::getRemark, item.getRemark())
        .eq(OutOrderDetail::getOrderDetailId, item.getOrderDetailId());
      outOrderDetailService.update(updateWrapper);

      Map<String, Object> maps = new HashMap<>();
      maps.put("orderDetailId", item.getOrderDetailId());
      maps.put("quantityOrder", item.getFinishedQuantity());
      orderList.add(maps);

    }
    //修改已生成的运单数量
    RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
    rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_ORDER_UPDATE_WAY_BILL); //类别
    rabbitReceiverDto.setBillId(outOrder.getOrderId());
    rabbitReceiverDto.setBillCode(outOrder.getOrderCode());
    Map<String, Object> maps = new HashMap<>();
    maps.put("orderList", orderList);
    rabbitReceiverDto.setOtherField(maps);
    taskQueueService.createTask(rabbitReceiverDto);

    outOrder = outOrderService.getBaseMapper().selectById(outScanMainBo.getOrderId());

    ctx.setOutOrder(outOrder);
  }
}
