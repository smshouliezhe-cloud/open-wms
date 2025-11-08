package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageCheckDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 盘点单明细视图对象 storage_check_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageCheckDetail.class)
public class StorageCheckDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 盘点明细ID
   */
  @ExcelProperty(value = "盘点明细ID")
  private Long checkDetailId;

  /**
   * 盘点单ID
   */
  @ExcelProperty(value = "盘点单ID")
  private Long checkId;

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
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 账面库存量
   */
  @ExcelProperty(value = "账面库存量")
  private BigDecimal productStorage;

  /**
   * 平均成本价
   */
  @ExcelProperty(value = "平均成本价")
  private BigDecimal purchasePrice;

  /**
   * 成本额
   */
  @ExcelProperty(value = "成本额")
  private BigDecimal purchaseAmount;

  /**
   * 盘点数量
   */
  @ExcelProperty(value = "盘点数量")
  private BigDecimal checkQuantity;

  /**
   * 盘盈数量
   */
  @ExcelProperty(value = "盘盈数量")
  private BigDecimal profitQuantity;

  /**
   * 盘盈金额
   */
  @ExcelProperty(value = "盘盈金额")
  private BigDecimal profitAmount;

  /**
   * 盘亏数量
   */
  @ExcelProperty(value = "盘亏数量")
  private BigDecimal lossQuantity;

  /**
   * 盘亏金额
   */
  @ExcelProperty(value = "盘亏金额")
  private BigDecimal lossAmount;

  /**
   * 缺货数量
   */
  @ExcelProperty(value = "缺货数量")
  private Long lackStorage;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 托盘号
   */
  @ExcelProperty(value = "托盘号")
  private String plateCode;

  /**
   * 关联码
   */
  @ExcelProperty(value = "关联码")
  private String relationCode;

  /**
   * 盈亏明细ID
   */
  @ExcelProperty(value = "盈亏明细ID")
  private Long profitLossDetailId;

  /**
   * 盈亏ID
   */
  @ExcelProperty(value = "盈亏ID")
  private Long profitLossId;

  /**
   * 原始盘点明细ID
   */
  @ExcelProperty(value = "原始盘点明细ID")
  private Long fromCheckDetailId;

  /**
   * 原始盘点单ID
   */
  @ExcelProperty(value = "原始盘点单ID")
  private Long fromCheckId;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

  /**
   * 限制日期
   */
  @ExcelProperty(value = "限制日期")
  private Date limitDate;

  /**
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

  /**
   * 毛重小计
   */
  @ExcelProperty(value = "毛重小计")
  private BigDecimal rowWeight;

  /**
   * 入库时间
   */
  @ExcelProperty(value = "入库时间")
  private Date inStorageDate;

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
   * 盘盈重量
   */
  @ExcelProperty(value = "盘盈重量")
  private BigDecimal profitWeight;

  /**
   * SN号
   */
  @ExcelProperty(value = "SN号")
  private String singleSignCode;

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
   * 库存ID
   */
  @ExcelProperty(value = "库存ID")
  private Long inventoryId;

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


}
