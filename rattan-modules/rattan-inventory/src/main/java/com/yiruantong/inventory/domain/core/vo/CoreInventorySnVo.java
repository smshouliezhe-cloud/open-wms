package com.yiruantong.inventory.domain.core.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
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
 * 库存明细SN视图对象 core_inventory_sn
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoreInventorySn.class)
public class CoreInventorySnVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * SN Id
   */
  @ExcelProperty(value = "SN Id")
  private Long snId;

  /**
   * 库存ID
   */
  @ExcelProperty(value = "库存ID")
  private Long inventoryId;

  /**
   * SN号
   */
  @ExcelProperty(value = "SN号")
  private String snNo;

  /**
   * 出库类型
   */
  @ExcelProperty(value = "出库类型")
  private String outType;

  /**
   * 出库单ID
   */
  @ExcelProperty(value = "出库单ID")
  private Long outBillId;

  /**
   * 出库编码
   */
  @ExcelProperty(value = "出库编码")
  private String outBillCode;

  /**
   * 出库时间
   */
  @ExcelProperty(value = "出库时间")
  private Date outDate;

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
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;

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
   * 入库时间
   */
  @ExcelProperty(value = "入库时间")
  private Date inStorageDate;

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


}
