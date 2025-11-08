package com.yiruantong.inbound.domain.in.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;

import java.util.List;

/**
 * 扫描主表入库bo数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InScanOrderBo {
  /**
   * 扫描明细
   */
  List<InScanOrderDetailBo> dataList;
  /**
   * 预到货单ID
   */
  private Long orderId;
  /**
   * 预到货单号
   */
  private String orderCode;
  /**
   * 上架单号
   */
  private String shelveCode;
  /**
   * 仓库ID
   */
  private Long storageId;
  /**
   * 仓库名称
   */
  private String storageName;
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
   * 托盘号
   */
  private String plateCode;
  /**
   * LPN号
   */
  private String lpnCode;
  /**
   * 自动生成上架单
   */
  private boolean isOnShelve;
  /**
   * 收货说明
   */
  private String remark;
  /**
   * 入库类型
   */
  private InventorySourceTypeEnum scanInType;
}
