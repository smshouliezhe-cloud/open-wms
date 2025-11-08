package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysUser;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.core.xss.Xss;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户信息业务对象 sys_user
 *
 * @author YiRuanTong
 * @date 2024-08-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysUser.class, reverseConvertGenerate = false)
public class SysUserBo extends BaseEntity {

  /**
   * 用户ID
   */
  @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 部门ID
   */
  @NotNull(message = "部门ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 用户账号
   */
  @NotBlank(message = "用户账号不能为空", groups = {AddGroup.class, EditGroup.class})
  @Xss(message = "用户账号不能包含脚本字符")
  @Size(min = 0, max = 30, message = "用户账号长度不能超过{max}个字符")
  private String userName;

  /**
   * 用户昵称
   */
  @NotBlank(message = "用户昵称不能为空", groups = {AddGroup.class, EditGroup.class})
  @Xss(message = "用户昵称不能包含脚本字符")
  @Size(min = 0, max = 30, message = "用户昵称长度不能超过{max}个字符")
  private String nickName;

  /**
   * 用户类型
   */
  @NotBlank(message = "用户类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String userType;

  /**
   * 用户邮箱
   */
  @NotBlank(message = "用户邮箱不能为空", groups = {AddGroup.class, EditGroup.class})
  @Email(message = "邮箱格式不正确")
  @Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
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
  @NotBlank(message = "密码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String password;

  /**
   * 账号状态
   */
  @NotNull(message = "账号状态不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 部门名称
   */
  @NotBlank(message = "部门名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 超级管理员
   */
  @NotNull(message = "超级管理员不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isAdministrator;

  /**
   * 微信
   */
  @NotBlank(message = "微信不能为空", groups = {AddGroup.class, EditGroup.class})
  private String weChat;

  /**
   * 所属仓库
   */
  @NotBlank(message = "所属仓库不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 父级ID
   */
  @NotBlank(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fullDeptId;

  /**
   * 父级名称
   */
  @NotBlank(message = "父级名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fullDeptName;

  /**
   * 仓管人员（1是 0否）
   */
  @NotNull(message = "仓管人员（1是 0否）不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte storageUser;

  /**
   * 关联账套
   */
  @NotNull(message = "关联账套不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte relationTenant;

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

  /**
   * 角色组
   */
  @Size(min = 1, message = "用户角色不能为空")
  private Long[] roleIds;

  /**
   * 岗位组
   */
  private Long[] postIds;

  /**
   * 数据权限 当前角色ID
   */
  private Long roleId;
  /**
   * 所有部门子集ID
   */
  private List allChildrenId;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;
  /**
   * 统一社会信用代码
   */
  @NotBlank(message = "统一社会信用代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String licenseNumber;
  /**
   * 联系人
   */
  @NotBlank(message = "联系人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String linker;
  /**
   * 传真
   */
  @NotBlank(message = "传真不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fax;
  /**
   * 省id
   */
  @NotNull(message = "省id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long provinceId;
  /**
   * 省
   */
  @NotBlank(message = "省不能为空", groups = {AddGroup.class, EditGroup.class})
  private String provinceName;
  /**
   * 市id
   */
  @NotNull(message = "市id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long cityId;
  /**
   * 市
   */
  @NotBlank(message = "市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;
  /**
   * 地址
   */
  @NotBlank(message = "地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String address;
  /**
   * 文件上传
   */
  @NotBlank(message = "文件上传不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fileName;

  /**
   * 单据编码
   */
  @NotBlank(message = "单据编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String userCode;

  public SysUserBo(Long userId) {
    this.userId = userId;
  }

  public boolean isSuperAdmin() {
    return UserConstants.SUPER_ADMIN_ID.equals(this.userId);
  }


}
