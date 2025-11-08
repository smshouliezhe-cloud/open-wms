package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateInCost;
import com.yiruantong.inventory.domain.plate.BasePlateInDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 容器归还明细视图
 *
 * @author YRT
 * @date 2024-03-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateInDetail.class)
public class BasePlateInCostComposeVo extends BasePlateInCost implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;


  /**
   * 容器归还Id
   */
  @ExcelProperty(value = "容器归还Id")
  private Long inId;

  /**
   * 容器归还编号
   */
  @ExcelProperty(value = "容器归还编号")
  private String inCode;

  /**
   * 经手人ID
   */
  @ExcelProperty(value = "经手人ID")
  private Long userId;

  /**
   * 经手人
   */
  @ExcelProperty(value = "经手人")
  private String nickName;

  /**
   * 类型
   */
  @ExcelProperty(value = "类型")
  private String plateType;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String statusText;

  /**
   * 合计归还数量
   */
  @ExcelProperty(value = "合计归还数量")
  private BigDecimal totalReturnQty;

  /**
   * 归还日期
   */
  @ExcelProperty(value = "归还日期")
  private Date returnDate;

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
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

  /**
   * 仓库Id
   */
  @ExcelProperty(value = "仓库Id")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 客户Id
   */
  @ExcelProperty(value = "客户Id")
  private Long clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户名称
   */
  @ExcelProperty(value = "客户名称")
  private String clientShortName;

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
   * 源借出单号
   */
  @ExcelProperty(value = "源借出单号")
  private String sourceOutCode;

  /**
   * 运输类别
   */
  @ExcelProperty(value = "运输类别")
  private String transportType;

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
  private String totalFreight;

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
   * 源借出单id
   */
  @ExcelProperty(value = "源借出单id")
  private Long sourceOutId;

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
   * 计价方式
   */
  @ExcelProperty(value = "计价方式")
  private String pricingManner;

  /**
   * 返空桶差异备注
   */
  private String differenceRemark;

  /**
   * 出库订单日期
   */
  private Date applyDate;

}
