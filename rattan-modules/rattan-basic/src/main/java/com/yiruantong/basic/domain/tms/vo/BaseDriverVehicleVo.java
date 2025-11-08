package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.BaseDriverVehicle;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 司机车辆绑定视图对象 base_driver_vehicle
 *
 * @author YRT
 * @date 2024-05-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseDriverVehicle.class)
public class BaseDriverVehicleVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车辆司机ID
   */
  @ExcelProperty(value = "车辆司机ID")
  private Long driverVehicleId;

  /**
   * 司机ID
   */
  @ExcelProperty(value = "司机ID")
  private Long driverId;

  /**
   * 司机姓名
   */
  @ExcelProperty(value = "司机姓名")
  private String driverName;

  /**
   * 车辆ID
   */
  @ExcelProperty(value = "车辆ID")
  private Long vehicleId;

  /**
   * 车辆编码
   */
  @ExcelProperty(value = "车辆编码")
  private String vehicleCode;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

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
