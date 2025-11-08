package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutPlanDetailComposeVo extends OutOrderPlanDetailVo implements Serializable {
  /**
   * 出库计划单号
   */
  @ExcelProperty(value = "出库计划单号")
  private String orderPlanCode;

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
   * 单据状态
   */
  @ExcelProperty(value = "单据状态")
  private String planStatus;

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
   * 计划出库日期
   */
  @ExcelProperty(value = "计划出库日期")
  private Date planDate;

  /**
   * 联系人
   */
  @ExcelProperty(value = "联系人")
  private String linkerName;

  /**
   * 邮编
   */
  @ExcelProperty(value = "邮编")
  private String zip;

  /**
   * 国家
   */
  @ExcelProperty(value = "国家")
  private String countryName;

  /**
   * 收货人
   */
  @ExcelProperty(value = "收货人")
  private String shippingName;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 电话
   */
  @ExcelProperty(value = "电话")
  private String tel;

  /**
   * 收货地址
   */
  @ExcelProperty(value = "收货地址")
  private String shippingAddress;

  /**
   * 经手人
   */
  @ExcelProperty(value = "省")
  private String provinceName;

  /**
   * 部门名称
   */
  @ExcelProperty(value = "市")
  private String cityName;

  /**
   * 经手人
   */
  @ExcelProperty(value = "区")
  private String regionName;

  /**
   * 客户ID
   */
  @ExcelProperty(value = "客户ID")
  private String clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户名称
   */
  @ExcelProperty(value = "客户")
  private String clientShortName;

}
