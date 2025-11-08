package com.yiruantong.inventory.domain.core.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inventory.domain.core.CoreInventory;
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
 * 库存调整选择器视图对象 core_inventory
 *
 * @author YiRuanTong
 * @date 2025-03-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoreInventory.class)
public class CoreInventoryVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 库存ID
   */
  @ExcelProperty(value = "库存ID")
  private Long inventoryId;

  /**
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源主表ID
   */
  @ExcelProperty(value = "来源主表ID")
  private Long mainId;

  /**
   * 来源明细ID
   */
  @ExcelProperty(value = "来源明细ID")
  private Long detailId;

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
   * 默认货位
   */
  @ExcelProperty(value = "默认货位")
  private String positionName;

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
   * 定制唯一码
   */
  @ExcelProperty(value = "定制唯一码")
  private String singleSignCode;

  /**
   * 入库时间
   */
  @ExcelProperty(value = "入库时间")
  private Date inStorageDate;

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
   * 供应商简称
   */
  @ExcelProperty(value = "供应商简称")
  private String providerShortName;

  /**
   * 库存量
   */
  @ExcelProperty(value = "库存量")
  private BigDecimal productStorage;

  /**
   * 原始库存量
   */
  @ExcelProperty(value = "原始库存量")
  private BigDecimal originStorage;

  /**
   * 进货价
   */
  @ExcelProperty(value = "进货价")
  private BigDecimal purchasePrice;

  /**
   * 进货总额
   */
  @ExcelProperty(value = "进货总额")
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
   * 移动平均价
   */
  @ExcelProperty(value = "移动平均价")
  private BigDecimal avgPrice;

  /**
   * 执行单号
   */
  @ExcelProperty(value = "执行单号")
  private String billCode;

  /**
   * 入库单号
   */
  @ExcelProperty(value = "入库单号")
  private String enterCode;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
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
   * 拍号
   */
  @ExcelProperty(value = "拍号")
  private String plateCode;

  /**
   * 关联号
   */
  @ExcelProperty(value = "关联号")
  private String relationCode;

  /**
   * 保质期天数
   */
  @ExcelProperty(value = "保质期天数")
  private BigDecimal shelfLifeDay;

  /**
   * 库存保质期
   */
  @ExcelProperty(value = "库存保质期")
  private Date shelfLifeDate;

  /**
   * 最长库存天数
   */
  @ExcelProperty(value = "最长库存天数")
  private BigDecimal validShelfLifeDay;

  /**
   * 库存状态
   */
  @ExcelProperty(value = "库存状态")
  private String storageStatus;

  /**
   * 产品属性
   */
  @ExcelProperty(value = "产品属性")
  private String productAttribute;

  /**
   * 到期日期
   */
  @ExcelProperty(value = "到期日期")
  private Date limitDate;

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
   * 原始毛重
   */
  @ExcelProperty(value = "原始毛重")
  private BigDecimal rowWeightOrigin;

  /**
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String originPlace;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 明细扩展字段
   */
  @ExcelProperty(value = "明细扩展字段")
  private Map<String, Object> detailExpandFields;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

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
   * 原始净重
   */
  @ExcelProperty(value = "原始净重")
  private BigDecimal rowNetWeightOrigin;

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
   * 占位量
   */
  @ExcelProperty(value = "占位量")
  private BigDecimal holderStorage;

  /**
   * 有效库存
   */
  @ExcelProperty(value = "有效库存")
  private BigDecimal validStorage;

  /**
   * 合计重量(吨)
   */
  @ExcelProperty(value = "合计重量(吨)")
  private BigDecimal rowWeightTon;

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
   * 包数
   */
  @ExcelProperty(value = "包数")
  private BigDecimal parcelQuantity;

  /**
   * 均重
   */
  @ExcelProperty(value = "均重")
  private BigDecimal parcelAverageWeight;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;


}
