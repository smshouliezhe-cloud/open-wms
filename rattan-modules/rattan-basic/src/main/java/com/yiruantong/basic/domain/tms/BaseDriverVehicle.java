package com.yiruantong.basic.domain.tms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 司机车辆绑定对象 base_driver_vehicle
 *
 * @author YRT
 * @date 2024-05-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_driver_vehicle", autoResultMap = true)
public class BaseDriverVehicle extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车辆司机ID
   */
  @TableId(value = "driver_vehicle_id")
  private Long driverVehicleId;

  /**
   * 司机ID
   */
  private Long driverId;

  /**
   * 司机姓名
   */
  private String driverName;

  /**
   * 车辆ID
   */
  private Long vehicleId;

  /**
   * 车辆编码
   */
  private String vehicleCode;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}
