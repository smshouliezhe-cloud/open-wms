package com.yiruantong.system.domain.dataHandler;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 下拉框设置对象 sys_dropdown
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dropdown", autoResultMap = true)
public class SysDropdown extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 下拉框ID
   */
  @TableId(value = "dropdown_id")
  private Long dropdownId;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 下拉框名称
   */
  private String cnName;

  /**
   * sql语句
   */
  private String sqlScript;

  /**
   * 排序号
   */
  private Long orderNum;

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
