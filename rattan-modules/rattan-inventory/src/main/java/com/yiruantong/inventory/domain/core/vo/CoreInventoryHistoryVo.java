package com.yiruantong.inventory.domain.core.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
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
 * WMS库存变化推送视图对象 core_inventory_history
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoreInventoryHistory.class)
public class CoreInventoryHistoryVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long historyId;

  /**
   * 来源类型
   */
  @ExcelProperty(value = "来源类型")
  private String sourceType;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String billCode;

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
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
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
   * 入库数量
   */
  @ExcelProperty(value = "入库数量")
  private BigDecimal inQuantity;

  /**
   * 出库数量
   */
  @ExcelProperty(value = "出库数量")
  private BigDecimal outQuantity;

  /**
   * 入库金额
   */
  @ExcelProperty(value = "入库金额")
  private BigDecimal inAmount;

  /**
   * 出库金额
   */
  @ExcelProperty(value = "出库金额")
  private BigDecimal outAmount;

  /**
   * 操作前库存数量
   */
  @ExcelProperty(value = "操作前库存数量")
  private BigDecimal beforeQuantity;

  /**
   * 操作后库存数量
   */
  @ExcelProperty(value = "操作后库存数量")
  private BigDecimal afterQuantity;

  /**
   * 操作后库存金额
   */
  @ExcelProperty(value = "操作后库存金额")
  private BigDecimal afterAmount;

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
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

  /**
   * 拍号
   */
  @ExcelProperty(value = "拍号")
  private String plateCode;

  /**
   * 入库重量
   */
  @ExcelProperty(value = "入库重量")
  private BigDecimal inWeight;

  /**
   * 出库重量
   */
  @ExcelProperty(value = "出库重量")
  private BigDecimal outWeight;

  /**
   * 操作前重量
   */
  @ExcelProperty(value = "操作前重量")
  private BigDecimal beforeWeight;

  /**
   * 最终重量
   */
  @ExcelProperty(value = "最终重量")
  private BigDecimal afterWeight;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

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
   * 来源单号2
   */
  @ExcelProperty(value = "来源单号2")
  private String sourceCode2;

  /**
   * 来源单号3
   */
  @ExcelProperty(value = "来源单号3")
  private String sourceCode3;

  /**
   * 拍数
   */
  @ExcelProperty(value = "拍数")
  private BigDecimal plateQty;

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
   * 库存ID
   */
  @ExcelProperty(value = "库存ID")
  private Long inventoryId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

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
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;


}
