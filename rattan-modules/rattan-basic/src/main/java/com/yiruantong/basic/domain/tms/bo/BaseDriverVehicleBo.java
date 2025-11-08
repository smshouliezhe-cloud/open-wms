package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseDriverVehicle;
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
 * 司机车辆绑定业务对象 base_driver_vehicle
 *
 * @author YRT
 * @date 2024-05-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseDriverVehicle.class, reverseConvertGenerate = false)
public class BaseDriverVehicleBo extends BaseEntity {

  /**
   * 车辆司机ID
   */
  @NotNull(message = "车辆司机ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long driverVehicleId;

  /**
   * 司机ID
   */
  @NotNull(message = "司机ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long driverId;

  /**
   * 司机姓名
   */
  @NotBlank(message = "司机姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverName;

  /**
   * 车辆ID
   */
  @NotNull(message = "车辆ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleId;

  /**
   * 车辆编码
   */
  @NotBlank(message = "车辆编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleCode;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

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
