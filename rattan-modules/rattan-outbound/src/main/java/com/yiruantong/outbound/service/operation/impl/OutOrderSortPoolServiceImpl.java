package com.yiruantong.outbound.service.operation.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.PoolStateEnum;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.operation.OutOrderSortPool;
import com.yiruantong.outbound.domain.operation.bo.OutOrderSortPoolBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderSortPoolVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.mapper.operation.OutOrderSortPoolMapper;
import com.yiruantong.outbound.service.operation.IOutOrderSortPoolService;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 分拣池Service业务层处理
 *
 * @author YRT
 * @date 2024-05-23
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class OutOrderSortPoolServiceImpl extends ServiceImplPlus<OutOrderSortPoolMapper, OutOrderSortPool, OutOrderSortPoolVo, OutOrderSortPoolBo> implements IOutOrderSortPoolService {
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutOrderSortingService outOrderSortingService;

  /**
   * 自动分拣
   *
   * @param loginUser 登录用户
   */
  @Async
  @Override
  public void autoSorting(LoginUser loginUser) {
    LoginHelper.setLoginUser(loginUser);
    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到

    List<Byte> sortingStatusList = Stream.of(SortingStatusEnum.NONE, SortingStatusEnum.LACK, SortingStatusEnum.PARTIAL_ASSIGNED).map(SortingStatusEnum::getId).toList();
    MPJLambdaWrapper<OutOrderSortPool> sortPoolLambdaQueryWrapper = new MPJLambdaWrapper<>();
    sortPoolLambdaQueryWrapper
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderSortPool::getOrderId)
      .in(OutOrder::getSortingStatus, sortingStatusList)
      .eq(OutOrderSortPool::getPoolState, PoolStateEnum.ACTIVE.getId());
    List<OutOrderSortPool> sortPoolList = this.list(sortPoolLambdaQueryWrapper);
    for (var sortPool : sortPoolList) {
      try {
        outOrderSortingService.sorting(sortPool.getOrderId());
      } catch (Exception e) {
        log.error("自动分拣错误：" + e.getMessage());
      }
      // 重置位睡眠状态
      this.setSleep(sortPool.getOrderId());
    }
  }

  /**
   * 根据出库单ID集合激活分拣
   *
   * @param orderIdList 出库单ID集合
   * @param loginUser   登录用户
   */
  @Async
  @Override
  public void activeSortingByOrderId(List<Long> orderIdList, LoginUser loginUser, Consumer<String> consumer) {
    LoginHelper.setLoginUser(loginUser);
    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到

    List<OutOrder> outOrderList = CollUtil.isEmpty(orderIdList) ? new ArrayList<>() : outOrderService.listByIds(orderIdList);
    for (var outOrder : outOrderList) {
      LambdaQueryWrapper<OutOrderSortPool> sortPoolLambdaQueryWrapper = new LambdaQueryWrapper<>();
      sortPoolLambdaQueryWrapper.eq(OutOrderSortPool::getOrderId, outOrder.getOrderId());
      OutOrderSortPool outOrderSortPool = this.getOne(sortPoolLambdaQueryWrapper);
      if (ObjectUtil.isNull(outOrderSortPool)) {
        outOrderSortPool = new OutOrderSortPool();
        outOrderSortPool.setOrderId(outOrder.getOrderId());
        outOrderSortPool.setOrderCode(outOrder.getOrderCode());
        outOrderSortPool.setOrderNum(0L);
        outOrderSortPool.setPoolState(PoolStateEnum.ACTIVE.getId());
        outOrderSortPool.setStoreOrderCode(outOrder.getStoreOrderCode());
        this.save(outOrderSortPool);
      } else {
        outOrderSortPool.setPoolState(PoolStateEnum.ACTIVE.getId());
        this.updateById(outOrderSortPool);
      }
    }

    if (consumer != null) {
      consumer.accept(null);
    }
  }

  /**
   * 根据同仓库、同商品ID集合激活出库单
   *
   * @param storageId     仓库ID
   * @param productIdList 商品ID集合
   * @param loginUser     登录用户
   */
  @Async
  @Override
  public void activeSortingByProductId(Long storageId, List<Long> productIdList, LoginUser loginUser) {
    LoginHelper.setLoginUser(loginUser);
    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到

    MPJLambdaWrapper<OutOrderDetail> outOrderDetailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    outOrderDetailMPJLambdaWrapper
      .innerJoin(OutOrder.class, OutOrder::getOrderId, OutOrderDetail::getOrderId)
      .select(OutOrder::getOrderId, OutOrder::getOrderCode, OutOrder::getStoreOrderCode)
      .eq(OutOrder::getStorageId, storageId)
      .in(OutOrderDetail::getProductId, productIdList)
      .in(OutOrder::getSortingStatus, List.of(SortingStatusEnum.NONE.getId(), SortingStatusEnum.LACK.getId(), SortingStatusEnum.PARTIAL_ASSIGNED.getId()))
      .in(OutOrder::getOrderStatus, List.of(OutOrderStatusEnum.AUDIT_SUCCESS.getName())) // 审核成功
      .ge(OutOrderDetail::getLackStorage, BigDecimal.ZERO);

    // 根据同仓库、同商品ID、分拣状态=未分配成功的、出库单状态=审核成功的出库单找出来
    List<Map<String, Object>> mapList = outOrderDetailService.selectJoinMaps(outOrderDetailMPJLambdaWrapper);
    List<Long> orderIdList = mapList.stream().map(m -> Convert.toLong(m.get("orderId"))).toList();

    this.activeSortingByOrderId(orderIdList, loginUser, t -> {
      this.autoSorting(loginUser); // 激活出库单后，自动执行分拣操作
    });
  }

  @Async
  @Override
  public void activeSortingByInventory(LoginUser loginUser) {
    LoginHelper.setLoginUser(loginUser);
    TenantHelper.setDynamic(loginUser.getTenantId()); // 在异步线程里面需要手动设置当前租户ID，缓存用到

    MPJLambdaWrapper<CoreInventory> inventoryMPJLambdaWrapper = new MPJLambdaWrapper<>();
    inventoryMPJLambdaWrapper
      .select(CoreInventory::getStorageId, CoreInventory::getConsignorId, CoreInventory::getProductId)
      .innerJoin(BasePosition.class, on -> {
        on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
          .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
        return on;
      })
      .ge(CoreInventory::getCreateTime, DateUtil.offsetMinute(DateUtil.date(), -30))
      .eq(CoreInventory::getProductStorage, BigDecimal.ZERO)
      .in(CoreInventory::getStorageStatus, List.of(InventoryStatusEnum.NORMAL))
      .in(BasePosition::getPositionType, List.of(PositionTypeEnum.NORMAL.getId(), PositionTypeEnum.STORAGE.getId(), PositionTypeEnum.ELEVATED.getId()))
      .groupBy(CoreInventory::getStorageId)
      .groupBy(CoreInventory::getConsignorId)
      .groupBy(CoreInventory::getProductId);

    List<Map<String, Object>> mapList = coreInventoryService.selectJoinMaps(inventoryMPJLambdaWrapper);
    // 定义一个函数Function，该函数将元素对象映射到一个键的集合里
    Function<Map<String, Object>, List<Object>> compositeKey = person ->
      Arrays.asList(person.get("storageId"), person.get("consignorId"));
    // 分组
    Map<List<Object>, List<Map<String, Object>>> groupingMap =
      mapList.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));

    for (var item : groupingMap.entrySet()) {
      Long storageId = Convert.toLong(item.getKey().get(0));
      List<Long> productIdList = item.getValue().stream().map(m -> Convert.toLong(m.get("productId"))).toList();
      this.activeSortingByProductId(storageId, productIdList, loginUser);
    }
  }

  @Override
  public void setSleep(long orderId) {
    LambdaUpdateWrapper<OutOrderSortPool> poolLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    poolLambdaUpdateWrapper
      .set(OutOrderSortPool::getPoolState, PoolStateEnum.SLEEP.getId())
      .eq(OutOrderSortPool::getOrderId, orderId);

    // 重置位睡眠状态
    this.update(poolLambdaUpdateWrapper);
  }
}
