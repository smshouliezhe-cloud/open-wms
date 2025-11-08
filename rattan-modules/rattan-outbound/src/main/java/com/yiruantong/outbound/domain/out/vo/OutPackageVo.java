package com.yiruantong.outbound.domain.out.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.outbound.domain.out.OutPackage;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 打包单视图对象 out_package
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutPackage.class)
public class OutPackageVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 打包单ID
   */
  @ExcelProperty(value = "打包单ID")
  private Long packageId;

  /**
   * 打包单号
   */
  @ExcelProperty(value = "打包单号")
  private String packageCode;

  /**
   * 出库订单ID
   */
  @ExcelProperty(value = "出库订单ID")
  private Long orderId;

  /**
   * 出库单号
   */
  @ExcelProperty(value = "出库单号")
  private String orderCode;

  /**
   * ERP单号
   */
  @ExcelProperty(value = "ERP单号")
  private String storeOrderCode;

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
   * 部门名称
   */
  @ExcelProperty(value = "部门名称")
  private String deptName;

  /**
   * 出库日期
   */
  @ExcelProperty(value = "出库日期")
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
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalPackageQuantity;

  /**
   * 物件数量
   */
  @ExcelProperty(value = "物件数量")
  private Long materialCount;

  /**
   * 运费金额
   */
  @ExcelProperty(value = "运费金额")
  private BigDecimal shippingAmount;

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
   * 合计金额
   */
  @ExcelProperty(value = "合计金额")
  private BigDecimal totalAmount;

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
   * 关联包裹号
   */
  @ExcelProperty(value = "关联包裹号")
  private String refPackageCode;

  /**
   * 打包状态
   */
  @ExcelProperty(value = "打包状态")
  private String packageStatus;

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
   * 已结算金额
   */
  @ExcelProperty(value = "已结算金额")
  private BigDecimal settleAmount;

  /**
   * 未已结算金额
   */
  @ExcelProperty(value = "未已结算金额")
  private BigDecimal unSettleAmount;

  /**
   * 快递ID
   */
  @ExcelProperty(value = "快递ID")
  private Long expressCorpId;

  /**
   * 快递公司名称
   */
  @ExcelProperty(value = "快递公司名称")
  private String expressCorpName;

  /**
   * 快递编号
   */
  @ExcelProperty(value = "快递编号")
  private String expressCode;

  /**
   * 打印状态
   */
  @ExcelProperty(value = "打印状态")
  private String printStatus;

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
   * 是否已发货
   */
  @ExcelProperty(value = "是否已发货")
  private Byte isSend;

  /**
   * 发货人姓名
   */
  @ExcelProperty(value = "发货人姓名")
  private String shippingName;

  /**
   * 发货人地址
   */
  @ExcelProperty(value = "发货人地址")
  private String shippingAddress;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * 电话
   */
  @ExcelProperty(value = "电话")
  private String tel;

  /**
   * Email
   */
  @ExcelProperty(value = "Email")
  private String email;

  /**
   * 订单渠道
   */
  @ExcelProperty(value = "订单渠道")
  private String orderChannel;

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
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 是否生成费用
   */
  @ExcelProperty(value = "是否生成费用")
  private Byte isCreateFee;

  /**
   * 合计件数
   */
  @ExcelProperty(value = "合计件数")
  private BigDecimal totalPackage;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

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
   * 合计净重
   */
  @ExcelProperty(value = "合计净重")
  private BigDecimal totalNetWeight;

  /**
   * 上传文件
   */
  @ExcelProperty(value = "上传文件")
  private String uploadFile;

  /**
   * 合计大单位数量
   */
  @ExcelProperty(value = "合计大单位数量")
  private BigDecimal totalBigQty;

  /**
   * 税金额
   */
  @ExcelProperty(value = "税金额")
  private BigDecimal taxAmount;

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
   * 调入仓库
   */
  @ExcelProperty(value = "调入仓库")
  private String storageNameIn;

  /**
   * 调入仓ID
   */
  @ExcelProperty(value = "调入仓ID")
  private Long storageIdIn;

  /**
   * 费用项
   */
  @ExcelProperty(value = "费用项")
  private String feeItemIds;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;


}
