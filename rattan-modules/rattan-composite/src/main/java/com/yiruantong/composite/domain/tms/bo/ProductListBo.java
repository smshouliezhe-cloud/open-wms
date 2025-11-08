package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 表单的明细表二，商品信息明细
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductListBo {
  /**
   * 主表ID
   */
  private Long wayBillId;

  /**
   * 明细ID
   */
  private Long wayBillDetailId;

  /**
   * 出库明细ID
   */
  private Long orderDetailId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 商品规格
   */
  private String productSpec;

  /**
   * 总数量
   */
  private BigDecimal quantityOrder;

  /**
   * 已装载数量
   */
  private BigDecimal loadedQty;

  /**
   * 本次下发数量
   */
  private BigDecimal unloadQty;

  /**
   * 小计重量
   */
  private BigDecimal rowWeight;
  /**
   * 小计体积
   */
  private BigDecimal rowCube;
  /**
   * 单位重量
   */
  private BigDecimal weight;
  /**
   * 单位体积
   */
  private BigDecimal unitCube;
  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;
  /**
   * 打包件数
   */
  private BigDecimal rowPackage;
  /**
   * 客户单号
   */
  private String customerOrderCode;

  /**
   * 区域
   */
  private String consignorNameSale;

  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;
}
