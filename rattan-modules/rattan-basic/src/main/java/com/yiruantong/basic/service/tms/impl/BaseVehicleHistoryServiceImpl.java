package com.yiruantong.basic.service.tms.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.tms.BaseVehicle;
import com.yiruantong.basic.domain.tms.BaseVehicleHistory;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleHistoryBo;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleHistoryVo;
import com.yiruantong.basic.mapper.tms.BaseVehicleHistoryMapper;
import com.yiruantong.basic.service.tms.IBaseVehicleHistoryService;
import com.yiruantong.basic.service.tms.IBaseVehicleService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 车辆轨迹Service业务层处理
 *
 * @author YRT
 * @date 2024-05-31
 */
@RequiredArgsConstructor
@Service
public class BaseVehicleHistoryServiceImpl extends ServiceImplPlus<BaseVehicleHistoryMapper, BaseVehicleHistory, BaseVehicleHistoryVo, BaseVehicleHistoryBo> implements IBaseVehicleHistoryService {
  private final IBaseVehicleService baseVehicleService;

  @Override
  public void add(Long billId, String billCode, Long vehicleId) {
    BaseVehicle vehicle = baseVehicleService.getById(vehicleId);
    BaseVehicleHistory baseVehicleHistory = new BaseVehicleHistory();
    baseVehicleHistory.setBillId(billId);
    baseVehicleHistory.setBillCode(billCode);
    baseVehicleHistory.setOrderStatus("载配中"); // 状态：载配中
    if (ObjectUtil.isNotNull(vehicle)) {
      baseVehicleHistory.setVehicleId(vehicle.getVehicleId());
      baseVehicleHistory.setTruckNo(vehicle.getTruckNo());
    }
    this.save(baseVehicleHistory);
  }

  @Override
  public void update(Long billId, String billCode, String orderStatus, Long vehicleId) {
    LambdaUpdateWrapper<BaseVehicleHistory> historyLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    historyLambdaUpdateWrapper
      .set(BaseVehicleHistory::getBillId, billId) // id
      .set(BaseVehicleHistory::getBillCode, billCode) // Code
      .set(BaseVehicleHistory::getOrderStatus, orderStatus) // 状态：
      .eq(BaseVehicleHistory::getVehicleId, vehicleId);
    this.update(historyLambdaUpdateWrapper);
  }
}
