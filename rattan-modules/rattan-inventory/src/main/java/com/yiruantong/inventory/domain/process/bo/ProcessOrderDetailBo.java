package com.yiruantong.inventory.domain.process.bo;

import com.yiruantong.inventory.domain.process.ProcessOrderDetail;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 加工列明细业务对象 process_order_detail
 *
 * @author YRT
 * @date 2025-01-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ProcessOrderDetail.class, reverseConvertGenerate = false)
public class ProcessOrderDetailBo extends BaseEntity {

  /**
   * 明细id
   */
  @NotNull(message = "明细id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long processListId;

  /**
   * 主表id
   */
  @NotNull(message = "主表id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long processId;

  /**
   * 来源id
   */
  @NotNull(message = "来源id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long billId;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 商品编号
   */
  @NotBlank(message = "商品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 商品名称
   */
  @NotBlank(message = "商品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 出货仓库ID
   */
  @NotNull(message = "出货仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 出货仓库
   */
  @NotBlank(message = "出货仓库不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 平台主表id
   */
  @NotNull(message = "平台主表id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sourceMainId;

  /**
   * 平台明细id
   */
  @NotNull(message = "平台明细id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sourceListId;

  /**
   * 所属仓位
   */
  @NotBlank(message = "所属仓位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String areaId;

  /**
   * 投料重量
   */
  @NotNull(message = "投料重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal feedingWeight;

  /**
   * 投料重量单位
   */
  @NotBlank(message = "投料重量单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String feedingWeightUnit;

  /**
   * 投料数量
   */
  @NotNull(message = "投料数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal feedingNumber;

  /**
   * 投料数量单位
   */
  @NotBlank(message = "投料数量单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String feedingNumberUnit;

  /**
   * 产出重量
   */
  @NotNull(message = "产出重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal outputWeight;

  /**
   * 产出重量单位
   */
  @NotBlank(message = "产出重量单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outputWeightUnit;

  /**
   * 产出件数
   */
  @NotNull(message = "产出件数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outputNumber;

  /**
   * 产出件数单位
   */
  @NotBlank(message = "产出件数单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outputNumberUnit;

  /**
   * 出成率
   */
  @NotBlank(message = "出成率不能为空", groups = {AddGroup.class, EditGroup.class})
  private String yieId;

  /**
   * 状态
   */
  @NotBlank(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderStatus;

  /**
   * 仓位
   */
  @NotBlank(message = "仓位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String areaName;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

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
