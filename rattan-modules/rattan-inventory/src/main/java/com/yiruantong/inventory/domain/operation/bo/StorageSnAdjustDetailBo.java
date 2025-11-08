package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageSnAdjustDetail;
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
 * SN调整明细业务对象 storage_sn_adjust_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageSnAdjustDetail.class, reverseConvertGenerate = false)
public class StorageSnAdjustDetailBo extends BaseEntity {

  /**
   * SN调整ID
   */
  @NotNull(message = "SN调整ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long snAdjustDetailId;

  /**
   * SN调整ID
   */
  @NotNull(message = "SN调整ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long snAdjustId;

  /**
   * 库存ID
   */
  @NotNull(message = "库存ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inventoryId;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 产品名称
   */
  @NotBlank(message = "产品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 条形码
   */
  @NotBlank(message = "条形码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 产品规格
   */
  @NotBlank(message = "产品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 原是否可用
   */
  @NotNull(message = "原是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 新是否可用
   */
  @NotNull(message = "新是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enableTarget;

  /**
   * 原SN号
   */
  @NotBlank(message = "原SN号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String snNo;

  /**
   * 新SN号
   */
  @NotBlank(message = "新SN号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String snNoTarget;

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
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源主表ID
   */
  @NotBlank(message = "来源主表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  @NotBlank(message = "来源明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceDetailId;

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


}
