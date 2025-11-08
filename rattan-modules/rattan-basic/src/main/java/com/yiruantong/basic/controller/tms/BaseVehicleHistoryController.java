package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.BaseVehicleHistory;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleHistoryVo;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleHistoryBo;
import com.yiruantong.basic.mapper.tms.BaseVehicleHistoryMapper;
import com.yiruantong.basic.service.tms.IBaseVehicleHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆轨迹
 *
 * @author YRT
 * @date 2024-05-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/vehicleHistory")
public class BaseVehicleHistoryController extends AbstractController<BaseVehicleHistoryMapper, BaseVehicleHistory, BaseVehicleHistoryVo, BaseVehicleHistoryBo> {
}
