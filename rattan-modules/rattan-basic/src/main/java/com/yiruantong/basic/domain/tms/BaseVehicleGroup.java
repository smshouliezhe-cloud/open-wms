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
 * 车队管理对象 base_vehicle_group
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_vehicle_group", autoResultMap = true)
public class BaseVehicleGroup extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车队ID
   */
  @TableId(value = "vehicle_group_id")
  private Long vehicleGroupId;

  /**
   * 车队编号
   */
  private String vehicleGroupCode;

  /**
   * 车队名称
   */
  private String vehicleGroupName;

  /**
   * 车队负责人
   */
  private String teamLeader;

  /**
   * 负责人电话
   */
  private String personCharge;

  /**
   * 是否开票
   */
  private String whetherInvoice;

  /**
   * 票据类型
   */
  private String billType;

  /**
   * 开票税点
   */
  private String billingTaxPoint;

  /**
   * 司机
   */
  private String driverName;

  /**
   * 司机ID
   */
  private String driverId;

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

  /**
   * 是否可用
   */
  private Long enable;


}
