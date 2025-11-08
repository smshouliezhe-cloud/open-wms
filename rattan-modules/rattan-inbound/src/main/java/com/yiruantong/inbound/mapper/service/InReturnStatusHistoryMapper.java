package com.yiruantong.inbound.mapper.service;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inbound.domain.service.InReturnStatusHistory;
import com.yiruantong.inbound.domain.service.vo.InReturnStatusHistoryVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 退货单流水Mapper接口
 *
 * @author YRT
 * @date 2024-01-27
 */
public interface InReturnStatusHistoryMapper extends BaseMapperPlus<InReturnStatusHistory, InReturnStatusHistoryVo> {

}
