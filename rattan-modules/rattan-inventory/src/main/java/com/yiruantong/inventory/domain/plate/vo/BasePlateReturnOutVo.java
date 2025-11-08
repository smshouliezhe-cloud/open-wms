package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateReturnOut;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 返厂出库记录视图对象 base_plate_return_out
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateReturnOut.class)
public class BasePlateReturnOutVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 返厂出库记录
   */
  @ExcelProperty(value = "返厂出库记录")
  private Long outDetailId;

  /**
   * 外键id
   */
  @ExcelProperty(value = "外键id")
  private Long returnFactoryId;

  /**
   * 客户id
   */
  @ExcelProperty(value = "客户id")
  private Long clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户简称
   */
  @ExcelProperty(value = "客户简称")
  private String clientShortName;

  /**
   * 销售区域id
   */
  @ExcelProperty(value = "销售区域id")
  private Long consignorIdSale;

  /**
   * 销售区域编号
   */
  @ExcelProperty(value = "销售区域编号")
  private String consignorCodeSale;

  /**
   * 销售区域名称
   */
  @ExcelProperty(value = "销售区域名称")
  private String consignorNameSale;

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
   * 返厂出库数量
   */
  @ExcelProperty(value = "返厂出库数量")
  private Long returnFactoryQty;

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
   * 单位重量
   */
  @ExcelProperty(value = "单位重量")
  private BigDecimal weight;

  /**
   * 小计重量
   */
  @ExcelProperty(value = "小计重量")
  private BigDecimal rowWeight;

  /**
   * 单位体积
   */
  @ExcelProperty(value = "单位体积")
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @ExcelProperty(value = "小计体积")
  private BigDecimal rowCube;

  /**
   * 容器名称
   */
  @ExcelProperty(value = "容器名称")
  private String plateName;


}
