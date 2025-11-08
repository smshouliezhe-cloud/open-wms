package com.yiruantong.inbound.domain.in.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inbound.domain.in.InOrder;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 预到货单视图对象 in_order
 *
 * @author YiRuanTong
 * @date 2025-03-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InOrder.class)
public class InOrderVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 预到货单ID
   */
  @ExcelProperty(value = "预到货单ID")
  private Long orderId;

  /**
   * 预到货单号
   */
  @ExcelProperty(value = "预到货单号")
  private String orderCode;

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
   * 申请日期
   */
  @ExcelProperty(value = "申请日期")
  private Date applyDate;

  /**
   * 预计到货日期
   */
  @ExcelProperty(value = "预计到货日期")
  private Date arrivedDate;

  /**
   * 采购商ID
   */
  @ExcelProperty(value = "采购商ID")
  private Long providerId;

  /**
   * 采购商编号
   */
  @ExcelProperty(value = "采购商编号")
  private String providerCode;

  /**
   * 采购商名称
   */
  @ExcelProperty(value = "采购商名称")
  private String providerShortName;

  /**
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  @ExcelProperty(value = "合计金额")
  private BigDecimal totalAmount;

  /**
   * 退款额
   */
  @ExcelProperty(value = "退款额")
  private BigDecimal refundAmount;

  /**
   * 剩余应付
   */
  @ExcelProperty(value = "剩余应付")
  private BigDecimal surplusAmount;

  /**
   * 已支付额
   */
  @ExcelProperty(value = "已支付额")
  private BigDecimal paidAmount;

  /**
   * 未付金额
   */
  @ExcelProperty(value = "未付金额")
  private BigDecimal unpaidAmount;

  /**
   * 快递费
   */
  @ExcelProperty(value = "快递费")
  private BigDecimal expressFee;

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
   * 采购状态
   */
  @ExcelProperty(value = "采购状态")
  private String orderStatus;

  /**
   * 退货状态
   */
  @ExcelProperty(value = "退货状态")
  private String returnStatus;

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
   * 跟踪单号
   */
  @ExcelProperty(value = "跟踪单号")
  private String trackingNumber;

  /**
   * 发货人ID
   */
  @ExcelProperty(value = "发货人ID")
  private Long consignorId;

  /**
   * 发货人编号
   */
  @ExcelProperty(value = "发货人编号")
  private String consignorCode;

  /**
   * 发货人名称
   */
  @ExcelProperty(value = "发货人名称")
  private String consignorName;

  /**
   * 是否需要质检
   */
  @ExcelProperty(value = "是否需要质检")
  private Byte isChecking;

  /**
   * 是否到货加工
   */
  @ExcelProperty(value = "是否到货加工")
  private Byte isArrivalProcess;

  /**
   * 财务状态
   */
  @ExcelProperty(value = "财务状态")
  private String finStatusText;

  /**
   * 关联单号1
   */
  @ExcelProperty(value = "关联单号1")
  private String externalNo;

  /**
   * 关联单号2
   */
  @ExcelProperty(value = "关联单号2")
  private String externalNo2;

  /**
   * 需要海关报检
   */
  @ExcelProperty(value = "需要海关报检")
  private Long isCiqDeclare;

  /**
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

  /**
   * 退货单据编号
   */
  @ExcelProperty(value = "退货单据编号")
  private String returnOrderCode;

  /**
   * 税额合计
   */
  @ExcelProperty(value = "税额合计")
  private BigDecimal taxAmountTotal;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNos;

  /**
   * 费用项
   */
  @ExcelProperty(value = "费用项")
  private String feeItemIds;

  /**
   * 供应商状态
   */
  @ExcelProperty(value = "供应商状态")
  private String providerStatus;

  /**
   * 发货人
   */
  @ExcelProperty(value = "发货人")
  private String shippingName;

  /**
   * 电话
   */
  @ExcelProperty(value = "电话")
  private String telephone;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 发货人地址
   */
  @ExcelProperty(value = "发货人地址")
  private String shippingAddress;

  /**
   * 国家名称
   */
  @ExcelProperty(value = "国家名称")
  private String countryName;

  /**
   * 邮政编码
   */
  @ExcelProperty(value = "邮政编码")
  private String postCode;

  /**
   * 街道
   */
  @ExcelProperty(value = "街道")
  private String street;

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
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人id
   */
  @ExcelProperty(value = "创建人id")
  private Long createBy;

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
   * 修改人id
   */
  @ExcelProperty(value = "修改人id")
  private Long updateBy;

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
   * 上架状态
   */
  @ExcelProperty(value = "上架状态")
  private String shelveStatus;

  /**
   * 合计上架数量
   */
  @ExcelProperty(value = "合计上架数量")
  private BigDecimal totalShelvedQuantity;

  /**
   * 快递类别
   */
  @ExcelProperty(value = "快递类别")
  private Long expressCorpType;

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
   * 采购商全称
   */
  @ExcelProperty(value = "采购商全称")
  private String providerName;

  /**
   * 合计入库数量
   */
  @ExcelProperty(value = "合计入库数量")
  private BigDecimal totalEnterQuantity;

  /**
   * 上传文件
   */
  @ExcelProperty(value = "上传文件")
  private String uploadFile;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 合计件数
   */
  @ExcelProperty(value = "合计件数")
  private BigDecimal totalPackage;

  /**
   * 铅封号
   */
  @ExcelProperty(value = "铅封号")
  private String sealNos;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQtyTotal;

  /**
   * 调出仓库
   */
  @ExcelProperty(value = "调出仓库")
  private String storageNameOut;

  /**
   * 调出仓库ID
   */
  @ExcelProperty(value = "调出仓库ID")
  private Long storageIdOut;

  /**
   * 调出货主
   */
  @ExcelProperty(value = "调出货主")
  private String consignorNameOut;

  /**
   * 调出货主ID
   */
  @ExcelProperty(value = "调出货主ID")
  private Long consignorIdOut;

  /**
   * 调出货主编号
   */
  @ExcelProperty(value = "调出货主编号")
  private String consignorCodeOut;

  /**
   * 是否越库
   */
  @ExcelProperty(value = "是否越库")
  private Byte crossDocking;

  /**
   * 质检状态
   */
  @ExcelProperty(value = "质检状态")
  private String checkingStatus;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;


}
