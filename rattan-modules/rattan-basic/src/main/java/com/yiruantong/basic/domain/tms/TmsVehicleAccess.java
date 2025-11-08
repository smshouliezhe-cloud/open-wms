package com.yiruantong.basic.domain.tms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 车辆出入信息对象 tms_vehicle_access
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tms_vehicle_access", autoResultMap = true)
public class TmsVehicleAccess extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车辆出入ID
   */
  @TableId(value = "vehicle_access_id")
  private Long vehicleAccessId;

  /**
   * 车辆出入时间
   */
  private String takeGoodsCarCode;

  /**
   * 装载计划编号
   */
  private String outLoadCode;

  /**
   * 车辆出入时间
   */
  private Date enterExitTime;

  /**
   * 进出
   */
  private Long enterExitType;

  /**
   * 车牌号
   */
  private String carrierPlateNumber;

  /**
   * 车辆性质
   */
  private String carrierPlateType;

  /**
   * 承运车辆驾驶员姓名
   */
  private String carrierDriverName;

  /**
   * 承运车辆驾驶员身份证号
   */
  private String carrierDriverIdcard;

  /**
   * 押运员姓名
   */
  private String escort;

  /**
   * 押运员身份证号
   */
  private String escortIdcard;

  /**
   * 上报时间
   */
  private Date reportTime;

  /**
   * 到场日期
   */
  private Date takeGoodsDate;

  /**
   * 离场日期
   */
  private Date outGoodsDate;

  /**
   * 车辆入场过磅重
   */
  private BigDecimal overWeightInCar;

  /**
   * 车辆出场过磅重
   */
  private BigDecimal overWeightOutCar;

  /**
   * 过磅货重
   */
  private BigDecimal overWeight;

  /**
   * 是否带货出场
   */
  private Long isBringGoodsOut;

  /**
   * wms系统出库重量
   */
  private BigDecimal systemWeightOut;

  /**
   * 差值
   */
  private BigDecimal difference;

  /**
   * 订单状态
   */
  private String orderStatus;

  /**
   * 联系方式
   */
  private String contactInformation;

  /**
   * 停车区域
   */
  private String parkingArea;

  /**
   * 发布日期
   */
  private Date releaseDate;

  /**
   * 发布人ID
   */
  private String releaseUserId;

  /**
   * 发布人
   */
  private String releaseUserTrueName;

  /**
   * 办单结算时间
   */
  private Date bulkLoadingDate;

  /**
   * 车牌省份
   */
  private String carProvince;

  /**
   * 车牌市区
   */
  private String carCity;

  /**
   * 车牌字码
   */
  private String carNum;

  /**
   * 经办人ID
   */
  private Long userId;

  /**
   * 经办人
   */
  private String nickName;

  /**
   * 呼叫时间
   */
  private Date callTime;

  /**
   * 开始等待时间
   */
  private Date waitStartTime;

  /**
   * 作业开始时间
   */
  private Date taskStartTime;

  /**
   * 等待分钟数
   */
  private BigDecimal waitMinCount;

  /**
   * 作业时间
   */
  private BigDecimal taskSpanTime;

  /**
   * 是否预结算
   */
  private String isPreConsignor;

  /**
   * 是否预装载
   */
  private String isPreLoadingSave;

  /**
   * 称重系统ID
   */
  private String czxh;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核状态
   */
  private Long auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 提货车号
   */
  private String truckNumber;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;


}
