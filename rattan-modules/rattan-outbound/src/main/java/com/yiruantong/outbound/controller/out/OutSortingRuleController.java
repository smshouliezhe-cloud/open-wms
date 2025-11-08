package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.vo.OutSortingRuleVo;
import com.yiruantong.outbound.domain.out.bo.OutSortingRuleBo;
import com.yiruantong.outbound.mapper.out.OutSortingRuleMapper;
import com.yiruantong.outbound.service.out.IOutSortingRuleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单特殊分拣规则
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/sortingRule")
public class OutSortingRuleController extends AbstractController<OutSortingRuleMapper, OutSortingRule, OutSortingRuleVo, OutSortingRuleBo> {
}
