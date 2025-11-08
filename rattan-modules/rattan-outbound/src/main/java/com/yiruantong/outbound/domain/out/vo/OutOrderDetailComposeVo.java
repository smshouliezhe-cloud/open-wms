package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.outbound.domain.out.OutOrderDetail;

import java.io.Serializable;
import java.util.Date;


/**
 * 销售订单明细视图对象 out_order_detail
 *
 * @author YiRuanTong
 * @date 2023-11-27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderDetail.class)
public class OutOrderDetailComposeVo extends OutOrderDetail implements Serializable {
  /*******************************
   * 以下为出库单主表字段
   *******************************/
  /**
   * 出库编号
   */
  @ExcelProperty(value = "出库编号")
  private String orderCode;

  /**
   * ERP单号
   */
  @ExcelProperty(value = "ERP单号")
  private String storeOrderCode;

  /**
   * 渠道订单
   */
  @ExcelProperty(value = "渠道订单")
  private String orderChannel;

  /**
   * 店铺ID
   */
  @ExcelProperty(value = "店铺ID")
  private Long storeId;

  /**
   * 店铺名称
   */
  @ExcelProperty(value = "店铺名称")
  private String storeName;

  /**
   * 出货仓库ID
   */
  @ExcelProperty(value = "出货仓库ID")
  private Long storageId;

  /**
   * 出货仓库名称
   */
  @ExcelProperty(value = "出货仓库名称")
  private String storageName;

  /**
   * 出库类型
   */
  @ExcelProperty(value = "出库类型")
  private String orderType;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 订单状态
   */
  @ExcelProperty(value = "订单状态")
  private String orderStatus;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Byte sortingStatus;

  /**
   * 客户名称
   */
  @ExcelProperty(value = "客户名称")
  private String clientShortName;

  /**
   * 波次号
   */
  @ExcelProperty(value = "波次号")
  private String orderWaveCode;

  /**
   * 经手人
   */
  @ExcelProperty(value = "经手人")
  private String nickName;

  /**
   * 部门
   */
  @ExcelProperty(value = "部门")
  private String deptName;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 收货人
   */
  @ExcelProperty(value = "收货人")
  private String shippingName;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String telephone;


  /**
   * 收货地址
   */
  @ExcelProperty(value = "收货地址")
  private String shippingAddress;

  /**
   * 邮编
   */
  @ExcelProperty(value = "邮编")
  private String postCode;

  /**
   * 国家
   */
  @ExcelProperty(value = "国家")
  private String countryName;

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
   * 传真
   */
  @ExcelProperty(value = "传真")
  private String fax;

  /**
   * 街道
   */
  @ExcelProperty(value = "街道")
  private String street;

  /**
   * 电子邮件
   */
  @ExcelProperty(value = "电子邮件")
  private String email;

  /**
   * 快递类别
   */
  @ExcelProperty(value = "快递类别")
  private String expressCorpType;

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
   * 配送线路
   */
  @ExcelProperty(value = "配送线路")
  private String lineName;

  /**
   * 配送类型
   */
  @ExcelProperty(value = "配送类型")
  private String distributionType;


  /**
   * 运输方法
   */
  @ExcelProperty(value = "运输方法")
  private String shippingMethod;

  /**
   * 货主名称
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
   * 预计到达时间
   */
  private Date arriveDate;

  /**
   * 交货日期
   */
  private Date deliveryDate;


  /*******************************
   * 以下为商品信息字段
   *******************************/

}
