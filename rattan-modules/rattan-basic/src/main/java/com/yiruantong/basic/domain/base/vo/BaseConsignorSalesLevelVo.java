package com.yiruantong.basic.domain.base.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.base.BaseConsignorSalesLevel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 门店销售等级设置视图对象 base_consignor_sales_level
 *
 * @author YRT
 * @date 2025-01-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseConsignorSalesLevel.class)
public class BaseConsignorSalesLevelVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 销售等级id
   */
  @ExcelProperty(value = "销售等级id")
  private Long salesLevelId;

  /**
   * 销售等级S
   */
  @ExcelProperty(value = "销售等级S")
  private String salesLevelS;

  /**
   * 销售等级A
   */
  @ExcelProperty(value = "销售等级A")
  private String salesLevelA;

  /**
   * 销售等级B
   */
  @ExcelProperty(value = "销售等级B")
  private String salesLevelB;

  /**
   * 销售等级C
   */
  @ExcelProperty(value = "销售等级C")
  private String salesLevelC;

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


}
