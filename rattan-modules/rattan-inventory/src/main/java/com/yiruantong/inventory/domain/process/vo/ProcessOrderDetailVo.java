package com.yiruantong.inventory.domain.process.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.process.ProcessOrderDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 加工列明细视图对象 process_order_detail
 *
 * @author YRT
 * @date 2025-01-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ProcessOrderDetail.class)
public class ProcessOrderDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 明细id
   */
  @ExcelProperty(value = "明细id")
  private Long processListId;

  /**
   * 主表id
   */
  @ExcelProperty(value = "主表id")
  private Long processId;

  /**
   * 来源id
   */
  @ExcelProperty(value = "来源id")
  private Long billId;

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
   * 出货仓库ID
   */
  @ExcelProperty(value = "出货仓库ID")
  private Long storageId;

  /**
   * 出货仓库
   */
  @ExcelProperty(value = "出货仓库")
  private String storageName;

  /**
   * 平台主表id
   */
  @ExcelProperty(value = "平台主表id")
  private Long sourceMainId;

  /**
   * 平台明细id
   */
  @ExcelProperty(value = "平台明细id")
  private Long sourceListId;

  /**
   * 所属仓位
   */
  @ExcelProperty(value = "所属仓位")
  private String areaId;

  /**
   * 投料重量
   */
  @ExcelProperty(value = "投料重量")
  private BigDecimal feedingWeight;

  /**
   * 投料重量单位
   */
  @ExcelProperty(value = "投料重量单位")
  private String feedingWeightUnit;

  /**
   * 投料数量
   */
  @ExcelProperty(value = "投料数量")
  private BigDecimal feedingNumber;

  /**
   * 投料数量单位
   */
  @ExcelProperty(value = "投料数量单位")
  private String feedingNumberUnit;

  /**
   * 产出重量
   */
  @ExcelProperty(value = "产出重量")
  private BigDecimal outputWeight;

  /**
   * 产出重量单位
   */
  @ExcelProperty(value = "产出重量单位")
  private String outputWeightUnit;

  /**
   * 产出件数
   */
  @ExcelProperty(value = "产出件数")
  private Long outputNumber;

  /**
   * 产出件数单位
   */
  @ExcelProperty(value = "产出件数单位")
  private String outputNumberUnit;

  /**
   * 出成率
   */
  @ExcelProperty(value = "出成率")
  private String yieId;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String orderStatus;

  /**
   * 仓位
   */
  @ExcelProperty(value = "仓位")
  private String areaName;

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
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

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
