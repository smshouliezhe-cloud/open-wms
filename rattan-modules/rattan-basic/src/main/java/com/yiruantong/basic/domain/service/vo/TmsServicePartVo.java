package com.yiruantong.basic.domain.service.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.service.TmsServicePart;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 维修配件管理视图对象 tms_service_part
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TmsServicePart.class)
public class TmsServicePartVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 配件ID
   */
  @ExcelProperty(value = "配件ID")
  private Long servicePartId;

  /**
   * 配件编号
   */
  @ExcelProperty(value = "配件编号")
  private String partCode;

  /**
   * 配件名称
   */
  @ExcelProperty(value = "配件名称")
  private String partName;

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
