package com.yiruantong.inventory.domain.core;

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
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * WMS库存变化推送对象 core_inventory_history
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "core_inventory_history", autoResultMap = true)
public class CoreInventoryHistory extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "history_id")
  private Long historyId;

  /**
   * 来源类型
   */
  private String sourceType;

  /**
   * 来源单号
   */
  private String billCode;

  /**
   * 来源主表ID
   */
  private Long mainId;

  /**
   * 来源明细ID
   */
  private Long detailId;

  /**
   * 货位名称
   */
  private String positionName;

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
   * 入库数量
   */
  private BigDecimal inQuantity;

  /**
   * 出库数量
   */
  private BigDecimal outQuantity;

  /**
   * 入库金额
   */
  private BigDecimal inAmount;

  /**
   * 出库金额
   */
  private BigDecimal outAmount;

  /**
   * 操作前库存数量
   */
  private BigDecimal beforeQuantity;

  /**
   * 操作后库存数量
   */
  private BigDecimal afterQuantity;

  /**
   * 操作后库存金额
   */
  private BigDecimal afterAmount;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
   * 生产日期
   */
  private Date produceDate;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 入库重量
   */
  private BigDecimal inWeight;

  /**
   * 出库重量
   */
  private BigDecimal outWeight;

  /**
   * 操作前重量
   */
  private BigDecimal beforeWeight;

  /**
   * 最终重量
   */
  private BigDecimal afterWeight;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 明细扩展字段
   */
  @TableField(value = "detail_expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> detailExpandFields;

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
   * 来源单号2
   */
  private String sourceCode2;

  /**
   * 来源单号3
   */
  private String sourceCode3;

  /**
   * 拍数
   */
  private BigDecimal plateQty;

  /**
   * 类别编号
   */
  private Long typeId;

  /**
   * 类别名称
   */
  private String typeName;

  /**
   * 产品型号
   */
  private String productBarCode;

  /**
   * 品牌ID
   */
  private Long brandId;

  /**
   * 品牌名
   */
  private String brandName;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 库存ID
   */
  private Long inventoryId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;

  /**
   * 仓库编号
   */
  private String storageCode;


}
