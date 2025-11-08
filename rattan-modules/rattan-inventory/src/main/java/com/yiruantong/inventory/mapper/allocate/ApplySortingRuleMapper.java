package com.yiruantong.inventory.mapper.allocate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.allocate.ApplySortingRule;
import com.yiruantong.inventory.domain.allocate.vo.ApplySortingRuleVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 调拨单设置规则Mapper接口
 *
 * @author YRT
 * @date 2024-01-05
 */
public interface ApplySortingRuleMapper extends BaseMapperPlus<ApplySortingRule, ApplySortingRuleVo> {

}
