package com.yiruantong.outbound.domain.api;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 出库订单业务对象 out_order
 *
 * @author YiRuanTong
 * @date 2024-06-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiOutOrderBo {

  /**
   * 出库单明细
   */
  List<ApiOutOrderDetailBo> detailList;
  /**
   * 出库单ID
   */
  private Long orderId;
  /**
   * 出库编号
   */
  private String orderCode;
  /**
   * ERP单号
   */
  @NotBlank(message = "ERP单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storeOrderCode;
  /**
   * 渠道订单
   */
  private String orderChannel;
  /**
   * 店铺ID
   */
  private Long storeId;
  /**
   * 店铺名称
   */
  private String storeName;
  /**
   * 出货仓库ID
   */
  private Long storageId;
  /**
   * 出货仓库名称
   */
  @NotBlank(message = "出货仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 仓库编号
   */
  private String storageCode;
  /**
   * 经手人ID
   */
  private Long userId;
  /**
   * 经手人
   */
  private String nickName;
  /**
   * 部门ID
   */
  private Long deptId;
  /**
   * 部门
   */
  private String deptName;
  /**
   * 下单日期
   */
  private Date applyDate;
  /**
   * 客户ID
   */
  private Long clientId;
  /**
   * 客户编号
   */
  @NotBlank(message = "客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientCode;
  /**
   * 客户名称
   */
  @NotBlank(message = "客户名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;
  /**
   * Email
   */
  private String email;
  /**
   * 电话
   */
  private String telephone;
  /**
   * 传真
   */
  private String fax;
  /**
   * 交货日期
   */
  private Date deliveryDate;
  /**
   * 合计数量
   */
  private BigDecimal totalQuantityOrder;
  /**
   * 取消数量
   */
  private BigDecimal totalQuantityCanceled;
  /**
   * 已开票数量
   */
  private BigDecimal totalQuantityInvoiced;
  /**
   * 退货数量
   */
  private BigDecimal totalQuantityRefunded;
  /**
   * 已出货数量
   */
  private BigDecimal totalQuantityOuted;
  /**
   * 已发货数量
   */
  private BigDecimal totalQuantityShipped;
  /**
   * 合计毛重
   */
  private BigDecimal totalWeight;
  /**
   * 物件数量
   */
  private Long materialCount;
  /**
   * 包装
   */
  private String orderPackage;
  /**
   * 出库状态
   */
  @NotBlank(message = "出库状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderStatus;
  /**
   * 财务状态
   */
  private String finStatus;
  /**
   * 支付方式
   */
  private Byte payMode;
  /**
   * 支付
   */
  private String payment;
  /**
   * 分拣状态
   */
  private Byte sortingStatus;
  /**
   * 分拣日期
   */
  private Date sortingDate;
  /**
   * 审核人
   */
  private String auditor;
  /**
   * 审核
   */
  private Byte auditing;
  /**
   * 审核日期
   */
  private Date auditDate;
  /**
   * 审核备注
   */
  private String auditRemark;
  /**
   * 波次单ID
   */
  private Long orderWaveId;
  /**
   * 波次单号
   */
  private String orderWaveCode;

  /**
   * 公司
   */
  private String company;
  /**
   * 邮政编码
   */
  private String postCode;
  /**
   * 国家ID
   */
  private Long countryId;
  /**
   * 国家
   */
  private String countryName;
  /**
   * 国家全称
   */
  private String countryFullName;
  /**
   * 省ID
   */
  private Long provinceId;
  /**
   * 省
   */
  @NotBlank(message = "省不能为空", groups = {AddGroup.class, EditGroup.class})
  private String provinceName;
  /**
   * 市ID
   */
  private Long cityId;
  /**
   * 市
   */
  @NotBlank(message = "市不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cityName;
  /**
   * 区ID
   */
  private Long regionId;
  /**
   * 区
   */
  @NotBlank(message = "区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionName;
  /**
   * 街道
   */
  private String street;
  /**
   * 快递类别
   */
  private Byte expressCorpType;
  /**
   * 快递ID
   */
  private Long expressCorpId;
  /**
   * 快递名称
   */
  private String expressCorpName;
  /**
   * 快递编号
   */
  private String expressCode;
  /**
   * 税金额
   */
  private BigDecimal taxAmount;
  /**
   * 客户运费
   */
  private BigDecimal shippingAmount;
  /**
   * 实付快递运费
   */
  private BigDecimal factFreight;
  /**
   * 折扣金额
   */
  private BigDecimal discountAmount;
  /**
   * 商品金额
   */
  private BigDecimal totalProductAmount;
  /**
   * 总计金额
   */
  @NotNull(message = "总计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;
  /**
   * 已支付额
   */
  private BigDecimal totalPaid;
  /**
   * 未付金额
   */
  private BigDecimal totalUnpaid;
  /**
   * 已退款
   */
  private BigDecimal totalRefunded;
  /**
   * 取消总额
   */
  private BigDecimal totalCanceled;
  /**
   * 已开发票金额
   */
  private BigDecimal totalInvoiced;
  /**
   * 在线退款金额
   */
  private BigDecimal totalOnlineRefunded;
  /**
   * 线下退款金额
   */
  private BigDecimal totalOffRefunded;
  /**
   * 汇率
   */
  private BigDecimal exchangeRate;
  /**
   * 币种
   */
  private String currencyCode;
  /**
   * 发货方式
   */
  private String shippingMethod;
  /**
   * 发货描述
   */
  private String shippingDescription;
  /**
   * Email发出
   */
  private Byte isSendEmail;
  /**
   * 礼品信息
   */
  private String giftMessage;
  /**
   * 发件人
   */
  @NotBlank(message = "发件人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingName;
  /**
   * 开票名称
   */
  private String billingName;
  /**
   * 发件人地址
   */
  @NotBlank(message = "发件人地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingAddress;
  /**
   * 开票地址
   */
  private String billingAddress;
  /**
   * 优惠额度
   */
  private BigDecimal favourAmount;
  /**
   * 税率
   */
  private BigDecimal rate;
  /**
   * 合计价税
   */
  private BigDecimal totalRateAmount;
  /**
   * 标签
   */
  private String tag;
  /**
   * 单据类型
   */
  @NotBlank(message = "单据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;
  /**
   * 妥投天数
   */
  private Long signDays;
  /**
   * 货主ID
   */
  private Long consignorId;
  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;
  /**
   * 货主名称
   */
  private String consignorName;
  /**
   * 手机号
   */
  @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;
  /**
   * 关联单号
   */
  private String externalNo;
  /**
   * 关联单号2
   */
  private String externalNo2;
  /**
   * 清关状态
   */
  private String ciqStatus;
  /**
   * 清关类型
   */
  private String ciqCheckType;
  /**
   * 大头笔
   */
  private String bigPen;
  /**
   * 支付方式
   */
  private String paymentWay;
  /**
   * 司机
   */
  private String driverName;
  /**
   * 集装箱号
   */
  private String containerNo;
  /**
   * 项目费用ID
   */
  private String feeItemIds;
  /**
   * 订货单ID
   */
  private Long retailId;
  /**
   * 订货单编号
   */
  private String retailCode;
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
   * 合计净重
   */
  private BigDecimal totalNetWeight;
  /**
   * 来源类别
   */
  private String sourceType;
  /**
   * 来源ID
   */
  private String sourceId;
  /**
   * 来源单号
   */
  private String sourceCode;
  /**
   * 线路Id
   */
  private Long lineId;
  /**
   * 线路编号
   */
  private String lineCode;
  /**
   * 线路名称
   */
  private String lineName;
  /**
   * 配送类型
   */
  private String distributionType;
  /**
   * 合计体积
   */
  private BigDecimal totalCube;
  /**
   * 上传文件
   */
  private String uploadFile;
  /**
   * 拣货状态
   */
  private String pickingStatus;
  /**
   * 配货状态
   */
  private String matchStatus;
  /**
   * 打包状态
   */
  private String packageStatus;
  /**
   * 销售组织
   */
  private String consignorNameSale;
  /**
   * 销售组织编号
   */
  private String consignorCodeSale;
  /**
   * 销售组织ID
   */
  private Long consignorIdSale;
  /**
   * 实际重量
   */
  private BigDecimal factWeight;
  /**
   * 是否容器商品
   */
  private Long isPlate;
  /**
   * 要求到货日期
   */
  private Date askArrivalDate;
  /**
   * 承运商Id
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
   * 物流重量合计
   */
  private BigDecimal totalLogisticsWeight;
  /**
   * 预计到达时间
   */
  private Date arriveDate;
  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;
  /**
   * 物流专线
   */
  private String expressCorpLine;
  /**
   * 物流电话
   */
  private String expressCorpTel;
  /**
   * 发货人电话
   */
  private String billingMobile;
  /**
   * 出库日期
   */
  private Date outDate;
  /**
   * 是否是PDA修改状态
   */
  private boolean isApp;

}
