package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.common.core.xss.Xss;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.sensitive.annotation.Sensitive;
import com.yiruantong.common.sensitive.core.SensitiveStrategy;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 个人信息业务处理
 *
 * @author YiRuanTong
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUserProfileBo extends BaseEntity {

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 用户昵称
   */
  @Xss(message = "用户昵称不能包含脚本字符")
  @Size(min = 0, max = 30, message = "用户昵称长度不能超过{max}个字符")
  private String nickName;

  /**
   * 用户邮箱
   */
  @Sensitive(strategy = SensitiveStrategy.EMAIL)
  @Email(message = "邮箱格式不正确")
  @Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
  private String email;

  /**
   * 手机号码
   */
  @Sensitive(strategy = SensitiveStrategy.PHONE)
  private String phoneNumber;

  /**
   * 用户性别（0男 1女 2未知）
   */
  private String sex;

}
