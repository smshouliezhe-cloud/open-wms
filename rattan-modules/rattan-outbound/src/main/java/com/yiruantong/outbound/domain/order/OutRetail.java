package com.yiruantong.outbound.domain.order;

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
 * 订货单对象 out_retail
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_retail", autoResultMap = true)
public class OutRetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 要货单ID
   */
  @TableId(value = "retail_id")
  private Long retailId;

  /**
   * 要货单编号
   */
  private String retailCode;

  /**
   * 收款期限
   */
  private Date payLimitDate;

  /**
   * 要货单状态
   */
  private String retailStatus;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户简称
   */
  private String clientShortName;

  /**
   * 交货日期
   */
  private Date deliveryDate;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Long auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 审核备注
   */
  private String auditRemark;

  /**
   * 店铺订单编号
   */
  private String storeOrderCode;

  /**
   * 订单渠道
   */
  private String orderChannel;

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
   * 部门名称
   */
  private String deptName;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantityOrder;

  /**
   * 取消数量
   */
  private BigDecimal totalQuantityCanceled;

  /**
   * 已发货数量
   */
  private BigDecimal totalQuantityShipped;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 财务状态
   */
  private String finStatusText;

  /**
   * 付款模式
   */
  private String payMode;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

  /**
   * 分拣时间
   */
  private Date sortingDate;

  /**
   * 邮费
   */
  private BigDecimal shippingAmount;

  /**
   * 实付快递运费
   */
  private BigDecimal factFreight;

  /**
   * 总计金额
   */
  private BigDecimal totalAmount;

  /**
   * 已支付额
   */
  private BigDecimal totalPaid;

  /**
   * 已开发票金额
   */
  private BigDecimal totalInvoiced;

  /**
   * 合计价税
   */
  private BigDecimal totalRateMoney;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 开票信息
   */
  private String invoiceInfo;

  /**
   * 收货人信息
   */
  private String consigneeInfo;

  /**
   * 发货人信息
   */
  private String shipperInfo;

  /**
   * 文件
   */
  private String files;

  /**
   * 单据类型
   */
  private String orderType;

  /**
   * 订单原始总价
   */
  private BigDecimal sumPrice;

  /**
   * 商品促销折扣金额
   */
  private BigDecimal discountAmount;

  /**
   * 商品促销直降金额
   */
  private BigDecimal directAmount;

  /**
   * 实付金额
   */
  private BigDecimal payAmount;

  /**
   * 采购商ID
   */
  private Long providerId;

  /**
   * 采购商爱那红
   */
  private String providerCode;

  /**
   * 采购商名称
   */
  private String providerShortName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 单据类型名称
   */
  private String orderTypeName;

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


}
