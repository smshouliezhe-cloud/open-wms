package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库单明细与占位表里个别字段
 *
 * @author YiRuanTong
 * @date 2023-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrder.class)
public class OutOrderDetailHolderComposeVO extends OutOrderDetail implements Serializable {
  /**
   * 销售明细ID
   */
  @ExcelProperty(value = "销售明细ID")
  private Long orderDetailId;

  /**
   * 销售ID
   */
  @ExcelProperty(value = "销售ID")
  private Long orderId;

  /**
   * 产品ID
   */
  @ExcelProperty(value = "产品ID")
  private Long productId;

  /**
   * 产品编号
   */
  @ExcelProperty(value = "产品编号")
  private String productCode;

  /**
   * 产品名称
   */
  @ExcelProperty(value = "产品名称")
  private String productName;

  /**
   * 条形码
   */
  @ExcelProperty(value = "条形码")
  private String productModel;

  /**
   * 产品规格
   */
  @ExcelProperty(value = "产品规格")
  private String productSpec;

  /**
   * 小单位
   */
  @ExcelProperty(value = "小单位")
  private String smallUnit;

  /**
   * 大单位
   */
  @ExcelProperty(value = "大单位")
  private String bigUnit;

  /**
   * 订购数量
   */
  @ExcelProperty(value = "订购数量")
  private BigDecimal quantityOrder;

  /**
   * 取消数量
   */
  @ExcelProperty(value = "取消数量")
  private BigDecimal quantityCanceled;

  /**
   * 已开票数量
   */
  @ExcelProperty(value = "已开票数量")
  private BigDecimal quantityInvoiced;

  /**
   * 退货数量
   */
  @ExcelProperty(value = "退货数量")
  private BigDecimal quantityRefunded;

  /**
   * 已出货数量
   */
  @ExcelProperty(value = "已出货数量")
  private BigDecimal quantityOuted;

  /**
   * 已发货数量
   */
  @ExcelProperty(value = "已发货数量")
  private BigDecimal quantityShipped;

  /**
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

  /**
   * 单位换算描述
   */
  @ExcelProperty(value = "单位换算描述")
  private String unitConvertText;

  /**
   * 成本价
   */
  @ExcelProperty(value = "成本价")
  private BigDecimal purchasePrice;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 销售售价
   */
  @ExcelProperty(value = "销售售价")
  private BigDecimal salePrice;

  /**
   * 折后单价
   */
  @ExcelProperty(value = "折后单价")
  private BigDecimal salePriceDiscount;

  /**
   * 优惠金额
   */
  @ExcelProperty(value = "优惠金额")
  private BigDecimal discountAmount;

  /**
   * 退货额
   */
  @ExcelProperty(value = "退货额")
  private BigDecimal amountRefunded;

  /**
   * 应收总额
   */
  @ExcelProperty(value = "应收总额")
  private BigDecimal saleAmount;

  /**
   * 已开票税务总额
   */
  @ExcelProperty(value = "已开票税务总额")
  private BigDecimal invoicedAmount;

  /**
   * 税价
   */
  @ExcelProperty(value = "税价")
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @ExcelProperty(value = "价税合计")
  private BigDecimal rateAmount;

  /**
   * 缺货数量
   */
  @ExcelProperty(value = "缺货数量")
  private BigDecimal lackStorage;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Byte sortingStatus;

  /**
   * 已采购数量
   */
  @ExcelProperty(value = "已采购数量")
  private BigDecimal purchaseStorage;

  /**
   * SN号
   */
  @ExcelProperty(value = "SN号")
  private String singleSignCode;

  /**
   * 质量类型
   */
  @ExcelProperty(value = "质量类型")
  private String qualityType;

  /**
   * 交货日期
   */
  @ExcelProperty(value = "交货日期")
  private Date deliveryDate;

  /**
   * 波次号
   */
  @ExcelProperty(value = "波次号")
  private String batchNumber;

  /**
   * 生成补货单
   */
  @ExcelProperty(value = "生成补货单")
  private Long isCreateReplenishment;

  /**
   * 波次数量
   */
  @ExcelProperty(value = "波次数量")
  private BigDecimal batchQuantity;

  /**
   * 货主编号
   */
  @ExcelProperty(value = "货主编号")
  private String consignorCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal rowWeight;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 有效库存
   */
  @ExcelProperty(value = "有效库存")
  private BigDecimal validQuantity;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String originPlace;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;


  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 单位净重
   */
  @ExcelProperty(value = "单位净重")
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  @ExcelProperty(value = "小计净重")
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源主表ID
   */
  @ExcelProperty(value = "来源主表ID")
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  @ExcelProperty(value = "来源明细ID")
  private String sourceDetailId;

  /**
   * 单位体积
   */
  @ExcelProperty(value = "单位体积")
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @ExcelProperty(value = "小计体积")
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQty;

  /**
   * 是否重货
   */
  @ExcelProperty(value = "是否重货")
  private Byte isHeavyCargo;

  /**
   * 建议拍数
   */
  @ExcelProperty(value = "建议拍数")
  private BigDecimal paiQty;

  /**
   * 打包配置
   */
  @ExcelProperty(value = "打包配置")
  private String unitPackage;

  /**
   * 合计重量吨
   */
  @ExcelProperty(value = "合计重量吨")
  private BigDecimal rowWeightTon;

  /**
   * 采购商ID
   */
  private Long providerId;

  /**
   * 采购商编号
   */
  private String providerCode;

  /**
   * 采购商名称
   */
  private String providerShortName;

  /**
   * 品牌ID
   */
  @NotNull(message = "品牌ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long brandId;

  /**
   * 品牌
   */
  @NotBlank(message = "品牌不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandName;

  /**
   * 类别
   */
  @ExcelProperty(value = "类别")
  private String typeName;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocline;

  /**
   * 产品型号
   */
  @ExcelProperty(value = "产品型号")
  private String productBarCode;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String images;

}
