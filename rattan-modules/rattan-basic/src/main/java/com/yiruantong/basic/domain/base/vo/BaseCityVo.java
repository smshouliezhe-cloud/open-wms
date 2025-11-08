package com.yiruantong.basic.domain.base.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.base.BaseCity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 省市区管理视图对象 base_city
 *
 * @author YRT
 * @date 2024-11-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseCity.class)
public class BaseCityVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 地区ID
   */
  @ExcelProperty(value = "地区ID")
  private Long cityId;

  /**
   * 地区名称
   */
  @ExcelProperty(value = "地区名称")
  private String cityName;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 所属仓库ID
   */
  @ExcelProperty(value = "所属仓库ID")
  private Long storageId;

  /**
   * 所属仓库
   */
  @ExcelProperty(value = "所属仓库")
  private String storageName;

  /**
   * 货到付款
   */
  @ExcelProperty(value = "货到付款")
  private Byte isPayAfter;

  /**
   * 发货时效
   */
  @ExcelProperty(value = "发货时效")
  private Long sendDay;

  /**
   * 层级ID
   */
  @ExcelProperty(value = "层级ID")
  private Long stepId;

  /**
   * 根ID
   */
  @ExcelProperty(value = "根ID")
  private Long rootId;

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
