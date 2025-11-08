package com.yiruantong.outbound.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 出库单波次明细Mapper接口
 *
 * @author YRT
 * @date 2023-11-01
 */
public interface OutOrderWaveDetailMapper extends BaseMapperPlus<OutOrderWaveDetail, OutOrderWaveDetailVo> {

}
