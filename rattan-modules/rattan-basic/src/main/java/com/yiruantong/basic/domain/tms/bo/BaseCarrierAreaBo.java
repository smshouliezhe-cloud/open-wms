package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseCarrierArea;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;


/**
 * 承运商管辖区域业务对象 base_carrier_area
 *
 * @author YRT
 * @date 2025-02-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseCarrierArea.class, reverseConvertGenerate = false)
public class BaseCarrierAreaBo extends BaseEntity {

  /**
   * 明细id
   */
  @NotNull(message = "明细id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long carrierAreaId;

  /**
   * 承运商ID
   */
  @NotNull(message = "承运商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long carrierId;

  /**
   * 省ID
   */
  @NotNull(message = "省ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long provinceId;

  /**
   * 省
   */
  @NotBlank(message = "省不能为空", groups = {AddGroup.class, EditGroup.class})
  private String provinceName;

  /**
   * 市ID
   */
  @NotNull(message = "市ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long cityId;

  /**
   * 市
   */
  @NotBlank(message = "市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;

  /**
   * 区ID
   */
  @NotNull(message = "区ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long regionId;

  /**
   * 区
   */
  @NotBlank(message = "区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionName;

  /**
   * 计价方式
   */
  @NotBlank(message = "计价方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pricingManner;

  /**
   * 费用科目
   */
  @NotBlank(message = "费用科目不能为空", groups = {AddGroup.class, EditGroup.class})
  private String feeItemName;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
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

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 目的地网点
   */
  @NotNull(message = "目的地网点", groups = {AddGroup.class, EditGroup.class})
  private String unloadSite;

  /**
   * 温层
   */
  @NotNull(message = "温层", groups = {AddGroup.class, EditGroup.class})
  private String thermocLine;
}
