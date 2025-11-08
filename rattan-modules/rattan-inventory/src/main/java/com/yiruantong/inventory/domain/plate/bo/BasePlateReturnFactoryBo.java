package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateReturnFactory;
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
 * 容器返厂单业务对象 base_plate_return_factory
 *
 * @author YRT
 * @date 2024-03-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateReturnFactory.class, reverseConvertGenerate = false)
public class BasePlateReturnFactoryBo extends BaseEntity {

  /**
   * 返厂id
   */
  @NotNull(message = "返厂id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long returnFactoryId;

  /**
   * 返厂单号
   */
  @NotBlank(message = "返厂单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String returnFactoryCode;

  /**
   * 采购商ID
   */
  @NotNull(message = "采购商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 采购商编号
   */
  @NotBlank(message = "采购商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 采购商名称
   */
  @NotBlank(message = "采购商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 返厂数量
   */
  @NotNull(message = "返厂数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalReturnFactoryQty;

  /**
   * 返厂日期
   */
  @NotNull(message = "返厂日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date returnFactoryDate;

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
   * 审核时间
   */
  @NotNull(message = "审核时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 审核备注
   */
  @NotBlank(message = "审核备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditRemark;

  /**
   * 单据状态
   */
  @NotBlank(message = "单据状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusText;

  /**
   * 合计费用
   */
  @NotNull(message = "合计费用不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCost;

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
  @NotNull(message = "合计运费不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalFreight;

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
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;


}
