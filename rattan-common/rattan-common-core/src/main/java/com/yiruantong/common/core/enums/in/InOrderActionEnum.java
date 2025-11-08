package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库单动作
 */
@Getter
@AllArgsConstructor
public enum InOrderActionEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 审核
   */
  AUDITING((byte) 2, "审核"),
  /**
   * PC按单扫描入库
   */
  PC_RECEIVE_IN((byte) 3, "PC按单扫描入库"),
  /**
   * PC直接上架
   */
  PC_RECEIVE_SHELVE_DIRECT((byte) 4, "PC直接上架"),
  /**
   * 单据操作
   */
  OPERATION((byte) 5, "单据操作"),
  /**
   * 入库计划转入
   */
  PLAN_TO_ORDER((byte) 6, "入库计划转入"),
  /**
   * 终止
   */
  STOP((byte) 7, "终止"),
  /**
   * 开启
   */
  OPEN((byte) 8, "开启"),
  /**
   * 导入
   */
  IMPORT((byte) 9, "导入"),
  /**
   * 缺货转预到货
   */
  LOCK_TO_ORDER((byte) 10, "缺货转预到货"),
  /**
   * PC撤销入库
   */
  PC_CANCEL_IN((byte) 11, "PC撤销入库"),
  /**
   * 复制
   */
  COPY((byte) 12, "复制"),
  /**
   * 拆分
   */
  SPLIT((byte) 13, "拆分"),
  /**
   * 撤销上架
   */
  CANCEL_SHELVE((byte) 14, "撤销上架"),
  /**
   * 出库退货转入
   */
  RETURN_TO_ORDER((byte) 15, "出库退货转入"),
  /**
   * 调拨单转入
   */
  PC_ALLOCATE_APPLY_PLAN((byte) 16, "调拨单转入"),
  /**
   * 残品计划转入
   */
  DAMAGED_TO_ORDER((byte) 17, "残品入库转入"),
  /**
   * 建议采购转入
   */
  SUGGESTION_TO((byte) 18, "建议采购转入"),
  /**
   * PDA按单扫描入库
   */
  PDA_RECEIVE_IN((byte) 19, "PDA按单扫描入库"),
  /**
   * PDA扫描上架
   */
  PDA_SCAN_SHELVE_IN((byte) 20, "PDA扫描上架"),
  /**
   * MES退料
   */
  RETURN_ORDER_TO_ORDER((byte) 21, "MES退料"),
  /**
   * MES成品入库
   */
  COMPLETION_TO_ORDER((byte) 22, "MES成品入库"),
  /**
   * PC码盘入库
   */
  PC_STACKING_IN((byte) 22, "PC码盘入库"),
  /**
   * PC一键入库
   */
  PC_QUICK_ENTER((byte) 23, "PC一键入库"),
  /**
   * PDA按单码盘
   */
  PDA_ORDER_STACKING_IN((byte) 24, "PDA按单码盘"),
  /**
   * PDA扫拍入库
   */
  PDA_PAI_SCAN_IN((byte) 25, "PDA扫拍入库"),
  /**
   * PDA码盘上架
   */
  PDA_STACKING_IN((byte) 26, "PDA码盘上架"),
  /**
   * PC按单码盘入库
   */
  PC_ORDER_STACKING_IN((byte) 26, "PC按单码盘入库"),
  /**
   * PCLPN扫描入库
   */
  PC_LPN_SCAN_IN((byte) 27, "PCLPN扫描入库"),
  /**
   * PC按拍扫描入库
   */
  PC_PAI_SCAN_IN((byte) 28, "PC按拍扫描入库"),
  /**
   * PC扫描上架
   */
  PC_SCAN_SHELVE_IN((byte) 29, "PC扫描上架"),
  /**
   * PC无单扫描上架
   */
  PC_NO_BILL_SHELVE_IN((byte) 30, "PC无单扫描上架"),
  /**
   * PC_LPN扫描上架
   */
  PC_LPN_SCAN_SHELVE_IN((byte) 31, "PC_LPN扫描上架"),
  /**
   * PC按拍上架
   */
  PC_PLATE_SHELVE_IN((byte) 32, "PC扫拍上架"),
  /**
   * PC装箱入库
   */
  PC_CASING_IN((byte) 33, "PC装箱入库"),
  /**
   * PDALPN入库
   */
  PDA_LPN_SCAN_IN((byte) 34, "PDALPN入库"),
  /**
   * PDALPN扫描上架
   */
  PDA_LPN_SCAN_SHELVE_DIRECT((byte) 35, "PDALPN扫描上架"),
  /**
   * PDA_无单入库
   */
  PDA_NO_BILL_IN((byte) 36, "PDA无单扫描入库"),
  /**
   * PDA_无单扫描上架
   */
  PDA_NO_BILL_SHELVE_DIRECT((byte) 37, "PDA无单直接上架"),
  /**
   * PC_无单入库
   */
  PC_NO_BILL_IN((byte) 38, "PC无单入库"),
  /**
   * PC码盘上架
   */
  PC_STACKING_SHELVE((byte) 39, "PC码盘上架"),
  /**
   * PC装箱上架
   */
  PC_CASING_SHELVE_DIRECT((byte) 40, "PC装箱直接上架"),
  /**
   * PC按拍扫描上架
   */
  PC_PAI_SCAN_SHELVE_DIRECT((byte) 41, "PC扫拍直接上架"),
  /**
   * PC按单码盘上架
   */
  PC_ORDER_STACKING_SHELVE_DIRECT((byte) 42, "PC码盘直接上架"),
  /**
   * PC一键上架
   */
  PC_QUICK_SHELVE_DIRECT((byte) 43, "PC一键直接上架"),
  /**
   * PCLpn扫描入库
   */
  PC_LPN_SCAN_SHELVE_DIRECT((byte) 44, "PCLPN直接上架"),
  /**
   * PDA一键入库
   */
  PDA_QUICK_ENTER((byte) 45, "PDA一键入库"),
  /**
   * PDA一键直接上架
   */
  PDA_QUICK_SHELVE_DIRECT((byte) 46, "PDA一键直接上架"),
  /**
   * PC装箱入库
   */
  PDA_CASING_IN((byte) 47, "PDA装箱入库"),
  /**
   * PDA装箱直接上架
   */
  PDA_CASING_SHELVE_DIRECT((byte) 48, "PDA装箱直接上架"),
  /**
   * PDA按拍上架
   */
  PDA_PLATE_SHELVE_IN((byte) 49, "PDA扫拍上架"),
  /**
   * PDA无单扫描上架
   */
  PDA_NO_BILL_SHELVE_IN((byte) 50, "PDA无单扫描上架"),
  /**
   * PC码盘直接上架
   */
  PC_STACKING_SHELVE_DIRECT((byte) 51, "PC码盘直接上架"),
  /**
   * PDA直接上架
   */
  PDA_RECEIVE_SHELVE_DIRECT((byte) 52, "PDA直接上架"),
  /**
   * PDA扫拍直接上架
   */
  PDA_PAI_SCAN_SHELVE_DIRECT((byte) 53, "PDA扫拍直接上架"),
  /**
   * PC无单直接上架
   */
  PC_NO_BILL_SHELVE_DIRECT((byte) 54, "PC无单直接上架"),
  /**
   * PDALPN扫描上架
   */
  PDA_LPN_SCAN_SHELVE_IN((byte) 55, "PDALPN扫描上架"),
  /**
   * PDA码盘直接上架
   */
  PDA_ORDER_STACKING_SHELVE_DIRECT((byte) 56, "PDA码盘直接上架"),
  /**
   * PDA一键闪入
   */
  PDA_NO_BILL_FLASH_IN((byte) 57, "PDA一键闪入"),
  /**
   * PDA装箱直接上架
   */
  PDA_NO_BILL_FLASH_SHELVE_DIRECT((byte) 58, "PDA闪入直接上架"),
  /**
   * PC成品入库单转入
   */
  PC_COMPLETION_ORDER((byte) 59, "PC成品入库单转入"),
  /**
   * PC成品直接上架
   */
  PC_COMPLETION_ORDER_DIRECT((byte) 60, "PC成品直接上架"),
  /**
   * PC采购单转入
   */
  PC_PURCHASE_IN((byte) 61, "PC采购单转入"),
  /**
   * 零库存出库
   */
  PC_TO_OUT_ORDER_FINISHED((byte) 62, "零库存出库"),
  /**
   * PC越库入库
   */
  PC_CROSS_DOCKING_IN((byte) 63, "PC越库入库"),
  /**
   * PC越库直接上架
   */
  PC_CROSS_DOCKING_DIRECT((byte) 64, "PC越库直接上架"),
  /**
   * PDA_无单入库
   */
  PDA_NO_BILL_SHELVE((byte) 65, "PDA无单扫描上架"),
  /**
   * 强制收货
   */
  COMPULSORY_RECEIPT((byte) 66, "强制收货"),
  /**
   * 收货剩余
   */
  TAKE_DELIVERY_REMAINING((byte) 67, "收货剩余"),
  ;


  private final Byte id;
  private final String name;


  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static InOrderActionEnum matchingEnum(String name) {
    for (InOrderActionEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}
