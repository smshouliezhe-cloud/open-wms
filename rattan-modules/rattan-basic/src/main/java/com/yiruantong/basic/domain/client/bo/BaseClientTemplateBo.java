package com.yiruantong.basic.domain.client.bo;

import com.yiruantong.basic.domain.client.BaseClientTemplate;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 客户运价模板业务对象 base_client_template
 *
 * @author YRT
 * @date 2024-04-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseClientTemplate.class, reverseConvertGenerate = false)
public class BaseClientTemplateBo extends BaseEntity {

  /**
   * 模板ID
   */
  @NotNull(message = "模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long templateId;

  /**
   * 客户ID
   */
  @NotNull(message = "客户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientId;

  /**
   * 费用模块
   */
  @NotBlank(message = "费用模块不能为空", groups = {AddGroup.class, EditGroup.class})
  private String costModule;

  /**
   * 费用科目id
   */
  @NotNull(message = "费用科目id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long feeItemId;

  /**
   * 费用科目
   */
  @NotBlank(message = "费用科目不能为空", groups = {AddGroup.class, EditGroup.class})
  private String feeItemName;

  /**
   * 计价方式
   */
  @NotBlank(message = "计价方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pricingManner;

  /**
   * 模版类型
   */
  @NotBlank(message = "模版类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String templateType;

  /**
   * 自动生成运费
   */
  @NotNull(message = "自动生成运费不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long autoBuildFreight;

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
