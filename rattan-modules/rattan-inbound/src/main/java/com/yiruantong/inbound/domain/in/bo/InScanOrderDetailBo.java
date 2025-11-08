package com.yiruantong.inbound.domain.in.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 扫描明细入库bo数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InScanOrderDetailBo {

  /**
   * 入库明细ID
   */
  private Long orderDetailId;

  /**
   * 入库单ID
   */
  private Long orderId;

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
   * 商品规格
   */
  private String productSpec;

  /**
   * 参考URL
   */
  private String url;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 数量
   */
  private BigDecimal quantity;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 折扣单价
   */
  private BigDecimal purchasePrice;

  /**
   * 入库金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 已收货数量
   */
  private BigDecimal enterQuantity;

  /**
   * 退货数量
   */
  private BigDecimal returnQuantity;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 预计到货日期
   */
  private Date deliveryDate;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生成日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date produceDate;

  /**
   * 厂家拍号
   */
  private String plateCode;

  /**
   * 关联码
   */
  private String relationCode;

  /**
   * 保质期
   */
  private BigDecimal shelfLifeDay;

  /**
   * 超收百分比
   */
  private BigDecimal overcharges;

  /**
   * 禁收日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date noReceivingDate;

  /**
   * 到期日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
  private Date limitDate;

  /**
   * 市场入库价
   */
  private BigDecimal marketPrice;

  /**
   * 折扣率
   */
  private BigDecimal discountRate;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 原产地
   */
  private String originPlace;

  /**
   * 总件数
   */
  private BigDecimal rowPackage;

  /**
   * 建议拍数
   */
  private BigDecimal paiQty;

  /**
   * 打包配置
   */
  private BigDecimal unitPackage;

  /**
   * 图片
   */
  private String productImage;

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
   * 收货位
   */
  private String positionName;

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
   * 次品数量
   */
  private BigDecimal defectiveQty;

  /**
   * 计划数量
   */
  private BigDecimal planQty;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 类别编号
   */
  private Long typeId;

  /**
   * 类别名称
   */
  private String typeName;

  /**
   * 产品型号
   */
  private String productBarCode;

  /**
   * 品牌ID
   */
  private Long brandId;

  /**
   * 品牌名
   */
  private String brandName;

  /**
   * 合计重量（吨）
   */
  private BigDecimal rowWeightTon;

  /**
   * 图片
   */
  private String images;

  /**
   * 扫描入库数量
   */
  private BigDecimal finishedQuantity;

  /**
   * 上架货位
   */
  private String shelvePositionName;

  /**
   * 上架单明细
   */
  private Long shelveDetailId;

  /**
   * 生产日期
   */
  private Date inStorageDate;

  /**
   * 商品属性
   */
  private String productAttribute;

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
   * 包数
   */
  private BigDecimal parcelQuantity;

  /**
   * 均重
   */
  private BigDecimal parcelAverageWeight;

  /**
   * 是否可以直接状态为收货完成
   */
  private Boolean isAccomplish;

  /**
   * 唛头
   */
  private String marking;

  /**
   * 箱型
   */
  private String containerType;

  /**
   * 包装种类
   */
  private String packagingType;

}
