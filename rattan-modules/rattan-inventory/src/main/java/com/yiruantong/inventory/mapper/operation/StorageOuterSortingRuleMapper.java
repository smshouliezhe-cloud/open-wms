package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageOuterSortingRule;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterSortingRuleVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 其他出库规则Mapper接口
 *
 * @author YRT
 * @date 2023-12-20
 */
public interface StorageOuterSortingRuleMapper extends BaseMapperPlus<StorageOuterSortingRule, StorageOuterSortingRuleVo> {

}
