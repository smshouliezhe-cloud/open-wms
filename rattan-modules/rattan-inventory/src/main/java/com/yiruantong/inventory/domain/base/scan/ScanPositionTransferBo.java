package com.yiruantong.inventory.domain.base.scan;

import lombok.Data;
import com.yiruantong.common.core.enums.inventory.TransferTypeEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 货位转移扫描视图 StorageScanPositionTransferVo
 *
 * @author YRT
 * @date 2023-12-01
 */
@Data
public class ScanPositionTransferBo implements Serializable {
  /**
   * 货位转移ID
   */
  private Long transferId;

  /**
   * 货位转移单号
   */
  private String transferCode;

  /**
   * 货位转移类型
   */
  private TransferTypeEnum transferType;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalPurchaseAmount;

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
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源ID
   */
  private String sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 扫描明细
   */
  private List<ScanPositionTransferDetailBo> dataList;

}
