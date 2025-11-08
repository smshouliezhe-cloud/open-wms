package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateReturnFactoryDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 容器返厂单明细视图对象 base_plate_return_factory_detail
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateReturnFactoryDetail.class)
public class BasePlateReturnFactoryDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器返厂明细id
   */
  @ExcelProperty(value = "容器返厂明细id")
  private Long returnFactoryDetailId;

  /**
   * 容器返厂id
   */
  @ExcelProperty(value = "容器返厂id")
  private Long returnFactoryId;

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

  /**
   * 容器编号
   */
  @ExcelProperty(value = "容器编号")
  private String plateCode;

  /**
   * 容器规格
   */
  @ExcelProperty(value = "容器规格")
  private String plateSpec;

  /**
   * 容器类别
   */
  @ExcelProperty(value = "容器类别")
  private String plateType;

  /**
   * 返厂数量
   */
  @ExcelProperty(value = "返厂数量")
  private BigDecimal returnFactoryQty;

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
   * 单位重量
   */
  @ExcelProperty(value = "单位重量")
  private BigDecimal weight;

  /**
   * 单位体积
   */
  @ExcelProperty(value = "单位体积")
  private BigDecimal unitCube;

  /**
   * 小计重量
   */
  @ExcelProperty(value = "小计重量")
  private BigDecimal rowWeight;

  /**
   * 小计体积
   */
  @ExcelProperty(value = "小计体积")
  private BigDecimal rowCube;

  /**
   * 运费单价
   */
  @ExcelProperty(value = "运费单价")
  private BigDecimal price;

  /**
   * 小计运费
   */
  @ExcelProperty(value = "小计运费")
  private BigDecimal subFreight;

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
   * 容器属性
   */
  @ExcelProperty(value = "容器属性")
  private String plateAttribute;


}
