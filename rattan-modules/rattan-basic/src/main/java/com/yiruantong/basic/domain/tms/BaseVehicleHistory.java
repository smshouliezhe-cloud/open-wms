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
 * 车辆轨迹对象 base_vehicle_history
 *
 * @author YRT
 * @date 2024-05-31
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_vehicle_history", autoResultMap = true)
public class BaseVehicleHistory extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车辆轨迹id
   */
  @TableId(value = "vehicle_history_id")
  private Long vehicleHistoryId;

  /**
   * 车辆id
   */
  private Long vehicleId;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 单据id
   */
  private Long billId;

  /**
   * 单据编号
   */
  private String billCode;

  /**
   * 状态类型
   */
  private String statusType;

  /**
   * 业务类型
   */
  private String operationType;

  /**
   * 变更前状态
   */
  private String fromStatus;

  /**
   * 变更后状态
   */
  private String toStatus;

  /**
   * 城市名称
   */
  private String cityName;

  /**
   * 邮递员
   */
  private String courier;

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
   * 单据状态
   */
  private String orderStatus;


}
