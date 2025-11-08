package com.yiruantong.inventory.service.operation.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.operation.bo.StorageOuterSortingRuleBo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterSortingRuleVo;
import com.yiruantong.inventory.domain.operation.StorageOuterSortingRule;
import com.yiruantong.inventory.mapper.operation.StorageOuterSortingRuleMapper;
import com.yiruantong.inventory.service.operation.IStorageOuterSortingRuleService;

/**
 * 其他出库规则Service业务层处理
 *
 * @author YRT
 * @date 2023-12-20
 */
@RequiredArgsConstructor
@Service
public class StorageOuterSortingRuleServiceImpl extends ServiceImplPlus<StorageOuterSortingRuleMapper, StorageOuterSortingRule, StorageOuterSortingRuleVo, StorageOuterSortingRuleBo> implements IStorageOuterSortingRuleService {
}
