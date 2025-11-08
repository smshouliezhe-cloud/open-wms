package com.yiruantong.basic.controller.tms;

import com.yiruantong.basic.domain.tms.BaseVehicle;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleBo;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleVo;
import com.yiruantong.basic.mapper.tms.BaseVehicleMapper;
import com.yiruantong.basic.service.tms.IBaseVehicleService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 车辆管理
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/vehicle")
public class BaseVehicleController extends AbstractController<BaseVehicleMapper, BaseVehicle, BaseVehicleVo, BaseVehicleBo> {
  private final IBaseVehicleService baseVehicleService;

  /**
   * 查询车辆
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = baseVehicleService.getList(map);
    return R.ok(list);
  }


  /**
   * 获取车辆下拉框信息
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getTruckNoList")
  public R<List<Map<String, Object>>> getTruckNoList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = baseVehicleService.getTruckNoList(map);
    return R.ok(list);
  }

  /**
   * 车辆停用
   *
   * @param ids
   * @return 返回查询列表数据
   */
  @PostMapping("/stopUsing/{ids}")
  public R<Void> stopUsing(@PathVariable List<Long> ids) {
    return baseVehicleService.stopUsing(ids);
  }

  /**
   * 车辆启用
   *
   * @param ids 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/openUsing/{ids}")
  public R<Void> openUsing(@PathVariable List<Long> ids) {
    return baseVehicleService.openUsing(ids);
  }
}
