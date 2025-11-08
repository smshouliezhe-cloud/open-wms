package com.yiruantong.inbound.mapper.in;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inbound.domain.in.InEnterStatusHistory;
import com.yiruantong.inbound.domain.in.vo.InEnterStatusHistoryVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 入库记录轨迹Mapper接口
 *
 * @author YRT
 * @date 2023-11-22
 */
public interface InEnterStatusHistoryMapper extends BaseMapperPlus<InEnterStatusHistory, InEnterStatusHistoryVo> {

}
