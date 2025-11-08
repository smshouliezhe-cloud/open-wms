package com.yiruantong.inventory.domain.log;

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
 * 状态转变日志对象 log_storage_status_change
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "log_storage_status_change", autoResultMap = true)
public class LogStorageStatusChange extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 日志ID
   */
  @TableId(value = "log_id")
  private Long logId;

  /**
   * result
   */
  private String result;

  /**
   * 操作类型
   */
  private String operationType;

  /**
   * 库存ID
   */
  private Long inventoryId;

  /**
   * 来源单号
   */
  private String billCode;

  /**
   * 入库单号
   */
  private String enterCode;

  /**
   * 预到货单号
   */
  private String orderCode;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生成日期
   */
  private Date produceDate;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * 关联码
   */
  private String relationCode;

  /**
   * 保质期天数
   */
  private Long shelfLifeDay;

  /**
   * 库存保质期
   */
  private Date shelfLifeDate;

  /**
   * 最长库存天数
   */
  private Long validShelfLifeDay;

  /**
   * 仓库状态
   */
  private String storageStatus;

  /**
   * 产品属性
   */
  private String productAttribute;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源主表ID
   */
  private Long sourceMainId;

  /**
   * 来源明细ID
   */
  private Long sourceDetailId;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 商品条码
   */
  private String productModel;

  /**
   * 商品规格
   */
  private String productSpec;

  /**
   * SN
   */
  private String singleSignCode;

  /**
   * 入库时间
   */
  private Date inStorageDate;

  /**
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商名称
   */
  private String providerShortName;

  /**
   * 库存量
   */
  private Long productStorage;

  /**
   * 原始库存量
   */
  private Long originStorage;

  /**
   * 进货价
   */
  private BigDecimal purchasePrice;

  /**
   * 进货总额
   */
  private BigDecimal purchaseAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 移动平均价
   */
  private BigDecimal avgPrice;

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
