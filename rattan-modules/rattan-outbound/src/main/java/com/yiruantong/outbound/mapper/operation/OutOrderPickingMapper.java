package com.yiruantong.outbound.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.operation.OutOrderPicking;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 订单拣货查询Mapper接口
 *
 * @author YRT
 * @date 2023-12-01
 */
public interface OutOrderPickingMapper extends BaseMapperPlus<OutOrderPicking, OutOrderPickingVo> {

}
