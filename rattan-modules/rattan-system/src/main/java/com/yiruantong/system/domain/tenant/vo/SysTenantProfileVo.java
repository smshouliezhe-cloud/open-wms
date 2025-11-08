package com.yiruantong.system.domain.tenant.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.tenant.SysTenantProfile;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 租户档案视图对象 sys_tenant_profile
 *
 * @author YRT
 * @date 2024-06-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenantProfile.class)
public class SysTenantProfileVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 档案ID
   */
  @ExcelProperty(value = "档案ID")
  private Long profileId;

  /**
   * 用户账号
   */
  @ExcelProperty(value = "用户账号")
  private String userName;

  /**
   * 用户实名
   */
  @ExcelProperty(value = "用户实名")
  private String nickName;

  /**
   * 用户邮箱
   */
  @ExcelProperty(value = "用户邮箱")
  private String email;

  /**
   * 手机号码
   */
  @ExcelProperty(value = "手机号码")
  private String phoneNumber;

  /**
   * 用户性别
   */
  @ExcelProperty(value = "用户性别")
  private String sex;

  /**
   * 头像地址
   */
  @ExcelProperty(value = "头像地址")
  private Long avatar;

  /**
   * 微信
   */
  @ExcelProperty(value = "微信")
  private String weChat;

  /**
   * QQ号
   */
  @ExcelProperty(value = "QQ号")
  private String qq;

  /**
   * 帐号状态（1正常 0停用）
   */
  @ExcelProperty(value = "帐号状态", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=正常,0=停用")
  private Byte enable;

  /**
   * 最后登录IP
   */
  @ExcelProperty(value = "最后登录IP")
  private String loginIp;

  /**
   * 最后登录时间
   */
  @ExcelProperty(value = "最后登录时间")
  private Date loginDate;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 更新时间
   */
  @ExcelProperty(value = "更新时间")
  private Date updateTime;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 公司名称
   */
  @ExcelProperty(value = "公司名称")
  private String companyName;

  /**
   * 公司地址
   */
  @ExcelProperty(value = "公司地址")
  private String address;

  /**
   * 公司介绍
   */
  @ExcelProperty(value = "公司介绍")
  private String intro;

  /**
   * 统一社会信用代码
   */
  @ExcelProperty(value = "统一社会信用代码")
  private String licenseNumber;

  /**
   * 密码强度
   */
  @ExcelProperty(value = "密码强度")
  private String passwordStrength;

  /**
   * 密码修改时间
   */
  @ExcelProperty(value = "密码修改时间")
  private Date passwordLastDate;

  /**
   * 手机号已验证
   */
  @ExcelProperty(value = "手机号已验证")
  private Byte phoneVerified;

  /**
   * email已验证
   */
  @ExcelProperty(value = "email已验证")
  private Byte emailVerified;


}
