package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.BaseVehicle;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 车辆管理Mapper接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface BaseVehicleMapper extends BaseMapperPlus<BaseVehicle, BaseVehicleVo> {

}
