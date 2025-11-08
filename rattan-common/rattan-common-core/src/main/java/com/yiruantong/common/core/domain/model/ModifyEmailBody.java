package com.yiruantong.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.common.core.validate.auth.SmsGroup;

/**
 * 修改Email对象
 *
 * @author xtb
 */
@Data
@EqualsAndHashCode()
public class ModifyEmailBody {
  /**
   * 手机号
   */
  @NotBlank(message = "{user.email.not.blank}", groups = {SmsGroup.class})
  private String email;
  /**
   * 滑块二验码
   */
  @NotBlank(message = "{user.captchaVerification.not.blank}", groups = {SmsGroup.class})
  private String captchaVerification;
  /**
   * 短信验证码
   */
  @NotBlank(message = "{user.code.not.blank}", groups = {SmsGroup.class})
  private String code;
}
