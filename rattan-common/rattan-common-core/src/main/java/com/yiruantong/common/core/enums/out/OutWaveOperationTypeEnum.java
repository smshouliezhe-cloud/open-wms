package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 波次单操作类型枚举
 */
@Getter
@AllArgsConstructor
public enum OutWaveOperationTypeEnum {
  /**
   * 生成波次
   */
  CREATE_WARE((byte) 1, "生成波次"),
  /**
   * PC出库打包
   */
  PC_PACKAGE((byte) 2, "PC出库打包"),
  /**
   * 拣货下架
   */
  PC_PICKING((byte) 3, "PC拣货下架"),
  /**
   * 出库配货
   */
  PC_MATCHING((byte) 4, "PC出库配货"),
  /**
   * PC闪电发货
   */
  PC_SEND_BATCH((byte) 5, "PC闪电发货"),
  /**
   * PC发货校验
   */
  PC_SEND_GOODS_CHECK((byte) 6, "PC发货校验"),
  /**
   * PDA扫描出库
   */
  PDA_SCAN_OUT((byte) 7, "PDA扫描出库"),
  /**
   * PDA无单打包出库
   */
  PDA_NO_BILL_OUT((byte) 8, "PDA无单打包出库"),
  /**
   * PDA下架回拣出库
   */
  PDA_ORDER_PICKING_OUT((byte) 9, "PDA下架回拣出库"),
  /**
   * PDA摘果下架
   */
  PDA_ORDER_PICKING_ZG((byte) 10, "PDA摘果下架"),
  /**
   * PDA拣货下架
   */
  PDA_ORDER_PICKING((byte) 11, "PDA拣货下架"),
  /**
   * PC出库批量配货
   */
  PC_MATCHING_BATCH((byte) 12, "PC出库批量配货"),
  /**
   * PDA出库配货
   */
  PDA_MATCHING((byte) 13, "PDA出库配货"),
  /**
   * PC出库批量配货
   */
  PDA_MATCHING_BATCH((byte) 14, "PDA出库批量配货"),
  /**
   * PC按单扫描出库
   */
  PC_ORDER_OUT((byte) 15, "PC按单扫描出库"),
  /**
   * PDA一键出库
   */
  PDA_QUICK_OUT((byte) 16, "PDA一键出库"),
  /**
   * PDA扫拍下架
   */
  PDA_PLATE_PICKING((byte) 15, "PDA扫拍下架"),
  /**
   * PC扫拍出库
   */
  PC_ORDER_OUT_PLATE((byte) 16, "PC扫拍出库"),
  /**
   * PDA扫拍出库
   */
  PDA_ORDER_OUT_PLATE((byte) 17, "PDA扫拍出库"),
  /**
   * PDA一键闪出
   */
  PDA_ORDER_FLASH_OUT((byte) 18, "PDA一键闪出"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutWaveOperationTypeEnum matchingEnum(String name) {
    for (OutWaveOperationTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}
