package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/*
 * @description: 残品入库明细查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InDamagedDetailComposeVo extends InDamagedOrderDetailVo implements Serializable {
  /**
   * 残品单编号
   */
  @ExcelProperty(value = "残品单编号")
  private String damagedOrderCode;

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
   * 报残日期
   */
  @ExcelProperty(value = "报残日期")
  private Data damagedData;

  /**
   * 单据状态
   */
  @ExcelProperty(value = "单据状态")
  private String damagedStatus;

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
}
