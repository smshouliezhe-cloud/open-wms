package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class InEnterDetailComposeVo extends InEnterDetailVo implements Serializable {
  /**
   * 入库单编号
   */
  @ExcelProperty(value = "入库单编号")
  private String enterCode;

  /**
   * 预到货单号
   */
  @ExcelProperty(value = "预到货单号")
  private String orderCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

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
   * ERP单号
   */
  @ExcelProperty(value = "ERP单号")
  private String trackingNumber;

  /**
   * 单据状态
   */
  @ExcelProperty(value = "单据状态")
  private String enterStatus;

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
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

  /**
   * LPN号
   */
  @ExcelProperty(value = "LPN号")
  private String lpnCode;

  /**
   * 入库类型
   */
  @ExcelProperty(value = "入库类型")
  private String scanInType;

  /**
   * 入库日期
   */
  @ExcelProperty(value = "入库日期")
  private Date applyDate;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String detailRemark;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;
}
