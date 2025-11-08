package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.tms.BaseVehicleHistory;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleHistoryBo;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleHistoryVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 车辆轨迹Service接口
 *
 * @author YRT
 * @date 2024-05-31
 */
public interface IBaseVehicleHistoryService extends IServicePlus<BaseVehicleHistory, BaseVehicleHistoryVo, BaseVehicleHistoryBo> {
  // 添加轨迹
  void add(Long billId, String billCode, Long vehicleId);

  // 更新轨迹
  void update(Long billId, String billCode, String orderStatus, Long vehicleId);
}
