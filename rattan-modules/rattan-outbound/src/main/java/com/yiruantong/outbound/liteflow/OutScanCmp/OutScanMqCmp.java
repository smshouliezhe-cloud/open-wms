package com.yiruantong.outbound.liteflow.OutScanCmp;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.enums.out.OutSourceTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutPackage;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.OutScanContext;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.task.ITaskQueueService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@LiteflowComponent
@RequiredArgsConstructor
public class OutScanMqCmp {
  private final IOutOrderService outOrderService;
  private final ISysConfigService sysConfigService;
  private final ITaskQueueService taskQueueService;

  @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "OUT_FINISHED_TO_ALLOCATE", nodeName = "调拨单转到出库单，出库单出库后续操作", nodeType = NodeTypeEnum.COMMON)
  public void processA(NodeComponent bindCmp) {
    OutScanContext ctx = bindCmp.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();
    Long orderId = outOrder.getOrderId(); // 出库单ID

    //#region 消息队列
    if (StrUtil.equals(outOrder.getSourceType(), StorageAllocateApplyStatusEnum.ROUTINE_ALLOCATE_OUT.getName())) {
      OutOrder outOrderInfo = outOrderService.getById(orderId); // 从新获取出库单 放到IF里面 避免所有类型的单据都进行查询
      if (StrUtil.equals(outOrderInfo.getOrderStatus(), OutOrderStatusEnum.PACKAGE_FINISHED.getName())) {
        //调用RabbitMQ
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_ALLOCATE); //类别
        rabbitReceiverDto.setBillId(orderId);
        rabbitReceiverDto.setBillCode(outOrderInfo.getOrderCode());
        rabbitReceiverDto.setSourceCode(outOrderInfo.getSourceCode());
        rabbitReceiverDto.setSourceId(outOrderInfo.getSourceId());
        rabbitReceiverDto.setLoginUser(ctx.getLoginUser());
        taskQueueService.createTask(rabbitReceiverDto);
        //#endregion
      }
    }
  }


  @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "OUT_FINISHED_TO_RETURN_ORDER", nodeName = "出库完成后更新到货退货单", nodeType = NodeTypeEnum.COMMON)
  public void processB(NodeComponent bindCmp) {
    OutScanContext ctx = bindCmp.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();

    // 到货退货单
    if (StrUtil.equals(outOrder.getSourceType(), OutSourceTypeEnum.RETURN_ORDER.getName())) {
      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_RETURN_ORDER); //类别
      rabbitReceiverDto.setBillId(outOrder.getOrderId());
      rabbitReceiverDto.setBillCode(outOrder.getOrderCode());
      rabbitReceiverDto.setSourceCode(outOrder.getSourceCode());
      rabbitReceiverDto.setSourceId(outOrder.getSourceId());
      rabbitReceiverDto.setLoginUser(ctx.getLoginUser());
      taskQueueService.createTask(rabbitReceiverDto);
    }
  }

  @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "OUT_FINISHED_TO_BASE_PLATE_OUT", nodeName = "出库完成之后生成容器借出单", nodeType = NodeTypeEnum.COMMON)
  public void processC(NodeComponent bindCmp) {
    OutScanMainBo outScanMainBo = bindCmp.getRequestData();
    OutScanContext ctx = bindCmp.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();

    //#region 消息队列
    // 查询出库单
    var outOrderInfo = outOrderService.getById(outOrder.getOrderId());

    if (ObjectUtil.isNotNull(outOrderInfo)) {
      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_BASE_PLATE_OUT); //出库完成之后生成容器借出单
      rabbitReceiverDto.setBillId(outOrder.getOrderId());
      rabbitReceiverDto.setBillCode(outOrder.getOrderCode());
      rabbitReceiverDto.setSourceCode(outOrder.getSourceCode());
      rabbitReceiverDto.setSourceId(outOrder.getSourceId());
      rabbitReceiverDto.setLoginUser(ctx.getLoginUser());
      taskQueueService.createTask(rabbitReceiverDto);
    }
  }


  @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "OUT_FINISHED_GENERATING_BILL", nodeName = "出库完成后生成每日账单", nodeType = NodeTypeEnum.COMMON)
  public void processD(NodeComponent bindCmp) {
    OutScanContext ctx = bindCmp.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();
    OutPackage outPackage = ctx.getOutPackage();

    //#region 消息队列
    //如果费用项不为空则 创建每日账单
    if (StrUtil.isNotEmpty(outPackage.getFeeItemIds())) {
      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_GENERATING_BILL); //类别
      rabbitReceiverDto.setBillId(outPackage.getPackageId());
      rabbitReceiverDto.setBillCode(outPackage.getPackageCode());
      rabbitReceiverDto.setSourceId(outPackage.getOrderId().toString());
      rabbitReceiverDto.setSourceCode(outPackage.getOrderCode());
      rabbitReceiverDto.setLoginUser(ctx.getLoginUser());
      taskQueueService.createTask(rabbitReceiverDto);
    }

  }


  @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "OUT_FINISHED_TO_WCS", nodeName = "打包出库后自动生成WCS任务单", nodeType = NodeTypeEnum.COMMON)
  public void processE(NodeComponent bindCmp) {
    OutScanContext ctx = bindCmp.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();
    OutPackage outPackage = ctx.getOutPackage();

    //#region 消息队列
    // 生成WCS出库任务
    boolean out_finished_to_wcs = sysConfigService.getConfigBool("out_finished_to_wcs"); // 打包出库后自动生成WCS任务
    if (out_finished_to_wcs) {
      //调用RabbitMQ，生成WCS接口数据
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_WCS); // 入库上架后生成WCS接口数据
      rabbitReceiverDto.setBillId(outPackage.getPackageId());
      rabbitReceiverDto.setBillCode(outPackage.getPackageCode());
      rabbitReceiverDto.setSourceId(outPackage.getOrderId().toString());
      rabbitReceiverDto.setSourceCode(outPackage.getOrderCode());
      rabbitReceiverDto.setLoginUser(ctx.getLoginUser());
      taskQueueService.createTask(rabbitReceiverDto);
    }
  }

  @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "OUT_FINISHED_TO_PICKING", nodeName = "出库完成回更领料单", nodeType = NodeTypeEnum.COMMON)
  public void processF(NodeComponent bindCmp) {
    OutScanContext ctx = bindCmp.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();
    Long orderId = outOrder.getOrderId(); // 出库单ID

    //#region 消息队列
    // 出库后更新领料单
    if (StrUtil.equals(outOrder.getSourceType(), OutOrderTypeEnum.PICKING.getName())) {
      OutOrder outOrderInfo1 = outOrderService.getById(orderId); // 从新获取出库单 放到IF里面 避免所有类型的单据都进行查询

      //调用RabbitMQ
      RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
      rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_FINISHED_TO_PICKING); //类别
      rabbitReceiverDto.setBillId(orderId);
      rabbitReceiverDto.setBillCode(outOrderInfo1.getOrderCode());
      rabbitReceiverDto.setSourceCode(outOrderInfo1.getSourceCode());
      rabbitReceiverDto.setSourceId(outOrderInfo1.getSourceId());
      rabbitReceiverDto.setLoginUser(ctx.getLoginUser());
      taskQueueService.createTask(rabbitReceiverDto);
      //#endregion
    }
  }


  @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS, nodeId = "OUT_FINISHED_TO_PICKING", nodeName = "出库完成回更领料单", nodeType = NodeTypeEnum.COMMON)
  public void processG(NodeComponent bindCmp) {
    OutScanMainBo outScanMainBo = bindCmp.getRequestData();
    OutScanContext ctx = bindCmp.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();

    //#region 消息队列
    // 查询出库单
    var outOrderInfo = outOrderService.getById(outOrder.getOrderId());

    if (ObjectUtil.isNotNull(outOrderInfo)) {
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
        HashMap<String, Object> otherField = new HashMap<>();
        otherField.put("deliveryDate", outScanMainBo.getDeliveryDate());
        rabbitReceiverDto.setOtherField(otherField);
        rabbitReceiverDto.setLoginUser(ctx.getLoginUser());
        taskQueueService.createTask(rabbitReceiverDto);
      }
    }
  }
}
