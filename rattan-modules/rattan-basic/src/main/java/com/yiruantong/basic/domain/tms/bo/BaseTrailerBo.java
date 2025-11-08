package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseTrailer;
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
 * 挂车管理业务对象 base_trailer
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseTrailer.class, reverseConvertGenerate = false)
public class BaseTrailerBo extends BaseEntity {

  /**
   * 挂车ID
   */
  @NotNull(message = "挂车ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long trailerId;

  /**
   * 挂车编号
   */
  @NotBlank(message = "挂车编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String trailerCode;

  /**
   * 车牌号码
   */
  @NotBlank(message = "车牌号码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String licenseCode;

  /**
   * 挂车类型
   */
  @NotBlank(message = "挂车类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String trailerType;

  /**
   * 品牌型号
   */
  @NotBlank(message = "品牌型号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandModel;

  /**
   * 车辆识别代号
   */
  @NotBlank(message = "车辆识别代号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleIdentifyNo;

  /**
   * 总质量
   */
  @NotNull(message = "总质量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuality;

  /**
   * 核定载质量
   */
  @NotNull(message = "核定载质量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal approvedQuality;

  /**
   * 车辆外廓尺寸
   */
  @NotBlank(message = "车辆外廓尺寸不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleDimensions;

  /**
   * 货箱内部尺寸
   */
  @NotBlank(message = "货箱内部尺寸不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerInnerSize;

  /**
   * 核定容积
   */
  @NotBlank(message = "核定容积不能为空", groups = {AddGroup.class, EditGroup.class})
  private String approvedVolume;

  /**
   * 挂车车长
   */
  @NotBlank(message = "挂车车长不能为空", groups = {AddGroup.class, EditGroup.class})
  private String trailerCaptain;

  /**
   * 证件注册日期
   */
  @NotNull(message = "证件注册日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date idRegistrerDate;

  /**
   * 证件发证日期
   */
  @NotNull(message = "证件发证日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date idIssueDate;

  /**
   * 检验有效期
   */
  @NotNull(message = "检验有效期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date inspectionValidity;

  /**
   * 强制报废日期
   */
  @NotNull(message = "强制报废日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date retirementDate;

  /**
   * 驾驶执照
   */
  @NotBlank(message = "驾驶执照不能为空", groups = {AddGroup.class, EditGroup.class})
  private String drivingLicense;

  /**
   * 道路运输号
   */
  @NotBlank(message = "道路运输号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String transportnoNo;

  /**
   * 证件发证日期
   */
  @NotNull(message = "证件发证日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date dateIssue;

  /**
   * 年审到期日
   */
  @NotNull(message = "年审到期日不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date annualDueDate;

  /**
   * 经营许可证
   */
  @NotBlank(message = "经营许可证不能为空", groups = {AddGroup.class, EditGroup.class})
  private String operationLicense;

  /**
   * 车辆原产地
   */
  @NotNull(message = "车辆原产地不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date originVehicle;

  /**
   * 车辆登记日期
   */
  @NotNull(message = "车辆登记日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date registerDate;

  /**
   * 车辆
   */
  @NotBlank(message = "车辆不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleBigBen;

  /**
   * 车辆图片
   */
  @NotBlank(message = "车辆图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehiclePhotos;

  /**
   * 保险公司名称
   */
  @NotBlank(message = "保险公司名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String insurerName;

  /**
   * 保单单号
   */
  @NotBlank(message = "保单单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String policyNo;

  /**
   * 保险到期时间
   */
  @NotNull(message = "保险到期时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date insurerDueDate;

  /**
   * 货物温度险保额
   */
  @NotNull(message = "货物温度险保额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal tempCoverage;

  /**
   * 保险保额
   */
  @NotNull(message = "保险保额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal insuranceCoverage;

  /**
   * 物流险
   */
  @NotNull(message = "物流险不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal logisticsInsurance;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

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
