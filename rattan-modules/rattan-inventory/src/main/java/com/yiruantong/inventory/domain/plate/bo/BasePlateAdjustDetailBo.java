package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateAdjustDetail;
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
 * 容器调整明细业务对象 base_plate_adjust_detail
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateAdjustDetail.class, reverseConvertGenerate = false)
public class BasePlateAdjustDetailBo extends BaseEntity {

  /**
   * 容器调整明细Id
   */
  @NotNull(message = "容器调整明细Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long adjustDetailId;

  /**
   * 容器调整Id
   */
  @NotNull(message = "容器调整Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long adjustId;

  /**
   * 容器类别
   */
  @NotBlank(message = "容器类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 现借出数量
   */
  @NotNull(message = "现借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long nowOutQty;

  /**
   * 调整借出数量
   */
  @NotNull(message = "调整借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long adjustOutQty;

  /**
   * 调整归还数量
   */
  @NotNull(message = "调整归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long adjustReturnQty;

  /**
   * 剩余借出数量
   */
  @NotNull(message = "剩余借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long surplusOutQty;

  /**
   * SN
   */
  @NotBlank(message = "SN不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

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
   * 容器规格
   */
  @NotBlank(message = "容器规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateSpec;

  /**
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;


}
