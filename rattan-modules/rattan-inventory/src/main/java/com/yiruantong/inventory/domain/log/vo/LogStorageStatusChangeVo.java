package com.yiruantong.inventory.domain.log.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.log.LogStorageStatusChange;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 状态转变日志视图对象 log_storage_status_change
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = LogStorageStatusChange.class)
public class LogStorageStatusChangeVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 日志ID
   */
  @ExcelProperty(value = "日志ID")
  private Long logId;

  /**
   * result
   */
  @ExcelProperty(value = "result")
  private String result;

  /**
   * 操作类型
   */
  @ExcelProperty(value = "操作类型")
  private String operationType;

  /**
   * 库存ID
   */
  @ExcelProperty(value = "库存ID")
  private Long inventoryId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String billCode;

  /**
   * 入库单号
   */
  @ExcelProperty(value = "入库单号")
  private String enterCode;

  /**
   * 预到货单号
   */
  @ExcelProperty(value = "预到货单号")
  private String orderCode;

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
   * 保质期天数
   */
  @ExcelProperty(value = "保质期天数")
  private Long shelfLifeDay;

  /**
   * 库存保质期
   */
  @ExcelProperty(value = "库存保质期")
  private Date shelfLifeDate;

  /**
   * 最长库存天数
   */
  @ExcelProperty(value = "最长库存天数")
  private Long validShelfLifeDay;

  /**
   * 仓库状态
   */
  @ExcelProperty(value = "仓库状态")
  private String storageStatus;

  /**
   * 产品属性
   */
  @ExcelProperty(value = "产品属性")
  private String productAttribute;

  /**
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源主表ID
   */
  @ExcelProperty(value = "来源主表ID")
  private Long sourceMainId;

  /**
   * 来源明细ID
   */
  @ExcelProperty(value = "来源明细ID")
  private Long sourceDetailId;

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
   * 商品条码
   */
  @ExcelProperty(value = "商品条码")
  private String productModel;

  /**
   * 商品规格
   */
  @ExcelProperty(value = "商品规格")
  private String productSpec;

  /**
   * SN
   */
  @ExcelProperty(value = "SN")
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
   * 供应商名称
   */
  @ExcelProperty(value = "供应商名称")
  private String providerShortName;

  /**
   * 库存量
   */
  @ExcelProperty(value = "库存量")
  private Long productStorage;

  /**
   * 原始库存量
   */
  @ExcelProperty(value = "原始库存量")
  private Long originStorage;

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


}
