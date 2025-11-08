package com.yiruantong.common.core.enums.base;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 分拣状态
 */
@Getter
@AllArgsConstructor
public enum SortingStatusEnum {
  /**
   * 未分配
   */
  NONE((byte) 1, "未分配"),
  /**
   * 已分配
   */
  ASSIGNED((byte) 2, "已分配"),
  /**
   * 缺货中
   */
  LACK((byte) 3, "缺货中"),
  /**
   * 问题订单
   */
  QUESTION((byte) 4, "问题订单"),
  /**
   * 部分分配
   */
  PARTIAL_ASSIGNED((byte) 5, "部分分配");

  private final Byte id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static SortingStatusEnum matchingEnum(String name) {
    for (SortingStatusEnum i : values()) {
      if (StringUtils.equals(i.getName(), name)) {
        return i;
      }
    }
    return null;
  }

  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static SortingStatusEnum matchingEnumById(Byte name) {
    for (SortingStatusEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), name)) {
        return i;
      }
    }
    return null;
  }
}
