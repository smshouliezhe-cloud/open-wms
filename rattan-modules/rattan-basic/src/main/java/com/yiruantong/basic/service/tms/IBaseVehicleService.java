package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.tms.BaseVehicle;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleBo;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.VehicleStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 车辆管理Service接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface IBaseVehicleService extends IServicePlus<BaseVehicle, BaseVehicleVo, BaseVehicleBo> {

  /**
   * 通用 - 查询车辆
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  List<Map<String, Object>> getList(Map<String, Object> map);

  /**
   * 获取车辆下拉框信息
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  List<Map<String, Object>> getTruckNoList(Map<String, Object> map);

  /**
   * 车辆停用
   *
   * @param ids
   * @return 返回查询列表数据
   */
  R<Void> stopUsing(List<Long> ids);

  /**
   * 车辆启用
   *
   * @param ids 查询条件
   * @return 返回查询列表数据
   */
  R<Void> openUsing(List<Long> ids);

  /**
   * 根据车牌号查询车辆信息
   *
   * @param truckNo
   * @return
   */

  BaseVehicle getByTruckNo(String truckNo);

  /**
   * 根据新车辆ID更新状态
   *
   * @param vehicleId         车辆ID
   * @param vehicleStatusEnum 车辆状态
   */
  void updateStatus(Long vehicleId, VehicleStatusEnum vehicleStatusEnum);

  /**
   * 根据新车辆编号更新状态
   *
   * @param truckNo           车辆编号
   * @param vehicleStatusEnum 车辆状态
   */
  void updateStatus(String truckNo, VehicleStatusEnum vehicleStatusEnum);
}
