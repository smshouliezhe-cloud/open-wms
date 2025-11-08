package com.yiruantong.system.domain.tenant;

import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 租户档案对象 sys_tenant_profile
 *
 * @author YRT
 * @date 2024-06-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_tenant_profile", autoResultMap = true)
public class SysTenantProfile extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 档案ID
   */
  @TableId(value = "profile_id")
  private Long profileId;

  /**
   * 用户账号
   */
  private String userName;

  /**
   * 用户实名
   */
  private String nickName;

  /**
   * 用户邮箱
   */
  private String email;

  /**
   * 手机号码
   */
  private String phoneNumber;

  /**
   * 用户性别
   */
  private String sex;

  /**
   * 头像地址
   */
  private Long avatar;

  /**
   * 密码
   */
  @TableField(
    insertStrategy = FieldStrategy.NOT_EMPTY,
    updateStrategy = FieldStrategy.NOT_EMPTY,
    whereStrategy = FieldStrategy.NOT_EMPTY
  )
  private String password;

  /**
   * 微信
   */
  private String weChat;

  /**
   * QQ号
   */
  private String qq;

  /**
   * 帐号状态（1正常 0停用）
   */
  private Byte enable;

  /**
   * 最后登录IP
   */
  private String loginIp;

  /**
   * 最后登录时间
   */
  private Date loginDate;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 公司名称
   */
  private String companyName;

  /**
   * 公司地址
   */
  private String address;

  /**
   * 公司介绍
   */
  private String intro;

  /**
   * 统一社会信用代码
   */
  private String licenseNumber;

  /**
   * 密码强度
   */
  private String passwordStrength;

  /**
   * 密码修改时间
   */
  private Date passwordLastDate;

  /**
   * 手机号已验证
   */
  private Byte phoneVerified;

  /**
   * email已验证
   */
  private Byte emailVerified;


}
