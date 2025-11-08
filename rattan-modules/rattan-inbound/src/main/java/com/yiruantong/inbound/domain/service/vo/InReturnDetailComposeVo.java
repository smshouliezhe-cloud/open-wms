package com.yiruantong.inbound.domain.service.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
public class InReturnDetailComposeVo extends InReturnDetailVo implements Serializable {
  /**
   * 退货单号
   */
  @ExcelProperty(value = "退货单号")
  private String returnCode;
  /**
   * /**
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
   * 单据状态
   */
  @ExcelProperty(value = "单据状态")
  private String returnStatus;

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
   * 收货人
   */
  @ExcelProperty(value = "收货人")
  private String shippingName;

  /**
   * 收货地址
   */
  @ExcelProperty(value = "收货地址")
  private String shippingAddress;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;
}
