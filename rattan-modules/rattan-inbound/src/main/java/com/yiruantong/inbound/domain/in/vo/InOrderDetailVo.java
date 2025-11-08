package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import com.yiruantong.inbound.domain.in.InOrderDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 预到货单明细视图对象 in_order_detail
 *
 * @author YRT
 * @date 2024-12-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InOrderDetail.class)
public class InOrderDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 采购明细ID
   */
  @ExcelProperty(value = "采购明细ID")
  private Long orderDetailId;

  /**
   * 采购单ID
   */
  @ExcelProperty(value = "采购单ID")
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
   * 商品规格
   */
  @ExcelProperty(value = "商品规格")
  private String productSpec;

  /**
   * 参考URL
   */
  @ExcelProperty(value = "参考URL")
  private String url;

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
   * 数量
   */
  @ExcelProperty(value = "数量")
  private BigDecimal quantity;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  @ExcelProperty(value = "单位关系")
  private String unitConvertText;

  /**
   * 单价
   */
  @ExcelProperty(value = "单价")
  private BigDecimal purchasePrice;

  /**
   * 小计金额
   */
  @ExcelProperty(value = "小计金额")
  private BigDecimal purchaseAmount;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

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
   * 已收货数量
   */
  @ExcelProperty(value = "已收货数量")
  private BigDecimal enterQuantity;

  /**
   * 退货数量
   */
  @ExcelProperty(value = "退货数量")
  private BigDecimal returnQuantity;

  /**
   * SN号
   */
  @ExcelProperty(value = "SN号")
  private String singleSignCode;

  /**
   * 预计到货日期
   */
  @ExcelProperty(value = "预计到货日期")
  private Date deliveryDate;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 生成日期
   */
  @ExcelProperty(value = "生成日期")
  private Date produceDate;

  /**
   * 厂家拍号
   */
  @ExcelProperty(value = "厂家拍号")
  private String plateCode;

  /**
   * 关联码
   */
  @ExcelProperty(value = "关联码")
  private String relationCode;

  /**
   * 保质期
   */
  @ExcelProperty(value = "保质期")
  private BigDecimal shelfLifeDay;

  /**
   * 超收百分比
   */
  @ExcelProperty(value = "超收百分比")
  private BigDecimal overcharges;

  /**
   * 禁收日期
   */
  @ExcelProperty(value = "禁收日期")
  private Date noReceivingDate;

  /**
   * 到期日期
   */
  @ExcelProperty(value = "到期日期")
  private Date limitDate;

  /**
   * 市场采购价
   */
  @ExcelProperty(value = "市场采购价")
  private BigDecimal marketPrice;

  /**
   * 折扣率
   */
  @ExcelProperty(value = "折扣率")
  private BigDecimal discountRate;

  /**
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal rowWeight;

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
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQty;

  /**
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String originPlace;

  /**
   * 总件数
   */
  @ExcelProperty(value = "总件数")
  private BigDecimal rowPackage;

  /**
   * 建议拍数
   */
  @ExcelProperty(value = "建议拍数")
  private BigDecimal paiQty;

  /**
   * 打包配置
   */
  @ExcelProperty(value = "打包配置")
  private BigDecimal unitPackage;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String productImage;

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
   * 已上架数量
   */
  @ExcelProperty(value = "已上架数量")
  private BigDecimal shelvedQuantity;

  /**
   * 收货位
   */
  @ExcelProperty(value = "收货位")
  private String positionName;

  /**
   * 供应商ID
   */
  @ExcelProperty(value = "供应商ID")
  private Long providerId;

  /**
   * 供应商编号
   */
  @ExcelProperty(value = "供应商编号")
  private String providerCode;

  /**
   * 供应商名称
   */
  @ExcelProperty(value = "供应商名称")
  private String providerShortName;

  /**
   * 次品数量
   */
  @ExcelProperty(value = "次品数量")
  private BigDecimal defectiveQty;

  /**
   * 计划数量
   */
  @ExcelProperty(value = "计划数量")
  private BigDecimal planQty;

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
   * 类别编号
   */
  @ExcelProperty(value = "类别编号")
  private Long typeId;

  /**
   * 类别名称
   */
  @ExcelProperty(value = "类别名称")
  private String typeName;

  /**
   * 产品型号
   */
  @ExcelProperty(value = "产品型号")
  private String productBarCode;

  /**
   * 品牌ID
   */
  @ExcelProperty(value = "品牌ID")
  private Long brandId;

  /**
   * 品牌名
   */
  @ExcelProperty(value = "品牌名")
  private String brandName;

  /**
   * 合计重量（吨）
   */
  @ExcelProperty(value = "合计重量", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "吨=")
  private BigDecimal rowWeightTon;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String images;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocLine;

  /**
   * 商品属性
   */
  @ExcelProperty(value = "商品属性")
  private String productAttribute;

  /**
   * 入库日期
   */
  @ExcelProperty(value = "入库日期")
  private Date inStorageDate;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 税额
   */
  @ExcelProperty(value = "税额")
  private BigDecimal taxAmount;

  /**
   * 铅封号
   */
  @ExcelProperty(value = "铅封号")
  private String sealNo;

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
   * 皮重
   */
  @ExcelProperty(value = "皮重")
  private BigDecimal rowTareWeight;

  /**
   * 扣重
   */
  @ExcelProperty(value = "扣重")
  private BigDecimal rowDeductWeight;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceDetailCode;

  /**
   * 来源单号2
   */
  @ExcelProperty(value = "来源单号2")
  private String sourceDetailCode2;

  /**
   * 是否质检
   */
  @ExcelProperty(value = "是否质检")
  private Byte isChecking;

  /**
   * 包数
   */
  @ExcelProperty(value = "包数")
  private Long parcelQuantity;

  /**
   * 均重
   */
  @ExcelProperty(value = "均重")
  private BigDecimal parcelAverageWeight;


}
