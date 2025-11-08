package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 预到货单状态枚举
 */
@Getter
@AllArgsConstructor
public enum InOrderStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 审核成功
   */
  SUCCESS((byte) 2, "审核成功"),
  /**
   * 审核失败
   */
  FAILED((byte) 3, "审核失败"),
  /**
   * 在途中
   */
  IN_TRANSIT((byte) 4, "在途中"),
  /**
   * 等待入库
   */
  WAITING((byte) 5, "等待入库"),
  /**
   * 入库中
   */
  IN_STORAGE((byte) 6, "入库中"),
  /**
   * 部分交货
   */
  PARTIAL_FINISHED((byte) 7, "部分交货"),
  /**
   * 完全交货
   */
  FINISHED((byte) 8, "完全交货"),
  /**
   * 终止执行
   */
  STOPED((byte) 9, "终止执行"),
  /**
   * 超期订单
   */
  OVERDUE((byte) 10, "超期订单"),
  /**
   * 部分退货
   */
  PARTIAL_RETURNED((byte) 11, "部分退货"),
  /**
   * 完全退货
   */
  RETURNED((byte) 12, "完全退货"),
  /**
   * 强制完成
   */
  FORCE_FINISHED((byte) 13, "强制完成"),
  /**
   * 已合并
   */
  MERGED((byte) 14, "已合并"),
  /**
   * 待审核
   */
  PENDING((byte) 15, "待审核"),
  /**
   * 终止
   */
  STOP((byte) 16, "终止"),
  /**
   * 用于轨迹  上架完成
   */
  HISTORY_FINISHED((byte) 17, "上架完成"),
  /**
   * 用于轨迹  待上架
   */
  HISTORY_WAITING((byte) 18, "待上架"),
  /**
   * 部分上架
   */
  HISTORY_PARTIAL_FINISHED((byte) 19, "部分上架"),
  /**
   * 复制
   */
  COPY((byte) 20, "复制"),
  /**
   * 已质检
   */
  ALREADY_CHECKING((byte) 21, "已质检"),
  /**
   * 零库存出库完成
   */
  TO_OUT_ORDER_FINISHED((byte) 22, "零库存出库完成"),
  /**
   * 过磅确认
   */
  WEIGHT_CONFIRM((byte) 23, "过磅确认"),
  /**
   * 未质检
   */
  UN_ALREADY_CHECKING((byte) 24, "未质检"),
  /**
   * 审核通过
   */
  APPROVED((byte) 25, "审核通过"),
  /**
   * 送货完成
   */
  DELIVERY_COMPLETED((byte) 26, "送货完成"),
  /**
   * 部分质检
   */
  PARTIALLY_CHECKED((byte) 27, "部分质检"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static InOrderStatusEnum matchingEnum(String name) {
    for (InOrderStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}
