package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RabbitMQ执行动作
 */
@Getter
@AllArgsConstructor
public enum RabbitmqActionEnum {
  /**
   * 发起审核
   */
  INITIATOR((byte) 0, "发起审核"),
  /**
   * 审核通过
   */
  SUCCESS((byte) 1, "审核通过"),
  /**
   * 拒绝审核
   */
  REJECT((byte) 2, "拒绝审核"),
  /**
   * 退回任务
   */
  RETURN((byte) 3, "退回任务"),
  /**
   * 认领/签收任务
   */
  CLAIM((byte) 4, "认领/签收任务"),
  /**
   * 删除任务
   */
  DELETE((byte) 5, "删除任务"),
  /**
   * 取消认领/签收任务
   */
  UNCLAIM((byte) 6, "取消认领/签收任务"),
  /**
   * 删除任务
   */
  DELEGATE((byte) 7, "委派任务"),
  /**
   * 转办任务
   */
  TRANSFER((byte) 8, "转办任务"),
  /**
   * 取消申请
   */
  STOP((byte) 9, "取消申请"),
  /**
   * 撤回流程
   */
  REVOKE((byte) 10, "撤回流程");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static RabbitmqActionEnum matchingEnum(String name) {
    for (RabbitmqActionEnum i : values()) {
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
  public static RabbitmqActionEnum matchingEnumById(int id) {
    for (RabbitmqActionEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}
