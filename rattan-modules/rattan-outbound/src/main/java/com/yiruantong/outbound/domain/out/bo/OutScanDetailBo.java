package com.yiruantong.outbound.domain.out.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 扫描明细出库bo数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OutScanDetailBo {

  /**
   * 销售明细ID
   */
  private Long orderDetailId;

  /**
   * 出库单ID
   */
  private Long orderId;

  /**
   * 出库单编号
   */
  private String orderCode;

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
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 订购数量
   */
  private BigDecimal quantityOrder;

  /**
   * 取消数量
   */
  private BigDecimal quantityCanceled;

  /**
   * 已开票数量
   */
  private BigDecimal quantityInvoiced;

  /**
   * 退货数量
   */
  private BigDecimal quantityRefunded;

  /**
   * 已出货数量
   */
  private BigDecimal quantityOuted;

  /**
   * 已发货数量
   */
  private BigDecimal quantityShipped;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位换算描述
   */
  private String unitConvertText;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 销售售价
   */
  private BigDecimal salePrice;

  /**
   * 折后单价
   */
  private BigDecimal salePriceDiscount;

  /**
   * 优惠金额
   */
  private BigDecimal discountAmount;

  /**
   * 退货额
   */
  private BigDecimal amountRefunded;

  /**
   * 应收总额
   */
  private BigDecimal saleAmount;

  /**
   * 已开票税务总额
   */
  private BigDecimal invoicedAmount;

  /**
   * 税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 已采购数量
   */
  private BigDecimal purchaseStorage;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 质量类型
   */
  private String qualityType;

  /**
   * 交货日期
   */
  private Date deliveryDate;

  /**
   * 波次号
   */
  private String batchNumber;

  /**
   * 生成补货单
   */
  private Long isCreateReplenishment;

  /**
   * 波次数量
   */
  private BigDecimal batchQuantity;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 有效库存
   */
  private BigDecimal validQuantity;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 原产地
   */
  private String originPlace;

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
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 是否重货
   */
  private Byte isHeavyCargo;

  /**
   * 建议拍数
   */
  private BigDecimal paiQty;

  /**
   * 打包配置
   */
  private String unitPackage;

  /**
   * 扫描完成数量
   */
  private BigDecimal finishedQuantity;

  /**
   * 配货位
   */
  private String allotPositionName;

  /**
   * 明细件数
   */
  private BigDecimal rowPackage;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * 管理SN
   */
  private Byte isManageSn;

  /**
   * 项目号
   */
  @ExcelProperty(value = "项目号")
  private String projectCode;

  /**
   * 箱号
   */
  @ExcelProperty(value = "箱号")
  private String caseNumber;

  /**
   * 包数 (湘钢)
   */
  @ExcelProperty(value = "包数")
  private Long parcelQuantity;

  /**
   * 均重 (湘钢)
   */
  @ExcelProperty(value = "均重")
  private BigDecimal poundAvg;
}
