package com.yiruantong.basic.domain.base.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.base.BaseCountry;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 国家信息视图对象 base_country
 *
 * @author YRT
 * @date 2024-06-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseCountry.class)
public class BaseCountryVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 国家ID
   */
  @ExcelProperty(value = "国家ID")
  private Long countryId;

  /**
   * ISO二字代码
   */
  @ExcelProperty(value = "ISO二字代码")
  private String iso2Code;

  /**
   * ISO三字代码
   */
  @ExcelProperty(value = "ISO三字代码")
  private String iso3Code;

  /**
   * 数字代码
   */
  @ExcelProperty(value = "数字代码")
  private String digitalCode;

  /**
   * 国家英文名
   */
  @ExcelProperty(value = "国家英文名")
  private String countryName;

  /**
   * 国家中文名
   */
  @ExcelProperty(value = "国家中文名")
  private String countryNameCn;

  /**
   * 区域代码
   */
  @ExcelProperty(value = "区域代码")
  private String countryRegionCode;

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


}
