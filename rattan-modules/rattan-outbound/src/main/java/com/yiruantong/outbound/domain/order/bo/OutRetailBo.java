package com.yiruantong.outbound.domain.order.bo;

import com.yiruantong.outbound.domain.order.OutRetail;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 订货单业务对象 out_retail
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutRetail.class, reverseConvertGenerate = false)
public class OutRetailBo extends BaseEntity {

  /**
   * 要货单ID
   */
  @NotNull(message = "要货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long retailId;

  /**
   * 要货单编号
   */
  @NotBlank(message = "要货单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String retailCode;

  /**
   * 收款期限
   */
  @NotNull(message = "收款期限不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date payLimitDate;

  /**
   * 要货单状态
   */
  @NotBlank(message = "要货单状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String retailStatus;

  /**
   * 客户ID
   */
  @NotNull(message = "客户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientId;

  /**
   * 客户编号
   */
  @NotBlank(message = "客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientCode;

  /**
   * 客户简称
   */
  @NotBlank(message = "客户简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;

  /**
   * 交货日期
   */
  @NotNull(message = "交货日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deliveryDate;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 审核备注
   */
  @NotBlank(message = "审核备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditRemark;

  /**
   * 店铺订单编号
   */
  @NotBlank(message = "店铺订单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storeOrderCode;

  /**
   * 订单渠道
   */
  @NotBlank(message = "订单渠道不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderChannel;

  /**
   * 经手人ID
   */
  @NotNull(message = "经手人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 经手人
   */
  @NotBlank(message = "经手人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 部门ID
   */
  @NotNull(message = "部门ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 部门名称
   */
  @NotBlank(message = "部门名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantityOrder;

  /**
   * 取消数量
   */
  @NotNull(message = "取消数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantityCanceled;

  /**
   * 已发货数量
   */
  @NotNull(message = "已发货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantityShipped;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 财务状态
   */
  @NotBlank(message = "财务状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String finStatusText;

  /**
   * 付款模式
   */
  @NotBlank(message = "付款模式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String payMode;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sortingStatus;

  /**
   * 分拣时间
   */
  @NotNull(message = "分拣时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date sortingDate;

  /**
   * 邮费
   */
  @NotNull(message = "邮费不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shippingAmount;

  /**
   * 实付快递运费
   */
  @NotNull(message = "实付快递运费不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal factFreight;

  /**
   * 总计金额
   */
  @NotNull(message = "总计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;

  /**
   * 已支付额
   */
  @NotNull(message = "已支付额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPaid;

  /**
   * 已开发票金额
   */
  @NotNull(message = "已开发票金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalInvoiced;

  /**
   * 合计价税
   */
  @NotNull(message = "合计价税不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalRateMoney;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 开票信息
   */
  @NotBlank(message = "开票信息不能为空", groups = {AddGroup.class, EditGroup.class})
  private String invoiceInfo;

  /**
   * 收货人信息
   */
  @NotBlank(message = "收货人信息不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consigneeInfo;

  /**
   * 发货人信息
   */
  @NotBlank(message = "发货人信息不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shipperInfo;

  /**
   * 文件
   */
  @NotBlank(message = "文件不能为空", groups = {AddGroup.class, EditGroup.class})
  private String files;

  /**
   * 单据类型
   */
  @NotBlank(message = "单据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 订单原始总价
   */
  @NotNull(message = "订单原始总价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal sumPrice;

  /**
   * 商品促销折扣金额
   */
  @NotNull(message = "商品促销折扣金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal discountAmount;

  /**
   * 商品促销直降金额
   */
  @NotNull(message = "商品促销直降金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal directAmount;

  /**
   * 实付金额
   */
  @NotNull(message = "实付金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal payAmount;

  /**
   * 采购商ID
   */
  @NotNull(message = "采购商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 采购商爱那红
   */
  @NotBlank(message = "采购商爱那红不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 采购商名称
   */
  @NotBlank(message = "采购商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 单据类型名称
   */
  @NotBlank(message = "单据类型名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderTypeName;

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
   * 合计净重
   */
  @NotNull(message = "合计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalNetWeight;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源ID
   */
  @NotBlank(message = "来源ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;


}
