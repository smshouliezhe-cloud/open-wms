package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.BaseDriverVehicle;
import com.yiruantong.basic.domain.tms.vo.BaseDriverVehicleVo;
import com.yiruantong.basic.domain.tms.bo.BaseDriverVehicleBo;
import com.yiruantong.basic.mapper.tms.BaseDriverVehicleMapper;
import com.yiruantong.basic.service.tms.IBaseDriverVehicleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 司机车辆绑定
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/driverVehicle")
public class BaseDriverVehicleController extends AbstractController<BaseDriverVehicleMapper, BaseDriverVehicle, BaseDriverVehicleVo, BaseDriverVehicleBo> {
}
