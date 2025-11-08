package com.yiruantong.inventory.service.plate;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.plate.BasePlateAdjust;
import com.yiruantong.inventory.domain.plate.bo.BasePlateAdjustBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateAdjustVo;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 容器调整主Service接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface IBasePlateAdjustService extends IServicePlus<BasePlateAdjust, BasePlateAdjustVo, BasePlateAdjustBo> {
  /**
   * 获取现借出数量
   *
   * @param map
   * @return
   */
  BigDecimal getNowOutQty(Map<String, Object> map);
}
