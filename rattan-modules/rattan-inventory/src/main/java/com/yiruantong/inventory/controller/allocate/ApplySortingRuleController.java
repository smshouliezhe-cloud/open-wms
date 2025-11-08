package com.yiruantong.inventory.controller.allocate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.allocate.ApplySortingRule;
import com.yiruantong.inventory.domain.allocate.vo.ApplySortingRuleVo;
import com.yiruantong.inventory.domain.allocate.bo.ApplySortingRuleBo;
import com.yiruantong.inventory.mapper.allocate.ApplySortingRuleMapper;
import com.yiruantong.inventory.service.allocate.IApplySortingRuleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调拨单设置规则
 *
 * @author YRT
 * @date 2024-01-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/allocate/sortingRule")
public class ApplySortingRuleController extends AbstractController<ApplySortingRuleMapper, ApplySortingRule, ApplySortingRuleVo, ApplySortingRuleBo> {
}
