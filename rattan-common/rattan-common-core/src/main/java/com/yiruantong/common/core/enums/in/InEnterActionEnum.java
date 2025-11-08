package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库单动作
 */
@Getter
@AllArgsConstructor
public enum InEnterActionEnum {
  /**
   * PC收货入库
   */
  PC_RECEIVE_IN((byte) 1, "PC收货入库"),
  /**
   * PC直接上架
   */
  PC_RECEIVE_SHELVE_DIRECT((byte) 2, "PC直接上架"),
  /**
   * PC装箱入库
   */
  PC_ENCHASE_IN((byte) 3, "PC装箱入库"),
  /**
   * PC撤销入库
   */
  PC_CANCEL_IN((byte) 4, "PC撤销入库"),
  /**
   * PC直接上架
   */
  PC_SCAN_SHELVE_IN((byte) 5, "PC扫描上架"),
  /**
   * PC码盘入库
   */
  PC_STACKING_IN((byte) 6, "PC码盘入库"),
  /**
   * PC撤销上架
   */
  PC_CANCEL_SHELVE((byte) 7, "PC撤销上架"),
  /**
   * PDA直接上架
   */
  PDA_RECEIVE_SHELVE_DIRECT((byte) 8, "PDA直接上架"),
  /**
   * PDA收货入库
   */
  PDA_RECEIVE_IN((byte) 9, "PDA收货入库"),
  /**
   * PDA扫描上架
   */
  PDA_SCAN_SHELVE_IN((byte) 10, "PDA扫描上架"),
  /**
   * PC无单扫描上架
   */
  PC_NO_BILL_SHELVE_IN((byte) 11, "PC无单扫描上架"),
  /**
   * PCLPN扫描上架
   */
  PC_LPN_SCAN_SHELVE_IN((byte) 12, "PCLPN扫描上架"),
  /**
   * PC按拍上架
   */
  PC_PLATE_SHELVE_IN((byte) 13, "PC扫拍上架"),
  /**
   * PC按单码盘上架
   */
  PC_ORDER_STACKING_SHELVE_DIRECT((byte) 14, "PC码盘直接上架"),
  /**
   * PC按单码盘
   */
  PC_ORDER_STACKING_IN((byte) 15, "PC按单码盘"),
  /**
   * PC装箱入库
   */
  PC_CASING_IN((byte) 16, "PC装箱入库"),
  /**
   * PC装箱上架
   */
  PC_CASING_SHELVE_DIRECT((byte) 17, "PC装箱直接上架"),
  /**
   * PC装箱入库
   */
  PDA_CASING_IN((byte) 18, "PDA装箱入库"),
  /**
   * PDA装箱直接上架
   */
  PDA_CASING_SHELVE_DIRECT((byte) 19, "PDA装箱直接上架"),
  /**
   * PDA扫拍入库
   */
  PDA_PAI_SCAN_IN((byte) 20, "PDA扫拍入库"),
  /**
   * PDA扫拍直接上架
   */
  PDA_PAI_SCAN_SHELVE_DIRECT((byte) 21, "PDA扫拍直接上架"),
  /**
   * PDA一键入库
   */
  PDA_QUICK_ENTER((byte) 22, "PDA一键入库"),
  /**
   * PDA一键直接上架
   */
  PDA_QUICK_SHELVE_DIRECT((byte) 23, "PDA一键直接上架"),
  /**
   * PC_无单入库
   */
  PC_NO_BILL_IN((byte) 24, "PC无单入库"),
  /**
   * PC无单直接上架
   */
  PC_NO_BILL_SHELVE_DIRECT((byte) 25, "PC无单直接上架"),
  /**
   * PC按拍扫描入库
   */
  PC_PAI_SCAN_IN((byte) 26, "PC按拍扫描入库"),
  /**
   * PC按拍扫描上架
   */
  PC_PAI_SCAN_SHELVE_DIRECT((byte) 27, "PC扫拍直接上架"),
  /**
   * PC一键入库
   */
  PC_QUICK_ENTER((byte) 28, "PC一键入库"),
  /**
   * PC一键上架
   */
  PC_QUICK_SHELVE_DIRECT((byte) 29, "PC一键直接上架"),
  /**
   * PC码盘直接上架
   */
  PC_STACKING_SHELVE_DIRECT((byte) 30, "PC码盘直接上架"),
  /**
   * PDALPN入库
   */
  PDA_LPN_SCAN_IN((byte) 31, "PDALPN入库"),
  /**
   * PDA_LPN扫描上架
   */
  PDA_LPN_SCAN_SHELVE_DIRECT((byte) 32, "PDALPN直接上架"),
  /**
   * PDALPN扫描上架
   */
  PDA_LPN_SCAN_SHELVE_IN((byte) 33, "PDALPN扫描上架"),
  /**
   * PCLPN扫描入库
   */
  PC_LPN_SCAN_IN((byte) 34, "PCLPN扫描入库"),
  /**
   * PCLpn扫描入库
   */
  PC_LPN_SCAN_SHELVE_DIRECT((byte) 35, "PCLPN直接上架"),
  /**
   * PDA按拍上架
   */
  PDA_PLATE_SHELVE_IN((byte) 36, "PDA扫拍上架"),
  /**
   * PDA无单扫描上架
   */
  PDA_NO_BILL_SHELVE_IN((byte) 37, "PDA无单扫描上架"),
  /**
   * PDA按单码盘
   */
  PDA_ORDER_STACKING_IN((byte) 28, "PDA按单码盘入库"),
  /**
   * PDA码盘直接上架
   */
  PDA_ORDER_STACKING_SHELVE_DIRECT((byte) 29, "PDA码盘直接上架"),
  /**
   * PDA_无单入库
   */
  PDA_NO_BILL_IN((byte) 30, "PDA无单扫描入库"),
  /**
   * PDA_无单扫描上架
   */
  PDA_NO_BILL_SHELVE_DIRECT((byte) 31, "PDA无单直接上架"),
  /**
   * PDA一键闪入
   */
  PDA_NO_BILL_FLASH_IN((byte) 32, "PDA一键闪入"),
  /**
   * PDA装箱直接上架
   */
  PDA_NO_BILL_FLASH_SHELVE_DIRECT((byte) 33, "PDA闪入直接上架"),
  /**
   * PC成品入库单转入
   */
  PC_COMPLETION_ORDER((byte) 34, "PC成品入库单转入"),
  /**
   * PC成品入库单转入
   */
  PC_COMPLETION_ORDER_DIRECT((byte) 35, "PC成品直接上架"),
  /**
   * MES退料
   */
  RETURN_ORDER_TO_ORDER((byte) 36, "MES退料"),
  /**
   * PC越库入库
   */
  PC_CROSS_DOCKING_IN((byte) 37, "PC越库入库"),
  /**
   * PC越库直接上架
   */
  PC_CROSS_DOCKING_DIRECT((byte) 38, "PC越库直接上架"),
  /**
   * PDA_无单入库
   */
  PDA_NO_BILL_SHELVE((byte) 39, "PDA无单扫描上架");

  private final Byte id;
  private final String name;
}
