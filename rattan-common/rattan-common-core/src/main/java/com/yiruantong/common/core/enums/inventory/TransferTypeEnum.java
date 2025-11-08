package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.enums.base.BaseEnum;

/**
 * 库存来源类型枚举
 */
@Getter
@AllArgsConstructor
public enum TransferTypeEnum implements BaseEnum {
  /**
   * PC货位转移
   */
  PC_POSITION_TRANSFTER((byte) 1, "PC货位转移"),
  /**
   * PC下架回拣
   */
  PC_PICKING_SHELVE((byte) 2, "PC下架回拣"),
  /**
   * PC码盘入库
   */
  PC_STACKING_IN((byte) 3, "PC码盘入库"),
  /**
   * 属性转换
   */
  ATTRIBUTE_CHANGE((byte) 4, "属性转换"),
  /**
   * PDA货位转移
   */
  PDA_POSITION_TRANSFTER((byte) 5, "PDA货位转移"),
  /**
   * PDA下架回拣
   */
  PDA_ORDER_PICKING_RETURN((byte) 6, "PDA下架回拣"),
  /**
   * PDA码盘入库
   */
  PDA_ENTER_STACKING_SCAN((byte) 7, "PDA码盘入库"),
  /**
   * PC补货扫描
   */
  PC_REPLENISHMENT_SCAN((byte) 8, "PC补货扫描");

  private final Byte id;
  private final String name;

  @Override
  public String getCode() {
    return this.toString();
  }
}
