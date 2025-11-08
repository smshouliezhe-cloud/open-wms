package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存占位类型枚举
 */
@Getter
@AllArgsConstructor
public enum HolderSourceTypeEnum {
  /**
   * 常规出库单
   */
  OUT_ORDER_NORMAL((byte) 1, "常规出库单"),
  /**
   * 货位转移
   */
  STORAGE_POSITION_TRANSFTER((byte) 2, "货位转移"),
  /**
   * 其他出库
   */
  STORAGE_OUTER((byte) 3, "其他出库"),
  /**
   * 货主过户
   */
  STORAGE_CONSIGNOR_TRANSFER((byte) 4, "货主过户"),
  /**
   * 库存调整
   */
  STORAGE_ADJUST((byte) 5, "库存调整"),
  /**
   * 库存调价
   */
  STORAGE_PURCHASE_PRICE_ADJUST((byte) 6, "库存调价"),
  /**
   * PC码盘入库
   */
  PC_STACKING_IN((byte) 7, "PC码盘入库"),
  /**
   * 拣货下架
   */
  OUT_PICKING((byte) 8, "拣货下架"),
  /**
   * 残次品调拨
   */
  DEFECTIVE_ALLOCATE((byte) 9, "残次品调拨"),
  /**
   * 赠品样品调拨
   */
  GIVEAWAYS_SAMPLES_ALLOCATE((byte) 10, "赠品样品调拨"),
  /**
   * 临保品调拨
   */
  CASUAL_ALLOCATE((byte) 11, "临保品调拨"),
  /**
   * 普通调拨
   */
  ORDINARY_ALLOCATE((byte) 12, "普通调拨"),
  /**
   * 其他调拨
   */
  OTHER_ALLOCATE((byte) 13, "其他调拨"),
  /**
   * 同价调拨
   */
  PARITY_ALLOCATE((byte) 14, "同价调拨"),
  /**
   * 变价调拨
   */
  ALTERATION_ALLOCATE((byte) 15, "变价调拨"),
  /**
   * 调拨在途虚拟入库
   */
  PC_ALLOCATE_ROUTE_VIRTUAL_IN((byte) 16, "调拨在途虚拟入库"),
  /**
   * 出库计划
   */
  PC_OUT_PLAN((byte) 17, "出库计划转入"),
  /**
   * 到货退货单
   */
  RETURN_ORDER((byte) 18, "到货退货单"),
  /**
   * PC无单扫描出库
   */
  PC_NO_BILL_OUT((byte) 19, "无单扫描出库"),
  /**
   * 调整库存
   */
  ADJUST_INVENTORY((byte) 20, "调整库存"),
  /**
   * 报废出库
   */
  INVALIDATE_OUT((byte) 21, "报废出库"),
  /**
   * 领料单
   */
  PICKING_ORDER((byte) 22, "领料单"),
  /**
   * TOB销售单
   */
  TOB_SALE_ORDER((byte) 23, "TOB销售单"),
  /**
   * 工单领料
   */
  WORK_ORDER_PICKING((byte) 24, "工单领料"),
  /**
   * 生产补料
   */
  PRODUCTION_REPLENISHMENT((byte) 25, "生产补料"),
  /**
   * PC下架回拣
   */
  PC_PICKING_SHELVE((byte) 26, "PC下架回拣"),
  /**
   * 属性转换
   */
  ATTRIBUTE_CHANGE((byte) 27, "属性转换"),
  /**
   * PDA货位转移
   */
  PDA_STORAGE_POSITION_TRANSFTER((byte) 28, "PDA货位转移"),
  /**
   * PDA下架回拣
   */
  PDA_ORDER_PICKING_RETURN((byte) 29, "PDA下架回拣"),
  /**
   * PC补货
   */
  PC_REPLENISHMENT((byte) 30, "PC补货"),
  /**
   * 材料/备件领用单
   */
  SPARE_PARTS_BILL((byte) 31, "材料/备件领用单"),
  /**
   * 合金/辅料领用单
   */
  AUXILIARY_MATERIAL_BILL((byte) 32, "合金/辅料领用单"),
  /**
   * 紧急放行单
   */
  EMERGENCY_RELEASE_BILL((byte) 33, "紧急放行单"),
  /**
   * 出库单拆分
   */
  OUT_SPLIT((byte) 34, "出库单拆分"),
  /**
   * 紧急放行
   */
  EMERGENCY_RELEASE((byte) 34, "紧急放行"),
  /**
   * 销售订单
   */
  SALE_ORDER((byte) 35, "销售订单"),
  /**
   * 越库出库
   */
  CROSS_ORDER((byte) 36, "越库出库"),
  /**
   * 材料/备件
   */
  MATERIAL_SPARE_PARTS((byte) 37, "材料/备件"),
  /**
   * 质检退货
   */
  QUALITY_INSPECTION_RETURN((byte) 38, "质检退货"),
  /**
   * 合金/辅料
   */
  ALLOY_AUXILIARY_MATERIALS((byte) 39, "合金/辅料"),
  /**
   * 溢出
   */
  SPILLOVER((byte) 40, "溢出"),
  /**
   * 头程仓库出库
   */
  FREIGHT_ORDER_STORAGE_OUT((byte) 41, "头程仓库出库"),
  /**
   * TOC销售单
   */
  SALE_ORDER_TOC((byte) 42, "TOC销售单"),
  /**
   * 生产加工出库
   */
  MANUFACTURING_OUT((byte) 43, "生产加工出库"),
  /**
   * 其他出库单
   */
  STORAGE_OUTER_CODE((byte) 44, "其他出库单"),
  /**
   * 调拨出库
   */
  ALLOCATE_OUT((byte) 45, "调拨出库"),
  /**
   * 领料出库
   */
  MATERIAL_REQUISITION_OUT((byte) 46, "领料出库"),
  /**
   * 销售出库
   */
  SALE_OUT((byte) 47, "销售出库"),
  ;


  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static HolderSourceTypeEnum matchingEnum(String name) {
    for (HolderSourceTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}
