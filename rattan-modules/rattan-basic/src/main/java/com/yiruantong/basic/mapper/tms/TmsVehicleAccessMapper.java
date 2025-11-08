package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.TmsVehicleAccess;
import com.yiruantong.basic.domain.tms.vo.TmsVehicleAccessVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 车辆出入信息Mapper接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface TmsVehicleAccessMapper extends BaseMapperPlus<TmsVehicleAccess, TmsVehicleAccessVo> {

}
