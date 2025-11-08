package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateAdjust;
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
 * 容器调整主业务对象 base_plate_adjust
 *
 * @author YRT
 * @date 2024-03-28
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateAdjust.class, reverseConvertGenerate = false)
public class BasePlateAdjustBo extends BaseEntity {

  /**
   * 容器调整Id
   */
  @NotNull(message = "容器调整Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long adjustId;

  /**
   * 容器调整编号
   */
  @NotBlank(message = "容器调整编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String adjustCode;

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
   * 调整类型
   */
  @NotBlank(message = "调整类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String adjustType;

  /**
   * 调整状态
   */
  @NotBlank(message = "调整状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusText;

  /**
   * 合计归还数量
   */
  @NotNull(message = "合计归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long totalReturnQty;

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
   * 合计借出数量
   */
  @NotNull(message = "合计借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalNowOutQty;

  /**
   * 调整后数量
   */
  @NotNull(message = "调整后数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAfterQty;

  /**
   * 调整借出数量
   */
  @NotNull(message = "调整借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalOutQty;


}
