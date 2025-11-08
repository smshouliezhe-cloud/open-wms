package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yiruantong.system.domain.permission.SysUser;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import com.yiruantong.common.sensitive.annotation.Sensitive;
import com.yiruantong.common.sensitive.core.SensitiveStrategy;
import com.yiruantong.common.translation.annotation.Translation;
import com.yiruantong.common.translation.constant.TransConstant;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户信息视图对象 sys_user
 *
 * @author YiRuanTong
 * @date 2024-08-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUser.class)
public class SysUserVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @ExcelProperty(value = "用户ID")
  private Long userId;

  /**
   * 租户编号
   */
  @ExcelProperty(value = "租户编号")
  private String tenantId;

  /**
   * 部门ID
   */
  @ExcelProperty(value = "部门ID")
  private Long deptId;

  /**
   * 用户账号
   */
  @ExcelProperty(value = "用户账号")
  private String userName;

  /**
   * 用户昵称
   */
  @ExcelProperty(value = "用户昵称")
  private String nickName;

  /**
   * 用户类型
   */
  @ExcelProperty(value = "用户类型")
  private String userType;

  /**
   * 用户邮箱
   */
  @ExcelProperty(value = "用户邮箱")
  @Sensitive(strategy = SensitiveStrategy.EMAIL)
  private String email;

  /**
   * 手机号码
   */
  @ExcelProperty(value = "手机号码")
  @Sensitive(strategy = SensitiveStrategy.PHONE)
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
  @Translation(type = TransConstant.OSS_ID_TO_URL)
  private Long avatar;

  /**
   * 密码
   */
  @ExcelProperty(value = "密码")
  @JsonIgnore
  @JsonProperty
  private String password;

  /**
   * 账号状态
   */
  @ExcelProperty(value = "账号状态")
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
   * 部门名称
   */
  @ExcelProperty(value = "部门名称")
  private String deptName;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 更新人
   */
  @ExcelProperty(value = "更新人")
  private String updateByName;

  /**
   * 超级管理员
   */
  @ExcelProperty(value = "超级管理员")
  private Byte isAdministrator;

  /**
   * 微信
   */
  @ExcelProperty(value = "微信")
  private String weChat;

  /**
   * 所属仓库
   */
  @ExcelProperty(value = "所属仓库")
  private String storageName;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private String fullDeptId;

  /**
   * 父级名称
   */
  @ExcelProperty(value = "父级名称")
  private String fullDeptName;

  /**
   * 仓管人员（1是 0否）
   */
  @ExcelProperty(value = "仓管人员", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=是,0=否")
  private Byte storageUser;

  /**
   * 关联账套
   */
  @ExcelProperty(value = "关联账套")
  private Byte relationTenant;

  /**
   * 角色名
   */
  @ExcelProperty(value = "角色名")
  private String roleName;

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

  /**
   * 部门对象
   */
  private SysDeptVo dept;

  /**
   * 角色对象
   */
  private List roles;

  /**
   * 角色组
   */
  private Long[] roleIds;

  /**
   * 角色名称，逗号分隔
   */
  private String roleNames;

  /**
   * 岗位对象
   */
  private List posts;

  /**
   * 岗位组
   */
  private Long[] postIds;

  /**
   * 数据权限 当前角色ID
   */
  private Long roleId;
  /**
   * 岗位名称，逗号分隔
   */
  private String postNames;

  /**
   * 套餐ID
   */
  private Long packageId;

  /**
   * 统一社会信用代码
   */
  @ExcelProperty(value = "统一社会信用代码")
  private String licenseNumber;

  /**
   * 联系人
   */
  @ExcelProperty(value = "联系人")
  private String linker;

  /**
   * 传真
   */
  @ExcelProperty(value = "传真")
  private String fax;

  /**
   * 省id
   */
  @ExcelProperty(value = "省id")
  private Long provinceId;

  /**
   * 省
   */
  @ExcelProperty(value = "省")
  private String provinceName;

  /**
   * 市id
   */
  @ExcelProperty(value = "市id")
  private Long cityId;

  /**
   * 市
   */
  @ExcelProperty(value = "市")
  private String cityName;

  /**
   * 地址
   */
  @ExcelProperty(value = "地址")
  private String address;

  /**
   * 文件上传
   */
  @ExcelProperty(value = "文件上传")
  private String fileName;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;


  /**
   * 单据编码
   */
  @ExcelProperty(value = "单据编码")
  private String userCode;

}
