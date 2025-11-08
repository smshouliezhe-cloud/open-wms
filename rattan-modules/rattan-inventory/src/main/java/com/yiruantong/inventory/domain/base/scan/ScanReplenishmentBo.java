package com.yiruantong.inventory.domain.base.scan;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 补货单扫描视图 storage_replenishment
 *
 * @author YRT
 * @date 2024-10-14
 */
@Data
public class ScanReplenishmentBo implements Serializable {
  /**
   * 补货单ID
   */
  private Long replenishmentId;

  /**
   * 补货单编号
   */
  private String replenishmentCode;

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
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 补货单状态
   */
  private String billStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核状态
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 补货单类型
   */
  private String billType;

  /**
   * 是否整拣
   */
  private Byte isFullPick;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;

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
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 扫描明细
   */
  private List<ScanReplenishmentDetailBo> dataList;
}
