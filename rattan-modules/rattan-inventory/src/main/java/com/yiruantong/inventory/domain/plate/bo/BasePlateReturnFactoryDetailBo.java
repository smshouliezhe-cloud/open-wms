package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateReturnFactoryDetail;
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
 * 容器返厂单明细业务对象 base_plate_return_factory_detail
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateReturnFactoryDetail.class, reverseConvertGenerate = false)
public class BasePlateReturnFactoryDetailBo extends BaseEntity {

  /**
   * 容器返厂明细id
   */
  @NotNull(message = "容器返厂明细id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long returnFactoryDetailId;

  /**
   * 容器返厂id
   */
  @NotNull(message = "容器返厂id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long returnFactoryId;

  /**
   * 容器id
   */
  @NotNull(message = "容器id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long plateId;

  /**
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;

  /**
   * 容器编号
   */
  @NotBlank(message = "容器编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

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
   * 返厂数量
   */
  @NotNull(message = "返厂数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal returnFactoryQty;

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
   * 小计重量
   */
  @NotNull(message = "小计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 小计体积
   */
  @NotNull(message = "小计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowCube;

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
   * 费用单价
   */
  @NotNull(message = "费用单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal costPrice;

  /**
   * 小计费用
   */
  @NotNull(message = "小计费用不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal subCost;

  /**
   * 容器属性
   */
  @NotBlank(message = "容器属性不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateAttribute;


}
