package com.yiruantong.inventory.domain.operation;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
  import java.math.BigDecimal;


import java.io.Serial;

/**
 * 建议采购转遇到货对象 core_inventory_advise
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "core_inventory_advise", autoResultMap = true)
public class CoreInventoryAdvise extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 行ID
   */
    @TableId(value = "advise_id")
  private Long adviseId;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 条形码
   */
  private String productModel;

  /**
   * 产品规格
   */
  private String productSpec;

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
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商简称
   */
  private String providerShortName;

  /**
   * 库存量
   */
  private BigDecimal productStorage;

  /**
   * 最低库存
   */
  private Long storageLower;

  /**
   * 30天销量
   */
  private Long thirtyDaySale;

  /**
   *  周期
   */
  private Long cycle;

  /**
   * 最近30天销量均值
   */
  private BigDecimal thirtyDayAverageSale;

  /**
   *  建议采购量
   */
  private BigDecimal adviseQty;

  /**
   * 备注
   */
  private String remark;

  /**
   * 预定数量
   */
  private Long reserveQty;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 仓库编号
   */
  private String storageCode;


}
