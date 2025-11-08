package com.yiruantong.inventory.domain.base.dto;

import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class CommonMainDto {
  /**
   * 占位来源类型
   */
  private HolderSourceTypeEnum holderSourceType;

  /**
   * 主表ID
   */
  private Long mainId;

  /**
   * 主表单号
   */
  private String mainCode;

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
   * 合计出库数量
   */
  private BigDecimal totalOutQuantity;

  /**
   * 合计入库数量
   */
  private BigDecimal totalInQuantity;

  /**
   * 合计成本金额
   */
  private BigDecimal totalPurchaseAmount;

  /**
   * 合计销售金额
   */
  private BigDecimal totalSaleAmount;

  /**
   * 单据状态
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
   * 合计重量
   */
  private BigDecimal totalWeight;

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
   * 集装箱号
   */
  private String containerNo;

  /**
   * 店铺订单号
   */
  private String storeOrderCode;

  /**
   * 费用项
   */
  private String feeItemIds;

  /**
   * 可分拣的库存属性
   */
  private List<InventoryStatusEnum> productAttributeEnumList;
}
