package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.basic.domain.tms.BaseCarrierArea;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 承运商管辖区域视图对象 base_carrier_area
 *
 * @author YRT
 * @date 2025-02-19
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseCarrierArea.class)
public class BaseCarrierAreaVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 明细id
   */
  @ExcelProperty(value = "明细id")
  private Long carrierAreaId;

  /**
   * 承运商ID
   */
  @ExcelProperty(value = "承运商ID")
  private Long carrierId;

  /**
   * 省ID
   */
  @ExcelProperty(value = "省ID")
  private Long provinceId;

  /**
   * 省
   */
  @ExcelProperty(value = "省")
  private String provinceName;

  /**
   * 市ID
   */
  @ExcelProperty(value = "市ID")
  private Long cityId;

  /**
   * 市
   */
  @ExcelProperty(value = "市")
  private String cityName;

  /**
   * 区ID
   */
  @ExcelProperty(value = "区ID")
  private Long regionId;

  /**
   * 区
   */
  @ExcelProperty(value = "区")
  private String regionName;

  /**
   * 计价方式
   */
  @ExcelProperty(value = "计价方式")
  private String pricingManner;

  /**
   * 费用科目
   */
  @ExcelProperty(value = "费用科目")
  private String feeItemName;

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
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Long enable;

  /**
   * 目的地网点
   */
  @ExcelProperty(value = "目的地网点")
  private String unloadSite;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocLine;
}
