package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备类型
 * 针对一套 用户体系
 *
 * @author YiRuanTong
 */
@Getter
@AllArgsConstructor
public enum DeviceTypeEnum {

  /**
   * pc端
   */
  PC("pc"),

  /**
   * app端
   */
  APP("app"),

  /**
   * 小程序端
   */
  XCX("xcx"),

  /**
   * social第三方端
   */
  SOCIAL("social");

  private final String device;
}
