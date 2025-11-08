package com.yiruantong.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author YiRuanTong
 */

@Data
@Component
@ConfigurationProperties(prefix = "rattan")
public class RattanConfig {

  /**
   * 项目名称
   */
  private String name;

  /**
   * 版本
   */
  private String version;

  /**
   * 版权年份
   */
  private String copyrightYear;

  /**
   * 开启注册
   */
  private boolean openReg;

  /**
   * 开启注册
   */
  private boolean openMer;

  /**
   * 默认的角色名字
   */
  private String regDefaultRoleName;

  /**
   * 企业微信跳转地址
   */
  private String qyWechatUrl;
}
