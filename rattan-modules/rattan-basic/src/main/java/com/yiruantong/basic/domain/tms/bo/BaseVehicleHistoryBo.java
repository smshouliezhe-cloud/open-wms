package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseVehicleHistory;
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
 * 车辆轨迹业务对象 base_vehicle_history
 *
 * @author YRT
 * @date 2024-05-31
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseVehicleHistory.class, reverseConvertGenerate = false)
public class BaseVehicleHistoryBo extends BaseEntity {

  /**
   * 车辆轨迹id
   */
  @NotNull(message = "车辆轨迹id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleHistoryId;

  /**
   * 车辆id
   */
  @NotNull(message = "车辆id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleId;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 单据id
   */
  @NotNull(message = "单据id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long billId;

  /**
   * 单据编号
   */
  @NotBlank(message = "单据编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

  /**
   * 状态类型
   */
  @NotBlank(message = "状态类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusType;

  /**
   * 业务类型
   */
  @NotBlank(message = "业务类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String operationType;

  /**
   * 变更前状态
   */
  @NotBlank(message = "变更前状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fromStatus;

  /**
   * 变更后状态
   */
  @NotBlank(message = "变更后状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String toStatus;

  /**
   * 城市名称
   */
  @NotBlank(message = "城市名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;

  /**
   * 邮递员
   */
  @NotBlank(message = "邮递员不能为空", groups = {AddGroup.class, EditGroup.class})
  private String courier;

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
   * 单据状态
   */
  @NotBlank(message = "单据状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderStatus;


}
