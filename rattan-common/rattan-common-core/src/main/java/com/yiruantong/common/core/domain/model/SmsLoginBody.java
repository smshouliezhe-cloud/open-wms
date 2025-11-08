package com.yiruantong.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 短信登录对象
 *
 * @author YiRuanTong
 */

@Data
public class SmsLoginBody {

  /**
   * 租户ID
   */
  @NotBlank(message = "{tenant.number.not.blank}")
  private String tenantId;

  /**
   * 手机号
   */
  @NotBlank(message = "{user.phoneNumber.not.blank}")
  private String phoneNumber;

  /**
   * 短信code
   */
  @NotBlank(message = "{sms.code.not.blank}")
  private String smsCode;

}
