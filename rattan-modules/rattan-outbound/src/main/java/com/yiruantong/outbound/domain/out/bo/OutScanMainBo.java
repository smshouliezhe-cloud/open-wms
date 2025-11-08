package com.yiruantong.outbound.domain.out.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 扫描主表出库bo数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OutScanMainBo {
  /**
   * 出库单ID
   */
  private Long orderId;

  /**
   * 出库单号
   */
  private String orderCode;

  /**
   * 波次单号
   */
  private String orderWaveCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 客户ID
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
   * 货位
   */
  private String positionName;

  /**
   * 出库类型
   */
  private InventorySourceTypeEnum scanInType;

  /**
   * 占位类型
   */
  private HolderSourceTypeEnum holderSourceTypeEnum;

  /**
   * 件数
   */
  private Long orderNumber;

  /**
   * 扫描明细
   */
  List<OutScanDetailBo> dataList;

  /**
   * 实际重量
   */
  private BigDecimal factWeight;

  /**
   * 配送日期
   */
  private Date deliveryDate;

  /**
   * 是否溢出（湘钢）
   */
  private Byte spillover;
}
