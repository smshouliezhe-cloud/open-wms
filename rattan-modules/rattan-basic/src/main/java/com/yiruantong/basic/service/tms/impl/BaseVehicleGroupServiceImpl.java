package com.yiruantong.basic.service.tms.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.BaseVehicleGroup;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleGroupBo;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleGroupVo;
import com.yiruantong.basic.mapper.tms.BaseVehicleGroupMapper;
import com.yiruantong.basic.service.tms.IBaseVehicleGroupService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 车队管理Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class BaseVehicleGroupServiceImpl extends ServiceImplPlus<BaseVehicleGroupMapper, BaseVehicleGroup, BaseVehicleGroupVo, BaseVehicleGroupBo> implements IBaseVehicleGroupService {
  //#region 通用 - 获取车队信息
  /**
   * 通用 - 获取车队信息
   *
   * @param map 查询条件
   * @return 返回查询结果
   */
  @Override
  public R<Map<String, Object>> getList(Map<String, Object> map) {
    LambdaQueryWrapper<BaseVehicleGroup> queryWrapper =new LambdaQueryWrapper<>();

    // 返回数据
    Map<String, Object> resultData = new HashMap<>();
    resultData.put("vehicleGroupList", this.list(queryWrapper));

    return R.ok(resultData);
  }
  //#endregion


}
