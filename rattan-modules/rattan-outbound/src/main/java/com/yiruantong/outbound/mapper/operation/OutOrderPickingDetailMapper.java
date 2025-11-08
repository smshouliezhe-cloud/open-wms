package com.yiruantong.outbound.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.operation.OutOrderPickingDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 订单拣货查询明细Mapper接口
 *
 * @author YRT
 * @date 2023-12-01
 */
public interface OutOrderPickingDetailMapper extends BaseMapperPlus<OutOrderPickingDetail, OutOrderPickingDetailVo> {

}
