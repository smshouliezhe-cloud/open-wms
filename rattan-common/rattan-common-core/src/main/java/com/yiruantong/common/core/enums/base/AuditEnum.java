package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditEnum {
  /**
   * 待审核
   */
  AUDIT((byte) 0, "待审核"),
  /**
   * 审核失败
   */
  AUDIT_FAILED((byte) 1, "审核失败"),
  /**
   * 审核成功
   */
  AUDITED_SUCCESS((byte) 2, "审核成功"),
  /**
   * 等待调整
   */
  WAITING_AJDUST((byte) 3, "等待调整"),
  /**
   * 调整完毕
   */
  AUDITED((byte) 4, "调整完毕"),
  /**
   * 可审核
   */
  AUDITING((byte) 5, "可审核"),
  /**
   * 终止
   */
  STOPED((byte) 6, "终止"),
  /**
   * 新建
   */
  NEWED((byte) 7, "新建");

  private final Byte id;
  private final String name;
}
