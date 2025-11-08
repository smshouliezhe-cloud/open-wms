package com.yiruantong.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.common.core.validate.auth.SmsGroup;

/**
 * 用户注册对象
 *
 * @author xtb
 */
@Data
@EqualsAndHashCode()
public class RegisterBody {
  /**
   * 手机号
   */
  @NotBlank(message = "{user.phoneNumber.not.blank}", groups = {SmsGroup.class})
  private String phoneNumber;
  /**
   * 公司名称
   */
  @NotBlank(message = "{user.companyName.not.blank}", groups = {SmsGroup.class})
  private String companyName;
  /**
   * 昵称
   */
  @NotBlank(message = "{user.nickName.not.blank}", groups = {SmsGroup.class})
  private String nickName;
  /**
   * 验证码
   */
  @NotBlank(message = "{user.code.not.blank}", groups = {SmsGroup.class})
  private String code;
}
