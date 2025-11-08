package com.yiruantong.composite.service.inventory.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.inbound.domain.in.vo.InOrderVo;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.composite.service.inventory.IInventoryService;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InventoryServiceImpl implements IInventoryService {
  //  private final ICoreInventoryHolderService coreInventoryHolderService;
//  private final ICoreInventoryService coreInventoryService;
//  private final IOrderSortingService outOrderSortingService;
  private final IInOrderService inOrderService;
  private final IOutOrderService outOrderService;
  //#region 生成预到货单

  /**
   * 生成预到货单
   *
   * @param ids 删除ID
   * @return 提示信息
   */
  @Override
  public int deleteByIds(Long[] ids) {
    LambdaUpdateWrapper<CoreInventoryHolder> holderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    holderLambdaUpdateWrapper.in(CoreInventoryHolder::getHolderId, Arrays.asList(ids));

    ICoreInventoryHolderService bean = SpringUtils.getBean(ICoreInventoryHolderService.class);
    List<CoreInventoryHolder> holderList = bean.getBaseMapper().selectList(holderLambdaUpdateWrapper);

    bean.remove(holderLambdaUpdateWrapper);

// 定义一个函数Function，该函数将元素对象映射到一个键的集合里
    Function<CoreInventoryHolder, List<Object>> compositeKey = item -> Arrays.asList(item.getSourceType(), item.getMainId());
    // 分组
    Map<List<Object>, List<CoreInventoryHolder>> groupingMap = holderList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));
    for (var item : groupingMap.keySet()) {
      String sourceType = Convert.toStr(item.get(0));
      Long mainId = Convert.toLong(item.get(1));
      if (ObjectUtil.equals(sourceType, HolderSourceTypeEnum.OUT_ORDER_NORMAL.getName()) || ObjectUtil.equals(sourceType, HolderSourceTypeEnum.OUT_PICKING.getName())) {

        IOutOrderSortingService bean1 = SpringUtils.getBean(IOutOrderSortingService.class);
        // 出库单常规出库或者拣货下架
        bean1.computeLackStorage(mainId);
      }
    }

    // 更新库存占位数量
    for (var holder : holderList) {

      ICoreInventoryService bean2 = SpringUtils.getBean(ICoreInventoryService.class);
      bean2.updateHolderStorage(holder.getInventoryId());
    }


    return 1;
  }
  //#endregion


  //#region 获得角色权限右侧表格树列表


  @Override
  public R<Map<String, Object>> getBadgeCount() {

    Map<String, Object> maps = new HashMap<>();

    //#region 入库统计数量
    List<String> statusList = List.of(
      InOrderStatusEnum.IN_TRANSIT.getName(),
      InOrderStatusEnum.SUCCESS.getName(),
      InOrderStatusEnum.NEWED.getName(),
      InOrderStatusEnum.PARTIAL_FINISHED.getName()
    );

    PageQuery pageQuery= new PageQuery();
    pageQuery.setMenuId(MenuEnum.MENU_1001.getId());
    pageQuery.setPageIndex(1);
    pageQuery.setPageSize(5);
    pageQuery.setTableName("in_order");
    pageQuery.setIsAsc("desc");
    pageQuery.setPrefixRouter("/inbound/in/order");
    pageQuery.setOrderByColumn("orderId");
    pageQuery.setSumColumnNames(List.of());

    QueryBo queryBo =new QueryBo();
    queryBo.setValues(StringUtils.join(statusList, ","));
    queryBo.setColumn("orderStatus");
    queryBo.setDataType(DataTypeEnum.STRING);
    queryBo.setQueryType(QueryTypeEnum.IN);
    pageQuery.setQueryBoList(pageQuery.addQueryBo(queryBo));
    TableDataInfo<InOrderVo> inOrderVoTableDataInfo = inOrderService.pageList(pageQuery);
    maps.put("inOrderCount",inOrderVoTableDataInfo.getTotal());
    //#endregion

    //#region 出库统计数量
     statusList = List.of(
       OutOrderStatusEnum.PICKED.getName(), // 拣货完成
       OutOrderStatusEnum.MATCHED.getName(), // 拣货完成
       OutOrderStatusEnum.PACKAGE_PARTIAL.getName(), // 部分打包
       OutOrderStatusEnum.WAVE_FINISHED.getName() // 波次完成
    );
    LambdaQueryWrapper<OutOrder> outOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
    outOrderLambdaQueryWrapper.in(OutOrder::getOrderStatus, statusList);

    maps.put("outOrderCount", outOrderService.count(outOrderLambdaQueryWrapper));
    //#endregion
    return R.ok(maps);
  }
  //#endregion

}
