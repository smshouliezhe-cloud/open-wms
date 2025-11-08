package com.yiruantong.inventory.domain.plate;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 容器调整明细对象 base_plate_adjust_detail
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_adjust_detail", autoResultMap = true)
public class BasePlateAdjustDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器调整明细Id
   */
  @TableId(value = "adjust_detail_id")
  private Long adjustDetailId;

  /**
   * 容器调整Id
   */
  private Long adjustId;

  /**
   * 容器类别
   */
  private String plateType;

  /**
   * 现借出数量
   */
  private Long nowOutQty;

  /**
   * 调整借出数量
   */
  private Long adjustOutQty;

  /**
   * 调整归还数量
   */
  private Long adjustReturnQty;

  /**
   * 剩余借出数量
   */
  private Long surplusOutQty;

  /**
   * SN
   */
  private String singleSignCode;

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
   * 容器规格
   */
  private String plateSpec;

  /**
   * 容器名称
   */
  private String plateName;


}
