package com.yiruantong.basic.domain.base.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.base.BasePark;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 物流园区视图对象 base_park
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePark.class)
public class BaseParkVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 园区ID
   */
  @ExcelProperty(value = "园区ID")
  private Long parkId;

  /**
   * 园区名称
   */
  @ExcelProperty(value = "园区名称")
  private String parkName;

  /**
   * 地图地址
   */
  @ExcelProperty(value = "地图地址")
  private String mapAddress;

  /**
   * 详细地址
   */
  @ExcelProperty(value = "详细地址")
  private String detailAddress;

  /**
   * 经度
   */
  @ExcelProperty(value = "经度")
  private BigDecimal longitude;

  /**
   * 纬度
   */
  @ExcelProperty(value = "纬度")
  private BigDecimal latitude;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 百度地图
   */
  @ExcelProperty(value = "百度地图")
  private String baiduMap;

  /**
   * 经度
   */
  @ExcelProperty(value = "经度")
  private String lng;

  /**
   * 维度
   */
  @ExcelProperty(value = "维度")
  private String lat;

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

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;


}
