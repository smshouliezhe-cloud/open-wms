package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.TmsVehicleAccess;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 车辆出入信息视图对象 tms_vehicle_access
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TmsVehicleAccess.class)
public class TmsVehicleAccessVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车辆出入ID
   */
  @ExcelProperty(value = "车辆出入ID")
  private Long vehicleAccessId;

  /**
   * 车辆出入时间
   */
  @ExcelProperty(value = "车辆出入时间")
  private String takeGoodsCarCode;

  /**
   * 装载计划编号
   */
  @ExcelProperty(value = "装载计划编号")
  private String outLoadCode;

  /**
   * 车辆出入时间
   */
  @ExcelProperty(value = "车辆出入时间")
  private Date enterExitTime;

  /**
   * 进出
   */
  @ExcelProperty(value = "进出")
  private Long enterExitType;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String carrierPlateNumber;

  /**
   * 车辆性质
   */
  @ExcelProperty(value = "车辆性质")
  private String carrierPlateType;

  /**
   * 承运车辆驾驶员姓名
   */
  @ExcelProperty(value = "承运车辆驾驶员姓名")
  private String carrierDriverName;

  /**
   * 承运车辆驾驶员身份证号
   */
  @ExcelProperty(value = "承运车辆驾驶员身份证号")
  private String carrierDriverIdcard;

  /**
   * 押运员姓名
   */
  @ExcelProperty(value = "押运员姓名")
  private String escort;

  /**
   * 押运员身份证号
   */
  @ExcelProperty(value = "押运员身份证号")
  private String escortIdcard;

  /**
   * 上报时间
   */
  @ExcelProperty(value = "上报时间")
  private Date reportTime;

  /**
   * 到场日期
   */
  @ExcelProperty(value = "到场日期")
  private Date takeGoodsDate;

  /**
   * 离场日期
   */
  @ExcelProperty(value = "离场日期")
  private Date outGoodsDate;

  /**
   * 车辆入场过磅重
   */
  @ExcelProperty(value = "车辆入场过磅重")
  private BigDecimal overWeightInCar;

  /**
   * 车辆出场过磅重
   */
  @ExcelProperty(value = "车辆出场过磅重")
  private BigDecimal overWeightOutCar;

  /**
   * 过磅货重
   */
  @ExcelProperty(value = "过磅货重")
  private BigDecimal overWeight;

  /**
   * 是否带货出场
   */
  @ExcelProperty(value = "是否带货出场")
  private Long isBringGoodsOut;

  /**
   * wms系统出库重量
   */
  @ExcelProperty(value = "wms系统出库重量")
  private BigDecimal systemWeightOut;

  /**
   * 差值
   */
  @ExcelProperty(value = "差值")
  private BigDecimal difference;

  /**
   * 订单状态
   */
  @ExcelProperty(value = "订单状态")
  private String orderStatus;

  /**
   * 联系方式
   */
  @ExcelProperty(value = "联系方式")
  private String contactInformation;

  /**
   * 停车区域
   */
  @ExcelProperty(value = "停车区域")
  private String parkingArea;

  /**
   * 发布日期
   */
  @ExcelProperty(value = "发布日期")
  private Date releaseDate;

  /**
   * 发布人ID
   */
  @ExcelProperty(value = "发布人ID")
  private String releaseUserId;

  /**
   * 发布人
   */
  @ExcelProperty(value = "发布人")
  private String releaseUserTrueName;

  /**
   * 办单结算时间
   */
  @ExcelProperty(value = "办单结算时间")
  private Date bulkLoadingDate;

  /**
   * 车牌省份
   */
  @ExcelProperty(value = "车牌省份")
  private String carProvince;

  /**
   * 车牌市区
   */
  @ExcelProperty(value = "车牌市区")
  private String carCity;

  /**
   * 车牌字码
   */
  @ExcelProperty(value = "车牌字码")
  private String carNum;

  /**
   * 经办人ID
   */
  @ExcelProperty(value = "经办人ID")
  private Long userId;

  /**
   * 经办人
   */
  @ExcelProperty(value = "经办人")
  private String nickName;

  /**
   * 呼叫时间
   */
  @ExcelProperty(value = "呼叫时间")
  private Date callTime;

  /**
   * 开始等待时间
   */
  @ExcelProperty(value = "开始等待时间")
  private Date waitStartTime;

  /**
   * 作业开始时间
   */
  @ExcelProperty(value = "作业开始时间")
  private Date taskStartTime;

  /**
   * 等待分钟数
   */
  @ExcelProperty(value = "等待分钟数")
  private BigDecimal waitMinCount;

  /**
   * 作业时间
   */
  @ExcelProperty(value = "作业时间")
  private BigDecimal taskSpanTime;

  /**
   * 是否预结算
   */
  @ExcelProperty(value = "是否预结算")
  private String isPreConsignor;

  /**
   * 是否预装载
   */
  @ExcelProperty(value = "是否预装载")
  private String isPreLoadingSave;

  /**
   * 称重系统ID
   */
  @ExcelProperty(value = "称重系统ID")
  private String czxh;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核状态
   */
  @ExcelProperty(value = "审核状态")
  private Long auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

  /**
   * 提货车号
   */
  @ExcelProperty(value = "提货车号")
  private String truckNumber;

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
