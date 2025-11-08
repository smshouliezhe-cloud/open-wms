package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjustDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 库存成本价调整明细视图对象 storage_purchase_price_adjust_detail
 *
 * @author YRT
 * @date 2023-12-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StoragePurchasePriceAdjustDetail.class)
public class StoragePurchasePriceAdjustDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 成本价调整明细ID
   */
  @ExcelProperty(value = "成本价调整明细ID")
  private Long purchasePriceAdjustDetailId;

  /**
   * 成本价调整单ID
   */
  @ExcelProperty(value = "成本价调整单ID")
  private Long purchasePriceAdjustId;

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
   * 货位数量
   */
  @ExcelProperty(value = "货位数量")
  private BigDecimal productStorage;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 成本价
   */
  @ExcelProperty(value = "成本价")
  private BigDecimal purchasePrice;

  /**
   * 成本额
   */
  @ExcelProperty(value = "成本额")
  private BigDecimal purchaseAmount;

  /**
   * 调整后成本价
   */
  @ExcelProperty(value = "调整后成本价")
  private BigDecimal adjustPrice;

  /**
   * 差价
   */
  @ExcelProperty(value = "差价")
  private BigDecimal diffPrice;

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
   * 合计毛重
   */
  @ExcelProperty(value = "合计毛重")
  private BigDecimal rowWeight;

  /**
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal adjustWeight;

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
   * 库存ID
   */
  @ExcelProperty(value = "库存ID")
  private Long inventoryId;


}
