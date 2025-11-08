package com.yiruantong.common.core.enums.base;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存来源类型枚举
 */
@Getter
@AllArgsConstructor
public enum InventorySourceTypeEnum implements BaseEnum {
  /**
   * PC按单扫描入库
   */
  PC_RECEIVE_IN((byte) 1, "PC按单扫描入库"),
  /**
   * PDA按单扫描入库
   */
  PDA_RECEIVE_IN((byte) 2, "PDA按单扫描入库"),
  /**
   * PDA扫描上架
   */
  PDA_SCAN_SHELVE_IN((byte) 3, "PDA扫描上架"),
  /**
   * PDA直接上架出库
   */
  PDA_SHELVE_OUT((byte) 4, "PDA直接上架出库"),
  /**
   * PC按单扫描出库
   */
  PC_ORDER_OUT((byte) 5, "PC按单扫描出库"),
  /**
   * PDA扫描出库
   */
  PDA_ORDER_OUT((byte) 6, "PDA扫描出库"),
  /**
   * PC按拍上架
   */
  PC_PLATE_SHELVE_IN((byte) 7, "PC扫拍上架"),
  /**
   * PC收货直接上架
   */
  PC_RECEIVE_SHELVE_IN((byte) 9, "PC收货直接上架"),
  /**
   * PC扫描上架出库
   */
  PC_SCAN_SHELVE_OUT((byte) 10, "PC扫描上架出库"),
  /**
   * PDA码盘上架
   */
  PDA_STACKING_IN((byte) 11, "PDA码盘上架"),
  /**
   * PDA码盘出库
   */
  PDA_STACKING_OUT((byte) 12, "PDA码盘出库"),
  /**
   * PC无单扫描入库
   */
  PC_NO_BILL_IN((byte) 13, "PC无单扫描入库"),
  /**
   * PC无单扫描出库
   */
  PC_NO_BILL_OUT((byte) 14, "PC无单扫描出库"),
  /**
   * PC货位转移入库
   */
  PC_POSITION_TRANSFER_IN((byte) 15, "PC货位转移入库"),
  /**
   * PC货位转移出库
   */
  PC_POSITION_TRANSFER_OUT((byte) 15, "PC货位转移出库"),
  /**
   * PC其他入库
   */
  PC_STORAGE_IN((byte) 16, "PC其他入库"),
  /**
   * PC其他出库
   */
  PC_STORAGE_OUT((byte) 17, "PC其他出库"),
  /**
   * PC货主过户入库
   */
  PC_STORAGE_CONSIGNOR_TRANSFER_IN((byte) 18, "PC货主过户入库"),

  /**
   * PC货主过户出库
   */
  PC_STORAGE_CONSIGNOR_TRANSFER_OUT((byte) 19, "PC货主过户出库"),
  /**
   * PC成本调整入库
   */
  PC_STORAGE_PURCHASE_PRICE_ADJUST_IN((byte) 20, "PC成本调整入库"),

