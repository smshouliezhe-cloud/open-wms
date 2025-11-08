package com.yiruantong.system.domain.tenant;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 租户管理对象 sys_tenant
 *
 * @author YiRuanTong
 * @date 2024-12-11
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_tenant", autoResultMap = true)
public class SysTenant extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId(value = "id")
  private Long id;

  /**
   * 联系人
   */
  private String contactUserName;

  /**
   * 联系电话
   */
  private String contactPhone;

  /**
   * 企业名称
   */
  private String companyName;

  /**
   * 统一社会信用代码
   */
  private String licenseNumber;

  /**
   * 地址
   */
  private String address;

  /**
   * 企业简介
   */
  private String intro;

  /**
   * 域名
   */
  private String domain;

  /**
   * 备注
   */
  private String remark;

  /**
   * 租户套餐编号
   */
  private Long packageId;

  /**
   * 过期时间
   */
  private Date expireTime;

  /**
   * 用户数量（-1不限制）
   */
  private Long accountCount;

  /**
   * 租户状态
   */
  private Byte status;

  /**
   * 删除标志
   */
  @TableLogic
  private Byte delFlag;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 套餐名称
   */
  private String packageName;

  /**
   * 登录模板
   */
  private String loginTemplate;

  /**
   * 主界面模板
   */
  private String mainTemplate;

  /**
   * 系统全称
   */
  private String sysFullName;

  /**
   * 系统简称
   */
  private String sysShortName;

  /**
   * 短logo
   */
  private String logoShort;

  /**
   * 长logo
   */
  private String logoLong;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;


}
