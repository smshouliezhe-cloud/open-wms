package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateIn;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 容器归还主业务对象 base_plate_in
 *
 * @author YRT
 * @date 2024-05-11
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateIn.class, reverseConvertGenerate = false)
public class BasePlateInBo extends BaseEntity {

  /**
   * 容器归还Id
   */
  @NotNull(message = "容器归还Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inId;

  /**
   * 容器归还编号
   */
  @NotBlank(message = "容器归还编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String inCode;

  /**
   * 经手人ID
   */
  @NotNull(message = "经手人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 经手人
   */
  @NotBlank(message = "经手人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 类型
   */
  @NotBlank(message = "类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 状态
   */
  @NotBlank(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusText;

  /**
   * 合计归还数量
   */
  @NotNull(message = "合计归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalReturnQty;

  /**
   * 归还日期
   */
  @NotNull(message = "归还日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date returnDate;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核状态
   */
  @NotNull(message = "审核状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 仓库Id
   */
  @NotNull(message = "仓库Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 客户Id
   */
  @NotNull(message = "客户Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientId;

  /**
   * 客户编号
   */
  @NotBlank(message = "客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientCode;

  /**
   * 客户名称
   */
  @NotBlank(message = "客户名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * 销售组织
   */
  @NotBlank(message = "销售组织不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  @NotBlank(message = "销售组织编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  @NotNull(message = "销售组织ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorIdSale;

  /**
   * 源借出单号
   */
  @NotBlank(message = "源借出单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceOutCode;

  /**
   * 运输类别
   */
  @NotBlank(message = "运输类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String transportType;

  /**
   * 承运商ID
   */
  @NotNull(message = "承运商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long carrierId;

  /**
   * 承运商
   */
  @NotBlank(message = "承运商不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierName;

  /**
   * 运输车型
   */
  @NotBlank(message = "运输车型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleType;

  /**
   * 车辆id
   */
  @NotNull(message = "车辆id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleId;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 司机id
   */
  @NotNull(message = "司机id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long driverId;

  /**
   * 司机名称
   */
  @NotBlank(message = "司机名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverName;

  /**
   * 司机电话
   */
  @NotBlank(message = "司机电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverTel;

  /**
   * 合计运费
   */
  @NotBlank(message = "合计运费不能为空", groups = {AddGroup.class, EditGroup.class})
  private String totalFreight;

  /**
   * 出发地id
   */
  @NotNull(message = "出发地id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long placeOriginId;

  /**
   * 出发地
   */
  @NotBlank(message = "出发地不能为空", groups = {AddGroup.class, EditGroup.class})
  private String placeOrigin;

  /**
   * 目的地id
   */
  @NotNull(message = "目的地id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long placeDestinationId;

  /**
   * 目的地
   */
  @NotBlank(message = "目的地不能为空", groups = {AddGroup.class, EditGroup.class})
  private String placeDestination;

  /**
   * 源借出单id
   */
  @NotNull(message = "源借出单id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sourceOutId;

  /**
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 计价方式
   */
  @NotBlank(message = "计价方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pricingManner;

  /**
   * 送货订单号
   */
  @NotBlank(message = "送货订单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deliveryCode;

  /**
   * 容器借出单id
   */
  @NotNull(message = "容器借出单id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outId;

  /**
   * 订单日期
   */
  @NotNull(message = "订单日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date applyDate;

  /**
   * 单据类型
   */
  @NotBlank(message = "单据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 返空桶差异备注
   */
  @NotBlank(message = "返空桶差异备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String differenceRemark;


}
