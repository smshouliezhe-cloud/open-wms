package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageCheck;
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
 * 盘点单业务对象 storage_check
 *
 * @author YRT
 * @date 2023-10-24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageCheck.class, reverseConvertGenerate = false)
public class StorageCheckBo extends BaseEntity {

  /**
   * 盘点单ID
   */
  @NotNull(message = "盘点单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long checkId;

  /**
   * 盘点单编号
   */
  @NotBlank(message = "盘点单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String checkCode;

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
   * 盘点日期
   */
  @NotNull(message = "盘点日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date applyDate;

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
   * 合计账面库存量
   */
  @NotNull(message = "合计账面库存量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalProductStorage;

  /**
   * 合计账面成本额
   */
  @NotNull(message = "合计账面成本额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPurchaseAmount;

  /**
   * 合计盘点数量
   */
  @NotNull(message = "合计盘点数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCheckQuantity;

  /**
   * 合计盘盈数量
   */
  @NotNull(message = "合计盘盈数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalProfitQuantity;

  /**
   * 合计盘盈金额
   */
  @NotNull(message = "合计盘盈金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalProfitAmount;

  /**
   * 合计盘亏数量
   */
  @NotNull(message = "合计盘亏数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalLossQuantity;

  /**
   * 合计盘亏金额
   */
  @NotNull(message = "合计盘亏金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalLossAmount;

  /**
   * 盘点状态
   */
  @NotBlank(message = "盘点状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String checkStatus;

  /**
   * 分拣日期
   */
  @NotNull(message = "分拣日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date sortingDate;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sortingStatus;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

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
   * 盘点单类型
   */
  @NotBlank(message = "盘点单类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String checkType;

  /**
   * 差异天数
   */
  @NotNull(message = "差异天数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long diffDate;

  /**
   * 是否盲盘
   */
  @NotNull(message = "是否盲盘不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isBlind;

  /**
   * 盈亏单ID
   */
  @NotNull(message = "盈亏单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long profitLossId;

  /**
   * 原始盘点单ID
   */
  @NotNull(message = "原始盘点单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long fromCheckId;

  /**
   * 原始盘点单编号
   */
  @NotBlank(message = "原始盘点单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fromCheckCode;

  /**
   * 库区
   */
  @NotBlank(message = "库区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String areaCode;

  /**
   * 账面库存毛重合计
   */
  @NotNull(message = "账面库存毛重合计不能为空", groups = {AddGroup.class, EditGroup.class})
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


}
