package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.in.InDamagedOrderDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 残品入库单明细视图对象 in_damaged_order_detail
 *
 * @author YiRuanTong
 * @date 2024-01-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InDamagedOrderDetail.class)
public class InDamagedOrderDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 残品明细ID
   */
  @ExcelProperty(value = "残品明细ID")
  private Long damagedOrderDetailId;

  /**
   * 残品单ID
   */
  @ExcelProperty(value = "残品单ID")
  private Long damagedOrderId;

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
   * 条形码
   */
  @ExcelProperty(value = "条形码")
  private String productModel;

  /**
   * 产品名称
   */
  @ExcelProperty(value = "产品名称")
  private String productName;

  /**
   * 产品规格
   */
  @ExcelProperty(value = "产品规格")
  private String productSpec;

  /**
   * 关联编号
   */
  @ExcelProperty(value = "关联编号")
  private String relationCode;

  /**
   * 残品数量
   */
  @ExcelProperty(value = "残品数量")
  private BigDecimal quantity;

  /**
   * 成本单价
   */
  @ExcelProperty(value = "成本单价")
  private BigDecimal purchasePrice;

  /**
   * 成本金额
   */
  @ExcelProperty(value = "成本金额")
  private BigDecimal rowAmount;

  /**
   * 报残日期
   */
  @ExcelProperty(value = "报残日期")
  private Date damagedDate;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchOrder;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date productDate;

  /**
   * 小单位
   */
  @ExcelProperty(value = "小单位")
  private String smallUnit;

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
   * 大单位
   */
  @ExcelProperty(value = "大单位")
  private String bigUnit;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQty;

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
   * 产品型号
   */
  @ExcelProperty(value = "产品型号")
  private String productBarCode;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String images;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 含税价
   */
  @ExcelProperty(value = "含税价")
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @ExcelProperty(value = "价税合计")
  private BigDecimal rateAmount;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocLine;


}
