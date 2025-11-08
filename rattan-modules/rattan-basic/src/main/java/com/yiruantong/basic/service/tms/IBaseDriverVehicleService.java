package com.yiruantong.basic.service.tms;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.tms.BaseDriverVehicle;
import com.yiruantong.basic.domain.tms.vo.BaseDriverVehicleVo;
import com.yiruantong.basic.domain.tms.bo.BaseDriverVehicleBo;

/**
 * 司机车辆绑定Service接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface IBaseDriverVehicleService extends IServicePlus<BaseDriverVehicle, BaseDriverVehicleVo, BaseDriverVehicleBo> {
}
