package com.yiruantong.outbound.liteflow.OutOrderSorting;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.websocket.utils.WebSocketUtils;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.liteflow.Context.OrderSortingContext;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.system.service.core.ISysConfigService;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LiteflowComponent(id = "orderSortingCalculateCmp", name = "4.出库单分拣后结果")
@RequiredArgsConstructor
public class OrderSortingCalculateCmp extends NodeComponent {

  private final ISysConfigService sysConfigService;
  private final IOutOrderSortingService outOrderSortingService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;

  @Override
  public void process() throws Exception {

    OrderSortingContext ctx = this.getContextBean(OrderSortingContext.class);
    List<OutOrderDetail> outOrderDetailList = ctx.getOutOrderDetails();
    OutOrder outOrder = ctx.getOrderInfo();
    /*-- **********************************************
      -- 更新库存明细占位数量及分拣状态
      -- **********************************************/
    // 计算出库单明细缺货数量
    outOrderSortingService.computeLackStorage(outOrderDetailList, ctx.getHolderSourceTypeEnum());
    outOrderSortingService.updateMainSorting(outOrder, outOrderDetailList);
    OutOrderDetail outOrderDetail = outOrderDetailList.stream().filter(f -> B.isGreater(f.getLackStorage())).findFirst().orElse(null);
    String msg = "";
    if (ObjectUtil.isNotNull(outOrderDetail)) {
      msg = "分拣失败：" + outOrderDetail.getProductCode() + "缺货！";
    }
    if (outOrderDetailList.stream().allMatch(a -> B.isEqual(a.getLackStorage()))) {
      outOrder.setSortingStatus(SortingStatusEnum.ASSIGNED.getId());
      // 出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.SORTING, OutOrderStatusEnum.ASSIGNED);
    } else if (outOrderDetailList.stream().anyMatch(a -> B.isEqual(a.getLackStorage())) && outOrderDetailList.stream().anyMatch(a -> B.isGreater(a.getLackStorage()))) {
      outOrder.setSortingStatus(SortingStatusEnum.PARTIAL_ASSIGNED.getId());
      // 出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.SORTING, OutOrderStatusEnum.PARTIAL_ASSIGNED);
    } else {
      outOrder.setSortingStatus(SortingStatusEnum.LACK.getId());
      // 出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.SORTING, OutOrderStatusEnum.LACK);
    }

    // 分拣完成后，推送消息通知前端分拣完成
    RabbitReceiverDto rabbitReceiverDto = getRabbitReceiverDto(outOrder);
    WebSocketUtils.publishAll(JsonUtils.toJsonString(rabbitReceiverDto));

    // if (StrUtil.isNotEmpty(msg)) {
    //   throw new ServiceException(msg);
    // }
  }

  /**
   * 分拣完成后，推送消息到前端
   *
   * @param outOrder 出库单信息
   * @return RabbitReceiverDto
   */
  @NotNull
  private RabbitReceiverDto getRabbitReceiverDto(OutOrder outOrder) {
    RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
    rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_ORDER_SORTING);
    rabbitReceiverDto.setBillId(outOrder.getOrderId());
    rabbitReceiverDto.setBillCode(outOrder.getOrderCode());
    rabbitReceiverDto.setMenuId(Long.valueOf(MenuEnum.MENU_1671.getId()));
    Map<String, Object> otherField = new HashMap<>();
    otherField.put("sortingStatus", outOrder.getSortingStatus());
    rabbitReceiverDto.setOtherField(otherField);
    return rabbitReceiverDto;
  }
  //#endregion
}
