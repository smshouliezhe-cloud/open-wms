package com.yiruantong.inventory.domain.core.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import com.yiruantong.inventory.domain.core.CoreInventory;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 库存调整选择器视图对象 core_inventory
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Data
public class CoreInventoryComposeVo extends CoreInventory implements Serializable {
  /*******************************
   * 以下为货位字段
   *******************************/
  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  BigDecimal bigQty;
  /**
   * 是否冻结
   */
  @ExcelProperty(value = "是否冻结")
  private Byte isFreeze;
  /**
   * 最大容量
   */
  @ExcelProperty(value = "最大容量")
  private Long maxCapacity;
  /**
   * 通道代码
   */
  @ExcelProperty(value = "通道代码")
  private String channelCode;
  /**
   * 面
   */
  @ExcelProperty(value = "面")
  private String lineCode;
  /**
   * 货架号
   */
  @ExcelProperty(value = "货架号")
  private String shelveCode;
  /**
   * 列
   */
  @ExcelProperty(value = "列")
  private String columnCode;
  /**
   * 层
   */
  @ExcelProperty(value = "层")
  private String rowCode;
  /**
   * 库区
   */
  @ExcelProperty(value = "库区")
  private String areaCode;
  /**
   * 摆放模式
   */
  @ExcelProperty(value = "摆放模式")
  private String shelveMode;
  /**
   * 是否支持混物料编号
   */
  @ExcelProperty(value = "是否支持混物料编号")
  private Byte isMixProduct;
  /**
   * 货位长度
   */
  @ExcelProperty(value = "货位长度")
  private BigDecimal positionLength;
  /**
   * 货位宽度
   */
  @ExcelProperty(value = "货位宽度")
  private BigDecimal positionWidth;
  /**
   * 最低库存
   */
  @ExcelProperty(value = "最低库存")
  private Long minCapacity;
  /**
   * 库区排序号
   */
  @ExcelProperty(value = "库区排序号")
  private Long areaOrderNum;

  /*******************************
   * 以下为占位表的数据
   *******************************/
  /**
   * 货位类型
   */
  @ExcelProperty(value = "货位宽度")
  private Byte positionType;
  /**
   * 有效库存
   */
  @ExcelProperty(value = "有效库存")
  private BigDecimal validStorage;
  /**
   * 占位量
   */
  @ExcelProperty(value = "占位量")
  private BigDecimal holderStorage;
  /**
   * 大单位
   */
  @ExcelProperty(value = "大单位")
  private String bigUnit;
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
   * 库存单位
   */
  @ExcelProperty(value = "库存单位")
  private String smallUnit;

  /**
   * 销售价
   */
  @ExcelProperty(value = "销售价")
  private BigDecimal salePrice;

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
   * 上架数量
   */
  @ExcelProperty(value = "上架数量")
  private BigDecimal shelveQuantity;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

  /**
   * 类别ID
   */
  @ExcelProperty(value = "类别ID")
  private Long typeId;

  /**
   * 停售提前时长(天)
   */
  @ExcelProperty(value = "停售提前时长(天)")
  private Long stopSaleDay;

  /**
   * 库存有效期天数
   */
  @ExcelProperty(value = "库存有效期天数")
  private BigDecimal validityDay;

  /**
   * 保质期天数
   */
  @ExcelProperty(value = "保质期天数")
  private BigDecimal shelfLifeDay;

  /**
   * 提前预警剩余天数
   */
  @ExcelProperty(value = "提前预警剩余天数")
  private BigDecimal storageAdvanceDay;

  /**
   * 入库库龄(天)
   */
  @ExcelProperty(value = "入库库龄(天)")
  private BigDecimal inDay;

  /**
   * 生产库龄
   */
  @ExcelProperty(value = "生产库龄")
  private BigDecimal produceDay;

  /**
   * 低于量
   */
  @ExcelProperty(value = "低于量")
  private BigDecimal diffStorage;

  /**
   * 高于量
   */
  @ExcelProperty(value = "高于量")
  private BigDecimal aboveQuantity;

  /**
   * 转移货位
   */
  @ExcelProperty(value = "转移货位")
  private BigDecimal positionNames;
  /**
   * 管理SN
   */
  @ExcelProperty(value = "管理SN")
  private Byte isManageSn;
}
