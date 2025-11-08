package com.yiruantong.inventory.service.allocate;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.allocate.ApplySortingRule;
import com.yiruantong.inventory.domain.allocate.vo.ApplySortingRuleVo;
import com.yiruantong.inventory.domain.allocate.bo.ApplySortingRuleBo;

/**
 * 调拨单设置规则Service接口
 *
 * @author YRT
 * @date 2024-01-05
 */
public interface IApplySortingRuleService extends IServicePlus<ApplySortingRule, ApplySortingRuleVo, ApplySortingRuleBo> {
}
