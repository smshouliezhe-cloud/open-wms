package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RabbitMQ类型
 */
@Getter
@AllArgsConstructor
public enum RabbitmqTypeEnum {
  /**
   * 调拨单转到出库单，出库单出库后续操作
   * 1、更新调拨申请单的状态、状态轨迹，以及明细出库数量；
   * 2、生成预到货单；
   * 3、生成在途虚拟仓的预到货单，并自动入库完成（库存状态为“调拨在途”）
   */
  OUT_FINISHED_TO_ALLOCATE((byte) 1, "调拨单转到出库单，出库单出库后续操作"),
  /**
   * 入库完成更新调拨申请单的状态
   */
  IN_FINISHED_TO_ALLOCATE_APPLY((byte) 2, "入库完成更新调拨申请单的状态"),
  /**
   * 在途虚拟出库分拣
   */
  VIRTUAL_ORDER_SORTING((byte) 3, "在途虚拟出库分拣"),

  /**
   * 入库完成后来源类型是 退货转入 则回更退货单数据
   */
  IN_FINISHED_UPDATE_OUT_RETURN((byte) 4, "入库完成回更退货单数据"),
  /**
   * 出库完成后回更出库计划单
   */
  OUT_FINISHED_TO_PLAN((byte) 5, "出库完成后回更出库计划单"),
  /**
   * 入库完成回更入库计划单
   */
  IN_FINISHED_TO_PLAN((byte) 6, "入库完成回更入库计划单"),
  /**
   * 上架完成后，执行出库单自动分拣
   */
  SHELVE_FINISHED_TO_AUTO_SORTING((byte) 7, "上架完成后，执行出库单自动分拣"),
  /**
   * 入库上架完成后，执行出库单自动分拣
   */
  IN_FINISHED_TO_AUTO_SORTING((byte) 7, "入库上架完成后，执行出库单自动分拣"),
  /**
   * 出库完成后更新到货退货单
   */
  OUT_FINISHED_TO_RETURN_ORDER((byte) 8, "出库完成后更新到货退货单"),

  /**
   * 出库完成之后生成容器借出单
   */
  OUT_FINISHED_TO_BASE_PLATE_OUT((byte) 9, "出库完成之后生成容器借出单"),

  /**
   * 出库完成后生成运单
   */
  OUT_FINISHED_TO_TMS_WAY_BILL((byte) 10, "出库完成后生成运单"),


  /**
   * 出库单出库后，根据运单自动生成运输单
   */
  TMS_WAY_BILL_TO_TMS_TRANSPORT((byte) 11, "出库后，根据运单自动生成运输单"),

  /**
   * 出库单分拣
   */
  OUT_ORDER_SORTING((byte) 12, "出库单分拣"),

  /**
   * 付款单保存更新运输单
   */
  PAYOUT_SAVE_TO_TMS_TRANSPORT((byte) 13, "付款单保存更新运输单"),

  /**
   * 入库完成后生成每日账单
   */
  IN_FINISHED_TO_GENERATING_BILL((byte) 14, "入库单生成每日账单"),

  /**
   * 出库完成后生成每日账单
   */
  OUT_FINISHED_GENERATING_BILL((byte) 15, "出库完成后生成每日账单"),

  /**
   * 付款单审核更新运输单
   */
  PAYOUT_AUDIT_TO_TMS_TRANSPORT((byte) 16, "付款单审核更新运输单"),
  /**
   * 确认开票后，回更销售单
   */
  CONFIRM_INVOICING_TO_ERP_SALE_ORDER((byte) 17, "确认开票后，回更销售单"),
  /**
   * 入库后 报废出库的商品属性 生成出库单 并自动出库
   */
  IN_FINISHED_INVALIDATE_OUT((byte) 18, "入库后报废出库的商品属性生成出库单并自动出库"),
  /**
   * 卸车入库更改运单
   */
  UNLOADING_UPDATE_WAY_BILL((byte) 19, "卸车入库更改运单"),
  /**
   * 出库完成回更领料单
   */
  OUT_FINISHED_TO_PICKING((byte) 20, "出库完成回更领料单"),
  /**
   * 业务审核流处理
   */
  WORKFLOW((byte) 21, "业务审核流处理"),

