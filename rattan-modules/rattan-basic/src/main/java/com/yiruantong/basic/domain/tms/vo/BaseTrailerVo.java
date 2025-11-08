package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.BaseTrailer;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 挂车管理视图对象 base_trailer
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseTrailer.class)
public class BaseTrailerVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 挂车ID
   */
  @ExcelProperty(value = "挂车ID")
  private Long trailerId;

  /**
   * 挂车编号
   */
  @ExcelProperty(value = "挂车编号")
  private String trailerCode;

  /**
   * 车牌号码
   */
  @ExcelProperty(value = "车牌号码")
  private String licenseCode;

  /**
   * 挂车类型
   */
  @ExcelProperty(value = "挂车类型")
  private String trailerType;

  /**
   * 品牌型号
   */
  @ExcelProperty(value = "品牌型号")
  private String brandModel;

  /**
   * 车辆识别代号
   */
  @ExcelProperty(value = "车辆识别代号")
  private String vehicleIdentifyNo;

  /**
   * 总质量
   */
  @ExcelProperty(value = "总质量")
  private BigDecimal totalQuality;

  /**
   * 核定载质量
   */
  @ExcelProperty(value = "核定载质量")
  private BigDecimal approvedQuality;

  /**
   * 车辆外廓尺寸
   */
  @ExcelProperty(value = "车辆外廓尺寸")
  private String vehicleDimensions;

  /**
   * 货箱内部尺寸
   */
  @ExcelProperty(value = "货箱内部尺寸")
  private String containerInnerSize;

  /**
   * 核定容积
   */
  @ExcelProperty(value = "核定容积")
  private String approvedVolume;

  /**
   * 挂车车长
   */
  @ExcelProperty(value = "挂车车长")
  private String trailerCaptain;

  /**
   * 证件注册日期
   */
  @ExcelProperty(value = "证件注册日期")
  private Date idRegistrerDate;

  /**
   * 证件发证日期
   */
  @ExcelProperty(value = "证件发证日期")
  private Date idIssueDate;

  /**
   * 检验有效期
   */
  @ExcelProperty(value = "检验有效期")
  private Date inspectionValidity;

  /**
   * 强制报废日期
   */
  @ExcelProperty(value = "强制报废日期")
  private Date retirementDate;

  /**
   * 驾驶执照
   */
  @ExcelProperty(value = "驾驶执照")
  private String drivingLicense;

  /**
   * 道路运输号
   */
  @ExcelProperty(value = "道路运输号")
  private String transportnoNo;

  /**
   * 证件发证日期
   */
  @ExcelProperty(value = "证件发证日期")
  private Date dateIssue;

  /**
   * 年审到期日
   */
  @ExcelProperty(value = "年审到期日")
  private Date annualDueDate;

  /**
   * 经营许可证
   */
  @ExcelProperty(value = "经营许可证")
  private String operationLicense;

  /**
   * 车辆原产地
   */
  @ExcelProperty(value = "车辆原产地")
  private Date originVehicle;

  /**
   * 车辆登记日期
   */
  @ExcelProperty(value = "车辆登记日期")
  private Date registerDate;

  /**
   * 车辆
   */
  @ExcelProperty(value = "车辆")
  private String vehicleBigBen;

  /**
   * 车辆图片
   */
  @ExcelProperty(value = "车辆图片")
  private String vehiclePhotos;

  /**
   * 保险公司名称
   */
  @ExcelProperty(value = "保险公司名称")
  private String insurerName;

  /**
   * 保单单号
   */
  @ExcelProperty(value = "保单单号")
  private String policyNo;

  /**
   * 保险到期时间
   */
  @ExcelProperty(value = "保险到期时间")
  private Date insurerDueDate;

  /**
   * 货物温度险保额
   */
  @ExcelProperty(value = "货物温度险保额")
  private BigDecimal tempCoverage;

  /**
   * 保险保额
   */
  @ExcelProperty(value = "保险保额")
  private BigDecimal insuranceCoverage;

  /**
   * 物流险
   */
  @ExcelProperty(value = "物流险")
  private BigDecimal logisticsInsurance;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

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
