package com.yiruantong.inventory.domain.base.scan;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.yiruantong.common.core.enums.inventory.TransferTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 补货单扫描明细 storage_replenishment_detail
 *
 * @author YRT
 * @date 2024-10-14
 */
@Data
public class ScanReplenishmentDetailBo implements Serializable {

  /**
   * 补货单明细ID
   */
  @TableId(value = "replenishment_detail_id")
  private Long replenishmentDetailId;

  /**
   * 补货单ID
   */
  private Long replenishmentId;

  /**
   * 货位名称
   */
  private String positionName;

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
   * 产品规格
   */
  private String productSpec;

  /**
   * 补货数量
   */
  private BigDecimal transferQuantity;

  /**
   * 已扫描数量
   */
  private BigDecimal finishedQuantity;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 成本金额
   */
  private BigDecimal subTotalAmount;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 单据ID
   */
  private Long billId;

  /**
   * 已补货数量
   */
  private BigDecimal outQuantity;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 排序号
   */
  private Long orderNum;

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
   * 原货位
   */
  private String sortPositionName;

  /**
   * 目标货位
   */
  private String targetPositionName;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 补货单类型
   */
  private TransferTypeEnum billType;
}
