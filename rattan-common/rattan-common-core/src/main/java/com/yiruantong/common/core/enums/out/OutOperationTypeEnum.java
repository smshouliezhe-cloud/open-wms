package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库单操作类型枚举
 */
@Getter
@AllArgsConstructor
public enum OutOperationTypeEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 审核
   */
  AUDITING((byte) 2, "审核"),
  /**
   * PC扫描出库
   */
  PC_OUT_SCAN((byte) 3, "PC扫描出库"),
  /**
   * 终止
   */
  STOP((byte) 4, "终止"),
  /**
   * 开启
   */
  OPEN((byte) 5, "开启"),
  /**
   * 出库计划
   */
  PC_OUT_PLAN((byte) 6, "出库计划转入"),
  /**
   * 生成波次
   */
  CREATE_WAVE((byte) 7, "生成波次"),
  /**
   * 无单入库
   */
  PC_NO_BILL((byte) 8, "PC无单入库"),
  /**
   * 导入
   */
  IMPORT((byte) 9, "导入"),
  /**
   * PC拣货下架
   */
  PC_ORDER_PICKING((byte) 10, "PC拣货下架"),
  /**
   * 出库配货
   */
  PC_MATCHING((byte) 11, "PC出库配货"),
  /**
   * 复制
   */
  COPY((byte) 12, "复制"),
  /**
   * 拆分
   */
  SPLIT((byte) 13, "拆分"),
  /**
   * 波次剔除
   */
  WAVE_REMOVED((byte) 14, "波次剔除"),
  /**
   * 合并订单
   */
  INCORPORATION_ORDER((byte) 15, "合并订单"),
  /**
   * 调拨单转入
   */
  PC_ALLOCATE_APPLY_PLAN((byte) 16, "调拨单转入"),
  /**
   * PC出库打包
   */
  PC_PACKAGE((byte) 17, "PC出库打包"),
  /**
   * 闪电发货
   */
  PC_SEND_BATCH((byte) 18, "PC闪电发货"),
  /**
   * 批量出库
   */
  PC_BATCH_OUT((byte) 19, "PC批量出库"),
  /**
   * 强制完成
   */
  FORCE_FINISH((byte) 20, "强制完成"),
  /**
   * 闪电发货
   */
  PC_SEND_CHECK((byte) 21, "PC闪电发货"),
  /**
   * PC发货校验
   */
  PC_SEND_GOODS_CHECK((byte) 22, "PC发货校验"),
  /**
   * 退货单转入
   */
  RETURN_IN((byte) 23, "退货单转入"),
  /**
   * PC一键出库
   */
  PC_QUICK_OUT((byte) 24, "PC一键出库"),
  /**
   * 修改物流信息
   */
  PC_UPDATE_LOGISTICS((byte) 25, "修改物流信息"),
  /**
   * PDA扫描出库
   */
  PDA_ORDER_OUT((byte) 26, "PDA扫描出库"),
  /**
   * 报废出库
   */
  PC_INVALIDATE_OUT((byte) 27, "PC报废出库"),
  /**
   * MES领料
   */
  PICKING_IN((byte) 28, "MES领料"),
  /**
   * PDA无单打包出库
   */
  PDA_NO_BILL_OUT((byte) 29, "PDA无单打包出库"),
  /**
   * PDA下架回拣出库
   */
  PDA_ORDER_PICKING_OUT((byte) 30, "PDA下架回拣出库"),
  /**
   * PDA摘果下架
   */
  PDA_ORDER_PICKING_ZG((byte) 31, "PDA摘果下架"),
  /**
   * PDA拣货下架
   */
  PDA_ORDER_PICKING((byte) 32, "PDA拣货下架"),
  /**
   * PC出库批量配货
   */
  PC_MATCHING_BATCH((byte) 33, "PC出库批量配货"),
  /**
   * PDA出库配货
   */
  PDA_MATCHING((byte) 34, "PDA出库配货"),
  /**
   * PC出库批量配货
   */
  PDA_MATCHING_BATCH((byte) 35, "PDA出库批量配货"),
  /**
   * PC无单扫描出库
   */
  PC_NO_BILL_OUT((byte) 36, "PC无单扫描出库"),
  /**
   * PC按单扫描出库
   */
  PC_ORDER_OUT((byte) 37, "PC按单扫描出库"),
  /**
   * PDA一键出库
   */
  PDA_QUICK_OUT((byte) 38, "PDA一键出库"),
  /**
   * PDA扫拍下架
   */
  PDA_PLATE_PICKING((byte) 39, "PDA扫拍下架"),
  /**
   * PC扫拍出库
   */
  PC_ORDER_OUT_PLATE((byte) 40, "PC扫拍出库"),
  /**
   * PDA扫拍出库
   */
  PDA_ORDER_OUT_PLATE((byte) 41, "PDA扫拍出库"),
  /**
   * PDA一键闪出
   */
  PDA_ORDER_FLASH_OUT((byte) 42, "PDA一键闪出"),
  /**
   * 开启
   */
  SORTING((byte) 43, "分拣"),
  /**
   * 运单签收
   */
  TMS_SGIN((byte) 44, "运单签收"),
  /**
   * 销售订单
   */
  SALE_ORDER((byte) 45, "销售订单"),
  /**
   * 预到货单
   */
  IN_ORDER((byte) 46, "预到货单"),
  /**
   * 预到货单越库
   */
  IN_ORDER_CROSS((byte) 47, "预到货单越库"),
  /**
   * 强制完成
   */
  COMPULSORY_CCOMPLISH((byte) 48, "强制完成"),
  /**
   * ERP更新状态
   */
  ERP_UPDATE_STATUS((byte) 49, "ERP更新状态"),
  /**
   * 门禁确认
   */
  ENTRANCE_GUARD((byte) 50, "PDA门禁确认"),
  /**
   * 订单撤回
   */
  ORDER_CANCEL((byte) 51, "订单撤回"),
  /**
   * 订单操作
   */
  ORDER_OPERATE((byte) 52, "订单操作"),
  /**
   * 质检结果回推
   */
  QUALITY_TESTING_RESULT((byte) 53, "质检结果回推");


  private final Byte id;
  private final String name;


  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutOperationTypeEnum matchingEnum(String name) {
    for (OutOperationTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}
