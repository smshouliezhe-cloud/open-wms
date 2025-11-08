package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateReturnOut;
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
 * 返厂出库记录业务对象 base_plate_return_out
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateReturnOut.class, reverseConvertGenerate = false)
public class BasePlateReturnOutBo extends BaseEntity {

  /**
   * 返厂出库记录
   */
  @NotNull(message = "返厂出库记录不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outDetailId;

  /**
   * 外键id
   */
  @NotNull(message = "外键id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long returnFactoryId;

  /**
   * 客户id
   */
  @NotNull(message = "客户id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientId;

  /**
   * 客户编号
   */
  @NotBlank(message = "客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientCode;

  /**
   * 客户简称
   */
  @NotBlank(message = "客户简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;

  /**
   * 销售区域id
   */
  @NotNull(message = "销售区域id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorIdSale;

  /**
   * 销售区域编号
   */
  @NotBlank(message = "销售区域编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCodeSale;

  /**
   * 销售区域名称
   */
  @NotBlank(message = "销售区域名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorNameSale;

  /**
   * 容器规格
   */
  @NotBlank(message = "容器规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateSpec;

  /**
   * 容器类别
   */
  @NotBlank(message = "容器类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 返厂出库数量
   */
  @NotNull(message = "返厂出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long returnFactoryQty;

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
   * 运费单价
   */
  @NotNull(message = "运费单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal price;

  /**
   * 小计运费
   */
  @NotNull(message = "小计运费不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal subFreight;

  /**
   * 单位重量
   */
  @NotNull(message = "单位重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 小计重量
   */
  @NotNull(message = "小计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 单位体积
   */
  @NotNull(message = "单位体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @NotNull(message = "小计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowCube;

  /**
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;


}
