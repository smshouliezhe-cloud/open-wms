package com.yiruantong.system.domain.permission.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改其他用户密码bo
 */
@Data
public class SysUserPasswordOtherBo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @NotNull(message = "用户ID不能为空")
  private Long userId;

  /**
   * 新密码
   */
  @NotBlank(message = "新密码不能为空")
  private String newPassword;

  /**
   * 密码强度值
   */
  private double passwordStrength;
}
