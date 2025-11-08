package com.yiruantong.inventory.domain.plate;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 容器调整主对象 base_plate_adjust
 *
 * @author YRT
 * @date 2024-03-28
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_adjust", autoResultMap = true)
public class BasePlateAdjust extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器调整Id
   */
  @TableId(value = "adjust_id")
  private Long adjustId;

  /**
   * 容器调整编号
   */
  private String adjustCode;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 调整类型
   */
  private String adjustType;

  /**
   * 调整状态
   */
  private String statusText;

  /**
   * 合计归还数量
   */
  private Long totalReturnQty;

  /**
   * 归还日期
   */
  private Date returnDate;

  /**
   * 备注
   */
  private String remark;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核状态
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 客户Id
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户名称
   */
  private String clientShortName;

  /**
   * 仓库Id
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
   * 合计借出数量
   */
  private BigDecimal totalNowOutQty;

  /**
   * 调整后数量
   */
  private BigDecimal totalAfterQty;

  /**
   * 调整借出数量
   */
  private BigDecimal totalOutQty;


}
