package com.yiruantong.outbound.service.out;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.vo.OutSortingRuleVo;
import com.yiruantong.outbound.domain.out.bo.OutSortingRuleBo;

/**
 * 订单特殊分拣规则Service接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface IOutSortingRuleService extends IServicePlus<OutSortingRule, OutSortingRuleVo, OutSortingRuleBo> {

  /**
   * 根据出库单明细ID获取规则
   *
   * @param detailId 出库单明细ID
   * @return 返回规则信息
   */
  OutSortingRule getOneByDetailId(Long detailId);
}