  /**
   * PC成本调整出库
   */
  PC_STORAGE_PURCHASE_PRICE_ADJUST_OUT((byte) 21, "PC成本调整出库"),
  /**
   * PC库存调整入库
   */
  PC_STORAGE_ADJUST_IN((byte) 22, "PC库存调整入库"),
  /**
   * PC库存调整出库
   */
  PC_STORAGE_ADJUST_OUT((byte) 23, "PC库存调整出库"),
  /**
   * PC库内码盘
   */
  PC_STOREHOUSE_STACKING((byte) 24, "PC库内码盘"),
  /**
   * PC按单码盘
   */
  PC_ORDER_STACKING_IN((byte) 25, "PC按单码盘入库"),
  /**
   * PC拣货下架
   */
  PC_ORDER_PICKING((byte) 26, "PC拣货下架"),
  /**
   * PC闪电发货
   */
  PC_SEND_BATCH((byte) 27, "PC闪电发货"),
  /**
   * PC_LPN扫描入库
   */
  PC_LPN_SCAN_IN((byte) 28, "PC_LPN扫描入库"),
  /**
   * PC按拍扫描入库
   */
  PC_PAI_SCAN_IN((byte) 29, "PC按拍扫描入库"),
  /**
   * PC下架回拣入库
   */
  PC_ORDER_PICKING_IN((byte) 30, "PC下架回拣入库"),
  /**
   * PC下架回拣出库
   */
  PC_ORDER_PICKING_OUT((byte) 31, "PC下架回拣出库"),
  /**
   * PC出库退货
   */
  PC_OUT_RETURN((byte) 32, "PC出库退货"),
  /**
   * 调拨在途虚拟入库
   */
  PC_ALLOCATE_ROUTE_VIRTUAL_IN((byte) 33, "调拨在途虚拟入库"),
  /**
   * 调拨在途虚拟出库
   */
  PC_ALLOCATE_ROUTE_VIRTUAL_OUT((byte) 34, "调拨在途虚拟出库"),
  /**
   * PC码盘入库
   */
  PC_STACKING_IN((byte) 35, "PC码盘入库"),
  /**
   * PC码盘出库
   */
  PC_STACKING_OUT((byte) 36, "PC码盘出库"),
  /**
   * PC一键入库
   */
  PC_QUICK_ENTER((byte) 36, "PC一键入库"),
  /**
   * PC批量出库
   */
  PC_BATCH_OUT((byte) 37, "PC批量出库"),
  /**
   * 调拨在途出库
   */
  PC_ALLOCATE_ROUTE_OUT((byte) 38, "调拨在途出库"),
  /**
   * PC出库退货确认入库
   */
  PC_OUT_RETURN_CONFIRM((byte) 39, "PC出库退货确认入库"),
  /**
   * 到货退货单
   */
  RETURN_ORDER((byte) 40, "到货退货单"),
  /**
   * PC一键出库
   */
  PC_QUICK_OUT((byte) 41, "PC一键出库"),
  /**
   * PDA拣货下架
   */
  PDA_ORDER_PICKING((byte) 42, "PDA拣货下架"),
  /**
   * PDA按单码盘入库
   */
  PDA_ORDER_STACKING_IN((byte) 43, "PDA按单码盘入库"),
  /**
   * PDA按拍扫描入库
   */
  PDA_PAI_SCAN_IN((byte) 44, "PDA按拍扫描入库"),
  /**
   * 库存盘点出库
   */
  PC_ADJUST_INVENTORY_OUT((byte) 45, "库存盘点出库"),
  /**
   * 库存盘点入库
   */
  PC_ADJUST_INVENTORY_IN((byte) 45, "库存盘点入库"),
  /**
   * 报废出库
   */
  INVALIDATE_OUT((byte) 46, "报废出库"),
  /**
   * 领料单
   */
  PICKING_ORDER((byte) 47, "领料单"),

