package com.yiruantong.generator.domain.bo;

import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.generator.domain.GenTableTenant;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.util.Map;

/**
 * 生成表租户数据业务对象 gen_table_tenant
 *
 * @author 谢天保
 * @date 2023-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = GenTableTenant.class, reverseConvertGenerate = false)
public class GenTableTenantBo extends BaseEntity {

  /**
   * 表租户数据ID
   */
  @NotNull(
    message = "表租户数据ID不能为空",
    groups = {EditGroup.class})
  private Long tableTenantId;

  /**
   * 表ID
   */
  @NotBlank(
    message = "表ID不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private Long tableId;

  /**
   * 表租户名称
   */
  @NotBlank(
    message = "表租户名称不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String tableTenantName;

  /**
   * JSON数据
   */
  @NotBlank(
    message = "JSON数据不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String jsonData;

  /**
   * 来源ID
   */
  @NotBlank(
    message = "来源ID不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private Long fromTableTenantId;

  /**
   * 扩展字段
   */
  @NotBlank(
    message = "扩展字段不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 设计模式
   */
  private String designMode;

  /**
   * 模块ID
   */
  private Long menuId;

  /**
   * UI类型
   */
  private String vueType;

  /**
   * 路由
   */
  private String webRouter;

  /**
   * 保存类型
   */
  private String type;

  /**
   * 是否PDA
   */
  private Boolean pda;

  /**
   * 保存类型
   */
  private String mainCode;

  public boolean isPda() {
    return ObjectUtil.isNotEmpty(pda) && pda;
  }
}
