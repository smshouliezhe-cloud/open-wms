package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageValidAdjustDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 效期信息调整明细视图对象 storage_valid_adjust_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageValidAdjustDetail.class)
public class StorageValidAdjustDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 效期调整ID
   */
  @ExcelProperty(value = "效期调整ID")
  private Long validAdjustDetailId;

  /**
   * 效期调整ID
   */
  @ExcelProperty(value = "效期调整ID")
  private Long validAdjustId;

  /**
   * 库存ID
   */
  @ExcelProperty(value = "库存ID")
  private Long inventoryId;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 库存量
   */
  @ExcelProperty(value = "库存量")
  private BigDecimal productStorage;

  /**
   * 占位量
   */
  @ExcelProperty(value = "占位量")
  private BigDecimal placeholderStorage;

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
   * 保质期天数
   */
  @ExcelProperty(value = "保质期天数")
  private Long shelfLifeDay;

  /**
   * 原生产日期
   */
  @ExcelProperty(value = "原生产日期")
  private Date produceDate;

  /**
   * 原到期日期
   */
  @ExcelProperty(value = "原到期日期")
  private Date limitDate;

  /**
   * 目标生产日期
   */
  @ExcelProperty(value = "目标生产日期")
  private Date produceDateTarget;

  /**
   * 目标到期日期
   */
  @ExcelProperty(value = "目标到期日期")
  private Date limitDateTarget;

  /**
   * 原批次号
   */
  @ExcelProperty(value = "原批次号")
  private String batchNumber;

  /**
   * 目标批次号
   */
  @ExcelProperty(value = "目标批次号")
  private String batchNumberTarget;

  /**
   * 合计毛重
   */
  @ExcelProperty(value = "合计毛重")
  private BigDecimal rowWeight;

  /**
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

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
