package com.yiruantong.inventory.controller.core;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.core.CoreSortingRule;
import com.yiruantong.inventory.domain.core.vo.CoreSortingRuleVo;
import com.yiruantong.inventory.domain.core.bo.CoreSortingRuleBo;
import com.yiruantong.inventory.mapper.core.CoreSortingRuleMapper;
import com.yiruantong.inventory.service.core.ICoreSortingRuleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分拣规则
 *
 * @author YRT
 * @date 2025-02-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/core/sortingRule")
public class CoreSortingRuleController extends AbstractController<CoreSortingRuleMapper, CoreSortingRule, CoreSortingRuleVo, CoreSortingRuleBo> {
}
