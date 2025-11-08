package com.yiruantong.common.core.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 *
 * @author LionLi
 */
@Getter
@AllArgsConstructor
public enum TenantStatusEnum {
  /**
   * 正常
   */
  OK((byte) 1, "正常"),
  /**
   * 停用
   */
  DISABLE((byte) 0, "停用"),
  /**
   * 删除
   */
  DELETED((byte) 2, "删除");

  private final Byte code;
  private final String info;

}
