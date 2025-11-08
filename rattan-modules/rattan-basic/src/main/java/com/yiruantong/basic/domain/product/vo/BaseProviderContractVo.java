package com.yiruantong.basic.domain.product.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.product.BaseProviderContract;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 供应商合同管理视图对象 base_provider_contract
 *
 * @author YiRuanTong
 * @date 2024-05-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseProviderContract.class)
public class BaseProviderContractVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 合同ID
   */
  @ExcelProperty(value = "合同ID")
  private Long contractId;

  /**
   * 合同单号
   */
  @ExcelProperty(value = "合同单号")
  private String contractCode;

  /**
   * 供应商ID
   */
  @ExcelProperty(value = "供应商ID")
  private Long providerId;

  /**
   * 供应商编号
   */
  @ExcelProperty(value = "供应商编号")
  private String providerCode;

  /**
   * 供应商全称
   */
  @ExcelProperty(value = "供应商全称")
  private String providerName;

  /**
   * 供应商简称
   */
  @ExcelProperty(value = "供应商简称")
  private String providerShortName;

  /**
   * 货主id
   */
  @ExcelProperty(value = "货主id")
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
   * 合同金额
   */
  @ExcelProperty(value = "合同金额")
  private String contractPrice;

  /**
   * 账期开始日期
   */
  @ExcelProperty(value = "账期开始日期")
  private Date accountStartDate;

  /**
   * 账期结束日期
   */
  @ExcelProperty(value = "账期结束日期")
  private Date accountEndDate;

  /**
   * 开票周期开始日期
   */
  @ExcelProperty(value = "开票周期开始日期")
  private Date billingStartDate;

  /**
   * 开票周期结束日期
   */
  @ExcelProperty(value = "开票周期结束日期")
  private Date billingEndDate;

  /**
   * 付款周期开始日期
   */
  @ExcelProperty(value = "付款周期开始日期")
  private Date paymentStartDate;

  /**
   * 付款周期结束日期
   */
  @ExcelProperty(value = "付款周期结束日期")
  private Date paymentEndDate;

  /**
   * 责任人
   */
  @ExcelProperty(value = "责任人")
  private String responsiblePerson;

  /**
   * 发票名称
   */
  @ExcelProperty(value = "发票名称")
  private String invoiceName;

  /**
   * 收件人
   */
  @ExcelProperty(value = "收件人")
  private String receivablesName;

  /**
   * 月付方式
   */
  @ExcelProperty(value = "月付方式")
  private String monthWay;

  /**
   * 发票目录
   */
  @ExcelProperty(value = "发票目录")
  private String invoiceCategory;

  /**
   * 发票类型
   */
  @ExcelProperty(value = "发票类型")
  private String invoiceType;

  /**
   * 税点
   */
  @ExcelProperty(value = "税点")
  private BigDecimal taxPoint;

  /**
   * 合同类型
   */
  @ExcelProperty(value = "合同类型")
  private String contractType;

  /**
   * 合同开始日期
   */
  @ExcelProperty(value = "合同开始日期")
  private Date contractStart;

  /**
   * 合同结束日期
   */
  @ExcelProperty(value = "合同结束日期")
  private Date contractEnd;

  /**
   * 报价
   */
  @ExcelProperty(value = "报价")
  private String quotation;

  /**
   * 发票信息
   */
  @ExcelProperty(value = "发票信息")
  private String invoiceInfo;

  /**
   * 自定义编号
   */
  @ExcelProperty(value = "自定义编号")
  private String customNo;

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


}
