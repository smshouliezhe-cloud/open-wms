package com.yiruantong.basic.domain.base.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.base.BaseConsignorSales;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 门店明细视图对象 base_consignor_sales
 *
 * @author YRT
 * @date 2024-12-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseConsignorSales.class)
public class BaseConsignorSalesVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 门店明细ID
   */
  @ExcelProperty(value = "门店明细ID")
  private Long consignorDetailId;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 店主姓名
   */
  @ExcelProperty(value = "店主姓名")
  private String ownerName;

  /**
   * 店主电话
   */
  @ExcelProperty(value = "店主电话")
  private String ownerContact;

  /**
   * 采购员姓名
   */
  @ExcelProperty(value = "采购员姓名")
  private String purchasementName;

  /**
   * 采购员电话
   */
  @ExcelProperty(value = "采购员电话")
  private String purchasementContact;

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


}
