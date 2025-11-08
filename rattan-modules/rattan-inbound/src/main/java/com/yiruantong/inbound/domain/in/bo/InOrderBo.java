package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 预到货单业务对象 in_order
 *
 * @author YiRuanTong
 * @date 2025-03-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InOrder.class, reverseConvertGenerate = false)
public class InOrderBo extends BaseEntity {

  /**
   * 预到货单ID
   */
  private Long orderId;

  /**
   * 预到货单号
   */
  private String orderCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 申请日期
   */
  private Date applyDate;

  /**
   * 预计到货日期
   */
  private Date arrivedDate;

  /**
   * 采购商ID
   */
  private Long providerId;

  /**
   * 采购商编号
   */
  @NotBlank(message = "采购商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 采购商名称
   */
  @NotBlank(message = "采购商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 退款额
   */
  private BigDecimal refundAmount;

  /**
   * 剩余应付
   */
  private BigDecimal surplusAmount;

  /**
   * 已支付额
   */
  private BigDecimal paidAmount;

  /**
   * 未付金额
   */
  private BigDecimal unpaidAmount;

  /**
   * 快递费
   */
  private BigDecimal expressFee;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 合计价税
   */
  private BigDecimal totalRateAmount;

  /**
   * 采购状态
   */
  private String orderStatus;

  /**
   * 退货状态
   */
  private String returnStatus;

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
   * 跟踪单号
   */
  @NotBlank(message = "跟踪单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String trackingNumber;

  /**
   * 发货人ID
   */
  private Long consignorId;

  /**
   * 发货人编号
   */
  private String consignorCode;

  /**
   * 发货人名称
   */
  private String consignorName;

  /**
   * 是否需要质检
   */
  private Byte isChecking;

  /**
   * 是否到货加工
   */
  private Byte isArrivalProcess;

  /**
   * 财务状态
   */
  private String finStatusText;

  /**
   * 关联单号1
   */
  private String externalNo;

  /**
   * 关联单号2
   */
  private String externalNo2;

  /**
   * 需要海关报检
   */
  private Long isCiqDeclare;

  /**
   * 单据类型
   */
  @NotBlank(message = "单据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 退货单据编号
   */
  private String returnOrderCode;

  /**
   * 税额合计
   */
  private BigDecimal taxAmountTotal;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  private String containerNos;

  /**
   * 费用项
   */
  private String feeItemIds;

  /**
   * 供应商状态
   */
  private String providerStatus;

  /**
   * 发货人
   */
  private String shippingName;

  /**
   * 电话
   */
  private String telephone;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 发货人地址
   */
  private String shippingAddress;

  /**
   * 国家名称
   */
  private String countryName;

  /**
   * 邮政编码
   */
  private String postCode;

  /**
   * 街道
   */
  private String street;

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
   * 排序号
   */
  private Long orderNum;

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
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 上架状态
   */
  private String shelveStatus;

  /**
   * 合计上架数量
   */
  private BigDecimal totalShelvedQuantity;

  /**
   * 快递类别
   */
  private Long expressCorpType;

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
   * 采购商全称
   */
  private String providerName;

  /**
   * 合计入库数量
   */
  private BigDecimal totalEnterQuantity;

  /**
   * 上传文件
   */
  private String uploadFile;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 合计件数
   */
  private BigDecimal totalPackage;

  /**
   * 铅封号
   */
  private String sealNos;

  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;

  /**
   * 调出仓库
   */
  private String storageNameOut;

  /**
   * 调出仓库ID
   */
  private Long storageIdOut;

  /**
   * 调出货主
   */
  private String consignorNameOut;

  /**
   * 调出货主ID
   */
  private Long consignorIdOut;

  /**
   * 调出货主编号
   */
  private String consignorCodeOut;

  /**
   * 是否越库
   */
  @NotNull(message = "是否越库不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte crossDocking;

  /**
   * 质检状态
   */
  @NotBlank(message = "质检状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String checkingStatus;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
