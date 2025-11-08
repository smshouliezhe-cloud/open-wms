package com.yiruantong.inbound.mapper.in;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inbound.domain.in.InOrderPlanStatusHistory;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanStatusHistoryVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 入库计划状态轨迹Mapper接口
 *
 * @author YRT
 * @date 2024-09-19
 */
public interface InOrderPlanStatusHistoryMapper extends BaseMapperPlus<InOrderPlanStatusHistory, InOrderPlanStatusHistoryVo> {

}
