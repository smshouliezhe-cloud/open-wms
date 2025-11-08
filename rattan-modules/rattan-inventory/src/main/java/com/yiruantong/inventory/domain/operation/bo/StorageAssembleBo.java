package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageAssemble;
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
 * 商品拆装单业务对象 storage_assemble
 *
 * @author YRT
 * @date 2024-01-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageAssemble.class, reverseConvertGenerate = false)
public class StorageAssembleBo extends BaseEntity {

  /**
   * 组装单ID
   */
  @NotNull(message = "组装单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long assembleId;

  /**
   * 组装单编号
   */
  @NotBlank(message = "组装单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String assembleCode;

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
   * 组装日期
   */
  @NotNull(message = "组装日期不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 合计出库数量
   */
  @NotNull(message = "合计出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalOuterQuantity;

  /**
   * 合计出库金额
   */
  @NotNull(message = "合计出库金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalOuterAmount;

  /**
   * 合计入库数量
   */
  @NotNull(message = "合计入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalEnterQuantity;

  /**
   * 合计入库金额
   */
  @NotNull(message = "合计入库金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalEnterAmount;

  /**
   * 组装状态
   */
  @NotBlank(message = "组装状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String assembleStatus;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sortingStatus;

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
   * 主商品数量
   */
  @NotNull(message = "主商品数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal mainQuantity;

  /**
   * 主商品编号
   */
  @NotBlank(message = "主商品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mainProductCode;

  /**
   * 主商品名称
   */
  @NotBlank(message = "主商品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mainProductName;


}
