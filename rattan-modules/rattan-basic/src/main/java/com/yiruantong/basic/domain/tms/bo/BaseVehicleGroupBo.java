package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseVehicleGroup;
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
 * 车队管理业务对象 base_vehicle_group
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseVehicleGroup.class, reverseConvertGenerate = false)
public class BaseVehicleGroupBo extends BaseEntity {

  /**
   * 车队ID
   */
  @NotNull(message = "车队ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleGroupId;

  /**
   * 车队编号
   */
  @NotBlank(message = "车队编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleGroupCode;

  /**
   * 车队名称
   */
  @NotBlank(message = "车队名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleGroupName;

  /**
   * 车队负责人
   */
  @NotBlank(message = "车队负责人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String teamLeader;

  /**
   * 负责人电话
   */
  @NotBlank(message = "负责人电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String personCharge;

  /**
   * 是否开票
   */
  @NotBlank(message = "是否开票不能为空", groups = {AddGroup.class, EditGroup.class})
  private String whetherInvoice;

  /**
   * 票据类型
   */
  @NotBlank(message = "票据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billType;

  /**
   * 开票税点
   */
  @NotBlank(message = "开票税点不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billingTaxPoint;

  /**
   * 司机
   */
  @NotBlank(message = "司机不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverName;

  /**
   * 司机ID
   */
  @NotBlank(message = "司机ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverId;

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
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;


}
