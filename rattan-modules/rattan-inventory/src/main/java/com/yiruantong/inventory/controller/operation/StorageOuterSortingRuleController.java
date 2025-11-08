package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageOuterSortingRule;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterSortingRuleVo;
import com.yiruantong.inventory.domain.operation.bo.StorageOuterSortingRuleBo;
import com.yiruantong.inventory.mapper.operation.StorageOuterSortingRuleMapper;
import com.yiruantong.inventory.service.operation.IStorageOuterSortingRuleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 其他出库规则
 *
 * @author YRT
 * @date 2023-12-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/outerSortingRule")
public class StorageOuterSortingRuleController extends AbstractController<StorageOuterSortingRuleMapper, StorageOuterSortingRule, StorageOuterSortingRuleVo, StorageOuterSortingRuleBo> {
}
