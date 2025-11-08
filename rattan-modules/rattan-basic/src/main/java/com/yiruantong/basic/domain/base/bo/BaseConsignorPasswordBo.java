package com.yiruantong.basic.domain.base.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改其他用户密码bo
 */
@Data
public class BaseConsignorPasswordBo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @NotNull(message = "货主ID不能为空")
  private Long consignorId;

  /**
   * 新密码
   */
  @NotBlank(message = "新密码不能为空")
  private String newPassword;
}
