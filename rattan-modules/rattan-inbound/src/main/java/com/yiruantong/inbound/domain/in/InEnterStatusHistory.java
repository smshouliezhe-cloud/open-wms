package com.yiruantong.inbound.domain.in;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;


import java.io.Serial;
import java.util.Date;

/**
 * 入库记录轨迹对象 in_enter_status_history
 *
 * @author YRT
 * @date 2023-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_enter_status_history", autoResultMap = true)
public class InEnterStatusHistory extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 历史ID
   */
  @TableId(value = "history_id")
  private Long historyId;

  /**
   * 产品Id
   */
  private Long enterId;

  /**
   * 状态类型
   */
  private String statusType;

  /**
   * 操作类型
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
   * 单据ID
   */
  private Long billId;

  /**
   * 单据号
   */
  private String billCode;

  /**
   * 排序号
   */
  private Long orderNum;

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
