package com.yiruantong.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.common.core.validate.auth.SmsGroup;

/**
 * 修改手机号对象
 *
 * @author xtb
 */
@Data
@EqualsAndHashCode()
public class ModifyPhoneNumberBody {
  /**
   * 手机号
   */
  @NotBlank(message = "{user.phoneNumber.not.blank}", groups = {SmsGroup.class})
  private String phoneNumber;
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
