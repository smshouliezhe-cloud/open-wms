package com.yiruantong.inventory.service.allocate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.allocate.bo.ApplySortingRuleBo;
import com.yiruantong.inventory.domain.allocate.vo.ApplySortingRuleVo;
import com.yiruantong.inventory.domain.allocate.ApplySortingRule;
import com.yiruantong.inventory.mapper.allocate.ApplySortingRuleMapper;
import com.yiruantong.inventory.service.allocate.IApplySortingRuleService;

/**
 * 调拨单设置规则Service业务层处理
 *
 * @author YRT
 * @date 2024-01-05
 */
@RequiredArgsConstructor
@Service
public class ApplySortingRuleServiceImpl extends ServiceImplPlus<ApplySortingRuleMapper, ApplySortingRule, ApplySortingRuleVo, ApplySortingRuleBo> implements IApplySortingRuleService {
}