  /**
   * 入库完成后来源类型是 退料单 则回更退料单数据
   */
  IN_FINISHED_UPDATE_MES_RETURN((byte) 22, "入库完成回更退料单数据"),
  /**
   * 缺货单转补货单
   */
  OUT_ORDER_TO_REPLENISHMENT((byte) 23, "缺货单转补货单"),
  /**
   * 入库完成回更成品入库单
   */
  IN_FINISHED_TO_COMPLETION((byte) 24, "入库完成回更成品入库单"),
  /**
   * 收货上架后生成WCS任务单
   */
  IN_FINISHED_TO_WCS((byte) 25, "收货上架后生成WCS任务单"),
  /**
   * 扫描上架后生成WCS任务单
   */
  IN_SHELVED_TO_WCS((byte) 26, "扫描上架后生成WCS任务单"),
  /**
   * 打包出库后自动生成WCS任务单
   */
  OUT_FINISHED_TO_WCS((byte) 27, "打包出库后自动生成WCS任务单"),

  /**
   * 手动生成仓储费
   */
  MANUAL_CREATE_STORAGE_FEE((byte) 28, "手动生成仓储费"),
  /**
   * ERP销售订单生成出库单
   */
  SALE_ORDER_TO_OUT_ORDER((byte) 29, "ERP销售订单生成出库单"),
  /**
   * ERP采购单订单生成销售单
   */
  PURCHASE_ORDER_TO_SALE_ORDER((byte) 30, "ERP采购单订单生成销售单"),
  /**
   * 预到货单生成出库单
   */
  IN_ORDER_TO_OUT_ORDER((byte) 31, "预到货单生成出库单"),
  /**
   * 越库预到货单生成出库单
   */
  IN_ORDER_TO_OUT_ORDER_CROSS((byte) 32, "越库预到货单生成出库单"),
  /**
   * 出库单自动审核
   */
  OUT_ORDER_AUTO_AUDITING((byte) 33, "出库单自动审核"),
  /**
   * 出库单自动出库
   */
  OUT_ORDER_AUTO_OUT((byte) 34, "出库单自动出库"),
  /**
   * 预到货单生成出库单完成
   */
  IN_ORDER_TO_OUT_ORDER_FINISHED((byte) 35, "预到货单生成出库单完成"),
  /**
   * 预到货单越库入库
   */
  IN_ORDER_CROSS_DOCKING_IN((byte) 36, "预到货单越库入库"),
  /**
   * 出库单出库之后回更运单 （湘钢）
   */
  OUT_ORDER_UPDATE_WAY_BILL((byte) 37, "出库单出库之后回更运单"),
  /**
   * 付款对账单生成修改上游单据状态 （湘钢）
   */
  ORDER_TO_FINANCE_PAYABLE_UPDATE((byte) 38, "付款对账单生成修改上游单据状态"),
  /**
   * 出库单分拣成功后创建tms单据 （湘钢）
   */
  ORDER_SORTING_TO_TMS((byte) 39, "出库单分拣成功后创建tms单据 （湘钢）"),
  /**
   * 出库单保存规则后给出库单赋值 （湘钢）
   */
  SORTING_SAVE_ORDER_SET_RELEASE((byte) 40, "出库单保存规则后给出库单赋值 （湘钢）"),
  /**
   * 质检结果返回后需要将标记一键出库的单据进行一键出库 （湘钢）
   */
  QUALITY_TESTING_RESULT_OUT_ORDER_QUICK_OUT((byte) 41, "质检结果返回后需要将标记一键出库的单据进行一键出库 （湘钢）"),
  /**
   * 预到货明细新增货代订舱商品明细
   */
  FREIGHT_ORDER_BOOKCABIN_PRODUCT((byte) 42, "预到货明细新增货代订舱商品明细"),
  ;
  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static RabbitmqTypeEnum matchingEnum(String name) {
    for (RabbitmqTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getName(), name)) {
        return i;
      }
    }
    return null;
  }

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param id 值
   * @return 枚举
   */
  public static RabbitmqTypeEnum matchingEnumById(int id) {
    for (RabbitmqTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}
