package com.yiruantong.common.core.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 设备类型
 * 针对多套 用户体系
 *
 * @author YiRuanTong
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

  /**
   * pc端
   */
  SYS_USER("sys_user"),

  /**
   * app端
   */
  APP_USER("app_user"),

  /**
   * 货主端
   */
  APP_CONSIGNOR("app_consignor"),

  /**
   * 司机端
   */
  APP_DRIVER("app_driver"),

  /**
   * API端
   */
  API_USER("api_user");

  private final String userType;

  public static UserTypeEnum getUserType(String str) {
    for (UserTypeEnum value : values()) {
      if (StringUtils.contains(str, value.getUserType())) {
        return value;
      }
    }
    throw new RuntimeException("'UserTypeEnum' not found By " + str);
  }
}