  /**
   * 其他出库
   */
  STORAGE_OUTER((byte) 48, "其他出库"),
  /**
   * 退料单
   */
  RETURN_MATERIAL_ORDER((byte) 49, "退料单"),
  /**
   * PC无单扫描上架
   */
  PC_NO_BILL_SHELVE_IN((byte) 50, "PC无单扫描上架"),
  /**
   * PC_LPN扫描上架
   */
  PC_LPN_SCAN_SHELVE_IN((byte) 51, "PCLPN扫描上架"),
  /**
   * PC装箱入库
   */
  PC_CASING_IN((byte) 52, "PC装箱入库"),
  /**
   * 属性转换出库
   */
  ATTRIBUTE_CHANGE_OUT((byte) 53, "属性转换出库"),
  /**
   * 属性转换入库
   */
  ATTRIBUTE_CHANGE_IN((byte) 54, "属性转换入库"),
  /**
   * PDALPN入库
   */
  PDA_LPN_SCAN_IN((byte) 55, "PDALPN入库"),
  /**
   * PDA无单扫描入库
   */
  PDA_NO_BILL_IN((byte) 56, "PDA无单扫描入库"),
  /**
   * PDA无单打包出库
   */
  PDA_NO_BILL_OUT((byte) 57, "PDA无单打包出库"),
  /**
   * PDA下架回拣出库
   */
  PDA_ORDER_PICKING_OUT((byte) 58, "PDA下架回拣出库"),
  /**
   * PDA摘果下架
   */
  PDA_ORDER_PICKING_ZG((byte) 59, "PDA摘果下架"),
  /**
   * PDA货位转移入库
   */
  PDA_STORAGE_POSITION_TRANSFTER_IN((byte) 60, "PDA货位转移入库"),
  /**
   * PDA货位转移出库
   */
  PDA_STORAGE_POSITION_TRANSFTER_OUT((byte) 61, "PDA货位转移出库"),
  /**
   * PDA下架回拣入库
   */
  PDA_ORDER_PICKING_IN((byte) 62, "PDA下架回拣入库"),
  /**
   * PC出库配货
   */
  PC_MATCHING((byte) 63, "PC出库配货"),
  /**
   * PC出库批量配货
   */
  PC_MATCHING_BATCH((byte) 64, "PC出库批量配货"),
  /**
   * PDA出库配货
   */
  PDA_MATCHING((byte) 65, "PDA出库配货"),
  /**
   * PC出库批量配货
   */
  PDA_MATCHING_BATCH((byte) 66, "PDA出库批量配货"),
  /**
   * PDA一键入库
   */
  PDA_QUICK_ENTER((byte) 67, "PDA一键入库"),
  /**
   * PDA一键直接上架
   */
  PDA_QUICK_SHELVE_DIRECT((byte) 68, "PDA一键直接上架"),
  /**
   * PDA装箱入库
   */
  PDA_CASING_IN((byte) 69, "PDA装箱入库"),
  /**
   * PC扫描上架
   */
  PC_SCAN_SHELVE_IN((byte) 70, "PC扫描上架"),
  /**
   * PDA一键出库
   */
  PDA_QUICK_OUT((byte) 71, "PDA一键出库"),
  /**
   * PDA按拍上架
   */
  PDA_PLATE_SHELVE_IN((byte) 72, "PDA扫拍上架"),
  /**
   * PDA无单扫描上架
   */
  PDA_NO_BILL_SHELVE_IN((byte) 73, "PDA无单扫描上架"),
  /**
   * PC_LPN扫描上架
   */
  PDA_LPN_SCAN_SHELVE_IN((byte) 74, "PDALPN扫描上架"),
  /**
   * PDA扫拍下架
   */
  PDA_PLATE_PICKING((byte) 75, "PDA扫拍下架"),
  /**
   * PC扫拍出库
   */
  PC_ORDER_OUT_PLATE((byte) 76, "PC扫拍出库"),
  /**
   * PDA扫拍出库
   */
  PDA_ORDER_OUT_PLATE((byte) 77, "PDA扫拍出库"),
  /**
   * PC无单上架出库
   */
  PC_NO_BILL_SHELVE_OUT((byte) 78, "PC无单上架出库"),
  /**
   * PCLPN上架出库
   */
  PC_LPN_SCAN_SHELVE_OUT((byte) 79, "PCLPN上架出库"),
  /**
   * PC扫拍上架出库
   */
  PC_PLATE_SHELVE_OUT((byte) 80, "PC扫拍上架出库"),
  /**
   * PDA扫描上架出库
   */
  PDA_SCAN_SHELVE_OUT((byte) 81, "PDA扫描上架出库"),
  /**
   * PDALPN上架出库
   */
  PDA_LPN_SCAN_SHELVE_OUT((byte) 82, "PDALPN上架出库"),
  /**
   * PDA扫拍上架出库
   */
  PDA_PLATE_SHELVE_OUT((byte) 83, "PDA扫拍上架出库"),
  /**
   * PDA无单上架出库
   */
  PDA_NO_BILL_SHELVE_OUT((byte) 84, "PDA无单上架出库"),
  /**
   * PDA一键闪入
   */
  PDA_NO_BILL_FLASH_IN((byte) 85, "PDA一键闪入"),
  /**
   * PDA一键闪出
   */
  PDA_ORDER_FLASH_OUT((byte) 86, "PDA一键闪出"),
  /**
   * 成品入库单
   */
  COMPLETION_ORDER((byte) 87, "成品入库单"),
  /**
   * PC补货入库
   */
  PC_REPLENISHMENT_IN((byte) 88, "PC补货入库"),
  /**
   * PC补货出库
   */
  PC_REPLENISHMENT_OUT((byte) 89, "PC补货出库"),
  /**
   * PC采购单一键入库
   */
  PC_PURCHASE_IN((byte) 90, "PC采购单一键入库"),
  /**
   * PC越库入库
   */
  PC_CROSS_DOCKING_IN((byte) 91, "PC越库入库"),
  /**
   * PC越库入库
   */
  PDA_NO_BILL_SHELVE((byte) 92, "PDA无单扫描上架"),
  /**
   * 出库单溢出入库
   */
  OUT_SPILLOVER_IN((byte) 93, "出库单溢出入库"),
  /**
   * PC库存状态调整入库
   */
  PC_STORAGE_STATUS_ADJUST_IN((byte) 94, "PC库存状态调整入库"),
  /**
   * PC库存状态调整出库
   */
  PC_STORAGE_STATUS_ADJUST_OUT((byte) 95, "PC库存状态调整出库"),
  /**
   * PC质检结果返回出库
   */
  PC_QUALITY_TESTING_RETURN_OUT((byte) 96, "PC质检结果返回出库"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param enumName 枚举名
   * @return 枚举
   */
  public static InventorySourceTypeEnum matchingEnum(String enumName) {
    for (InventorySourceTypeEnum i : values()) {
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
  public static InventorySourceTypeEnum matchingEnumById(Byte id) {
    for (InventorySourceTypeEnum i : values()) {
      if (i.getId().equals(id)) {
        return i;
      }
    }
    return null;
  }

  @Override
  public String getCode() {
    return this.toString();
  }
}
