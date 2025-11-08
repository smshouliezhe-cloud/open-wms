package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.TmsFencedetail;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 围栏管理明细视图对象 tms_fenceDetail
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TmsFencedetail.class)
public class TmsFencedetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 电子围栏明细ID
   */
  @ExcelProperty(value = "电子围栏明细ID")
  private Long fenceDetailId;

  /**
   * 电子围栏ID
   */
  @ExcelProperty(value = "电子围栏ID")
  private Long fenceId;

  /**
   * 车辆ID
   */
  @ExcelProperty(value = "车辆ID")
  private Long vehicleId;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 设备ID
   */
  @ExcelProperty(value = "设备ID")
  private Long equipmentId;

  /**
   * 设备编号
   */
  @ExcelProperty(value = "设备编号")
  private String equipmentCode;

  /**
   * 设备名称
   */
  @ExcelProperty(value = "设备名称")
  private String equipmentName;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String statusText;

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
