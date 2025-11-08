package com.yiruantong.basic.domain.product.bo;

import com.yiruantong.basic.domain.product.BaseProductSet;
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
 * 商品套装主业务对象 base_product_set
 *
 * @author YRT
 * @date 2023-12-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseProductSet.class, reverseConvertGenerate = false)
public class BaseProductSetBo extends BaseEntity {

  /**
   * 商品拆分ID
   */
  @NotNull(message = "商品拆分ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productSetId;

  /**
   * 订单渠道
   */
  @NotNull(message = "订单渠道不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte orderChannel;

  /**
   * ERP店铺ID
   */
  @NotNull(message = "ERP店铺ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storeInfoId;

  /**
   * 店铺名称
   */
  @NotBlank(message = "店铺名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storeName;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 产品名称
   */
  @NotBlank(message = "产品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 产品条码
   */
  @NotBlank(message = "产品条码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 产品规格
   */
  @NotBlank(message = "产品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 销售售价
   */
  @NotNull(message = "销售售价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal salePrice;

  /**
   * 原始售价
   */
  @NotNull(message = "原始售价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal originalPrice;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 成本价
   */
  @NotNull(message = "成本价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

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
   * 货主名字
   */
  @NotBlank(message = "货主名字不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

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
   * 产品型号
   */
  @NotBlank(message = "产品型号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productBarCode;

  /**
   * 图片
   */
  @NotBlank(message = "图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String images;


}
