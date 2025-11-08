package com.yiruantong.inventory.domain.replenishment.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 补货单明细视图对象 storage_replenishment_detail
 *
 * @author YRT
 * @date 2024-10-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageReplenishmentDetail.class)
public class StorageReplenishmentDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 补货单明细ID
   */
  @ExcelProperty(value = "补货单明细ID")
  private Long replenishmentDetailId;

  /**
   * 补货单ID
   */
  @ExcelProperty(value = "补货单ID")
  private Long replenishmentId;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

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
   * 补货数量
   */
  @ExcelProperty(value = "补货数量")
  private BigDecimal transferQuantity;

  /**
   * 成本价
   */
  @ExcelProperty(value = "成本价")
  private BigDecimal purchasePrice;

  /**
   * 成本金额
   */
  @ExcelProperty(value = "成本金额")
  private BigDecimal subTotalAmount;

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
   * 单据ID
   */
  @ExcelProperty(value = "单据ID")
  private Long billId;

  /**
   * 已补货数量
   */
  @ExcelProperty(value = "已补货数量")
  private BigDecimal outQuantity;

  /**
   * 拍号
   */
  @ExcelProperty(value = "拍号")
  private String plateCode;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

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
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQty;

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
   * 原货位
   */
  @ExcelProperty(value = "原货位")
  private String sortPositionName;

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


}
