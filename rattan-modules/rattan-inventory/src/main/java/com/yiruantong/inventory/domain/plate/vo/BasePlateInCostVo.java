package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateInCost;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 容器归还费用明细视图对象 base_plate_in_cost
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateInCost.class)
public class BasePlateInCostVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 费用明细id
   */
  @ExcelProperty(value = "费用明细id")
  private Long costId;

  /**
   * 容器归还id
   */
  @ExcelProperty(value = "容器归还id")
  private Long inId;

  /**
   * 费用科目id
   */
  @ExcelProperty(value = "费用科目id")
  private Long feeItemId;

  /**
   * 费用科目
   */
  @ExcelProperty(value = "费用科目")
  private String feeItemName;

  /**
   * 计费方式
   */
  @ExcelProperty(value = "计费方式")
  private String pricingManner;

  /**
   * 费用单价
   */
  @ExcelProperty(value = "费用单价")
  private BigDecimal costPrice;

  /**
   * 小计费用
   */
  @ExcelProperty(value = "小计费用")
  private BigDecimal subCost;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalQuantity;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 计费值
   */
  @ExcelProperty(value = "计费值")
  private BigDecimal billableValue;

  /**
   * 合计件数
   */
  @ExcelProperty(value = "合计件数")
  private BigDecimal totalPackage;

  /**
   * 容器类别
   */
  @ExcelProperty(value = "容器类别")
  private String plateType;

  /**
   * 容器规格
   */
  @ExcelProperty(value = "容器规格")
  private String plateSpec;

  /**
   * 容器id
   */
  @ExcelProperty(value = "容器id")
  private Long plateId;

  /**
   * 容器名称
   */
  @ExcelProperty(value = "容器名称")
  private String plateName;


}
