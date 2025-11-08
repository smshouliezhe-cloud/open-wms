package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.TmsFencedetail;
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
 * 围栏管理明细业务对象 tms_fenceDetail
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsFencedetail.class, reverseConvertGenerate = false)
public class TmsFencedetailBo extends BaseEntity {

  /**
   * 电子围栏明细ID
   */
  @NotNull(message = "电子围栏明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long fenceDetailId;

  /**
   * 电子围栏ID
   */
  @NotNull(message = "电子围栏ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long fenceId;

  /**
   * 车辆ID
   */
  @NotNull(message = "车辆ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleId;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 设备ID
   */
  @NotNull(message = "设备ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long equipmentId;

  /**
   * 设备编号
   */
  @NotBlank(message = "设备编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String equipmentCode;

  /**
   * 设备名称
   */
  @NotBlank(message = "设备名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String equipmentName;

  /**
   * 状态
   */
  @NotBlank(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusText;

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


}
