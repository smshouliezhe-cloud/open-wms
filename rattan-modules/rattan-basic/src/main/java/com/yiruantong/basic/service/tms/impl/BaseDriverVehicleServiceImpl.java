package com.yiruantong.basic.service.tms.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.tms.bo.BaseDriverVehicleBo;
import com.yiruantong.basic.domain.tms.vo.BaseDriverVehicleVo;
import com.yiruantong.basic.domain.tms.BaseDriverVehicle;
import com.yiruantong.basic.mapper.tms.BaseDriverVehicleMapper;
import com.yiruantong.basic.service.tms.IBaseDriverVehicleService;

/**
 * 司机车辆绑定Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class BaseDriverVehicleServiceImpl extends ServiceImplPlus<BaseDriverVehicleMapper, BaseDriverVehicle, BaseDriverVehicleVo, BaseDriverVehicleBo> implements IBaseDriverVehicleService {
}
