package com.yiruantong.outbound.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.operation.OutOrderMatchingDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 订单配货明细Mapper接口
 *
 * @author YRT
 * @date 2023-12-09
 */
public interface OutOrderMatchingDetailMapper extends BaseMapperPlus<OutOrderMatchingDetail, OutOrderMatchingDetailVo> {

}
