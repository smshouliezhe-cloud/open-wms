package com.yiruantong.outbound.liteflow.OutOrderSorting;


import cn.hutool.core.convert.Convert;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.outbound.domain.out.OutOrderSorting;
import com.yiruantong.outbound.liteflow.Context.OrderSortingContext;
import com.yiruantong.system.service.core.ISysConfigService;

@LiteflowComponent(id = "orderSortingInitCmp", name = "1.出库分拣初始化")
@RequiredArgsConstructor
public class OrderSortingInitCmp extends NodeComponent {

  private final ISysConfigService sysConfigService;

  @Override
  public void process() throws Exception {
    OutOrderSorting sortingInfo = this.getRequestData();

    OrderSortingContext ctx = this.getContextBean(OrderSortingContext.class);
    // 获取全局参数
    boolean sorting_isFullPositionLoad = sysConfigService.getConfigBool("sorting_isFullPositionLoad"); // 是否整货位拣配单
    boolean sorting_crossConsignor = sysConfigService.getConfigBool("sorting_crossConsignor"); // 是否跨货主分拣
    Long sorting_crossConsignorDays = Convert.toLong(sysConfigService.selectConfigByKey("sorting_crossConsignorDays")); // 是否跨货主分拣(天数)
    boolean sorting_isFullContainerLoad = sysConfigService.getConfigBool("sorting_isFullContainerLoad"); // 是否整箱拣配单
    boolean batch_sorting_stopSaleday = sysConfigService.getConfigBool("batch_sorting_stopSaleday"); // 分拣订单时启用停售提前时长
    boolean sorting_positionPriorIndate = sysConfigService.getConfigBool("sorting_positionPriorIndate"); // 分拣时货位顺序优先于入库时间
    boolean batch_openStorageArea_regular = sysConfigService.getConfigBool("batch_openStorageArea_regular"); // 开启库区分拣策略
    boolean sorting_singleSignCode = sysConfigService.getConfigBool("sorting_singleSignCode"); // 出库按照唯一码进行分拣
    boolean sorting_isContainerNo = sysConfigService.getConfigBool("sorting_isContainerNo"); // 是否按集装箱分拣
    boolean sorting_positionGreaterQty = sysConfigService.getConfigBool("sorting_positionGreaterQty"); // 货位数量更多优先分拣

    ctx.setOrderId(sortingInfo.getOrderId());
    ctx.setIsOnlyStoragePosition(sortingInfo.getIsOnlyStoragePosition());
    ctx.setInventoryStatusEnumList(sortingInfo.getInventoryStatusEnumList());
    ctx.setPositionTypeEnumList(sortingInfo.getPositionTypeEnumList());

    ctx.setSorting_isFullPositionLoad(sorting_isFullPositionLoad);
    ctx.setSorting_crossConsignor(sorting_crossConsignor);
    ctx.setSorting_crossConsignorDays(sorting_crossConsignorDays);
    ctx.setSorting_isFullContainerLoad(sorting_isFullContainerLoad);
    ctx.setBatch_sorting_stopSaleday(batch_sorting_stopSaleday);
    ctx.setSorting_positionPriorIndate(sorting_positionPriorIndate);
    ctx.setBatch_openStorageArea_regular(batch_openStorageArea_regular);
    ctx.setSorting_singleSignCode(sorting_singleSignCode);
    ctx.setSorting_isContainerNo(sorting_isContainerNo);
    ctx.setSorting_positionGreaterQty(sorting_positionGreaterQty);
  }
}
