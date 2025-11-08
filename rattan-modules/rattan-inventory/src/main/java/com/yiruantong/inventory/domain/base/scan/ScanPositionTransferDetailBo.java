package com.yiruantong.inventory.domain.base.scan;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 货位转移明细视图对象 storage_position_transfer_detail
 *
 * @author YRT
 * @date 2023-12-07
 */
@Data
public class ScanPositionTransferDetailBo implements Serializable {
  /**
   * 货位转移明细ID
   */
  private Long transferDetailId;

  /**
   * 货位转移ID
   */
  private Long transferId;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 原货位名称
   */
  private String positionName;

  /**
   * 目标货位
   */
  private String targetPositionName;

  /**
   * 商品ID
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
   * 条形码
   */
  private String productModel;

  /**
   * 商品规格
   */
  private String productSpec;

  /**
   * 成本单据
   */
  private BigDecimal purchasePrice;

  /**
   * 转移数量
   */
  private BigDecimal finishedQuantity;

  /**
   * 成本金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产时间
   */
  private Date produceDate;

  /**
   * 原拍号
   */
  private String plateCode;

  /**
   * 目标拍号
   */
  private String targetPlateCode;

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
   * 仓库状态
   */
  private String storageStatus;

  /**
   * 库存属性
   */
  private String productAttribute;

  /**
   * SN码
   */
  private String singleSignCode;

  /**
   * 扩展字段
   */
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
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源主表ID
   */
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  private String sourceDetailId;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 库存Id
   */
  private Long inventoryId;

  /**
   * 管理SN
   */
  private Byte isManageSn;
}
