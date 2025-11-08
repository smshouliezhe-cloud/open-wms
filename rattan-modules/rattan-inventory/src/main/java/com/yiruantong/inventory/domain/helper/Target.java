package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 条件 Target
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class Target {

  /**
   * 按库区推荐
   */
  private StorageAreaInfo storageAreaInfo;

  /**
   * 按货位推荐
   */
  private PositionInfo positionInfo;

  /**
   * 按通道推荐
   */
  private ChannelInfo channelInfo;

  /**
   * 按货架推荐
   */
  private ShelveInfo shelveInfo;

  /**
   * 按货位类型推荐
   */
  private PositionTypeInfo positionTypeInfo;

  /**
   * 按仓库温层推荐
   */
  private ThermoclineInfo thermoclineInfo;

  /**
   * 无条件
   */
  private NoCondition noCondition;

}
