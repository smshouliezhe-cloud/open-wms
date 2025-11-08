package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 表单上面的数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FormInfoBo {

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 地址
   */
  private String siteAddress;

  /**
   * 司机姓名
   */
  private String driverName;

  /**
   * 司机电话
   */
  private String driverMobile;

  /**
   * 承运商
   */
  private String carrierName;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 计划配送时间
   */
  private Date arriveDate;

  /**
   * 备注
   */
  private String remark;

  /**
   * 合计运输费用
   */
  private BigDecimal totalCost;
  /**
   * 线路
   */
  private String lineName;

  /**
   * 仓库名称
   */
  private String storageName;


  /**
   * 单据数
   */
  private BigDecimal wayBillCodeCount;

  /**
   * 结算方式
   */
  private String settlementMode;

  /**
   * 车辆类型
   */
  private String vehicleType;

  /**
   * 配送级别
   */
  private String deliveryLevel;

  /**
   * 承运商单号
   */
  private String relationBillCode;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantityOrder;
  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;
  /**
   * 打包件数
   */
  private BigDecimal totalPackage;
  /**
   * 客户名称
   */
  private String clientShortName;

  /**
   * 承运商id
   */
  private Long carrierId;

  /**
   * 司机id
   */
  private Long driverId;

  /**
   * 车辆id
   */
  private Long vehicleId;

  /**
   * 电话号
   */
  private String tel;

  /**
   * 预计到货时间
   */
  private Date planDate;

  /**
   * 下面字段是干线的
   */

  /**
   * 载配网点
   */
  private String distributionSite;

  /**
   * 途经点
   */
  private String passing;

  /**
   * 目的地网点
   */
  private String unloadSite;

  /**
   * 目的地地址
   */
  private String destSiteAddress;

  /**
   * 线路路由
   */
  private String linkRoute;

  /**
   * 线路里程KM
   */
  private String mileage;

  /**
   * 线路类型
   */
  private String linkType;
  /**
   * 预计发货时间
   */
  private Date deliveryDate;
  /**
   * 目的地国家
   */
  private String countryName;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 集装箱号
   */
  private String trailerTruckNo;

  /**
   * 运输方式
   */
  private String requirement;
  /**
   * 运输状态
   */
  private String transportStatus;
  /**
   * 载配地址
   */
  private String distributionAddress;

  /**
   * 载配车牌号
   */
  private String distributionTruckNo;

  /**
   * 车辆归属
   */
  private String vehicleAscription;

  /**
   * 副驾驶1
   */
  private String deputyOne;

  /**
   * 副驾驶2
   */
  private String deputyTwo;

  /**
   * 副驾驶电话1
   */
  private String deputyPhoneOne;

  /**
   * 副驾驶电话2
   */
  private String deputyPhoneTwo;

  /**
   * 装车率
   */
  private String frequency;

  /**
   * 是否回程
   */
  private String takeReturn;

  /**
   * 费用项
   */
  private String expense;

  /**
   * 省ID
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市ID
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 区ID
   */
  private Long regionId;

  /**
   * 区
   */
  private String regionName;
  /**
   * 暂存标识
   */
  private Long tempStorage;

  /**
   * 运输类型
   */
  private String transportationType;

  /**
   * 中转模式选择
   */
  private String transferMode;
  /**
   * 负责人
   */
  private String nickName;
  /**
   * 电话
   */
  private String phone;
  /**
   * 到站联系人
   */
  private String stationLinker;
  /**
   * 到站联系电话
   */
  private String stationPhone;
  /**
   * 中转日期
   */
  private Date transferDate;
  /**
   * 中转地点
   */
  private String transferPlace;
  /**
   * 中转原因
   */
  private String transferReason;
  /**
   * 电话
   */
  private String telephone;
  /**
   * 身份证号
   */
  private String idCardCode;
  /**
   * 中转时间
   */
  private Date transferTime;

}
