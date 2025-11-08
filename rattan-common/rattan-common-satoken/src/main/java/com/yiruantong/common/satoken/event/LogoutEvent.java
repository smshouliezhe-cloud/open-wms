package com.yiruantong.common.satoken.event;

import lombok.Data;

import java.io.Serializable;

/**
 * 退出事件
 *
 * @author xietb
 */
@Data
public class LogoutEvent implements Serializable {
  private Long userId;
}
