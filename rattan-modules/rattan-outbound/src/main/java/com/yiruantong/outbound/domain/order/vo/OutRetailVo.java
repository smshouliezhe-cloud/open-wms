package com.yiruantong.outbound.domain.order.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.order.OutRetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 订货单视图对象 out_retail
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutRetail.class)
public class OutRetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 要货单ID
   */
  @ExcelProperty(value = "要货单ID")
  private Long retailId;

  /**
   * 要货单编号
   */
  @ExcelProperty(value = "要货单编号")
  private String retailCode;

  /**
   * 收款期限
   */
  @ExcelProperty(value = "收款期限")
  private Date payLimitDate;

  /**
   * 要货单状态
   */
  @ExcelProperty(value = "要货单状态")
  private String retailStatus;

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
   * 客户简称
   */
  @ExcelProperty(value = "客户简称")
  private String clientShortName;

  /**
   * 交货日期
   */
  @ExcelProperty(value = "交货日期")
  private Date deliveryDate;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核
   */
  @ExcelProperty(value = "审核")
  private Long auditing;

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
   * 店铺订单编号
   */
  @ExcelProperty(value = "店铺订单编号")
  private String storeOrderCode;

  /**
   * 订单渠道
   */
  @ExcelProperty(value = "订单渠道")
  private String orderChannel;

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
   * 已发货数量
   */
  @ExcelProperty(value = "已发货数量")
  private BigDecimal totalQuantityShipped;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 财务状态
   */
  @ExcelProperty(value = "财务状态")
  private String finStatusText;

  /**
   * 付款模式
   */
  @ExcelProperty(value = "付款模式")
  private String payMode;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Long sortingStatus;

  /**
   * 分拣时间
   */
  @ExcelProperty(value = "分拣时间")
  private Date sortingDate;

  /**
   * 邮费
   */
  @ExcelProperty(value = "邮费")
  private BigDecimal shippingAmount;

  /**
   * 实付快递运费
   */
  @ExcelProperty(value = "实付快递运费")
  private BigDecimal factFreight;

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
   * 已开发票金额
   */
  @ExcelProperty(value = "已开发票金额")
  private BigDecimal totalInvoiced;

  /**
   * 合计价税
   */
  @ExcelProperty(value = "合计价税")
  private BigDecimal totalRateMoney;

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
   * 开票信息
   */
  @ExcelProperty(value = "开票信息")
  private String invoiceInfo;

  /**
   * 收货人信息
   */
  @ExcelProperty(value = "收货人信息")
  private String consigneeInfo;

  /**
   * 发货人信息
   */
  @ExcelProperty(value = "发货人信息")
  private String shipperInfo;

  /**
   * 文件
   */
  @ExcelProperty(value = "文件")
  private String files;

  /**
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

  /**
   * 订单原始总价
   */
  @ExcelProperty(value = "订单原始总价")
  private BigDecimal sumPrice;

  /**
   * 商品促销折扣金额
   */
  @ExcelProperty(value = "商品促销折扣金额")
  private BigDecimal discountAmount;

  /**
   * 商品促销直降金额
   */
  @ExcelProperty(value = "商品促销直降金额")
  private BigDecimal directAmount;

  /**
   * 实付金额
   */
  @ExcelProperty(value = "实付金额")
  private BigDecimal payAmount;

  /**
   * 采购商ID
   */
  @ExcelProperty(value = "采购商ID")
  private Long providerId;

  /**
   * 采购商爱那红
   */
  @ExcelProperty(value = "采购商爱那红")
  private String providerCode;

  /**
   * 采购商名称
   */
  @ExcelProperty(value = "采购商名称")
  private String providerShortName;

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
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 单据类型名称
   */
  @ExcelProperty(value = "单据类型名称")
  private String orderTypeName;

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


}
