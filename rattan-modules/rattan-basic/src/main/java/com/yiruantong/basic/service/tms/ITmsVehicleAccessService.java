package com.yiruantong.basic.service.tms;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.tms.TmsVehicleAccess;
import com.yiruantong.basic.domain.tms.vo.TmsVehicleAccessVo;
import com.yiruantong.basic.domain.tms.bo.TmsVehicleAccessBo;

/**
 * 车辆出入信息Service接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface ITmsVehicleAccessService extends IServicePlus<TmsVehicleAccess, TmsVehicleAccessVo, TmsVehicleAccessBo> {
}
