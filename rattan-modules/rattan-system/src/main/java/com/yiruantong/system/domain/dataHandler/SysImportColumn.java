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
 * 导入字段对象 sys_import_column
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_import_column", autoResultMap = true)
public class SysImportColumn extends TenantEntity {

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
  private Long importId;

  /**
   * 字段类型
   */
  private String columnType;

  /**
   * 字段名
   */
  private String columnName;

  /**
   * 字段中文名
   */
  private String cnName;

  /**
   * 验证类型
   */
  private String validate;

  /**
   * 验证规则
   */
  private String valExpression;

  /**
   * 验证描述
   */
  private String valDescription;

  /**
   * 是否必填
   */
  private Byte isMust;

  /**
   * 导入模式
   */
  private Byte importMode;

  /**
   * 表达式
   */
  private String express;

  /**
   * 是否扩展字段
   */
  private Byte isExpandField;

  /**
   * 带入字段
   */
  private String bringField;

  /**
   * 是否分组
   */
  private Byte isGroup;

  /**
   * 数据类型
   */
  private String dataType;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 备注
   */
  private String remark;

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


}
