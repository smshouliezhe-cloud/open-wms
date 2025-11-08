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
 * 下拉框值设置对象 sys_param_value
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_param_value", autoResultMap = true)
public class SysParamValue extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "param_id")
  private Long paramId;

  /**
   * 参数类别ID
   */
  private Long typeId;

  /**
   * 参数ID
   */
  private Long value01;

  /**
   * 参数值1
   */
  private String value02;

  /**
   * 参数值2
   */
  private String value03;

  /**
   * 参数值3
   */
  private String value04;

  /**
   * 参数值4
   */
  private String value05;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 是否可用
   */
  private Long enable;

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
