package com.yiruantong.inventory.domain.allocate.bo;

import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
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
 * 调拨申请单业务对象 storage_allocate_apply
 *
 * @author YRT
 * @date 2024-01-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageAllocateApply.class, reverseConvertGenerate = false)
public class StorageAllocateApplyBo extends BaseEntity {

  /**
   * 调拨申请单ID
   */
  @NotNull(message = "调拨申请单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long allocateApplyId;

  /**
   * 调拨申请单编号
   */
  @NotBlank(message = "调拨申请单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String allocateApplyCode;

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
   * 部门ID
   */
  @NotNull(message = "部门ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 部门名称
   */
  @NotBlank(message = "部门名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 申请日期
   */
  @NotNull(message = "申请日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date applyDate;

  /**
   * 调出仓库ID
   */
  @NotNull(message = "调出仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 调出仓库名称
   */
  @NotBlank(message = "调出仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 调入仓库ID
   */
  @NotNull(message = "调入仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageIdIn;

  /**
   * 调入仓库名称
   */
  @NotBlank(message = "调入仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageNameIn;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  @NotNull(message = "合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;

  /**
   * 申请状态
   */
  @NotBlank(message = "申请状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String applyStatus;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte sortingStatus;

  /**
   * 分拣日期
   */
  @NotNull(message = "分拣日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date sortingDate;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 审核备注
   */
  @NotBlank(message = "审核备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditRemark;

  /**
   * 订单类型
   */
  @NotBlank(message = "订单类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 总重量
   */
  @NotNull(message = "总重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

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
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源ID
   */
  @NotBlank(message = "来源ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 合计净重
   */
  @NotNull(message = "合计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalNetWeight;

  /**
   * 含税金额
   */
  @NotNull(message = "含税金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal taxAmount;

  /**
   * 在途虚拟仓id
   */
  @NotNull(message = "在途虚拟仓id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long virtualStorageId;

  /**
   * 在途虚拟仓名称
   */
  @NotBlank(message = "在途虚拟仓名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String virtualStorageName;

  /**
   * 收货人
   */
  @NotBlank(message = "收货人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingName;

  /**
   * 邮政编码
   */
  @NotBlank(message = "邮政编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String postCode;

  /**
   * 国家ID
   */
  @NotNull(message = "国家ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long countryId;

  /**
   * 国家
   */
  @NotBlank(message = "国家不能为空", groups = {AddGroup.class, EditGroup.class})
  private String countryName;

  /**
   * 国家全称
   */
  @NotBlank(message = "国家全称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String countryFullName;

  /**
   * 省ID
   */
  @NotNull(message = "省ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long provinceId;

  /**
   * 省
   */
  @NotBlank(message = "省不能为空", groups = {AddGroup.class, EditGroup.class})
  private String provinceName;

  /**
   * 市ID
   */
  @NotNull(message = "市ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long cityId;

  /**
   * 市
   */
  @NotBlank(message = "市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;

  /**
   * 区ID
   */
  @NotNull(message = "区ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long regionId;

  /**
   * 区
   */
  @NotBlank(message = "区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionName;

  /**
   * 发件人地址
   */
  @NotBlank(message = "发件人地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingAddress;

  /**
   * 快递类别
   */
  @NotNull(message = "快递类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte expressCorpType;

  /**
   * 快递ID
   */
  @NotNull(message = "快递ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long expressCorpId;

  /**
   * 快递名称
   */
  @NotBlank(message = "快递名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCorpName;

  /**
   * 快递编号
   */
  @NotBlank(message = "快递编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCode;

  /**
   * 线路Id
   */
  @NotNull(message = "线路Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long lineId;

  /**
   * 线路编号
   */
  @NotBlank(message = "线路编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineCode;

  /**
   * 线路名称
   */
  @NotBlank(message = "线路名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineName;

  /**
   * 配送类型
   */
  @NotBlank(message = "配送类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String distributionType;

  /**
   * 手机号
   */
  @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQtyTotal;

  /**
   * 应付快递费
   */
  @NotNull(message = "应付快递费不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shippingAmount;

  /**
   * 本单应收
   */
  @NotNull(message = "本单应收不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalUnpaid;

  /**
   * 目标货主名称
   */
  @NotBlank(message = "目标货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorNameTarget;

  /**
   * 目标货主编号
   */
  @NotBlank(message = "目标货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCodeTarget;

  /**
   * 目标货主ID
   */
  @NotNull(message = "目标货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorIdTarget;


}
