package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateReturnCost;
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
 * 容器返厂费用明细业务对象 base_plate_return_cost
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateReturnCost.class, reverseConvertGenerate = false)
public class BasePlateReturnCostBo extends BaseEntity {

  /**
   * 明细费用id
   */
  @NotNull(message = "明细费用id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long costId;

  /**
   * 返厂单id
   */
  @NotNull(message = "返厂单id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long returnFactoryId;

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
   * 计费方式
   */
  @NotBlank(message = "计费方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pricingManner;

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
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantity;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 计费值
   */
  @NotNull(message = "计费值不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal billableValue;

  /**
   * 合计件数
   */
  @NotNull(message = "合计件数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPackage;

  /**
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;


}
