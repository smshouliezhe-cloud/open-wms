package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/*
 * @description: 入库计划明细查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InOrderPlanDetailComposeVo extends InOrderPlanDetailVo implements Serializable {
  /**
   * 入库计划单
   */
  @ExcelProperty(value = "入库计划单")
  private String planCode;

  /**
   * 计划状态
   */
  @ExcelProperty(value = "计划状态")
  private String planStatus;

  /**
   * 计划类型
   */
  @ExcelProperty(value = "计划类型")
  private String planType;

  /**
   * 货主id
   */
  @ExcelProperty(value = "货主名称")
  private Long consignorId;

  /**
   * 货主编号
   */
  @ExcelProperty(value = "货主名称")
  private String consignorCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;
}
