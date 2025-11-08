package com.yiruantong.outbound.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 出库单波次Mapper接口
 *
 * @author YRT
 * @date 2023-11-01
 */
public interface OutOrderWaveMapper extends BaseMapperPlus<OutOrderWave, OutOrderWaveVo> {

}
