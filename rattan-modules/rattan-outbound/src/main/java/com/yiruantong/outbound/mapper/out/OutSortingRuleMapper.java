package com.yiruantong.outbound.mapper.out;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.vo.OutSortingRuleVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 订单特殊分拣规则Mapper接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface OutSortingRuleMapper extends BaseMapperPlus<OutSortingRule, OutSortingRuleVo> {

}
