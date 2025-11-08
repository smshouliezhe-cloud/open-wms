package com.yiruantong.system.domain.core;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Map;

/**
 * 系统打印模板对象 sys_print_template
 *
 * @author YRT
 * @date 2023-11-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_print_template", autoResultMap = true)
public class SysPrintTemplate extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 打印模板ID
   */
  @TableId(value = "print_template_id")
  private Long printTemplateId;

  /**
   * 模板名称
   */
  private String templateName;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 模块ID
   */
  private Long menuId;

  /**
   * JSON数据
   */
  private String vueData;

  /**
   * 模板类型
   */
  private String templateType;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 排序号
   */
  private Long orderNum;


}
