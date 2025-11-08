package com.yiruantong.outbound.domain.out;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 打包单对象 out_package
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_package", autoResultMap = true)
public class OutPackage extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 打包单ID
   */
  @TableId(value = "package_id")
  private Long packageId;

  /**
   * 打包单号
   */
  private String packageCode;

  /**
   * 出库订单ID
   */
  private Long orderId;

  /**
   * 出库单号
   */
  private String orderCode;

  /**
   * ERP单号
   */
  private String storeOrderCode;

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
  private String storageName;

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
   * 出库日期
   */
  private Date applyDate;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户名称
   */
  private String clientShortName;

  /**
   * 合计数量
   */
  private BigDecimal totalPackageQuantity;

  /**
   * 物件数量
   */
  private Long materialCount;

  /**
   * 运费金额
   */
  private BigDecimal shippingAmount;

  /**
   * 折扣金额
   */
  private BigDecimal discountAmount;

  /**
   * 商品金额
   */
  private BigDecimal totalProductAmount;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 汇率
   */
  private BigDecimal exchangeRate;

  /**
   * 币种
   */
  private String currencyCode;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 合计价税
   */
  private BigDecimal totalRateAmount;

  /**
   * 关联包裹号
   */
  private String refPackageCode;

  /**
   * 打包状态
   */
  private String packageStatus;

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
   * 已结算金额
   */
  private BigDecimal settleAmount;

  /**
   * 未已结算金额
   */
  private BigDecimal unSettleAmount;

  /**
   * 快递ID
   */
  private Long expressCorpId;

  /**
   * 快递公司名称
   */
  private String expressCorpName;

  /**
   * 快递编号
   */
  private String expressCode;

  /**
   * 打印状态
   */
  private String printStatus;

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
   * 是否已发货
   */
  private Byte isSend;

  /**
   * 发货人姓名
   */
  private String shippingName;

  /**
   * 发货人地址
   */
  private String shippingAddress;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 订单类型
   */
  private String orderType;

  /**
   * 电话
   */
  private String tel;

  /**
   * Email
   */
  private String email;

  /**
   * 订单渠道
   */
  private String orderChannel;

  /**
   * 波次单ID
   */
  private Long orderWaveId;

  /**
   * 波次单号
   */
  private String orderWaveCode;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 是否生成费用
   */
  private Byte isCreateFee;

  /**
   * 合计件数
   */
  private BigDecimal totalPackage;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

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
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 上传文件
   */
  private String uploadFile;

  /**
   * 合计大单位数量
   */
  private BigDecimal totalBigQty;

  /**
   * 税金额
   */
  private BigDecimal taxAmount;

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
   * 调入仓库
   */
  private String storageNameIn;

  /**
   * 调入仓ID
   */
  private Long storageIdIn;

  /**
   * 费用项
   */
  private String feeItemIds;

  /**
   * 仓库编号
   */
  private String storageCode;


}
