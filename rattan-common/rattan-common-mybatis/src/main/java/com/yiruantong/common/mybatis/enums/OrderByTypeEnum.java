package com.yiruantong.common.mybatis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 排序类型
 *
 * @author 谢天保
 */
@Getter
@AllArgsConstructor
public enum OrderByTypeEnum {

  /** ASC */
  ASC("ASC"),

  /** Oracle */
  DESC("DESC");

  private final String type;

  public static OrderByTypeEnum find(String databaseProductName) {
    if (StringUtils.isBlank(databaseProductName)) {
      return null;
    }
    for (OrderByTypeEnum type : values()) {
      if (type.getType().equals(databaseProductName)) {
        return type;
      }
    }
    return null;
  }
}
