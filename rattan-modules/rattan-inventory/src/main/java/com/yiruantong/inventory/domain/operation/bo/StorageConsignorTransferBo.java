package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageConsignorTransfer;
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
 * 货位转移业务对象 storage_consignor_transfer
 *
 * @author YRT
 * @date 2024-11-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageConsignorTransfer.class, reverseConvertGenerate = false)
public class StorageConsignorTransferBo extends BaseEntity {

  /**
   * 货位转移ID
   */
  private Long consignorTransferId;

  /**
   * 货位转移编号
   */
  private String consignorTransferCode;

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
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalTransferQuantity;

  /**
   * 合计金额
   */
  @NotNull(message = "合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPurchaseAmount;

  /**
   * 货位状态
   */
  private String transferStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核状态
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 审核备注
   */
  private String auditRemark;

  /**
   * 毛重合计
   */
  @NotNull(message = "毛重合计不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 备注
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

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;


}
