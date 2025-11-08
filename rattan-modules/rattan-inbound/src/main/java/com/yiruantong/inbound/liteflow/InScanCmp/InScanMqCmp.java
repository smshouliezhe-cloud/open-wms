package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.InEnterDetail;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.task.ITaskQueueService;

import java.util.HashMap;
import java.util.List;

@LiteflowComponent(id = "inScanMqCmp", name = "6.消息队列组件")
@RequiredArgsConstructor
public class InScanMqCmp extends NodeComponent {
  private final IInOrderService inOrderService;
  private final ISysConfigService sysConfigService;
  private final ITaskQueueService taskQueueService;

  @Override
  public void process() {
    InScanOrderBo inScanOrderBo = this.getRequestData();
    InScanContext ctx = this.getContextBean(InScanContext.class);
    InOrder inOrder = ctx.getInOrder();
    InEnter inEnter = ctx.getInEnter();
    List<InEnterDetail> invalidateDetails = ctx.getInvalidateDetails();

    //如果订单是调拨单传入 并且来源类型是【常规调拨入库	】 则需要在执行其他业务逻辑信息
    if (StrUtil.equals(inOrder.getSourceType(), StorageAllocateApplyStatusEnum.ROUTINE_ALLOCATE_IN.getName())) {
      inOrder = inOrderService.getById(inOrder.getOrderId()); // 从新获取预到货库单 放到IF里面 避免所有类型的单据都进行查询
      if (StrUtil.equals(inOrder.getOrderStatus(), InOrderStatusEnum.FINISHED.getName())) {
        //调用RabbitMQ
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_ALLOCATE_APPLY); //类别
        rabbitReceiverDto.setBillId(inOrder.getOrderId());
        rabbitReceiverDto.setBillCode(inOrder.getOrderCode());
        rabbitReceiverDto.setSourceCode(inOrder.getSourceCode());
        rabbitReceiverDto.setSourceId(inOrder.getSourceId());
        taskQueueService.createTask(rabbitReceiverDto);
        //#endregion
      }
    }
    //如果订单是退货转入 则会更退货单数据
    if (StrUtil.equals(inOrder.getSourceType(), InOrderTypeEnum.RETURN_TO_ORDER.getName())) {
      if (!ObjectUtil.equals(inScanOrderBo.getScanInType(), InventorySourceTypeEnum.PC_OUT_RETURN_CONFIRM)) {

        //调用RabbitMQ
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_UPDATE_OUT_RETURN); //类别
        rabbitReceiverDto.setBillId(inOrder.getOrderId());
        rabbitReceiverDto.setBillCode(inOrder.getOrderCode());
        rabbitReceiverDto.setSourceCode(inOrder.getSourceCode());
        rabbitReceiverDto.setSourceId(inOrder.getSourceId());
        taskQueueService.createTask(rabbitReceiverDto);
      }
    }

    //如果订单是入库计划转入， 则会入库计划单数据
    if (StrUtil.equals(inOrder.getSourceType(), InOrderTypeEnum.ORDER_PLAN.getName())) {
      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_PLAN); //类别
      rabbitReceiverDto.setBillId(inOrder.getOrderId());
      rabbitReceiverDto.setBillCode(inOrder.getOrderCode());
      rabbitReceiverDto.setSourceCode(inOrder.getSourceCode());
      rabbitReceiverDto.setSourceId(inOrder.getSourceId());
      taskQueueService.createTask(rabbitReceiverDto);
    }

    //如果费用项不为空则 创建每日账单
    if (StrUtil.isNotEmpty(inOrder.getFeeItemIds())) {
      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_GENERATING_BILL); //类别
      rabbitReceiverDto.setBillId(inEnter.getEnterId());
      rabbitReceiverDto.setBillCode(inEnter.getEnterCode());
      rabbitReceiverDto.setSourceCode(inEnter.getOrderCode());
      rabbitReceiverDto.setSourceId("" + inEnter.getOrderId());
      taskQueueService.createTask(rabbitReceiverDto);
    }

    // 报废出库的商品属性 生成出库单 并自动出库
    if (B.isGreater(invalidateDetails.size())) {
      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_INVALIDATE_OUT); //类别
      rabbitReceiverDto.setBillId(inEnter.getEnterId());
      rabbitReceiverDto.setBillCode(inEnter.getEnterCode());
      rabbitReceiverDto.setSourceCode(inEnter.getOrderCode());
      rabbitReceiverDto.setSourceId("" + inEnter.getOrderId());
      HashMap<String, Object> map = new HashMap<>();
      map.put("data", invalidateDetails);
      rabbitReceiverDto.setOtherField(map);
      taskQueueService.createTask(rabbitReceiverDto);
    }

    if (inScanOrderBo.isOnShelve()) {
      //调用RabbitMQ，入库上架成功后，自动分拣出库单
      RabbitReceiverDto receiverDto = new RabbitReceiverDto();
      receiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_AUTO_SORTING); // 自动分拣出库单
      receiverDto.setBillId(inEnter.getEnterId());
      receiverDto.setBillCode(inEnter.getEnterCode());
      taskQueueService.createTask(receiverDto);

      boolean in_finished_to_wcs = sysConfigService.getConfigBool("in_finished_to_wcs"); // 入库直接上架后自动生成WCS任务
      if (in_finished_to_wcs) {
        //调用RabbitMQ，生成WCS接口数据
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_WCS); // 入库上架后生成WCS接口数据
        rabbitReceiverDto.setBillId(inEnter.getEnterId());
        rabbitReceiverDto.setBillCode(inEnter.getEnterCode());
        rabbitReceiverDto.setSourceId(inOrder.getOrderId().toString());
        rabbitReceiverDto.setSourceCode(inOrder.getOrderCode());
        taskQueueService.createTask(rabbitReceiverDto);
      }
    }

    //如果订单是退料单转入 则会更退料单数据
    if (StrUtil.equals(inOrder.getSourceType(), InOrderTypeEnum.RETURN_ORDER.getName())) {
      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_UPDATE_MES_RETURN); //类别
      rabbitReceiverDto.setBillId(inOrder.getOrderId());
      rabbitReceiverDto.setBillCode(inOrder.getOrderCode());
      rabbitReceiverDto.setSourceCode(inOrder.getSourceCode());
      rabbitReceiverDto.setSourceId(inOrder.getSourceId());
      taskQueueService.createTask(rabbitReceiverDto);
    }

    //如果订单是成品入库单转入， 则会回更成品入库单数据
    if (StrUtil.equals(inOrder.getSourceType(), InOrderTypeEnum.COMPLETION_ORDER.getName())) {
      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.IN_FINISHED_TO_COMPLETION); //类别
      rabbitReceiverDto.setBillId(inOrder.getOrderId());
      rabbitReceiverDto.setBillCode(inOrder.getOrderCode());
      rabbitReceiverDto.setSourceCode(inOrder.getSourceCode());
      rabbitReceiverDto.setSourceId(inOrder.getSourceId());
      taskQueueService.createTask(rabbitReceiverDto);
    }
  }
}
