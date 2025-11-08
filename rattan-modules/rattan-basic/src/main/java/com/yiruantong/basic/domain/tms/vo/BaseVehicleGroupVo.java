package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.BaseVehicleGroup;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 车队管理视图对象 base_vehicle_group
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseVehicleGroup.class)
public class BaseVehicleGroupVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车队ID
   */
  @ExcelProperty(value = "车队ID")
  private Long vehicleGroupId;

  /**
   * 车队编号
   */
  @ExcelProperty(value = "车队编号")
  private String vehicleGroupCode;

  /**
   * 车队名称
   */
  @ExcelProperty(value = "车队名称")
  private String vehicleGroupName;

  /**
   * 车队负责人
   */
  @ExcelProperty(value = "车队负责人")
  private String teamLeader;

  /**
   * 负责人电话
   */
  @ExcelProperty(value = "负责人电话")
  private String personCharge;

  /**
   * 是否开票
   */
  @ExcelProperty(value = "是否开票")
  private String whetherInvoice;

  /**
   * 票据类型
   */
  @ExcelProperty(value = "票据类型")
  private String billType;

  /**
   * 开票税点
   */
  @ExcelProperty(value = "开票税点")
  private String billingTaxPoint;

  /**
   * 司机
   */
  @ExcelProperty(value = "司机")
  private String driverName;

  /**
   * 司机ID
   */
  @ExcelProperty(value = "司机ID")
  private String driverId;

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

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Long enable;


}
