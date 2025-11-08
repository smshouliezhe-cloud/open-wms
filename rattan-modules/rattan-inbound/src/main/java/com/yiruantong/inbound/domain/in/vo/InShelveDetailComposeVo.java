package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class InShelveDetailComposeVo extends InShelveDetailVo implements Serializable {
  /**
   * 上架单号
   */
  @ExcelProperty(value = "上架单号")
  private String shelveCode;
  /**
   * 入库单号
   */
  @ExcelProperty(value = "入库单号")
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
   * 开始时间
   */
  @ExcelProperty(value = "开始时间")
  private Date startDate;

  /**
   * 结束时间
   */
  @ExcelProperty(value = "结束时间")
  private Date endDate;

  /**
   * 持续时间
   */
  @ExcelProperty(value = "持续时间")
  private String spanTime;

  /**
   * 上架类型
   */
  @ExcelProperty(value = "上架类型")
  private String shelveType;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

}
