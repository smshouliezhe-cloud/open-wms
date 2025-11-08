package com.yiruantong.outbound.mapper.out;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.out.OutOrderPlanStatusHistory;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanStatusHistoryVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 出库计划状态轨迹Mapper接口
 *
 * @author YRT
 * @date 2024-09-20
 */
public interface OutOrderPlanStatusHistoryMapper extends BaseMapperPlus<OutOrderPlanStatusHistory, OutOrderPlanStatusHistoryVo> {

}
