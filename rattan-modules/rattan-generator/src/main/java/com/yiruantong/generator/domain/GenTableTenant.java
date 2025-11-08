package com.yiruantong.generator.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.util.Map;

/**
 * 生成表租户数据对象 gen_table_tenant
 *
 * @author 谢天保
 * @date 2023-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "gen_table_tenant", autoResultMap = true)
public class GenTableTenant extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 表租户数据ID
   */
  @TableId(value = "table_tenant_id")
  private Long tableTenantId;

  /**
   * 表ID
   */
  private Long tableId;

  /**
   * 表租户名称
   */
  private String tableTenantName;

  /**
   * JSON数据
   */
  private String jsonData;

  /**
   * 来源ID
   */
  private Long fromTableTenantId;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 模块ID
   */
  private Long menuId;

  /**
   * UI类型
   */
  private String vueType;
}
