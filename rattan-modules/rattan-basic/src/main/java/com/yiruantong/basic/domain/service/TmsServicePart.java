package com.yiruantong.basic.domain.service;

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
 * 维修配件管理对象 tms_service_part
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tms_service_part", autoResultMap = true)
public class TmsServicePart extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 配件ID
   */
  @TableId(value = "service_part_id")
  private Long servicePartId;

  /**
   * 配件编号
   */
  private String partCode;

  /**
   * 配件名称
   */
  private String partName;

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
