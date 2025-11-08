package com.yiruantong.composite.domain.tms.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WayBillScanTableBo {

  /**
   * 运单明细ID
   */
  private Long wayBillDetailId;

  /**
   * 运单ID
   */
  private Long wayBillId;

  /**
   * 商品ID
   */
  private Long productId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 条形码
   */
  private String productModel;

  /**
   * 商品规格
   */
  private String productSpec;

  /**
   * 数量
   */
  private BigDecimal quantityOrder;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 单价
   */
  private BigDecimal salePrice;

  /**
   * 金额
   */
  private BigDecimal rowTotal;

  /**
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 毛重(吨)
   */
  private BigDecimal rowWeightTon;

  /**
   * 明细状态
   */
  private String statusDetail;

  /**
   * 出站
   */
  private String outStation;

  /**
   * 发站
   */
  private String arriveStation;

  /**
   * 载配网点
   */
  private String distributionSite;

  /**
   * 收货地址
   */
  private String distributionAddress;

  /**
   * 收货人
   */
  private String consigneeName;

  /**
   * 收货人电话
   */
  private String consigneeMobile;

  /**
   * 收货地址
   */
  private String consigneeAddress;

  /**
   * 配送路线
   */
  private String distributionRoute;

  /**
   * 合计件数
   */
  private BigDecimal totalQuantity;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 运输要求
   */
  private String requirement;

  /**
   * 是否上门取件
   */
  private Byte take;

  /**
   * 紧急度
   */
  private String degrees;

  /**
   * 生成方式
   */
  private String generate;

  /**
   * 卸车网点
   */
  private String unloadSite;

  /**
   * 卸车地址
   */
  private String unloadAddress;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 箱号
   */
  private String carton;

  /**
   * 包装方式
   */
  private String packingMethod;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 司机扫描状态
   */
  private String driverScanState;

  /**
   * 未扫描数量
   */
  private BigDecimal unFinishedQuantity;

  /**
   * 运费单价
   */
  private BigDecimal freightPrice;

  /**
   * 小计运费
   */
  private BigDecimal rowFreight;

  /**
   * 允损比例
   */
  private BigDecimal allowRatio;

  /**
   * 外包单价
   */
  private BigDecimal outSourcePrice;

  /**
   * 外包运费
   */
  private BigDecimal outSourceFreight;

  /**
   * 计算公式
   */
  private String billFormula;

  /**
   * 货损赔偿
   */
  private BigDecimal damageClai;

  /**
   * 损耗金额
   */
  private BigDecimal wastageMoney;

  /**
   * 损耗单价
   */
  private BigDecimal wastagePrice;

  /**
   * 损耗数量
   */
  private Long wastageQuantity;

  /**
   * 允损数量
   */
  private Long allowQuantity;

  /**
   * 外包合计
   */
  private BigDecimal outSourceTotal;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
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
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源主表ID
   */
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  private String sourceDetailId;

  /**
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private String rowNetWeight;

  /**
   * 是否重货
   */
  private String isHeavyCargo;

  /**
   * 运单编号
   */
  @ExcelProperty(value = "运单编号")
  private String wayBillCode;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 货主编号
   */
  @ExcelProperty(value = "货主编号")
  private String consignorCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 口岸ID
   */
  @ExcelProperty(value = "口岸ID")
  private Long portId;

  /**
   * 口岸
   */
  @ExcelProperty(value = "口岸")
  private String portName;

  /**
   * 国内快递ID
   */
  @ExcelProperty(value = "国内快递ID")
  private Long expressCorpId;

  /**
   * 国内快递类型
   */
  @ExcelProperty(value = "国内快递类型")
  private String expressCorpType;

  /**
   * 国内快递名称
   */
  @ExcelProperty(value = "国内快递名称")
  private String expressCorpName;

  /**
   * 国内快递便阿红
   */
  @ExcelProperty(value = "国内快递便阿红")
  private String expressCode;

  /**
   * 航空主单ID
   */
  @ExcelProperty(value = "航空主单ID")
  private Long voyageId;

  /**
   * 航空主单编号
   */
  @ExcelProperty(value = "航空主单编号")
  private String voyageCode;

  /**
   * 托盘号
   */
  @ExcelProperty(value = "托盘号")
  private String plateCode;

  /**
   * 合计净重
   */
  @ExcelProperty(value = "合计净重")
  private BigDecimal totalNetWeight;

  /**
   * 单位
   */
  @ExcelProperty(value = "单位")
  private String unit;

  /**
   * 商品合计数量
   */
  @ExcelProperty(value = "商品合计数量")
  private BigDecimal totalQuantityOrder;

  /**
   * 合计金额
   */
  @ExcelProperty(value = "合计金额")
  private Long grandTotal;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String orderStatus;

  /**
   * 打印状态
   */
  @ExcelProperty(value = "打印状态")
  private String printStatus;

  /**
   * 发货人
   */
  @ExcelProperty(value = "发货人")
  private String billingName;

  /**
   * 发货人电话
   */
  @ExcelProperty(value = "发货人电话")
  private String billingMobile;

  /**
   * 发货人地址
   */
  @ExcelProperty(value = "发货人地址")
  private String billingAddress;

  /**
   * 收货人身份证号
   */
  @ExcelProperty(value = "收货人身份证号")
  private String consigneeIdcard;

  /**
   * 收货人邮政编码
   */
  @ExcelProperty(value = "收货人邮政编码")
  private String consigneePostCode;

  /**
   * 省ID
   */
  @ExcelProperty(value = "省ID")
  private Long provinceId;

  /**
   * 收货人省
   */
  @ExcelProperty(value = "收货人省")
  private String provinceName;

  /**
   * 市ID
   */
  @ExcelProperty(value = "市ID")
  private Long cityId;

  /**
   * 收货人市
   */
  @ExcelProperty(value = "收货人市")
  private String cityName;

  /**
   * 区ID
   */
  @ExcelProperty(value = "区ID")
  private Long regionId;

  /**
   * 收货人区
   */
  @ExcelProperty(value = "收货人区")
  private String regionName;

  /**
   * 街道
   */
  @ExcelProperty(value = "街道")
  private String street;

  /**
   * 揽收人
   */
  @ExcelProperty(value = "揽收人")
  private String collectName;

  /**
   * 揽收人电话
   */
  @ExcelProperty(value = "揽收人电话")
  private String collectMobile;

  /**
   * 揽收门店
   */
  @ExcelProperty(value = "揽收门店")
  private String collectStore;

  /**
   * 揽收时间
   */
  @ExcelProperty(value = "揽收时间")
  private Date collectDate;

  /**
   * 计划落件口
   */
  @ExcelProperty(value = "计划落件口")
  private String planDropOffPort;

  /**
   * 实际落件口
   */
  @ExcelProperty(value = "实际落件口")
  private String actualDropOffPort;

  /**
   * 动态称重
   */
  @ExcelProperty(value = "动态称重")
  private BigDecimal factWeight;

  /**
   * 组板结果
   */
  @ExcelProperty(value = "组板结果")
  private String groupBoard;

  /**
   * 操作人ID
   */
  @ExcelProperty(value = "操作人ID")
  private Long userId;

  /**
   * 组板操作人
   */
  @ExcelProperty(value = "组板操作人")
  private String userTrueName;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核
   */
  @ExcelProperty(value = "审核")
  private Byte auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

  /**
   * 审核备注
   */
  @ExcelProperty(value = "审核备注")
  private String auditRemark;

  /**
   * 是否仓配
   */
  @ExcelProperty(value = "是否仓配")
  private Long takeWarehoDistrib;

  /**
   * 组板方式
   */
  @ExcelProperty(value = "组板方式")
  private String panelWay;

  /**
   * 异常原因
   */
  @ExcelProperty(value = "异常原因")
  private String abnormalReason;

  /**
   * 代收货款状态
   */
  @ExcelProperty(value = "代收货款状态")
  private String collectStatus;

  /**
   * 身份证状态
   */
  @ExcelProperty(value = "身份证状态")
  private String idcardStatus;

  /**
   * 毛重
   */
  @ExcelProperty(value = "毛重")
  private BigDecimal grossWeight;

  /**
   * 异常时间
   */
  @ExcelProperty(value = "异常时间")
  private Date abnormalDate;

  /**
   * 打印次数
   */
  @ExcelProperty(value = "打印次数")
  private Long printNum;

  /**
   * 组板时间
   */
  @ExcelProperty(value = "组板时间")
  private Date groupBoardDate;

  /**
   * 大头笔
   */
  @ExcelProperty(value = "大头笔")
  private String bigPen;

  /**
   * 邮政小包请求次数
   */
  @ExcelProperty(value = "邮政小包请求次数")
  private Long apiSendCount;

  /**
   * 产品线
   */
  @ExcelProperty(value = "产品线")
  private String productLine;

  /**
   * 客户订单号
   */
  @ExcelProperty(value = "客户订单号")
  private String customerOrderCode;

  /**
   * 计划第2落件口
   */
  @ExcelProperty(value = "计划第2落件口")
  private String planDropOffPort2;

  /**
   * 载配网点Id
   */
  @ExcelProperty(value = "载配网点Id")
  private Long distributionSiteId;

  /**
   * 载配网点编号
   */
  @ExcelProperty(value = "载配网点编号")
  private String siteCode;

  /**
   * 载配网点地址
   */
  @ExcelProperty(value = "载配网点地址")
  private String siteAddress;

  /**
   * 收货人电话1
   */
  @ExcelProperty(value = "收货人电话1")
  private String consigneeMobileOne;

  /**
   * 收货人电话2
   */
  @ExcelProperty(value = "收货人电话2")
  private String consigneeMobileTwo;

  /**
   * 打包件数
   */
  @ExcelProperty(value = "打包件数")
  private BigDecimal totalPackage;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String wayBillType;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 运费合计
   */
  @ExcelProperty(value = "运费合计")
  private BigDecimal totalFreight;

  /**
   * 运费单价
   */
  @ExcelProperty(value = "运费单价")
  private BigDecimal orderFreightPrice;

  /**
   * 付款方式
   */
  @ExcelProperty(value = "付款方式")
  private String paymentMethod;

  /**
   * 货品价值
   */
  @ExcelProperty(value = "货品价值 ")
  private BigDecimal productValue;

  /**
   * 费用项
   */
  @ExcelProperty(value = "费用项")
  private String expense;

  /**
   * 是否保价
   */
  @ExcelProperty(value = "是否保价")
  private Byte takeInsured;

  /**
   * 代收货款
   */
  @ExcelProperty(value = "代收货款")
  private Byte collection;

  /**
   * 寄件客户名称
   */
  @ExcelProperty(value = "寄件客户名称")
  private String mailCustomer;

  /**
   * 路由
   */
  @ExcelProperty(value = "路由")
  private String routebill;

  /**
   * 送货方式
   */
  @ExcelProperty(value = "送货方式")
  private String deliveryMethod;

  /**
   * 保存收货人信息
   */
  @ExcelProperty(value = "保存收货人信息")
  private Byte storePreservation;

  /**
   * 保存发货人信息
   */
  @ExcelProperty(value = "保存发货人信息")
  private Byte addressPreservation;

  /**
   * 承运商Id
   */
  @ExcelProperty(value = "承运商Id")
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
   * 司机Id
   */
  @ExcelProperty(value = "司机Id")
  private Long driverId;

  /**
   * 司机姓名
   */
  @ExcelProperty(value = "司机姓名")
  private String driverName;

  /**
   * 司机电话
   */
  @ExcelProperty(value = "司机电话")
  private String tel;

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
   * 预计到达时间
   */
  @ExcelProperty(value = "预计到达时间")
  private Date arriveDate;

  /**
   * 线路类型
   */
  @ExcelProperty(value = "线路类型")
  private String lineType;

  /**
   * 副驾驶1
   */
  @ExcelProperty(value = "副驾驶1")
  private String deputyOne;

  /**
   * 副驾驶2
   */
  @ExcelProperty(value = "副驾驶2")
  private String deputyTwo;

  /**
   * 副驾驶电话1
   */
  @ExcelProperty(value = "副驾驶电话1")
  private String deputyPhoneOne;

  /**
   * 副驾驶电话2
   */
  @ExcelProperty(value = "副驾驶电话2")
  private String deputyPhoneTwo;

  /**
   * 挂车牌号
   */
  @ExcelProperty(value = "挂车牌号")
  private String trailerTruckNo;

  /**
   * 车辆归属
   */
  @ExcelProperty(value = "车辆归属")
  private String ownerName;

  /**
   * 载配车牌号
   */
  @ExcelProperty(value = "载配车牌号")
  private String distributionTruckNo;

  /**
   * 车辆线路
   */
  @ExcelProperty(value = "车辆线路")
  private String vehicleLine;

  /**
   * 线路路由
   */
  @ExcelProperty(value = "线路路由")
  private String linkRoute;

  /**
   * 运输单号
   */
  @ExcelProperty(value = "运输单号")
  private String transportbill;

  /**
   * 派车单号
   */
  @ExcelProperty(value = "派车单号")
  private String pickupCode;

  /**
   * 是否配仓
   */
  @ExcelProperty(value = "是否配仓")
  private Byte isStoreMate;

  /**
   * 是否保价
   */
  @ExcelProperty(value = "是否保价")
  private Byte insured;

  /**
   * 关联单号
   */
  @ExcelProperty(value = "关联单号")
  private String relationCode;

  /**
   * 出库订单Id
   */
  @ExcelProperty(value = "出库订单Id")
  private Long orderId;

  /**
   * 出库订单编号
   */
  @ExcelProperty(value = "出库订单编号")
  private String orderCode;

  /**
   * 车队负责人
   */
  @ExcelProperty(value = "车队负责人")
  private String teamLeader;

  /**
   * 车队名称
   */
  @ExcelProperty(value = "车队名称")
  private String vehicleGroupName;

  /**
   * 车队ID
   */
  @ExcelProperty(value = "车队ID")
  private Long vehicleGroupId;

  /**
   * 车队编号
   */
  @ExcelProperty(value = "车队编号")
  private String vehicleGroupCode;

  /**
   * 负责人电话
   */
  @ExcelProperty(value = "负责人电话")
  private String personCharge;

  /**
   * 运输状态
   */
  @ExcelProperty(value = "运输状态")
  private String transportStatus;

  /**
   * 收货客户
   */
  @ExcelProperty(value = "收货客户")
  private String clientShortName;

  /**
   * 线路ID
   */
  @ExcelProperty(value = "线路ID")
  private Long lineId;

  /**
   * 线路名称
   */
  @ExcelProperty(value = "线路名称")
  private String lineName;

  /**
   * 配送日期
   */
  @ExcelProperty(value = "配送日期")
  private Date deliveryDate;

  /**
   * 运输类型
   */
  @ExcelProperty(value = "运输类型")
  private String transportationType;

  /**
   * 运输方式
   */
  @ExcelProperty(value = "运输方式")
  private String transportMode;

  /**
   * 出发地
   */
  @ExcelProperty(value = "出发地")
  private String pricingManner;

  /**
   * 实际运费
   */
  @ExcelProperty(value = "实际运费")
  private BigDecimal factFreight;

  /**
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String placeOrigin;

  /**
   * 载配网点
   */
  @ExcelProperty(value = "载配网点")
  private String siteName;

  /**
   * 生成方式
   */
  @ExcelProperty(value = "生成方式")
  private String makeWay;

  /**
   * 已扫描数量
   */
  @ExcelProperty(value = "已扫描数量")
  private BigDecimal finishedQuantity;

}
