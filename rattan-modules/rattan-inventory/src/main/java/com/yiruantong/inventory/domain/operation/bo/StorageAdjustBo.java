package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageAdjust;
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
 * 库存调整单业务对象 storage_adjust
 *
 * @author YRT
 * @date 2024-11-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageAdjust.class, reverseConvertGenerate = false)
public class StorageAdjustBo extends BaseEntity {

  /**
   * 调整单ID
   */
  private Long adjustId;

  /**
   * 调整单编号
   */
  private String adjustCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

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
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 校验类型
   */
  private String checkType;

  /**
   * 盘点日期
   */
  private Date applyDate;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 合计账面库存量
   */
  private BigDecimal totalProductStorage;

  /**
   * 合计账面成本额
   */
  private BigDecimal totalPurchaseAmount;

  /**
   * 合计盘点数量
   */
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
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商名称
   */
  @NotBlank(message = "供应商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 状态
   */
  private String adjustStatus;

  /**
   * 审核人
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 审核
   */
  private String auditor;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 备注
   */
  private String auditRemark;

  /**
   * 合计毛重
   */
  @NotNull(message = "合计毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;

  /**
   * 审核备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源ID
   */
  private Long sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 合计净重
   */
  private BigDecimal totalNetWeight;


}
