package com.yiruantong.system.domain.tenant.bo;

import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Date;


/**
 * 租户档案业务对象 sys_tenant_profile
 *
 * @author YRT
 * @date 2024-06-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysTenantProfile.class, reverseConvertGenerate = false)
public class SysTenantProfileBo extends BaseEntity {

  /**
   * 档案ID
   */
  @NotNull(message = "档案ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long profileId;

  /**
   * 用户账号
   */
  @NotBlank(message = "用户账号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String userName;

  /**
   * 用户实名
   */
  @NotBlank(message = "用户实名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 用户邮箱
   */
  @NotBlank(message = "用户邮箱不能为空", groups = {AddGroup.class, EditGroup.class})
  private String email;

  /**
   * 手机号码
   */
  @NotBlank(message = "手机号码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String phoneNumber;

  /**
   * 用户性别
   */
  @NotBlank(message = "用户性别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sex;

  /**
   * 头像地址
   */
  @NotNull(message = "头像地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long avatar;

  /**
   * 密码
   */
  @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
  private String password;

  /**
   * 微信
   */
  @NotBlank(message = "微信不能为空", groups = {AddGroup.class, EditGroup.class})
  private String weChat;

  /**
   * QQ号
   */
  @NotBlank(message = "QQ号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String qq;

  /**
   * 帐号状态（1正常 0停用）
   */
  @NotNull(message = "帐号状态（1正常 0停用）不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 最后登录IP
   */
  @NotBlank(message = "最后登录IP不能为空", groups = {AddGroup.class, EditGroup.class})
  private String loginIp;

  /**
   * 最后登录时间
   */
  @NotNull(message = "最后登录时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date loginDate;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * 公司名称
   */
  @NotBlank(message = "公司名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String companyName;

  /**
   * 公司地址
   */
  @NotBlank(message = "公司地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String address;

  /**
   * 公司介绍
   */
  @NotBlank(message = "公司介绍不能为空", groups = {AddGroup.class, EditGroup.class})
  private String intro;

  /**
   * 统一社会信用代码
   */
  @NotBlank(message = "统一社会信用代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String licenseNumber;

  /**
   * 密码强度
   */
  @NotBlank(message = "密码强度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String passwordStrength;

  /**
   * 密码修改时间
   */
  @NotNull(message = "密码修改时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date passwordLastDate;

  /**
   * 手机号已验证
   */
  @NotNull(message = "手机号已验证不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte phoneVerified;

  /**
   * email已验证
   */
  @NotNull(message = "email已验证不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte emailVerified;


}
