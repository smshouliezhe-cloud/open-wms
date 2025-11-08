package com.yiruantong.basic.domain.tms.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.tms.BaseVehicle;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 车辆管理视图对象 base_vehicle
 *
 * @author YRT
 * @date 2024-12-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseVehicle.class)
public class BaseVehicleVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 车辆ID
   */
  @ExcelProperty(value = "车辆ID")
  private Long vehicleId;

  /**
   * 车辆编码
   */
  @ExcelProperty(value = "车辆编码")
  private String vehicleCode;

  /**
   * 所属区域
   */
  @ExcelProperty(value = "所属区域")
  private String regionName;

  /**
   * 车辆状态
   */
  @ExcelProperty(value = "车辆状态")
  private String vehicleStatus;

  /**
   * 车辆温层
   */
  @ExcelProperty(value = "车辆温层")
  private String temperature;

  /**
   * 车辆类型
   */
  @ExcelProperty(value = "车辆类型")
  private String vehicleType;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 车主姓名
   */
  @ExcelProperty(value = "车主姓名")
  private String ownerName;

  /**
   * 车主电话
   */
  @ExcelProperty(value = "车主电话")
  private String ownerPhone;

  /**
   * 身份证号
   */
  @ExcelProperty(value = "身份证号")
  private String idCardCode;

  /**
   * 车辆载重量
   */
  @ExcelProperty(value = "车辆载重量")
  private BigDecimal vehicleload;

  /**
   * 车辆体积
   */
  @ExcelProperty(value = "车辆体积")
  private BigDecimal vehicleVolume;

  /**
   * 车长
   */
  @ExcelProperty(value = "车长")
  private BigDecimal carLength;

  /**
   * 车宽
   */
  @ExcelProperty(value = "车宽")
  private BigDecimal carWidth;

  /**
   * 车高
   */
  @ExcelProperty(value = "车高")
  private BigDecimal carHeight;

  /**
   * 车龄
   */
  @ExcelProperty(value = "车龄")
  private Long carAge;

  /**
   * 司机Id
   */
  @ExcelProperty(value = "司机Id")
  private Long driverId;

  /**
   * 驾驶员姓名
   */
  @ExcelProperty(value = "驾驶员姓名")
  private String driverName;

  /**
   * 驾驶员电话
   */
  @ExcelProperty(value = "驾驶员电话")
  private String driverMobile;

  /**
   * 驾驶员身份证号
   */
  @ExcelProperty(value = "驾驶员身份证号")
  private String driverIdcard;

  /**
   * 驾驶证档案号
   */
  @ExcelProperty(value = "驾驶证档案号")
  private String driverLicenseNo;

  /**
   * 副驾姓名
   */
  @ExcelProperty(value = "副驾姓名")
  private String passengerName;

  /**
   * 联系电话(副驾)
   */
  @ExcelProperty(value = "联系电话(副驾)")
  private String telephoneVice;

  /**
   * 身份证号(副驾)
   */
  @ExcelProperty(value = "身份证号(副驾)")
  private String idcardVice;

  /**
   * 驾驶证号(副驾)
   */
  @ExcelProperty(value = "驾驶证号(副驾)")
  private String driveNoVice;

  /**
   * 车辆种类
   */
  @ExcelProperty(value = "车辆种类")
  private String typeVehicle;

  /**
   * 车辆分类代码
   */
  @ExcelProperty(value = "车辆分类代码")
  private String vehicleSortCode;

  /**
   * 牌照类型代码
   */
  @ExcelProperty(value = "牌照类型代码")
  private String licenseTypeCode;

  /**
   * 净值
   */
  @ExcelProperty(value = "净值")
  private Long netWorth;

  /**
   * 型号
   */
  @ExcelProperty(value = "型号")
  private String model;

  /**
   * 发动机号
   */
  @ExcelProperty(value = "发动机号")
  private String engineNumber;

  /**
   * 营运证号
   */
  @ExcelProperty(value = "营运证号")
  private String certificateNo;

  /**
   * 行车执照
   */
  @ExcelProperty(value = "行车执照")
  private String drivingLicense;

  /**
   * 车辆牌照号
   */
  @ExcelProperty(value = "车辆牌照号")
  private String vehicleLicenseNo;

  /**
   * 挂车车辆分类代码
   */
  @ExcelProperty(value = "挂车车辆分类代码")
  private String classCode;

  /**
   * 挂车车辆载重
   */
  @ExcelProperty(value = "挂车车辆载重")
  private String trailerHeavy;

  /**
   * 拖板号
   */
  @ExcelProperty(value = "拖板号")
  private String palletNumber;

  /**
   * 登记日期
   */
  @ExcelProperty(value = "登记日期")
  private Date registrationDate;

  /**
   * 挂车车牌号
   */
  @ExcelProperty(value = "挂车车牌号")
  private String trailerTruckNo;

  /**
   * 是否保险
   */
  @ExcelProperty(value = "是否保险")
  private Long isInsurance;

  /**
   * 保险公司
   */
  @ExcelProperty(value = "保险公司")
  private String insuranceCompany;

  /**
   * 保险公司电话
   */
  @ExcelProperty(value = "保险公司电话")
  private String insuranceMobile;

  /**
   * 保单号
   */
  @ExcelProperty(value = "保单号")
  private String policyNumber;

  /**
   * 保费
   */
  @ExcelProperty(value = "保费")
  private String premium;

  /**
   * 最近一次年审日期
   */
  @ExcelProperty(value = "最近一次年审日期")
  private Date lastAnnuallyDate;

  /**
   * 下次年审日期
   */
  @ExcelProperty(value = "下次年审日期")
  private Date nextAnnualDate;

  /**
   * 车辆所电话
   */
  @ExcelProperty(value = "车辆所电话")
  private String vehicleOfficeTel;

  /**
   * 当地派出所电话
   */
  @ExcelProperty(value = "当地派出所电话")
  private String policeStationTel;

  /**
   * 保养月数
   */
  @ExcelProperty(value = "保养月数")
  private Long maintainMonths;

  /**
   * 保养月数
   */
  @ExcelProperty(value = "保养月数")
  private String maintainKilometers;

  /**
   * 品牌
   */
  @ExcelProperty(value = "品牌")
  private String brand;

  /**
   * 车架号
   */
  @ExcelProperty(value = "车架号")
  private String frameNumber;

  /**
   * 购置日期
   */
  @ExcelProperty(value = "购置日期")
  private Date purchaseDate;

  /**
   * 最近一次季审日期
   */
  @ExcelProperty(value = "最近一次季审日期")
  private Date latestReviewDate;

  /**
   * 下一次季审日期
   */
  @ExcelProperty(value = "下一次季审日期")
  private Date nextReviewDate;

  /**
   * 商业险到期日
   */
  @ExcelProperty(value = "商业险到期日")
  private Date commercialDate;

  /**
   * 强制险到期日
   */
  @ExcelProperty(value = "强制险到期日")
  private Date compulsoryDate;

  /**
   * 原值
   */
  @ExcelProperty(value = "原值")
  private Long originalValue;

  /**
   * 残值率
   */
  @ExcelProperty(value = "残值率")
  private BigDecimal residualRate;

  /**
   * 残值
   */
  @ExcelProperty(value = "残值")
  private Long residualValue;

  /**
   * 折旧年限(月)
   */
  @ExcelProperty(value = "折旧年限(月)")
  private Date depreciationPeriod;

  /**
   * 月折旧额
   */
  @ExcelProperty(value = "月折旧额")
  private Long monthlyMoney;

  /**
   * 是否有车身广告
   */
  @ExcelProperty(value = "是否有车身广告")
  private String isCarbodyad;

  /**
   * 运营状态
   */
  @ExcelProperty(value = "运营状态")
  private String operationStatus;

  /**
   * 是否有通行证
   */
  @ExcelProperty(value = "是否有通行证")
  private Long isPass;

  /**
   * 是否带小工
   */
  @ExcelProperty(value = "是否带小工")
  private Long isBringWorker;

  /**
   * 是否带小推车
   */
  @ExcelProperty(value = "是否带小推车")
  private Long isBringTrolley;

  /**
   * 是否带测温仪
   */
  @ExcelProperty(value = "是否带测温仪")
  private Long isThermometer;

  /**
   * 行业偏号
   */
  @ExcelProperty(value = "行业偏号")
  private String industryPreference;

  /**
   * 货物喜号
   */
  @ExcelProperty(value = "货物喜号")
  private String cargoPreferences;

  /**
   * 配送喜号
   */
  @ExcelProperty(value = "配送喜号")
  private String deliveryPreferences;

  /**
   * 现合作何种业务
   */
  @ExcelProperty(value = "现合作何种业务")
  private String kindBusiness;

  /**
   * 车辆图片
   */
  @ExcelProperty(value = "车辆图片")
  private String vehiclePicture;

  /**
   * 身份证人像面
   */
  @ExcelProperty(value = "身份证人像面")
  private String portraitPortrait;

  /**
   * 身份证国徽面
   */
  @ExcelProperty(value = "身份证国徽面")
  private String idPadge;

  /**
   * 驾驶证图片
   */
  @ExcelProperty(value = "驾驶证图片")
  private String driverLicensePicture;

  /**
   * 司机、车辆图片
   */
  @ExcelProperty(value = "司机、车辆图片")
  private String vehiclePicture2;

  /**
   * 强制险图片
   */
  @ExcelProperty(value = "强制险图片")
  private String compulsoryPics;

  /**
   * 商业险图片
   */
  @ExcelProperty(value = "商业险图片")
  private String businessPic;

  /**
   * 行驶证图片
   */
  @ExcelProperty(value = "行驶证图片")
  private String drivingLicensePic;

  /**
   * 银行卡图片
   */
  @ExcelProperty(value = "银行卡图片")
  private String bankCardPic;

  /**
   * 道路运输许可证
   */
  @ExcelProperty(value = "道路运输许可证")
  private String roadTransportPermit;

  /**
   * 合同附件上传列表
   */
  @ExcelProperty(value = "合同附件上传列表")
  private String contractAttach;

  /**
   * 车辆归属
   */
  @ExcelProperty(value = "车辆归属")
  private String vehicleAscription;

  /**
   * 经度
   */
  @ExcelProperty(value = "经度")
  private String lng;

  /**
   * 维度
   */
  @ExcelProperty(value = "维度")
  private String lat;

  /**
   * 车队编号
   */
  @ExcelProperty(value = "车队编号")
  private String vehicleGroupCode;

  /**
   * 车队Id
   */
  @ExcelProperty(value = "车队Id")
  private Long vehicleGroupId;

  /**
   * 车队名称
   */
  @ExcelProperty(value = "车队名称")
  private String vehicleGroupName;

  /**
   * 承运商ID
   */
  @ExcelProperty(value = "承运商ID")
  private Long carrierId;

  /**
   * 承运商编号
   */
  @ExcelProperty(value = "承运商编号")
  private String carrierCode;

  /**
   * 承运商名称
   */
  @ExcelProperty(value = "承运商名称")
  private String carrierName;

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

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 仓库id
   */
  @ExcelProperty(value = "仓库id")
  private String storageId;

  /**
   * 司机电话
   */
  @ExcelProperty(value = "司机电话")
  private String tel;

  /**
   * 副驾id
   */
  @ExcelProperty(value = "副驾id")
  private Long passengerId;


}
