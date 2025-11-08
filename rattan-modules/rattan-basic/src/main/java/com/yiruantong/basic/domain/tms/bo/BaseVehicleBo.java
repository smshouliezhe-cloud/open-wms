package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.BaseVehicle;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 车辆管理业务对象 base_vehicle
 *
 * @author YRT
 * @date 2024-12-05
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseVehicle.class, reverseConvertGenerate = false)
public class BaseVehicleBo extends BaseEntity {

  /**
   * 车辆ID
   */
  @NotNull(message = "车辆ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleId;

  /**
   * 车辆编码
   */
  @NotBlank(message = "车辆编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleCode;

  /**
   * 所属区域
   */
  @NotBlank(message = "所属区域不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionName;

  /**
   * 车辆状态
   */
  @NotBlank(message = "车辆状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleStatus;

  /**
   * 车辆温层
   */
  @NotBlank(message = "车辆温层不能为空", groups = {AddGroup.class, EditGroup.class})
  private String temperature;

  /**
   * 车辆类型
   */
  @NotBlank(message = "车辆类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleType;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 车主姓名
   */
  @NotBlank(message = "车主姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String ownerName;

  /**
   * 车主电话
   */
  @NotBlank(message = "车主电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String ownerPhone;

  /**
   * 身份证号
   */
  @NotBlank(message = "身份证号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String idCardCode;

  /**
   * 车辆载重量
   */
  @NotNull(message = "车辆载重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal vehicleload;

  /**
   * 车辆体积
   */
  @NotNull(message = "车辆体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal vehicleVolume;

  /**
   * 车长
   */
  @NotNull(message = "车长不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal carLength;

  /**
   * 车宽
   */
  @NotNull(message = "车宽不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal carWidth;

  /**
   * 车高
   */
  @NotNull(message = "车高不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal carHeight;

  /**
   * 车龄
   */
  @NotNull(message = "车龄不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long carAge;

  /**
   * 司机Id
   */
  @NotNull(message = "司机Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long driverId;

  /**
   * 驾驶员姓名
   */
  @NotBlank(message = "驾驶员姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverName;

  /**
   * 驾驶员电话
   */
  @NotBlank(message = "驾驶员电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverMobile;

  /**
   * 驾驶员身份证号
   */
  @NotBlank(message = "驾驶员身份证号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverIdcard;

  /**
   * 驾驶证档案号
   */
  @NotBlank(message = "驾驶证档案号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverLicenseNo;

  /**
   * 副驾姓名
   */
  @NotBlank(message = "副驾姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String passengerName;

  /**
   * 联系电话(副驾)
   */
  @NotBlank(message = "联系电话(副驾)不能为空", groups = {AddGroup.class, EditGroup.class})
  private String telephoneVice;

  /**
   * 身份证号(副驾)
   */
  @NotBlank(message = "身份证号(副驾)不能为空", groups = {AddGroup.class, EditGroup.class})
  private String idcardVice;

  /**
   * 驾驶证号(副驾)
   */
  @NotBlank(message = "驾驶证号(副驾)不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driveNoVice;

  /**
   * 车辆种类
   */
  @NotBlank(message = "车辆种类不能为空", groups = {AddGroup.class, EditGroup.class})
  private String typeVehicle;

  /**
   * 车辆分类代码
   */
  @NotBlank(message = "车辆分类代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleSortCode;

  /**
   * 牌照类型代码
   */
  @NotBlank(message = "牌照类型代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String licenseTypeCode;

  /**
   * 净值
   */
  @NotNull(message = "净值不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long netWorth;

  /**
   * 型号
   */
  @NotBlank(message = "型号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String model;

  /**
   * 发动机号
   */
  @NotBlank(message = "发动机号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String engineNumber;

  /**
   * 营运证号
   */
  @NotBlank(message = "营运证号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String certificateNo;

  /**
   * 行车执照
   */
  @NotBlank(message = "行车执照不能为空", groups = {AddGroup.class, EditGroup.class})
  private String drivingLicense;

  /**
   * 车辆牌照号
   */
  @NotBlank(message = "车辆牌照号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleLicenseNo;

  /**
   * 挂车车辆分类代码
   */
  @NotBlank(message = "挂车车辆分类代码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String classCode;

  /**
   * 挂车车辆载重
   */
  @NotBlank(message = "挂车车辆载重不能为空", groups = {AddGroup.class, EditGroup.class})
  private String trailerHeavy;

  /**
   * 拖板号
   */
  @NotBlank(message = "拖板号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String palletNumber;

  /**
   * 登记日期
   */
  @NotNull(message = "登记日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date registrationDate;

  /**
   * 挂车车牌号
   */
  @NotBlank(message = "挂车车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String trailerTruckNo;

  /**
   * 是否保险
   */
  @NotNull(message = "是否保险不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isInsurance;

  /**
   * 保险公司
   */
  @NotBlank(message = "保险公司不能为空", groups = {AddGroup.class, EditGroup.class})
  private String insuranceCompany;

  /**
   * 保险公司电话
   */
  @NotBlank(message = "保险公司电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String insuranceMobile;

  /**
   * 保单号
   */
  @NotBlank(message = "保单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String policyNumber;

  /**
   * 保费
   */
  @NotBlank(message = "保费不能为空", groups = {AddGroup.class, EditGroup.class})
  private String premium;

  /**
   * 最近一次年审日期
   */
  @NotNull(message = "最近一次年审日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date lastAnnuallyDate;

  /**
   * 下次年审日期
   */
  @NotNull(message = "下次年审日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date nextAnnualDate;

  /**
   * 车辆所电话
   */
  @NotBlank(message = "车辆所电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleOfficeTel;

  /**
   * 当地派出所电话
   */
  @NotBlank(message = "当地派出所电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String policeStationTel;

  /**
   * 保养月数
   */
  @NotNull(message = "保养月数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long maintainMonths;

  /**
   * 保养月数
   */
  @NotBlank(message = "保养月数不能为空", groups = {AddGroup.class, EditGroup.class})
  private String maintainKilometers;

  /**
   * 品牌
   */
  @NotBlank(message = "品牌不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brand;

  /**
   * 车架号
   */
  @NotBlank(message = "车架号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String frameNumber;

  /**
   * 购置日期
   */
  @NotNull(message = "购置日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date purchaseDate;

  /**
   * 最近一次季审日期
   */
  @NotNull(message = "最近一次季审日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date latestReviewDate;

  /**
   * 下一次季审日期
   */
  @NotNull(message = "下一次季审日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date nextReviewDate;

  /**
   * 商业险到期日
   */
  @NotNull(message = "商业险到期日不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date commercialDate;

  /**
   * 强制险到期日
   */
  @NotNull(message = "强制险到期日不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date compulsoryDate;

  /**
   * 原值
   */
  @NotNull(message = "原值不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long originalValue;

  /**
   * 残值率
   */
  @NotNull(message = "残值率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal residualRate;

  /**
   * 残值
   */
  @NotNull(message = "残值不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long residualValue;

  /**
   * 折旧年限(月)
   */
  @NotNull(message = "折旧年限(月)不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date depreciationPeriod;

  /**
   * 月折旧额
   */
  @NotNull(message = "月折旧额不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long monthlyMoney;

  /**
   * 是否有车身广告
   */
  @NotBlank(message = "是否有车身广告不能为空", groups = {AddGroup.class, EditGroup.class})
  private String isCarbodyad;

  /**
   * 运营状态
   */
  @NotBlank(message = "运营状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String operationStatus;

  /**
   * 是否有通行证
   */
  @NotNull(message = "是否有通行证不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isPass;

  /**
   * 是否带小工
   */
  @NotNull(message = "是否带小工不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isBringWorker;

  /**
   * 是否带小推车
   */
  @NotNull(message = "是否带小推车不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isBringTrolley;

  /**
   * 是否带测温仪
   */
  @NotNull(message = "是否带测温仪不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isThermometer;

  /**
   * 行业偏号
   */
  @NotBlank(message = "行业偏号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String industryPreference;

  /**
   * 货物喜号
   */
  @NotBlank(message = "货物喜号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cargoPreferences;

  /**
   * 配送喜号
   */
  @NotBlank(message = "配送喜号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deliveryPreferences;

  /**
   * 现合作何种业务
   */
  @NotBlank(message = "现合作何种业务不能为空", groups = {AddGroup.class, EditGroup.class})
  private String kindBusiness;

  /**
   * 车辆图片
   */
  @NotBlank(message = "车辆图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehiclePicture;

  /**
   * 身份证人像面
   */
  @NotBlank(message = "身份证人像面不能为空", groups = {AddGroup.class, EditGroup.class})
  private String portraitPortrait;

  /**
   * 身份证国徽面
   */
  @NotBlank(message = "身份证国徽面不能为空", groups = {AddGroup.class, EditGroup.class})
  private String idPadge;

  /**
   * 驾驶证图片
   */
  @NotBlank(message = "驾驶证图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverLicensePicture;

  /**
   * 司机、车辆图片
   */
  @NotBlank(message = "司机、车辆图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehiclePicture2;

  /**
   * 强制险图片
   */
  @NotBlank(message = "强制险图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String compulsoryPics;

  /**
   * 商业险图片
   */
  @NotBlank(message = "商业险图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String businessPic;

  /**
   * 行驶证图片
   */
  @NotBlank(message = "行驶证图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String drivingLicensePic;

  /**
   * 银行卡图片
   */
  @NotBlank(message = "银行卡图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bankCardPic;

  /**
   * 道路运输许可证
   */
  @NotBlank(message = "道路运输许可证不能为空", groups = {AddGroup.class, EditGroup.class})
  private String roadTransportPermit;

  /**
   * 合同附件上传列表
   */
  @NotBlank(message = "合同附件上传列表不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractAttach;

  /**
   * 车辆归属
   */
  @NotBlank(message = "车辆归属不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleAscription;

  /**
   * 经度
   */
  @NotBlank(message = "经度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lng;

  /**
   * 维度
   */
  @NotBlank(message = "维度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lat;

  /**
   * 车队编号
   */
  @NotBlank(message = "车队编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleGroupCode;

  /**
   * 车队Id
   */
  @NotNull(message = "车队Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vehicleGroupId;

  /**
   * 车队名称
   */
  @NotBlank(message = "车队名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vehicleGroupName;

  /**
   * 承运商ID
   */
  @NotNull(message = "承运商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long carrierId;

  /**
   * 承运商编号
   */
  @NotBlank(message = "承运商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierCode;

  /**
   * 承运商名称
   */
  @NotBlank(message = "承运商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String carrierName;

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

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 仓库id
   */
  @NotBlank(message = "仓库id不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageId;

  /**
   * 司机电话
   */
  @NotBlank(message = "司机电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tel;

  /**
   * 副驾id
   */
  @NotNull(message = "副驾id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long passengerId;


}
