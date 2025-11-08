package com.yiruantong.outbound.mapper.out;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.out.OutOrderWaveStatusHistory;
import com.yiruantong.outbound.domain.out.vo.OutOrderWaveStatusHistoryVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 订单状态历史记录Mapper接口
 *
 * @author YRT
 * @date 2023-11-24
 */
public interface OutOrderWaveStatusHistoryMapper extends BaseMapperPlus<OutOrderWaveStatusHistory, OutOrderWaveStatusHistoryVo> {

}
