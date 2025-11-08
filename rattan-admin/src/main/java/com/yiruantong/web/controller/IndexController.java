package com.yiruantong.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.yiruantong.common.core.config.RattanConfig;
import com.yiruantong.common.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author YiRuanTong
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
public class IndexController {

  /**
   * 系统基础配置
   */
  private final RattanConfig ruoyiConfig;

  /**
   * 访问首页，提示语
   */
  @GetMapping("/")
  public String index() {
    return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", ruoyiConfig.getName(), ruoyiConfig.getVersion());
  }
}
