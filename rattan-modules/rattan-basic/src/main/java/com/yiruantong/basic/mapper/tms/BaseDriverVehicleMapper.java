package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.BaseDriverVehicle;
import com.yiruantong.basic.domain.tms.vo.BaseDriverVehicleVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 司机车辆绑定Mapper接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface BaseDriverVehicleMapper extends BaseMapperPlus<BaseDriverVehicle, BaseDriverVehicleVo> {

}
