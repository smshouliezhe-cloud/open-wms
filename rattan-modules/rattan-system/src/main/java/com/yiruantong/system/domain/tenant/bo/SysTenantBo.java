package com.yiruantong.system.domain.tenant.bo;

import com.yiruantong.system.domain.tenant.SysTenant;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.util.Date;


/**
 * 租户管理业务对象 sys_tenant
 *
 * @author YiRuanTong
 * @date 2024-12-11
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysTenant.class, reverseConvertGenerate = false)
public class SysTenantBo extends BaseEntity {

  /**
   * id
   */
  @NotNull(message = "id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long id;

  /**
   * 联系人
   */
  @NotBlank(message = "联系人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contactUserName;

  /**
   * 联系电话
   */
  @NotBlank(message = "联系电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contactPhone;

  /**
   * 企业名称
   */
  @NotBlank(message = "企业名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String companyName;

  /**
   * 统一社会信用代码
   */
  @NotBlank(message = "统一社会信用代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String licenseNumber;

  /**
   * 地址
   */
  @NotBlank(message = "地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String address;

  /**
   * 企业简介
   */
  @NotBlank(message = "企业简介不能为空", groups = {AddGroup.class, EditGroup.class})
  private String intro;

  /**
   * 域名
   */
  @NotBlank(message = "域名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String domain;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 租户套餐编号
   */
  @NotNull(message = "租户套餐编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long packageId;

  /**
   * 过期时间
   */
  @NotNull(message = "过期时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date expireTime;

  /**
   * 用户数量（-1不限制）
   */
  @NotNull(message = "用户数量（-1不限制）不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long accountCount;

  /**
   * 租户状态
   */
  @NotNull(message = "租户状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte status;

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
   * 套餐名称
   */
  @NotBlank(message = "套餐名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packageName;

  /**
   * 登录模板
   */
  @NotBlank(message = "登录模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String loginTemplate;

  /**
   * 主界面模板
   */
  @NotBlank(message = "主界面模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mainTemplate;

  /**
   * 系统全称
   */
  @NotBlank(message = "系统全称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sysFullName;

  /**
   * 系统简称
   */
  @NotBlank(message = "系统简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sysShortName;

  /**
   * 短logo
   */
  @NotBlank(message = "短logo不能为空", groups = {AddGroup.class, EditGroup.class})
  private String logoShort;

  /**
   * 长logo
   */
  @NotBlank(message = "长logo不能为空", groups = {AddGroup.class, EditGroup.class})
  private String logoLong;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;


  /**
   * 账套ID
   */
  @NotBlank(message = "账套ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tenantId;
  /**
   * 用户名
   */
  @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String username;

  /**
   * 密码
   */
  @NotBlank(message = "密码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String password;

}
