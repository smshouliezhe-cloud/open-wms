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
 * 库存占位查询(异常)对象 core_inventory_holder
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "core_inventory_holder", autoResultMap = true)
public class CoreInventoryHolder extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 分拣预占ID
   */
  @TableId(value = "holder_id")
  private Long holderId;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 编号
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
   * 库存ID
   */
  private Long inventoryId;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 默认货位
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
   * 进货价
   */
  private BigDecimal purchasePrice;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 税价
   */
  private BigDecimal ratePrice;

  /**
   * 入库时间
   */
  private Date inStorageDate;

  /**
   * 预占数量
   */
  private BigDecimal holderStorage;

  /**
   * 原始占位量
   */
  private BigDecimal orignHolderStorage;

  /**
   * 退货对冲量
   */
  private Long usedStorage;

  /**
   * 未出库数量
   */
  private Long unouterStorage;

  /**
   * 店铺订单号
   */
  private String storeOrderCode;

  /**
   * SN
   */
  private String singleSignCode;

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
   * 拍号
   */
  private String plateCode;

  /**
   * 关联号
   */
  private String relationCode;

  /**
   * 报检单号
   */
  private String declareNo;

  /**
   * 整拣单
   */
  private Byte isFullContainerLoad;

  /**
   * 预占位重量
   */
  private BigDecimal holderWeight;

  /**
   * 原始占位重量
   */
  private BigDecimal orignHolderWeight;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

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
   * 排序类型
   */
  private String sortType;

  /**
   * 实占数量
   */
  private BigDecimal factHolderStorage;

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
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * 仓库编号
   */
  private String storageCode;


}
