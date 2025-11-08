package com.yiruantong.basic.domain.tms.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 承运商管理业务对象
 *
 * @author YRT
 * @date 2024-06-03
 */
@Data
public class BaseCarrierPasswordBo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 司机ID
   */
  @NotNull(message = "司机ID不能为空")
  private Long carrierId;

  /**
   * 新密码
   */
  @NotBlank(message = "新密码不能为空")
  private String newPassword;

}
