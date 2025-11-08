package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageOuterSortingRule;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 其他出库规则业务对象 storage_outer_sorting_rule
 *
 * @author YRT
 * @date 2023-12-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageOuterSortingRule.class, reverseConvertGenerate = false)
public class StorageOuterSortingRuleBo extends BaseEntity {

  /**
   * ID
   */
  @NotNull(message = "ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long ruleId;

  /**
   * 其他出库单号
   */
  @NotBlank(message = "其他出库单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outerCode;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

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
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 生产日期
   */
  @NotNull(message = "生产日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 托盘号
   */
  @NotBlank(message = "托盘号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * SN号
   */
  @NotBlank(message = "SN号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 其他出库单ID
   */
  @NotNull(message = "其他出库单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outerId;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 销售明细ID
   */
  @NotNull(message = "销售明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailId;

  /**
   * 库存ID
   */
  @NotNull(message = "库存ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inventoryId;

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


}
