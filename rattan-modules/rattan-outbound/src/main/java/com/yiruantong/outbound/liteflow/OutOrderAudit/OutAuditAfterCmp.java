package com.yiruantong.outbound.liteflow.OutOrderAudit;

import cn.hutool.core.util.StrUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.liteflow.Context.OutOrderAuditingContext;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.system.service.task.ITaskQueueService;

import java.util.ArrayList;
import java.util.List;

@LiteflowComponent(id = "OutAuditAfterCmp", name = "出库审核后生成运单")
@RequiredArgsConstructor
public class OutAuditAfterCmp extends NodeComponent {


  private final ITaskQueueService taskQueueService;
  private final IOutOrderService outOrderService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;

  @Override
  public void process() {

    List<OutOrder> outOrders = this.getRequestData();
    OutOrderAuditingContext ctx = this.getContextBean(OutOrderAuditingContext.class);

    ctx.setOutOrders(outOrders);
    List<OutOrder> outOrderBo = ctx.getOutOrders();

    for (OutOrder outOrder : outOrderBo) {
      // 查询出库单
      var outOrderInfo = outOrderService.getById(outOrder.getOrderId());

      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto1 = new RabbitReceiverDto();
      rabbitReceiverDto1.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_BASE_PLATE_OUT); //出库完成之后生成容器借出单
      rabbitReceiverDto1.setBillId(outOrder.getOrderId());
      rabbitReceiverDto1.setBillCode(outOrder.getOrderCode());
      rabbitReceiverDto1.setSourceCode(outOrder.getSourceCode());
      rabbitReceiverDto1.setSourceId(outOrder.getSourceId());
      taskQueueService.createTask(rabbitReceiverDto1);
      //生成 TMS单据，物流发货，省际，城配
      List<String> arr = new ArrayList<>();
      arr.add("物流发货");
      arr.add("省际");
      arr.add("城配");
      if (arr.stream().anyMatch(item -> StrUtil.equals(outOrderInfo.getDistributionType(), item))) {
        //调用RabbitMQ
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_TMS_WAY_BILL); // 生成TMS运单
        rabbitReceiverDto.setBillId(outOrder.getOrderId());
        rabbitReceiverDto.setBillCode(outOrder.getOrderCode());
        rabbitReceiverDto.setSourceCode(outOrder.getSourceCode());
        rabbitReceiverDto.setSourceId(outOrder.getSourceId());
        // HashMap<String, Object> otherField = new HashMap<>();
        // otherField.put("deliveryDate", outScanMainBo.getDeliveryDate());
        // rabbitReceiverDto.setOtherField(otherField);
        taskQueueService.createTask(rabbitReceiverDto);
      }
    }
  }

}
