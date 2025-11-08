package com.yiruantong.inventory.service.core;

import com.yiruantong.common.core.enums.inventory.SortingRuleBillTypeEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.core.CoreSortingRule;
import com.yiruantong.inventory.domain.core.bo.CoreSortingRuleBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreSortingRuleVo;

import java.util.List;

/**
 * 分拣规则Service接口
 *
 * @author YRT
 * @date 2025-02-27
 */
public interface ICoreSortingRuleService extends IServicePlus<CoreSortingRule, CoreSortingRuleVo, CoreSortingRuleBo> {
  /**
   * 获取分拣规则
   *
   * @param billType     单据类型
   * @param billId       主表ID
   * @param billDetailId 明细表ID
   * @return CoreSortingRule
   */
  CoreSortingRule getSortingRuleInfo(SortingRuleBillTypeEnum billType, Long billId, Long billDetailId);

  /**
   * 通用库存过滤器
   *
   * @param inventoryList 现有可用库存数据集合
   * @param billType      单据类型
   * @param billId        主表ID
   * @param billDetailId  明细表ID
   * @return 返回过滤后库存集合
   */
  List<CoreInventoryComposeVo> filter(List<CoreInventoryComposeVo> inventoryList, SortingRuleBillTypeEnum billType, Long billId, Long billDetailId);

  /**
   * 根据单号获取规则
   *
   * @param orderCode
   * @return
   */
  CoreSortingRule getByCode(String orderCode);
}
