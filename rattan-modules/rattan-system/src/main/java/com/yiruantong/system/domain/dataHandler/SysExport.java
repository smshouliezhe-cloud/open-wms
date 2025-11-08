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
 * 导出设置对象 sys_export
 *
 * @author YRT
 * @date 2024-07-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_export", autoResultMap = true)
public class SysExport extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 导出ID
   */
  @TableId(value = "export_id")
  private Long exportId;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 表名称
   */
  private String tableName;

  /**
   * 表中文名
   */
  private String tableNameCn;

  /**
   * 模板路径
   */
  private String templatePath;

  /**
   * 导出类型
   */
  private String exportType;

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

  /**
   * 自定义导出ID
   */
  private Long customExportId;


}
