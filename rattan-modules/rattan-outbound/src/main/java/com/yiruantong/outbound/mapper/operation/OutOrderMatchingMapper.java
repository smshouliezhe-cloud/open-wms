package com.yiruantong.outbound.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.operation.OutOrderMatching;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 订单配货Mapper接口
 *
 * @author YRT
 * @date 2023-12-09
 */
public interface OutOrderMatchingMapper extends BaseMapperPlus<OutOrderMatching, OutOrderMatchingVo> {

}
