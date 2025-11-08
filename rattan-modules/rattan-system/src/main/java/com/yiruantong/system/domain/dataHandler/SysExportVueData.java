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
 * 导出视图设置对象 sys_export_vue_data
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_export_vue_data", autoResultMap = true)
public class SysExportVueData extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 导出视图ID
   */
  @TableId(value = "vue_data_id")
  private Long vueDataId;

  /**
   * 导出信息ID
   */
  private Long exportId;

  /**
   * 导出名称
   */
  private String vueDataName;

  /**
   * 导出JSON
   */
  private String vueData;

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
