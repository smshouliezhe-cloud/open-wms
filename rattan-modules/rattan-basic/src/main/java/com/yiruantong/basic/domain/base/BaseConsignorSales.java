package com.yiruantong.basic.domain.base;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Map;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 门店明细对象 base_consignor_sales
 *
 * @author YRT
 * @date 2024-12-28
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_consignor_sales", autoResultMap = true)
public class BaseConsignorSales extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 门店明细ID
   */
    @TableId(value = "consignor_detail_id")
  private Long consignorDetailId;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 店主姓名
   */
  private String ownerName;

  /**
   * 店主电话
   */
  private String ownerContact;

  /**
   * 采购员姓名
   */
  private String purchasementName;

  /**
   * 采购员电话
   */
  private String purchasementContact;

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
