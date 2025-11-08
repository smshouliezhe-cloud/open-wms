package com.yiruantong.outbound.service.operation;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderSortPool;
import com.yiruantong.outbound.domain.operation.bo.OutOrderSortPoolBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderSortPoolVo;

import java.util.List;
import java.util.function.Consumer;

/**
 * 分拣池Service接口
 *
 * @author YRT
 * @date 2024-05-23
 */
public interface IOutOrderSortPoolService extends IServicePlus<OutOrderSortPool, OutOrderSortPoolVo, OutOrderSortPoolBo> {
  /**
   * 自动分拣
   *
   * @param loginUser 登录用户
   */
  void autoSorting(LoginUser loginUser);

  /**
   * 根据出库单ID集合激活分拣
   *
   * @param orderIdList 出库单ID集合
   * @param loginUser   登录用户
   * @param consumer    登录用户
   */
  void activeSortingByOrderId(List<Long> orderIdList, LoginUser loginUser, Consumer<String> consumer);

  /**
   * 根据商品ID集合激活分拣
   *
   * @param storageId     仓库ID
   * @param productIdList 商品ID集合
   * @param loginUser     登录用户
   */
  void activeSortingByProductId(Long storageId, List<Long> productIdList, LoginUser loginUser);

  /**
   * 根据库存变化激活分拣
   *
   * @param loginUser 登录用户
   */
  void activeSortingByInventory(LoginUser loginUser);

  /**
   * 将分拣池中的单号置为睡眠状态
   *
   * @param orderId 订单ID
   */
  void setSleep(long orderId);
}
