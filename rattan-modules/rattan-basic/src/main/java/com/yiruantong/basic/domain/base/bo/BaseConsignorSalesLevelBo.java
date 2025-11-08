package com.yiruantong.basic.domain.base.bo;

import com.yiruantong.basic.domain.base.BaseConsignorSalesLevel;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.util.Map;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 门店销售等级设置业务对象 base_consignor_sales_level
 *
 * @author YRT
 * @date 2025-01-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseConsignorSalesLevel.class, reverseConvertGenerate = false)
public class BaseConsignorSalesLevelBo extends BaseEntity {

  /**
   * 销售等级id
   */
  @NotNull(message = "销售等级id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long salesLevelId;

  /**
   * 销售等级S
   */
  @NotBlank(message = "销售等级S不能为空", groups = {AddGroup.class, EditGroup.class})
  private String salesLevelS;

  /**
   * 销售等级A
   */
  @NotBlank(message = "销售等级A不能为空", groups = {AddGroup.class, EditGroup.class})
  private String salesLevelA;

  /**
   * 销售等级B
   */
  @NotBlank(message = "销售等级B不能为空", groups = {AddGroup.class, EditGroup.class})
  private String salesLevelB;

  /**
   * 销售等级C
   */
  @NotBlank(message = "销售等级C不能为空", groups = {AddGroup.class, EditGroup.class})
  private String salesLevelC;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;


}
