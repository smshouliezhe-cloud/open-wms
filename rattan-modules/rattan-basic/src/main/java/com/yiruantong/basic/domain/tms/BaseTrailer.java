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
 * 挂车管理对象 base_trailer
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_trailer", autoResultMap = true)
public class BaseTrailer extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 挂车ID
   */
  @TableId(value = "trailer_id")
  private Long trailerId;

  /**
   * 挂车编号
   */
  private String trailerCode;

  /**
   * 车牌号码
   */
  private String licenseCode;

  /**
   * 挂车类型
   */
  private String trailerType;

  /**
   * 品牌型号
   */
  private String brandModel;

  /**
   * 车辆识别代号
   */
  private String vehicleIdentifyNo;

  /**
   * 总质量
   */
  private BigDecimal totalQuality;

  /**
   * 核定载质量
   */
  private BigDecimal approvedQuality;

  /**
   * 车辆外廓尺寸
   */
  private String vehicleDimensions;

  /**
   * 货箱内部尺寸
   */
  private String containerInnerSize;

  /**
   * 核定容积
   */
  private String approvedVolume;

  /**
   * 挂车车长
   */
  private String trailerCaptain;

  /**
   * 证件注册日期
   */
  private Date idRegistrerDate;

  /**
   * 证件发证日期
   */
  private Date idIssueDate;

  /**
   * 检验有效期
   */
  private Date inspectionValidity;

  /**
   * 强制报废日期
   */
  private Date retirementDate;

  /**
   * 驾驶执照
   */
  private String drivingLicense;

  /**
   * 道路运输号
   */
  private String transportnoNo;

  /**
   * 证件发证日期
   */
  private Date dateIssue;

  /**
   * 年审到期日
   */
  private Date annualDueDate;

  /**
   * 经营许可证
   */
  private String operationLicense;

  /**
   * 车辆原产地
   */
  private Date originVehicle;

  /**
   * 车辆登记日期
   */
  private Date registerDate;

  /**
   * 车辆
   */
  private String vehicleBigBen;

  /**
   * 车辆图片
   */
  private String vehiclePhotos;

  /**
   * 保险公司名称
   */
  private String insurerName;

  /**
   * 保单单号
   */
  private String policyNo;

  /**
   * 保险到期时间
   */
  private Date insurerDueDate;

  /**
   * 货物温度险保额
   */
  private BigDecimal tempCoverage;

  /**
   * 保险保额
   */
  private BigDecimal insuranceCoverage;

  /**
   * 物流险
   */
  private BigDecimal logisticsInsurance;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 排序号
   */
  private Long orderNum;

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
