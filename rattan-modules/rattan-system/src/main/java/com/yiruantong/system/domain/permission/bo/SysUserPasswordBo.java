package com.yiruantong.system.domain.permission.bo;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户密码修改bo
 */
@Data
public class SysUserPasswordBo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 旧密码
   */
  @NotBlank(message = "旧密码不能为空")
  private String oldPassword;

  /**
   * 新密码
   */
  @NotBlank(message = "新密码不能为空")
  private String newPassword;

  /**
   * 密码强度值
   */
  @DecimalMin(value = "50", message = "密码强度值必须>=0")
  @DecimalMax(value = "100", message = "密码强度值必须<=100")
  private double passwordStrength;
}
