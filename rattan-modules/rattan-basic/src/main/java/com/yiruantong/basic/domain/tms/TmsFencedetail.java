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
 * 围栏管理明细对象 tms_fenceDetail
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tms_fenceDetail", autoResultMap = true)
public class TmsFencedetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 电子围栏明细ID
   */
  @TableId(value = "fence_detail_id")
  private Long fenceDetailId;

  /**
   * 电子围栏ID
   */
  private Long fenceId;

  /**
   * 车辆ID
   */
  private Long vehicleId;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 设备ID
   */
  private Long equipmentId;

  /**
   * 设备编号
   */
  private String equipmentCode;

  /**
   * 设备名称
   */
  private String equipmentName;

  /**
   * 状态
   */
  private String statusText;

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
