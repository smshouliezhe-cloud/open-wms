package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.BaseVehicleHistory;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 车辆轨迹视图对象 base_vehicle_history
 *
 * @author YRT
 * @date 2024-05-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseVehicleHistory.class)
public class BaseVehicleHistoryVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车辆轨迹id
   */
  @ExcelProperty(value = "车辆轨迹id")
  private Long vehicleHistoryId;

  /**
   * 车辆id
   */
  @ExcelProperty(value = "车辆id")
  private Long vehicleId;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 单据id
   */
  @ExcelProperty(value = "单据id")
  private Long billId;

  /**
   * 单据编号
   */
  @ExcelProperty(value = "单据编号")
  private String billCode;

  /**
   * 状态类型
   */
  @ExcelProperty(value = "状态类型")
  private String statusType;

  /**
   * 业务类型
   */
  @ExcelProperty(value = "业务类型")
  private String operationType;

  /**
   * 变更前状态
   */
  @ExcelProperty(value = "变更前状态")
  private String fromStatus;

  /**
   * 变更后状态
   */
  @ExcelProperty(value = "变更后状态")
  private String toStatus;

  /**
   * 城市名称
   */
  @ExcelProperty(value = "城市名称")
  private String cityName;

  /**
   * 邮递员
   */
  @ExcelProperty(value = "邮递员")
  private String courier;

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
   * 单据状态
   */
  @ExcelProperty(value = "单据状态")
  private String orderStatus;


}
