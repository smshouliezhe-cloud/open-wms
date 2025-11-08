package com.yiruantong.web.domain.vo;

import lombok.Data;

/**
 * 验证码信息
 *
 * @author YiRuanTong
 */
@Data
public class CaptchaVo {

  /**
   * 是否开启验证码
   */
  private Boolean captchaEnabled = true;

  private String uuid;

  /**
   * 需要校验验证码
   */
  private Boolean isCheck = true;

  /**
   * 验证码图片
   */
  private String img;

}
