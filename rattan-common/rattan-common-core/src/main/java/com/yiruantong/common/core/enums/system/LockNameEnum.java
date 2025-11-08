package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 锁名称
 */
@Getter
@AllArgsConstructor
public enum LockNameEnum {
  /**
   * 出库单生成每日账单
   */
  RABBIT_RECEIVER_OUT_GENERATING_BILL((byte) 1, "rabbit_receiver_out_generating_bill"),
  /**
   * 每日库存快照自动化作业
   */
  PROCESSORS_STORAGE_PLATE_PROCESSOR((byte) 2, "processors_storage_plate_processor"),
  /**
   * 每日库存封存
   */
  PROCESSORS_STORAGE_PROCESSOR((byte) 3, "processors_storage_processor"),
  /**
   * 入库上架后生成WCS接口数据
   */
  RABBIT_RECEIVER_IN_FINISHED_TO_WCS((byte) 4, "rabbit_receiver_in_finished_to_wcs"),
  /**
   * 入库完成后生成每日账单
   */
  IN_FINISHED_TO_GENERATING_BILL((byte) 5, "IN_FINISHED_TO_GENERATING_BILL"),
  /**
   * 出库完成后生成每日账单
   */
  OUT_FINISHED_GENERATING_BILL((byte) 6, "OUT_FINISHED_GENERATING_BILL"),
  /**
   * 手动生成仓储费
   */
  MANUAL_CREATE_STORAGE_FEE((byte) 7, "MANUAL_CREATE_STORAGE_FEE"),
  /**
   * 出库完成后生成运单
   */
  OUT_FINISHED_TO_TMS_WAY_BILL((byte) 8, "OUT_FINISHED_TO_TMS_WAY_BILL"),
  /**
   * 出库单分拣
   */
  OUT_ORDER_SORTING((byte) 9, "OUT_ORDER_SORTING"),
  /**
   * 出库单自动审核
   */
  OUT_ORDER_AUTO_AUDITING((byte) 10, "OUT_ORDER_AUTO_AUDITING"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static LockNameEnum matchingEnum(String name) {
    for (LockNameEnum i : values()) {
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
  public static LockNameEnum matchingEnumById(int id) {
    for (LockNameEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}
