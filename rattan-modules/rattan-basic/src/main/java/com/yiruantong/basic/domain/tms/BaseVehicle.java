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
 * 车辆管理对象 base_vehicle
 *
 * @author YRT
 * @date 2024-12-05
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_vehicle", autoResultMap = true)
public class BaseVehicle extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车辆ID
   */
  @TableId(value = "vehicle_id")
  private Long vehicleId;

  /**
   * 车辆编码
   */
  private String vehicleCode;

  /**
   * 所属区域
   */
  private String regionName;

  /**
   * 车辆状态
   */
  private String vehicleStatus;

  /**
   * 车辆温层
   */
  private String temperature;

  /**
   * 车辆类型
   */
  private String vehicleType;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 车主姓名
   */
  private String ownerName;

  /**
   * 车主电话
   */
  private String ownerPhone;

  /**
   * 身份证号
   */
  private String idCardCode;

  /**
   * 车辆载重量
   */
  private BigDecimal vehicleload;

  /**
   * 车辆体积
   */
  private BigDecimal vehicleVolume;

  /**
   * 车长
   */
  private BigDecimal carLength;

  /**
   * 车宽
   */
  private BigDecimal carWidth;

  /**
   * 车高
   */
  private BigDecimal carHeight;

  /**
   * 车龄
   */
  private Long carAge;

  /**
   * 司机Id
   */
  private Long driverId;

  /**
   * 驾驶员姓名
   */
  private String driverName;

  /**
   * 驾驶员电话
   */
  private String driverMobile;

  /**
   * 驾驶员身份证号
   */
  private String driverIdcard;

  /**
   * 驾驶证档案号
   */
  private String driverLicenseNo;

  /**
   * 副驾姓名
   */
  private String passengerName;

  /**
   * 联系电话(副驾)
   */
  private String telephoneVice;

  /**
   * 身份证号(副驾)
   */
  private String idcardVice;

  /**
   * 驾驶证号(副驾)
   */
  private String driveNoVice;

  /**
   * 车辆种类
   */
  private String typeVehicle;

  /**
   * 车辆分类代码
   */
  private String vehicleSortCode;

  /**
   * 牌照类型代码
   */
  private String licenseTypeCode;

  /**
   * 净值
   */
  private Long netWorth;

  /**
   * 型号
   */
  private String model;

  /**
   * 发动机号
   */
  private String engineNumber;

  /**
   * 营运证号
   */
  private String certificateNo;

  /**
   * 行车执照
   */
  private String drivingLicense;

  /**
   * 车辆牌照号
   */
  private String vehicleLicenseNo;

  /**
   * 挂车车辆分类代码
   */
  private String classCode;

  /**
   * 挂车车辆载重
   */
  private String trailerHeavy;

  /**
   * 拖板号
   */
  private String palletNumber;

  /**
   * 登记日期
   */
  private Date registrationDate;

  /**
   * 挂车车牌号
   */
  private String trailerTruckNo;

  /**
   * 是否保险
   */
  private Long isInsurance;

  /**
   * 保险公司
   */
  private String insuranceCompany;

  /**
   * 保险公司电话
   */
  private String insuranceMobile;

  /**
   * 保单号
   */
  private String policyNumber;

  /**
   * 保费
   */
  private String premium;

  /**
   * 最近一次年审日期
   */
  private Date lastAnnuallyDate;

  /**
   * 下次年审日期
   */
  private Date nextAnnualDate;

  /**
   * 车辆所电话
   */
  private String vehicleOfficeTel;

  /**
   * 当地派出所电话
   */
  private String policeStationTel;

  /**
   * 保养月数
   */
  private Long maintainMonths;

  /**
   * 保养月数
   */
  private String maintainKilometers;

  /**
   * 品牌
   */
  private String brand;

  /**
   * 车架号
   */
  private String frameNumber;

  /**
   * 购置日期
   */
  private Date purchaseDate;

  /**
   * 最近一次季审日期
   */
  private Date latestReviewDate;

  /**
   * 下一次季审日期
   */
  private Date nextReviewDate;

  /**
   * 商业险到期日
   */
  private Date commercialDate;

  /**
   * 强制险到期日
   */
  private Date compulsoryDate;

  /**
   * 原值
   */
  private Long originalValue;

  /**
   * 残值率
   */
  private BigDecimal residualRate;

  /**
   * 残值
   */
  private Long residualValue;

  /**
   * 折旧年限(月)
   */
  private Date depreciationPeriod;

  /**
   * 月折旧额
   */
  private Long monthlyMoney;

  /**
   * 是否有车身广告
   */
  private String isCarbodyad;

  /**
   * 运营状态
   */
  private String operationStatus;

  /**
   * 是否有通行证
   */
  private Long isPass;

  /**
   * 是否带小工
   */
  private Long isBringWorker;

  /**
   * 是否带小推车
   */
  private Long isBringTrolley;

  /**
   * 是否带测温仪
   */
  private Long isThermometer;

  /**
   * 行业偏号
   */
  private String industryPreference;

  /**
   * 货物喜号
   */
  private String cargoPreferences;

  /**
   * 配送喜号
   */
  private String deliveryPreferences;

  /**
   * 现合作何种业务
   */
  private String kindBusiness;

  /**
   * 车辆图片
   */
  private String vehiclePicture;

  /**
   * 身份证人像面
   */
  private String portraitPortrait;

  /**
   * 身份证国徽面
   */
  private String idPadge;

  /**
   * 驾驶证图片
   */
  private String driverLicensePicture;

  /**
   * 司机、车辆图片
   */
  private String vehiclePicture2;

  /**
   * 强制险图片
   */
  private String compulsoryPics;

  /**
   * 商业险图片
   */
  private String businessPic;

  /**
   * 行驶证图片
   */
  private String drivingLicensePic;

  /**
   * 银行卡图片
   */
  private String bankCardPic;

  /**
   * 道路运输许可证
   */
  private String roadTransportPermit;

  /**
   * 合同附件上传列表
   */
  private String contractAttach;

  /**
   * 车辆归属
   */
  private String vehicleAscription;

  /**
   * 经度
   */
  private String lng;

  /**
   * 维度
   */
  private String lat;

  /**
   * 车队编号
   */
  private String vehicleGroupCode;

  /**
   * 车队Id
   */
  private Long vehicleGroupId;

  /**
   * 车队名称
   */
  private String vehicleGroupName;

  /**
   * 承运商ID
   */
  private Long carrierId;

  /**
   * 承运商编号
   */
  private String carrierCode;

  /**
   * 承运商名称
   */
  private String carrierName;

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

  /**
   * 是否可用
   */
  private Long enable;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 仓库id
   */
  private String storageId;

  /**
   * 司机电话
   */
  private String tel;

  /**
   * 副驾id
   */
  private Long passengerId;


}
