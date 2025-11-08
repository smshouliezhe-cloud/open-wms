package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.TmsVehicleAccess;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 车辆出入信息业务对象 tms_vehicle_access
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsVehicleAccess.class, reverseConvertGenerate = false)
public class TmsVehicleAccessBo extends BaseEntity {

  /**
   * 车辆出入ID
   */
  @NotNull(message = "车辆出入ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleAccessId;

  /**
   * 车辆出入时间
   */
  @NotBlank(message = "车辆出入时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private String takeGoodsCarCode;

  /**
   * 装载计划编号
   */
  @NotBlank(message = "装载计划编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outLoadCode;

  /**
   * 车辆出入时间
   */
  @NotNull(message = "车辆出入时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date enterExitTime;

  /**
   * 进出
   */
  @NotNull(message = "进出不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enterExitType;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierPlateNumber;

  /**
   * 车辆性质
   */
  @NotBlank(message = "车辆性质不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierPlateType;

  /**
   * 承运车辆驾驶员姓名
   */
  @NotBlank(message = "承运车辆驾驶员姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierDriverName;

  /**
   * 承运车辆驾驶员身份证号
   */
  @NotBlank(message = "承运车辆驾驶员身份证号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierDriverIdcard;

  /**
   * 押运员姓名
   */
  @NotBlank(message = "押运员姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String escort;

  /**
   * 押运员身份证号
   */
  @NotBlank(message = "押运员身份证号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String escortIdcard;

  /**
   * 上报时间
   */
  @NotNull(message = "上报时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date reportTime;

  /**
   * 到场日期
   */
  @NotNull(message = "到场日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date takeGoodsDate;

  /**
   * 离场日期
   */
  @NotNull(message = "离场日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date outGoodsDate;

  /**
   * 车辆入场过磅重
   */
  @NotNull(message = "车辆入场过磅重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal overWeightInCar;

  /**
   * 车辆出场过磅重
   */
  @NotNull(message = "车辆出场过磅重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal overWeightOutCar;

  /**
   * 过磅货重
   */
  @NotNull(message = "过磅货重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal overWeight;

  /**
   * 是否带货出场
   */
  @NotNull(message = "是否带货出场不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isBringGoodsOut;

  /**
   * wms系统出库重量
   */
  @NotNull(message = "wms系统出库重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal systemWeightOut;

  /**
   * 差值
   */
  @NotNull(message = "差值不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal difference;

  /**
   * 订单状态
   */
  @NotBlank(message = "订单状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderStatus;

  /**
   * 联系方式
   */
  @NotBlank(message = "联系方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contactInformation;

  /**
   * 停车区域
   */
  @NotBlank(message = "停车区域不能为空", groups = {AddGroup.class, EditGroup.class})
  private String parkingArea;

  /**
   * 发布日期
   */
  @NotNull(message = "发布日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date releaseDate;

  /**
   * 发布人ID
   */
  @NotBlank(message = "发布人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String releaseUserId;

  /**
   * 发布人
   */
  @NotBlank(message = "发布人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String releaseUserTrueName;

  /**
   * 办单结算时间
   */
  @NotNull(message = "办单结算时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date bulkLoadingDate;

  /**
   * 车牌省份
   */
  @NotBlank(message = "车牌省份不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carProvince;

  /**
   * 车牌市区
   */
  @NotBlank(message = "车牌市区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carCity;

  /**
   * 车牌字码
   */
  @NotBlank(message = "车牌字码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carNum;

  /**
   * 经办人ID
   */
  @NotNull(message = "经办人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 经办人
   */
  @NotBlank(message = "经办人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 呼叫时间
   */
  @NotNull(message = "呼叫时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date callTime;

  /**
   * 开始等待时间
   */
  @NotNull(message = "开始等待时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date waitStartTime;

  /**
   * 作业开始时间
   */
  @NotNull(message = "作业开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date taskStartTime;

  /**
   * 等待分钟数
   */
  @NotNull(message = "等待分钟数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal waitMinCount;

  /**
   * 作业时间
   */
  @NotNull(message = "作业时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal taskSpanTime;

  /**
   * 是否预结算
   */
  @NotBlank(message = "是否预结算不能为空", groups = {AddGroup.class, EditGroup.class})
  private String isPreConsignor;

  /**
   * 是否预装载
   */
  @NotBlank(message = "是否预装载不能为空", groups = {AddGroup.class, EditGroup.class})
  private String isPreLoadingSave;

  /**
   * 称重系统ID
   */
  @NotBlank(message = "称重系统ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String czxh;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核状态
   */
  @NotNull(message = "审核状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 提货车号
   */
  @NotBlank(message = "提货车号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNumber;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;


}
