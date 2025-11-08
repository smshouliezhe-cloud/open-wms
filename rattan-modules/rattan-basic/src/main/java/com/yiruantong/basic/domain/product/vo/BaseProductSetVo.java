package com.yiruantong.basic.domain.product.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.product.BaseProductSet;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品套装主视图对象 base_product_set
 *
 * @author YRT
 * @date 2023-12-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseProductSet.class)
public class BaseProductSetVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 商品拆分ID
   */
  @ExcelProperty(value = "商品拆分ID")
  private Long productSetId;

  /**
   * 订单渠道
   */
  @ExcelProperty(value = "订单渠道")
  private Byte orderChannel;

  /**
   * ERP店铺ID
   */
  @ExcelProperty(value = "ERP店铺ID")
  private Long storeInfoId;

  /**
   * 店铺名称
   */
  @ExcelProperty(value = "店铺名称")
  private String storeName;

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
   * 产品条码
   */
  @ExcelProperty(value = "产品条码")
  private String productModel;

  /**
   * 产品规格
   */
  @ExcelProperty(value = "产品规格")
  private String productSpec;

  /**
   * 销售售价
   */
  @ExcelProperty(value = "销售售价")
  private BigDecimal salePrice;

  /**
   * 原始售价
   */
  @ExcelProperty(value = "原始售价")
  private BigDecimal originalPrice;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 成本价
   */
  @ExcelProperty(value = "成本价")
  private BigDecimal purchasePrice;

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
   * 货主名字
   */
  @ExcelProperty(value = "货主名字")
  private String consignorName;

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
