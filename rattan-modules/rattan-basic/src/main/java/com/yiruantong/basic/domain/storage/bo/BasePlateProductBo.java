package com.yiruantong.basic.domain.storage.bo;

import com.yiruantong.basic.domain.storage.BasePlateProduct;
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
 * 商品容器管理业务对象 base_plate_product
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateProduct.class, reverseConvertGenerate = false)
public class BasePlateProductBo extends BaseEntity {

  /**
   * 商品容器id
   */
  @NotNull(message = "商品容器id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long plateProductId;

  /**
   * 商品id
   */
  @NotNull(message = "商品id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 商品名称
   */
  @NotBlank(message = "商品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 商品编号
   */
  @NotBlank(message = "商品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 商品规格
   */
  @NotBlank(message = "商品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 商品条码
   */
  @NotBlank(message = "商品条码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 容器id
   */
  @NotNull(message = "容器id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long plateId;

  /**
   * 容器编号
   */
  @NotBlank(message = "容器编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 容器类型
   */
  @NotBlank(message = "容器类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 仓库id
   */
  @NotNull(message = "仓库id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

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
   * 容器名称（规格）
   */
  @NotBlank(message = "容器名称（规格）不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateSpec;

  /**
   * 单位重量
   */
  @NotNull(message = "单位重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 单位体积
   */
  @NotNull(message = "单位体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitCube;

  /**
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;


}
