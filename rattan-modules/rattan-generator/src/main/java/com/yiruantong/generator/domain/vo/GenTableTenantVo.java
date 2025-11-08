package com.yiruantong.generator.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.generator.domain.GenTableTenant;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;


/**
 * 生成表租户数据视图对象 gen_table_tenant
 *
 * @author 谢天保
 * @date 2023-06-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = GenTableTenant.class)
public class GenTableTenantVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 表租户数据ID
   */
  @ExcelProperty(value = "表租户数据ID")
  private Long tableTenantId;

  /**
   * 表ID
   */
  @ExcelProperty(value = "表ID")
  private String tableId;

  /**
   * 表租户名称
   */
  @ExcelProperty(value = "表租户名称")
  private String tableTenantName;

  /**
   * JSON数据
   */
  @ExcelProperty(value = "JSON数据")
  private String jsonData;

  /**
   * 来源ID
   */
  @ExcelProperty(value = "来源ID")
  private String fromTableTenantId;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;


}
