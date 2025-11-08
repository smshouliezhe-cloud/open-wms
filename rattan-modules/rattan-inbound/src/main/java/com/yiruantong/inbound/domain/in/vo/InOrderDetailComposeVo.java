package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/*
 * @description: 预到货单明细查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InOrderDetailComposeVo extends InOrderDetailVo implements Serializable {
  /**
   * 预到货单号
   */
  @ExcelProperty(value = "预到货单号")
  private String orderCode;

  /**
   * 源头单号
   */
  @ExcelProperty(value = "源头单号")
  private String sourceCode;

  /**
   * ERP单号
   */
  @ExcelProperty(value = "ERP单号")
  private String trackingNumber;

  /**
   * 供应商名称
   */
  @ExcelProperty(value = "供应商名称")
  private String providerShortName;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 上架状态
   */
  @ExcelProperty(value = "上架状态")
  private String shelveStatus;

  /**
   * 单据状态
   */
  @ExcelProperty(value = "单据状态")
  private String orderStatus;

  /**
   * 经手人
   */
  @ExcelProperty(value = "经手人")
  private String nickName;

  /**
   * 部门名称
   */
  @ExcelProperty(value = "部门名称")
  private String deptName;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

  /**
   * 预计到货时间
   */
  @ExcelProperty(value = "预计到货时间")
  private Date arrivedDate;

  /**
   * 是否需要质检
   */
  @ExcelProperty(value = "是否需要质检")
  private Byte isChecking;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 铅封号
   */
  @ExcelProperty(value = "铅封号")
  private String sealNo;
}
