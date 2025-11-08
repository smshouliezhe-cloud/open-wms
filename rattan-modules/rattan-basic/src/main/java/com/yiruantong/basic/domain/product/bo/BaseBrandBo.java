package com.yiruantong.basic.domain.product.bo;

import com.yiruantong.basic.domain.product.BaseBrand;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 品牌管理业务对象 base_brand
 *
 * @author YiRuanTong
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseBrand.class, reverseConvertGenerate = false)
public class BaseBrandBo extends BaseEntity {

  /**
   * 品牌ID
   */
  @NotNull(message = "品牌ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long brandId;

  /**
   * 父级ID
   */
  @NotNull(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 大类别ID
   */
  @NotNull(message = "大类别ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long bigTypeId;

  /**
   * 大类别名称
   */
  @NotBlank(message = "大类别名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bigTypeName;

  /**
   * 类型
   */
  @NotBlank(message = "类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String modeType;

  /**
   * 品牌名
   */
  @NotBlank(message = "品牌名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandName;

  /**
   * 品牌英文名
   */
  @NotBlank(message = "品牌英文名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandNameEn;

  /**
   * 地址
   */
  @NotBlank(message = "地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String address;

  /**
   * 供应商ID
   */
  @NotNull(message = "供应商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 供应商编号
   */
  @NotBlank(message = "供应商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 供应商简称
   */
  @NotBlank(message = "供应商简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 采购人ID
   */
  @NotNull(message = "采购人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long purchaseId;

  /**
   * 采购人
   */
  @NotBlank(message = "采购人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String purchaseName;

  /**
   * 周期(天)
   */
  @NotNull(message = "周期(天)不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long cycle;

  /**
   * logo
   */
  @NotBlank(message = "logo不能为空", groups = {AddGroup.class, EditGroup.class})
  private String logo;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 质检计划
   */
  @NotBlank(message = "质检计划不能为空", groups = {AddGroup.class, EditGroup.class})
  private String qualityPlan;

  /**
   * 质检比例
   */
  @NotNull(message = "质检比例不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal qualityProportion;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

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

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;


}
