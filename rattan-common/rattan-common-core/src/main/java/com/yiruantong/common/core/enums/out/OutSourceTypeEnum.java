package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 来源类型枚举
 */
@Getter
@AllArgsConstructor
public enum OutSourceTypeEnum {
  /**
   * 出库单
   */
  OUT_ORDER((byte) 1, "出库单"),
  /**
   * 无单入库
   */
  NO_BILL((byte) 2, "无单入库"),
  /**
   * 导入生成
   */
  IMPORT_CREATE((byte) 3, "导入生成"),
  /**
   * 复制生成
   */
  COPY_CREATE((byte) 4, "复制生成"),

  /**
   * 出库计划单
   */
  OUT_PLANORDER((byte) 5, "出库计划单"),
  /**
   * 到货退货单
   */
  RETURN_ORDER((byte) 6, "到货退货单"),
  /**
   * 出库单退货
   */
  OUT_ORDER_RETURN((byte) 7, "出库单退货"),
  /**
   * 拆分生成
   */
  SPLIT_CREATE((byte) 8, "拆分生成"),
  /**
   * 预到货单
   */
  IN_ORDER((byte) 9, "预到货单"),
  /**
   * 预到货单越库
   */
  IN_ORDER_CROSS((byte) 10, "预到货单越库"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutSourceTypeEnum matchingEnum(String name) {
    for (OutSourceTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}
