package com.yiruantong.basic.service.tms;

import com.yiruantong.basic.domain.tms.BaseVehicleGroup;
import com.yiruantong.basic.domain.tms.bo.BaseVehicleGroupBo;
import com.yiruantong.basic.domain.tms.vo.BaseVehicleGroupVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Map;

/**
 * 车队管理Service接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface IBaseVehicleGroupService extends IServicePlus<BaseVehicleGroup, BaseVehicleGroupVo, BaseVehicleGroupBo> {
  /**
   * 通用 - 获取车队信息
   * @param map 查询条件
   * @return 返回查询结果
   */
  R<Map<String, Object>> getList(Map<String, Object> map);
}
