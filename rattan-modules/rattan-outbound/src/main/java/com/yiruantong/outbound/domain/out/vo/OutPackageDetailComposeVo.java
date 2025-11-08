package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutPackageDetailComposeVo extends OutPackageDetailVo implements Serializable {
  /**
   * 打包单id
   */
  @ExcelProperty(value = "打包单id")
  private Long packageId;

  /**
   * 打包单号
   */
  @ExcelProperty(value = "打包单号")
  private String packageCode;


  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 客户名称
   */
  @ExcelProperty(value = "客户名称")
  private String clientShortName;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 仓库Id
   */
  @ExcelProperty(value = "仓库Id")
  private Long storageId;

  /**
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * 快递名称
   */
  @ExcelProperty(value = "快递名称")
  private String expressCorpName;

  /**
   * 快递单号
   */
  @ExcelProperty(value = "快递单号")
  private String expressCode;

  /**
   * 打包状态
   */
  @ExcelProperty(value = "打包状态")
  private String packageStatus;

  /**
   * 出库单号
   */
  @ExcelProperty(value = "出库单号")
  private String OrderCode;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 波次单号
   */
  @ExcelProperty(value = "波次单号")
  private String orderPrintCode;

  /**
   * ERP单号
   */
  @ExcelProperty(value = "ERP单号")
  private String storeOrderCode;

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
   * 订单渠道
   */
  @ExcelProperty(value = "订单渠道")
  private String orderChannel;

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
   * 销售组织
   */
  @ExcelProperty(value = "销售组织")
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  @ExcelProperty(value = "销售组织编号")
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  @ExcelProperty(value = "销售组织ID")
  private Long consignorIdSale;

  /**
   * 单位体积
   */
  @ExcelProperty(value = "单位体积")
  private BigDecimal unitCube;

  /**
   * 单位重量
   */
  @ExcelProperty(value = "单位重量")
  private BigDecimal weight;

  /**
   * 是否扫码费
   */
  private Byte isScanCost;


}
