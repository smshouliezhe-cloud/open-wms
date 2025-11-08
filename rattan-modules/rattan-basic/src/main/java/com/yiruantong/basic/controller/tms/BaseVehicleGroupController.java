package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.BaseVehicleGroup;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleGroupBo;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleGroupVo;
import com.yiruantong.basic.mapper.tms.BaseVehicleGroupMapper;
import com.yiruantong.basic.service.tms.IBaseVehicleGroupService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 车队管理
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/vehicleGroup")
public class BaseVehicleGroupController extends AbstractController<BaseVehicleGroupMapper, BaseVehicleGroup, BaseVehicleGroupVo, BaseVehicleGroupBo> {

  private final IBaseVehicleGroupService baseVehicleGroupService;
  /**
   * 获取车队信息
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<Map<String, Object>> getList(@RequestBody Map<String, Object> map) {
    return baseVehicleGroupService.getList(map);
  }
}
