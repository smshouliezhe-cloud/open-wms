package com.yiruantong.outbound.domain.service.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.service.OutReturnDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库退货单明细视图对象 out_return_detail
 *
 * @author YiRuanTong
 * @date 2024-11-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutReturnDetail.class)
public class OutReturnDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 退货单明细ID
   */
  @ExcelProperty(value = "退货单明细ID")
  private Long returnDetailId;

  /**
   * 退货单ID
   */
  @ExcelProperty(value = "退货单ID")
  private Long returnId;

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
   * 商品规格
   */
  @ExcelProperty(value = "商品规格")
  private String productSpec;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

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
  private BigDecimal orderQuantity;

  /**
   * 退货数量
   */
  @ExcelProperty(value = "退货数量")
  private BigDecimal returnQuantity;

  /**
   * 已出货数量
   */
  @ExcelProperty(value = "已出货数量")
  private Long outedQuantity;

  /**
   * 重量
   */
  @ExcelProperty(value = "重量")
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal rowWeight;

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
   * 成本价
   */
  @ExcelProperty(value = "成本价")
  private BigDecimal purchasePrice;

  /**
   * 成本总额
   */
  @ExcelProperty(value = "成本总额")
  private BigDecimal purchaseAmount;

  /**
   * 退款售价
   */
  @ExcelProperty(value = "退款售价")
  private BigDecimal salePrice;

  /**
   * 退款金额
   */
  @ExcelProperty(value = "退款金额")
  private BigDecimal saleAmount;

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
   * 缺货数量
   */
  @ExcelProperty(value = "缺货数量")
  private Long lackStorage;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Long sortingStatus;

  /**
   * SN
   */
  @ExcelProperty(value = "SN")
  private String singleSignCode;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

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
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQty;

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
   * 实退数量
   */
  @ExcelProperty(value = "实退数量")
  private BigDecimal actualReturnQuantity;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocline;

  /**
   * 商品品牌
   */
  @ExcelProperty(value = "商品品牌")
  private String brandName;

  /**
   * 商品类别
   */
  @ExcelProperty(value = "商品类别")
  private String typeName;

  /**
   * 商品型号
   */
  @ExcelProperty(value = "商品型号")
  private String productBarCode;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String images;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

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
   * 已收货数量
   */
  @ExcelProperty(value = "已收货数量")
  private BigDecimal enterQuantity;


}
