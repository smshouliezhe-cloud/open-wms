package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.out.OutOrder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库订单视图对象 out_order
 *
 * @author YiRuanTong
 * @date 2024-01-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrder.class)
public class OutOrderPrintVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 出库单ID
   */
  @ExcelProperty(value = "出库单ID")
  private Long orderId;

  /**
   * 出库编号
   */
  @ExcelProperty(value = "出库编号")
  private String orderCode;

  /**
   * ERP单号
   */
  @ExcelProperty(value = "ERP单号")
  private String storeOrderCode;

  /**
   * 渠道订单
   */
  @ExcelProperty(value = "渠道订单")
  private String orderChannel;

  /**
   * 店铺ID
   */
  @ExcelProperty(value = "店铺ID")
  private Long storeId;

  /**
   * 店铺名称
   */
  @ExcelProperty(value = "店铺名称")
  private String storeName;

  /**
   * 出货仓库ID
   */
  @ExcelProperty(value = "出货仓库ID")
  private Long storageId;

  /**
   * 出货仓库名称
   */
  @ExcelProperty(value = "出货仓库名称")
  private String storageName;

  /**
   * 经手人ID
   */
  @ExcelProperty(value = "经手人ID")
  private Long userId;

  /**
   * 经手人
   */
  @ExcelProperty(value = "经手人")
  private String nickName;

  /**
   * 部门ID
   */
  @ExcelProperty(value = "部门ID")
  private Long deptId;

  /**
   * 部门
   */
  @ExcelProperty(value = "部门")
  private String deptName;

  /**
   * 下单日期
   */
  @ExcelProperty(value = "下单日期")
  private Date applyDate;

  /**
   * 客户ID
   */
  @ExcelProperty(value = "客户ID")
  private Long clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户名称
   */
  @ExcelProperty(value = "客户名称")
  private String clientShortName;

  /**
   * Email
   */
  @ExcelProperty(value = "Email")
  private String email;

  /**
   * 电话
   */
  @ExcelProperty(value = "电话")
  private String telephone;

  /**
   * 传真
   */
  @ExcelProperty(value = "传真")
  private String fax;

  /**
   * 交货日期
   */
  @ExcelProperty(value = "交货日期")
  private Date deliveryDate;

  /**
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalQuantityOrder;

  /**
   * 取消数量
   */
  @ExcelProperty(value = "取消数量")
  private BigDecimal totalQuantityCanceled;

  /**
   * 已开票数量
   */
  @ExcelProperty(value = "已开票数量")
  private BigDecimal totalQuantityInvoiced;

  /**
   * 退货数量
   */
  @ExcelProperty(value = "退货数量")
  private BigDecimal totalQuantityRefunded;

  /**
   * 已出货数量
   */
  @ExcelProperty(value = "已出货数量")
  private BigDecimal totalQuantityOuted;

  /**
   * 已发货数量
   */
  @ExcelProperty(value = "已发货数量")
  private BigDecimal totalQuantityShipped;

  /**
   * 合计毛重
   */
  @ExcelProperty(value = "合计毛重")
  private BigDecimal totalWeight;

  /**
   * 物件数量
   */
  @ExcelProperty(value = "物件数量")
  private Long materialCount;

  /**
   * 包装
   */
  @ExcelProperty(value = "包装")
  private String orderPackage;

  /**
   * 出库状态
   */
  @ExcelProperty(value = "出库状态")
  private String orderStatus;

  /**
   * 财务状态
   */
  @ExcelProperty(value = "财务状态")
  private String finStatus;

  /**
   * 支付方式
   */
  @ExcelProperty(value = "支付方式")
  private Byte payMode;

  /**
   * 支付
   */
  @ExcelProperty(value = "支付")
  private String payment;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Byte sortingStatus;

  /**
   * 分拣日期
   */
  @ExcelProperty(value = "分拣日期")
  private Date sortingDate;

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
   * 波次单ID
   */
  @ExcelProperty(value = "波次单ID")
  private Long orderWaveId;

  /**
   * 波次单号
   */
  @ExcelProperty(value = "波次单号")
  private String orderWaveCode;

  /**
   * 出货仓库ID
   */
  @ExcelProperty(value = "出货仓库ID")
  private Long sortStorageId;

  /**
   * 出货仓库名称
   */
  @ExcelProperty(value = "出货仓库名称")
  private String sortStorageName;

  /**
   * 公司
   */
  @ExcelProperty(value = "公司")
  private String company;

  /**
   * 邮政编码
   */
  @ExcelProperty(value = "邮政编码")
  private String postCode;

  /**
   * 国家ID
   */
  @ExcelProperty(value = "国家ID")
  private Long countryId;

  /**
   * 国家
   */
  @ExcelProperty(value = "国家")
  private String countryName;

  /**
   * 国家全称
   */
  @ExcelProperty(value = "国家全称")
  private String countryFullName;

  /**
   * 省ID
   */
  @ExcelProperty(value = "省ID")
  private Long provinceId;

  /**
   * 省
   */
  @ExcelProperty(value = "省")
  private String provinceName;

  /**
   * 市ID
   */
  @ExcelProperty(value = "市ID")
  private Long cityId;

  /**
   * 市
   */
  @ExcelProperty(value = "市")
  private String cityName;

  /**
   * 区ID
   */
  @ExcelProperty(value = "区ID")
  private Long regionId;

  /**
   * 区
   */
  @ExcelProperty(value = "区")
  private String regionName;

  /**
   * 街道
   */
  @ExcelProperty(value = "街道")
  private String street;

  /**
   * 快递类别
   */
  @ExcelProperty(value = "快递类别")
  private Byte expressCorpType;

  /**
   * 快递ID
   */
  @ExcelProperty(value = "快递ID")
  private Long expressCorpId;

  /**
   * 快递名称
   */
  @ExcelProperty(value = "快递名称")
  private String expressCorpName;

  /**
   * 快递编号
   */
  @ExcelProperty(value = "快递编号")
  private String expressCode;

  /**
   * 税金额
   */
  @ExcelProperty(value = "税金额")
  private BigDecimal taxAmount;

  /**
   * 客户运费
   */
  @ExcelProperty(value = "客户运费")
  private BigDecimal shippingAmount;

  /**
   * 实付快递运费
   */
  @ExcelProperty(value = "实付快递运费")
  private BigDecimal factFreight;

  /**
   * 折扣金额
   */
  @ExcelProperty(value = "折扣金额")
  private BigDecimal discountAmount;

  /**
   * 商品金额
   */
  @ExcelProperty(value = "商品金额")
  private BigDecimal totalProductAmount;

  /**
   * 总计金额
   */
  @ExcelProperty(value = "总计金额")
  private BigDecimal totalAmount;

  /**
   * 已支付额
   */
  @ExcelProperty(value = "已支付额")
  private BigDecimal totalPaid;

  /**
   * 未付金额
   */
  @ExcelProperty(value = "未付金额")
  private BigDecimal totalUnpaid;

  /**
   * 已退款
   */
  @ExcelProperty(value = "已退款")
  private BigDecimal totalRefunded;

  /**
   * 取消总额
   */
  @ExcelProperty(value = "取消总额")
  private BigDecimal totalCanceled;

  /**
   * 已开发票金额
   */
  @ExcelProperty(value = "已开发票金额")
  private BigDecimal totalInvoiced;

  /**
   * 在线退款金额
   */
  @ExcelProperty(value = "在线退款金额")
  private BigDecimal totalOnlineRefunded;

  /**
   * 线下退款金额
   */
  @ExcelProperty(value = "线下退款金额")
  private BigDecimal totalOffRefunded;

  /**
   * 汇率
   */
  @ExcelProperty(value = "汇率")
  private BigDecimal exchangeRate;

  /**
   * 币种
   */
  @ExcelProperty(value = "币种")
  private String currencyCode;

  /**
   * 发货方式
   */
  @ExcelProperty(value = "发货方式")
  private String shippingMethod;

  /**
   * 发货描述
   */
  @ExcelProperty(value = "发货描述")
  private String shippingDescription;

  /**
   * Email发出
   */
  @ExcelProperty(value = "Email发出")
  private Byte isSendEmail;

  /**
   * 礼品信息
   */
  @ExcelProperty(value = "礼品信息")
  private String giftMessage;

  /**
   * 发件人
   */
  @ExcelProperty(value = "发件人")
  private String shippingName;

  /**
   * 开票名称
   */
  @ExcelProperty(value = "开票名称")
  private String billingName;

  /**
   * 发件人地址
   */
  @ExcelProperty(value = "发件人地址")
  private String shippingAddress;

  /**
   * 开票地址
   */
  @ExcelProperty(value = "开票地址")
  private String billingAddress;

  /**
   * 优惠额度
   */
  @ExcelProperty(value = "优惠额度")
  private BigDecimal favourAmount;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 合计价税
   */
  @ExcelProperty(value = "合计价税")
  private BigDecimal totalRateAmount;

  /**
   * 标签
   */
  @ExcelProperty(value = "标签")
  private String tag;

  /**
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

  /**
   * 妥投天数
   */
  @ExcelProperty(value = "妥投天数")
  private Long signDays;

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
   * 手机号
   */
  @ExcelProperty(value = "手机号")
  private String mobile;

  /**
   * 关联单号
   */
  @ExcelProperty(value = "关联单号")
  private String externalNo;

  /**
   * 关联单号2
   */
  @ExcelProperty(value = "关联单号2")
  private String externalNo2;

  /**
   * 清关状态
   */
  @ExcelProperty(value = "清关状态")
  private String ciqStatus;

  /**
   * 清关类型
   */
  @ExcelProperty(value = "清关类型")
  private String ciqCheckType;

  /**
   * 大头笔
   */
  @ExcelProperty(value = "大头笔")
  private String bigPen;

  /**
   * 支付方式
   */
  @ExcelProperty(value = "支付方式")
  private String paymentWay;

  /**
   * 司机
   */
  @ExcelProperty(value = "司机")
  private String driverName;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 项目费用ID
   */
  @ExcelProperty(value = "项目费用ID")
  private String feeItemIds;

  /**
   * 订货单ID
   */
  @ExcelProperty(value = "订货单ID")
  private Long retailId;

  /**
   * 订货单编号
   */
  @ExcelProperty(value = "订货单编号")
  private String retailCode;

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
   * 合计净重
   */
  @ExcelProperty(value = "合计净重")
  private BigDecimal totalNetWeight;

  /**
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源ID
   */
  @ExcelProperty(value = "来源ID")
  private String sourceId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 线路Id
   */
  @ExcelProperty(value = "线路Id")
  private Long lineId;

  /**
   * 线路编号
   */
  @ExcelProperty(value = "线路编号")
  private String lineCode;

  /**
   * 线路名称
   */
  @ExcelProperty(value = "线路名称")
  private String lineName;

  /**
   * 配送类型
   */
  @ExcelProperty(value = "配送类型")
  private String distributionType;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 上传文件
   */
  @ExcelProperty(value = "上传文件")
  private String uploadFile;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQtyTotal;

  /**
   * 拣货状态
   */
  @ExcelProperty(value = "拣货状态")
  private String pickingStatus;

  /**
   * 配货状态
   */
  @ExcelProperty(value = "配货状态")
  private String matchStatus;

  /**
   * 打包状态
   */
  @ExcelProperty(value = "打包状态")
  private String packageStatus;

  /**
   * 调入仓ID
   */
  @ExcelProperty(value = "调入仓ID")
  private Long storageIdIn;

  /**
   * 调入仓库
   */
  @ExcelProperty(value = "调入仓库")
  private String storageNameIn;

  /**
   * 销售组织
   */
  @ExcelProperty(value = "销售组织")
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  @ExcelProperty(value = "销售组织编号")
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  @ExcelProperty(value = "销售组织ID")
  private Long consignorIdSale;

  /**
   * 实际重量
   */
  @ExcelProperty(value = "实际重量")
  private BigDecimal factWeight;


  /**
   * 二维码
   */
  @ExcelProperty(value = "二维码")
  private String twoDimensionCode;

}
