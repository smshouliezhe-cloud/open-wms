package com.yiruantong.system.domain.tenant;

import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 租户套餐对象 sys_tenant_package
 *
 * @author YiRuanTong
 * @date 2024-05-28
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_tenant_package", autoResultMap = true)
public class SysTenantPackage extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 租户套餐id
   */
  @TableId(value = "package_id")
  private Long packageId;

  /**
   * 套餐名称
   */
  private String packageName;

  /**
   * 关联菜单id
   */
  private String menuIds;

  /**
   * 备注
   */
  private String remark;

  /**
   * 关联显示
   */
  private Byte menuCheckStrictly;

  /**
   * 套餐状态
   */
  private Byte status;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

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
   * 套餐切图
   */
  private String packageImage;

  /**
   * 套餐标签
   */
  private String tags;

  /**
   * 热门
   */
  private Byte hot;

  /**
   * 官方
   */
  private Byte official;

  /**
   * 应用次数
   */
  private Long frequency;

  /**
   * 套餐类别
   */
  private String packageType;


}
