package com.yiruantong.system.domain.tenant.bo;

import com.yiruantong.system.domain.tenant.SysTenantPackage;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 租户套餐业务对象 sys_tenant_package
 *
 * @author YiRuanTong
 * @date 2024-05-28
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysTenantPackage.class, reverseConvertGenerate = false)
public class SysTenantPackageBo extends BaseEntity {

  /**
   * 租户套餐id
   */
  @NotNull(message = "租户套餐id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long packageId;

  /**
   * 套餐名称
   */
  @NotBlank(message = "套餐名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packageName;

  /**
   * 关联菜单id
   */
  @NotBlank(message = "关联菜单id不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuIds;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 关联显示
   */
  @NotNull(message = "关联显示不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte menuCheckStrictly;

  /**
   * 套餐状态
   */
  @NotNull(message = "套餐状态不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 套餐切图
   */
  @NotBlank(message = "套餐切图不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packageImage;

  /**
   * 套餐标签
   */
  @NotBlank(message = "套餐标签不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tags;

  /**
   * 热门
   */
  @NotNull(message = "热门不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte hot;

  /**
   * 官方
   */
  @NotNull(message = "官方不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte official;

  /**
   * 应用次数
   */
  @NotNull(message = "应用次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long frequency;

  /**
   * 套餐类别
   */
  @NotBlank(message = "套餐类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packageType;


}
