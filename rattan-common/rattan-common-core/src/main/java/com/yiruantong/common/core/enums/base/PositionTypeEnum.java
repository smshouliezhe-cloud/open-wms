package com.yiruantong.common.core.enums.base;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 货位类型枚举
 */
@Getter
@AllArgsConstructor
public enum PositionTypeEnum {
  /**
   * 常规货位
   */
  NORMAL((byte) 1, "常规货位"),
  /**
   * 残品货位
   */
  SCRAP((byte) 2, "残品货位"),
  /**
   * 借入货位
   */
  BORROW_IN((byte) 3, "借入货位"),
  /**
   * 收货位
   */
  RECEIVING((byte) 4, "收货位"),
  /**
   * 下架理货位
   */
  UNLOADING((byte) 5, "下架理货位"),
  /**
   * 发货暂存货位
   */
  Temporary((byte) 6, "发货暂存货位"),
  /**
   * 虚拟货位
   */
  VIRTUAL((byte) 7, "虚拟货位"),
  /**
   * 次品货位
   */
  DEFECTIVE((byte) 8, "次品货位"),
  /**
   * 拣货车
   */
  PICKING_CAR((byte) 9, "拣货车"),
  /**
   * 配货位
   */
  ALLOCATION((byte) 10, "配货位"),
  /**
   * 灯光分拣位
   */
  LIGHT((byte) 11, "灯光分拣位"),
  /**
   * 高架货位
   */
  ELEVATED((byte) 12, "高架货位"),
  /**
   * 存储货位
   */
  STORAGE((byte) 13, "存储货位"),
  /**
   * 临期货位
   */
  EXPIRE((byte) 14, "临期货位"),
  /**
   * 在途货位
   */
  IN_TRANSIT((byte) 15, "在途货位");

  private final Byte id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param enumName 枚举名
   * @return 枚举
   */
  public static PositionTypeEnum matchingEnum(String enumName) {
    for (PositionTypeEnum i : values()) {
      if (ObjectUtil.equal(i.toString(), enumName)) {
        return i;
      }
    }
    return null;
  }

  /**
   * 匹配对应的枚举类
   *
   * @param id 值
   * @return 枚举
   */
  public static PositionTypeEnum matchingEnumById(Byte id) {
    for (PositionTypeEnum i : values()) {
      if (i.getId().equals(id)) {
        return i;
      }
    }
    return null;
  }
}
