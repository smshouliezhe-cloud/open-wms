package com.yiruantong.system.domain.dataHandler;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 导出字段对象 sys_export_column
 *
 * @author YRT
 * @date 2024-05-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_export_column", autoResultMap = true)
public class SysExportColumn extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 导入字段ID
   */
  @TableId(value = "column_id")
  private Long columnId;

  /**
   * 导入信息ID
   */
  private Long exportId;

  /**
   * 字段名
   */
  private String columnName;

  /**
   * 字段中文名
   */
  private String cnName;

  /**
   * 字段表达式
   */
  private String colExpression;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 备注
   */
  private String remark;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 数据类型
   */
  private String dataType;

  /**
   * 格式化模板
   */
  private String dataFormatter;

  /**
   * 开启扩展字段
   */
  private Byte isExpandField;
}
