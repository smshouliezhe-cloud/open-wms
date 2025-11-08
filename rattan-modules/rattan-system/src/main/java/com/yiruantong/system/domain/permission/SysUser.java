package com.yiruantong.system.domain.permission;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 用户信息对象 sys_user
 *
 * @author YiRuanTong
 * @date 2024-08-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user", autoResultMap = true)
public class SysUser extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @TableId(value = "user_id")
  private Long userId;

  /**
   * 部门ID
   */
  private Long deptId;

  /**
   * 用户账号
   */
  private String userName;

  /**
   * 用户昵称
   */
  private String nickName;

  /**
   * 用户类型
   */
  private String userType;

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
   * 账号状态
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
   * 部门名称
   */
  private String deptName;

  /**
   * 超级管理员
   */
  private Byte isAdministrator;

  /**
   * 微信
   */
  private String weChat;

  /**
   * 所属仓库
   */
  private String storageName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 父级ID
   */
  private String fullDeptId;

  /**
   * 父级名称
   */
  private String fullDeptName;

  /**
   * 仓管人员（1是 0否）
   */
  private Byte storageUser;

  /**
   * 关联账套
   */
  private Byte relationTenant;

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

  /**
   * 统一社会信用代码
   */
  private String licenseNumber;

  /**
   * 联系人
   */
  private String linker;

  /**
   * 传真
   */
  private String fax;

  /**
   * 省id
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市id
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 地址
   */
  private String address;

  /**
   * 文件上传
   */
  private String fileName;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 单据编码
   */
  private String userCode;


  public SysUser(Long userId) {
    this.userId = userId;
  }

  public boolean isSuperAdmin() {
    return UserConstants.SUPER_ADMIN_ID.equals(this.userId);
  }

}
