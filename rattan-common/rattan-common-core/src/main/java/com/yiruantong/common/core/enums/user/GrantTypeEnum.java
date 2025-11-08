package com.yiruantong.common.core.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 授权类型
 *
 * @author xtb
 */
@Getter
@AllArgsConstructor
public enum GrantTypeEnum {

  /**
   * password
   */
  PASSWORD("password"),
  ;

  private final String type;

  public static GrantTypeEnum getUserType(String str) {
    for (GrantTypeEnum value : values()) {
      if (StringUtils.contains(str, value.getType())) {
        return value;
      }
    }
    throw new RuntimeException("'UserTypeEnum' not found By " + str);
  }
}
