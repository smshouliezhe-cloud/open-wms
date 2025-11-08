package com.yiruantong.outbound.domain.out.bo;

import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.outbound.domain.out.OutPackage;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 打包单业务对象 out_package
 *
 * @author YRT
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutPackage.class, reverseConvertGenerate = false)
public class OutPackageBo extends BaseEntity {

  /**
   * 打包单ID
   */
  @NotNull(message = "打包单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long packageId;

  /**
   * 打包单号
   */
  @NotBlank(message = "打包单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packageCode;

  /**
   * 出库订单ID
   */
  @NotNull(message = "出库订单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 出库单号
   */
  @NotBlank(message = "出库单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

  /**
   * ERP单号
   */
  @NotBlank(message = "ERP单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storeOrderCode;

  /**
   * 店铺ID
   */
  @NotNull(message = "店铺ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storeId;

  /**
   * 店铺名称
   */
  @NotBlank(message = "店铺名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storeName;

  /**
   * 出货仓库ID
   */
  @NotNull(message = "出货仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 出货仓库名称
   */
  @NotBlank(message = "出货仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

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
   * 出库日期
   */
  @NotNull(message = "出库日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date applyDate;

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
   * 客户名称
   */
  @NotBlank(message = "客户名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPackageQuantity;

  /**
   * 物件数量
   */
  @NotNull(message = "物件数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long materialCount;

  /**
   * 运费金额
   */
  @NotNull(message = "运费金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shippingAmount;

  /**
   * 折扣金额
   */
  @NotNull(message = "折扣金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal discountAmount;

  /**
   * 商品金额
   */
  @NotNull(message = "商品金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalProductAmount;

  /**
   * 合计金额
   */
  @NotNull(message = "合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;

  /**
   * 汇率
   */
  @NotNull(message = "汇率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal exchangeRate;

  /**
   * 币种
   */
  @NotBlank(message = "币种不能为空", groups = {AddGroup.class, EditGroup.class})
  private String currencyCode;

  /**
   * 税率
   */
  @NotNull(message = "税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rate;

  /**
   * 合计价税
   */
  @NotNull(message = "合计价税不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalRateAmount;

  /**
   * 关联包裹号
   */
  @NotBlank(message = "关联包裹号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String refPackageCode;

  /**
   * 打包状态
   */
  @NotBlank(message = "打包状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packageStatus;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 已结算金额
   */
  @NotNull(message = "已结算金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal settleAmount;

  /**
   * 未已结算金额
   */
  @NotNull(message = "未已结算金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unSettleAmount;

  /**
   * 快递ID
   */
  @NotNull(message = "快递ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long expressCorpId;

  /**
   * 快递公司名称
   */
  @NotBlank(message = "快递公司名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCorpName;

  /**
   * 快递编号
   */
  @NotBlank(message = "快递编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCode;

  /**
   * 打印状态
   */
  @NotBlank(message = "打印状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String printStatus;

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
   * 是否已发货
   */
  @NotNull(message = "是否已发货不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isSend;

  /**
   * 发货人姓名
   */
  @NotBlank(message = "发货人姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingName;

  /**
   * 发货人地址
   */
  @NotBlank(message = "发货人地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingAddress;

  /**
   * 手机
   */
  @NotBlank(message = "手机不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 订单类型
   */
  @NotBlank(message = "订单类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 电话
   */
  @NotBlank(message = "电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tel;

  /**
   * Email
   */
  @NotBlank(message = "Email不能为空", groups = {AddGroup.class, EditGroup.class})
  private String email;

  /**
   * 订单渠道
   */
  @NotBlank(message = "订单渠道不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderChannel;

  /**
   * 波次单ID
   */
  @NotNull(message = "波次单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderWaveId;

  /**
   * 波次单号
   */
  @NotBlank(message = "波次单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderWaveCode;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 是否生成费用
   */
  @NotNull(message = "是否生成费用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isCreateFee;

  /**
   * 合计件数
   */
  @NotNull(message = "合计件数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPackage;

  /**
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
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

  /**
   * 合计净重
   */
  @NotNull(message = "合计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalNetWeight;

  /**
   * 上传文件
   */
  @NotBlank(message = "上传文件不能为空", groups = {AddGroup.class, EditGroup.class})
  private String uploadFile;

  /**
   * 合计大单位数量
   */
  @NotNull(message = "合计大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalBigQty;

  /**
   * 税金额
   */
  private BigDecimal taxAmount;

  /**
   * 销售组织
   */
  @NotBlank(message = "销售组织不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  @NotBlank(message = "销售组织编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  @NotNull(message = "销售组织ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorIdSale;

  /**
   * 调入仓库
   */
  @NotBlank(message = "调入仓库不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageNameIn;

  /**
   * 调入仓ID
   */
  @NotNull(message = "调入仓ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageIdIn;

  /**
   * 费用项
   */
  @NotBlank(message = "费用项不能为空", groups = {AddGroup.class, EditGroup.class})
  private String feeItemIds;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
