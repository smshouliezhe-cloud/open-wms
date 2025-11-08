package com.yiruantong.common.core.domain.model;

import lombok.Data;

/**
 * 邮件登录对象
 *
 * @author YiRuanTong
 */

@Data
public class KeyValue {

  /**
   * ID名称
   */
  private String idName;

  /**
   * ID值
   */
  private String idValue;

  /**
   * 键名称
   */
  private String keyName;

  /**
   * 键值
   */
  private String keyValue;

  /**
   * 编号名称
   */
  private String codeName;

  /**
   * 编号值
   */
  private String codeValue;

}
