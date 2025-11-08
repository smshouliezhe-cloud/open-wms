package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateReturnOut;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 返厂出库记录视图对象 base_plate_return_out
 *
 * @author YRT
 * @date 2024-03-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateReturnOut.class)
public class BasePlateReturnOutComposeVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 返厂id
   */
  @ExcelProperty(value = "返厂id")
  private Long returnFactoryId;

  /**
   * 返厂单号
   */
  @ExcelProperty(value = "返厂单号")
  private String returnFactoryCode;

  /**
   * 采购商ID
   */
  @ExcelProperty(value = "采购商ID")
  private Long providerId;

  /**
   * 采购商编号
   */
  @ExcelProperty(value = "采购商编号")
  private String providerCode;

  /**
   * 采购商名称
   */
  @ExcelProperty(value = "采购商名称")
  private String providerShortName;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 返厂数量
   */
  @ExcelProperty(value = "返厂数量")
  private BigDecimal totalReturnFactoryQty;

  /**
   * 返厂日期
   */
  @ExcelProperty(value = "返厂日期")
  private Date returnFactoryDate;

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

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核状态
   */
  @ExcelProperty(value = "审核状态")
  private Byte auditing;

  /**
   * 审核时间
   */
  @ExcelProperty(value = "审核时间")
  private Date auditDate;

  /**
   * 审核备注
   */
  @ExcelProperty(value = "审核备注")
  private String auditRemark;

  /**
   * 单据状态
   */
  @ExcelProperty(value = "单据状态")
  private String statusText;

  /**
   * 合计费用
   */
  @ExcelProperty(value = "合计费用")
  private BigDecimal totalCost;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 承运商ID
   */
  @ExcelProperty(value = "承运商ID")
  private Long carrierId;

  /**
   * 承运商
   */
  @ExcelProperty(value = "承运商")
  private String carrierName;

  /**
   * 运输车型
   */
  @ExcelProperty(value = "运输车型")
  private String vehicleType;

  /**
   * 车辆id
   */
  @ExcelProperty(value = "车辆id")
  private Long vehicleId;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 司机id
   */
  @ExcelProperty(value = "司机id")
  private Long driverId;

  /**
   * 司机名称
   */
  @ExcelProperty(value = "司机名称")
  private String driverName;

  /**
   * 司机电话
   */
  @ExcelProperty(value = "司机电话")
  private String driverTel;

  /**
   * 合计运费
   */
  @ExcelProperty(value = "合计运费")
  private BigDecimal totalFreight;

  /**
   * 出发地id
   */
  @ExcelProperty(value = "出发地id")
  private Long placeOriginId;

  /**
   * 出发地
   */
  @ExcelProperty(value = "出发地")
  private String placeOrigin;

  /**
   * 目的地id
   */
  @ExcelProperty(value = "目的地id")
  private Long placeDestinationId;

  /**
   * 目的地
   */
  @ExcelProperty(value = "目的地")
  private String placeDestination;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;
  /**
   * 返厂出库记录
   */
  @ExcelProperty(value = "返厂出库记录")
  private Long outDetailId;

  /**
   * 客户id
   */
  @ExcelProperty(value = "客户id")
  private Long clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户简称
   */
  @ExcelProperty(value = "客户简称")
  private String clientShortName;

  /**
   * 销售区域id
   */
  @ExcelProperty(value = "销售区域id")
  private Long consignorIdSale;

  /**
   * 销售区域编号
   */
  @ExcelProperty(value = "销售区域编号")
  private String consignorCodeSale;

  /**
   * 销售区域名称
   */
  @ExcelProperty(value = "销售区域名称")
  private String consignorNameSale;

  /**
   * 容器规格
   */
  @ExcelProperty(value = "容器规格")
  private String plateSpec;

  /**
   * 容器类别
   */
  @ExcelProperty(value = "容器类别")
  private String plateType;

  /**
   * 返厂出库数量
   */
  @ExcelProperty(value = "返厂出库数量")
  private Long returnFactoryQty;

  /**
   * 运费单价
   */
  @ExcelProperty(value = "运费单价")
  private BigDecimal price;

  /**
   * 小计运费
   */
  @ExcelProperty(value = "小计运费")
  private BigDecimal subFreight;

  /**
   * 单位重量
   */
  @ExcelProperty(value = "单位重量")
  private BigDecimal weight;

  /**
   * 小计重量
   */
  @ExcelProperty(value = "小计重量")
  private BigDecimal rowWeight;

  /**
   * 单位体积
   */
  @ExcelProperty(value = "单位体积")
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @ExcelProperty(value = "小计体积")
  private BigDecimal rowCube;


}
