package com.yiruantong.inbound.domain.in.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inbound.domain.in.InOrderPlanDetail;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 收货计划单明细视图对象 in_order_plan_detail
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InOrderPlanDetail.class)
public class InOrderPlanDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 入库计划明细ID
   */
  @ExcelProperty(value = "入库计划明细ID")
  private Long planDetailId;

  /**
   * 入库计划单ID
   */
  @ExcelProperty(value = "入库计划单ID")
  private Long planId;

  /**
   * 商品ID
   */
  @ExcelProperty(value = "商品ID")
  private Long productId;

  /**
   * 商品编号
   */
  @ExcelProperty(value = "商品编号")
  private String productCode;

  /**
   * 商品名称
   */
  @ExcelProperty(value = "商品名称")
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
   * 数量
   */
  @ExcelProperty(value = "数量")
  private BigDecimal quantityOrder;

  /**
   * 小单位
   */
  @ExcelProperty(value = "小单位")
  private String smallUnit;

  /**
   * 销售单价
   */
  @ExcelProperty(value = "销售单价")
  private BigDecimal salePrice;

  /**
   * 采购单价
   */
  @ExcelProperty(value = "采购单价")
  private BigDecimal purchasePrice;

  /**
   * 销售金额
   */
  @ExcelProperty(value = "销售金额")
  private BigDecimal rowSaleAmount;

  /**
   * 采购金额
   */
  @ExcelProperty(value = "采购金额")
  private BigDecimal rowPurchaseAmount;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

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
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String originPlace;

  /**
   * 生成日期
   */
  @ExcelProperty(value = "生成日期")
  private Date produceDate;

  /**
   * 打包配置
   */
  @ExcelProperty(value = "打包配置")
  private String unitPackage;

  /**
   * 建议拍数
   */
  @ExcelProperty(value = "建议拍数")
  private String plateCount;

  /**
   * 总件数
   */
  @ExcelProperty(value = "总件数 ")
  private BigDecimal totalPackageQty;

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
   * 供应商
   */
  @ExcelProperty(value = "供应商")
  private String providerShortName;

  /**
   * 合计重量(吨)
   */
  @ExcelProperty(value = "合计重量(吨)")
  private BigDecimal rowWeightTon;

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
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

  /**
   * 未入库数量
   */
  @ExcelProperty(value = "未入库数量")
  private BigDecimal surplusQuantity;

  /**
   * 已入库数量
   */
  @ExcelProperty(value = "已入库数量")
  private BigDecimal inQuantity;

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
   * 货主Id
   */
  @ExcelProperty(value = "货主Id")
  private Long consignorId;

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
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;

  /**
   * 来源明细编号
   */
  @ExcelProperty(value = "来源明细编号")
  private String sourceDetailCode;


}
