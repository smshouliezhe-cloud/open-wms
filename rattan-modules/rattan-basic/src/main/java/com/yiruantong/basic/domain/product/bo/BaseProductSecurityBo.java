package com.yiruantong.basic.domain.product.bo;

import com.yiruantong.basic.domain.product.BaseProductSecurity;
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
 * 防伪标签业务对象 base_product_security
 *
 * @author YRT
 * @date 2024-04-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseProductSecurity.class, reverseConvertGenerate = false)
public class BaseProductSecurityBo extends BaseEntity {

  /**
   * 防伪标签id
   */
  @NotNull(message = "防伪标签id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long securityId;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 商品编号
   */
  @NotBlank(message = "商品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 商品名称
   */
  @NotBlank(message = "商品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 条形码
   */
  @NotBlank(message = "条形码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 产品规格
   */
  @NotBlank(message = "产品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 原产地
   */
  @NotBlank(message = "原产地不能为空", groups = {AddGroup.class, EditGroup.class})
  private String originPlace;

  /**
   * 生产企业
   */
  @NotBlank(message = "生产企业不能为空", groups = {AddGroup.class, EditGroup.class})
  private String produceEnterPrise;

  /**
   * 批号名称
   */
  @NotBlank(message = "批号名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 批号编号
   */
  @NotBlank(message = "批号编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchCode;

  /**
   * 防伪码数量
   */
  @NotNull(message = "防伪码数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long quantity;

  /**
   * 单位
   */
  @NotBlank(message = "单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String unit;

  /**
   * 换算关系
   */
  @NotBlank(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private String unitConvert;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核状态
   */
  @NotNull(message = "审核状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

  /**
   * 审核时间
   */
  @NotNull(message = "审核时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

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
   * 上传图片
   */
  @NotBlank(message = "上传图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productImageUrl;


}
