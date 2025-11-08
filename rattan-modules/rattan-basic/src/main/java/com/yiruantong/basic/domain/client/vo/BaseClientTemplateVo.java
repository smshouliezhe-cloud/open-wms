package com.yiruantong.basic.domain.client.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.client.BaseClientTemplate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 客户运价模板视图对象 base_client_template
 *
 * @author YRT
 * @date 2024-04-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseClientTemplate.class)
public class BaseClientTemplateVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 模板ID
   */
  @ExcelProperty(value = "模板ID")
  private Long templateId;

  /**
   * 客户ID
   */
  @ExcelProperty(value = "客户ID")
  private Long clientId;

  /**
   * 费用模块
   */
  @ExcelProperty(value = "费用模块")
  private String costModule;

  /**
   * 费用科目id
   */
  @ExcelProperty(value = "费用科目id")
  private Long feeItemId;

  /**
   * 费用科目
   */
  @ExcelProperty(value = "费用科目")
  private String feeItemName;

  /**
   * 计价方式
   */
  @ExcelProperty(value = "计价方式")
  private String pricingManner;

  /**
   * 模版类型
   */
  @ExcelProperty(value = "模版类型")
  private String templateType;

  /**
   * 自动生成运费
   */
  @ExcelProperty(value = "自动生成运费")
  private Long autoBuildFreight;

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
