package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库单类型枚举
 */
@Getter
@AllArgsConstructor
public enum OutOrderStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 0, "新建"),
  /**
   * 待审核
   */
  AUDIT_WAITING((byte) 1, "待审核"),
  /**
   * 审核成功
   */
  AUDIT_SUCCESS((byte) 2, "审核成功"),
  /**
   * 审核失败
   */
  AUDIT_FAILED((byte) 3, "审核失败"),
  /**
   * 波次完成
   */
  WAVE_FINISHED((byte) 4, "波次完成"),
  /**
   * 拣货中
   */
  PICKING((byte) 5, "拣货中"),
  /**
   * 部分拣货
   */
  PICKING_PARTIAL((byte) 6, "部分拣货"),
  /**
   * 拣货完成
   */
  PICKED((byte) 6, "拣货完成"),
  /**
   * 等待配货
   */
  WAITING_MATCH((byte) 7, "等待配货"),
  /**
   * 配货中
   */
  MATCHING((byte) 8, "配货中"),
  /**
   * 配货完成
   */
  MATCHED((byte) 9, "配货完成"),
  /**
   * 打包中
   */
  PACKAGING((byte) 10, "打包中"),
  /**
   * 部分打包
   */
  PACKAGE_PARTIAL((byte) 11, "部分打包"),
  /**
   * 打包完成
   */
  PACKAGE_FINISHED((byte) 12, "打包完成"),
  /**
   * 部分发运
   */
  SHIPMENT_PARTIAL((byte) 13, "部分发运"),
  /**
   * 发运完成
   */
  SHIPMENT_FINISHED((byte) 14, "发运完成"),
  /**
   * 部分退货
   */
  RETURN_PARTIAL((byte) 15, "部分退货"),
  /**
   * 完全退货
   */
  RETURN_FINISHED((byte) 16, "完全退货"),
  /**
   * 终止
   */
  STOPED((byte) 17, "终止"),
  /**
   * 已妥投
   */
  DELIVERED((byte) 18, "已妥投"),
  /**
   * 拒收
   */
  REJECTION((byte) 19, "拒收"),
  /**
   * 已合并
   */
  MERGED((byte) 20, "已合并"),
  /**
   * 撤单
   */
  CANCEL_ORDER((byte) 21, "撤单"),
  /**
   * 缺货超期
   */
  LACK_OVERDUE((byte) 22, "缺货超期"),
  /**
   * 已完成
   */
  FINISHED((byte) 23, "已完成"),
  /**
   * 冻结中
   */
  frozen((byte) 24, "冻结中"),
  /**
   * 退件
   */
  RETURNED((byte) 25, "退件"),
  /**
   * 已关闭
   */
  CLOSED((byte) 26, "已关闭"),
  /**
   * 已签收
   */
  SIGNED((byte) 27, "已签收"),
  /**
   * 请付款
   */
  PAYMENT((byte) 28, "请付款"),
  /**
   * 部分配货
   */
  MATCHING_PARTIAL((byte) 29, "部分配货"),
  /**
   * 已合并
   */
  FULFILLMENT_INCORPORATION((byte) 30, "已合并"),
  /**
   * 强制完成
   */
  FORCE_FINISH((byte) 31, "强制完成"),
  /**
   * 已分配
   */
  ASSIGNED((byte) 32, "已分配"),
  /**
   * 缺货中
   */
  LACK((byte) 33, "缺货中"),
  /**
   * 部分分配
   */
  PARTIAL_ASSIGNED((byte) 34, "部分分配"),

  /**
   * 用户取消
   */
  CANCEL((byte) 35, "用户取消"),
  /**
   * 部分拒收
   */
  PART_REJECTION((byte) 36, "部分拒收"),
  /**
   * 现场确认
   */
  SCENE_CONFIRM((byte) 37, "现场确认"),
  /**
   * 门禁确认
   */
  ENTRANCE_GUARD((byte) 38, "门禁确认"),
  /**
   * 撤回申请
   */
  CANCEL_APPLY((byte) 39, "撤回申请"),
  /**
   * 未出库完成
   */
  NOT_OUT_ACCOMPLISH((byte) 40, "未出库完成");


  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutOrderStatusEnum matchingEnum(String name) {
    for (OutOrderStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}
