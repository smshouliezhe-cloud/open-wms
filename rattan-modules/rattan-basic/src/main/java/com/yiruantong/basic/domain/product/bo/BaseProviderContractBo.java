package com.yiruantong.basic.domain.product.bo;

import com.yiruantong.basic.domain.product.BaseProviderContract;
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
 * 供应商合同管理业务对象 base_provider_contract
 *
 * @author YiRuanTong
 * @date 2024-05-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseProviderContract.class, reverseConvertGenerate = false)
public class BaseProviderContractBo extends BaseEntity {

  /**
   * 合同ID
   */
  @NotNull(message = "合同ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long contractId;

  /**
   * 合同单号
   */
  @NotBlank(message = "合同单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractCode;

  /**
   * 供应商ID
   */
  @NotNull(message = "供应商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 供应商编号
   */
  @NotBlank(message = "供应商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 供应商全称
   */
  @NotBlank(message = "供应商全称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerName;

  /**
   * 供应商简称
   */
  @NotBlank(message = "供应商简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 货主id
   */
  @NotNull(message = "货主id不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 合同金额
   */
  @NotBlank(message = "合同金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractPrice;

  /**
   * 账期开始日期
   */
  @NotNull(message = "账期开始日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date accountStartDate;

  /**
   * 账期结束日期
   */
  @NotNull(message = "账期结束日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date accountEndDate;

  /**
   * 开票周期开始日期
   */
  @NotNull(message = "开票周期开始日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date billingStartDate;

  /**
   * 开票周期结束日期
   */
  @NotNull(message = "开票周期结束日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date billingEndDate;

  /**
   * 付款周期开始日期
   */
  @NotNull(message = "付款周期开始日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date paymentStartDate;

  /**
   * 付款周期结束日期
   */
  @NotNull(message = "付款周期结束日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date paymentEndDate;

  /**
   * 责任人
   */
  @NotBlank(message = "责任人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String responsiblePerson;

  /**
   * 发票名称
   */
  @NotBlank(message = "发票名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String invoiceName;

  /**
   * 收件人
   */
  @NotBlank(message = "收件人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String receivablesName;

  /**
   * 月付方式
   */
  @NotBlank(message = "月付方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String monthWay;

  /**
   * 发票目录
   */
  @NotBlank(message = "发票目录不能为空", groups = {AddGroup.class, EditGroup.class})
  private String invoiceCategory;

  /**
   * 发票类型
   */
  @NotBlank(message = "发票类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String invoiceType;

  /**
   * 税点
   */
  @NotNull(message = "税点不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal taxPoint;

  /**
   * 合同类型
   */
  @NotBlank(message = "合同类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractType;

  /**
   * 合同开始日期
   */
  @NotNull(message = "合同开始日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date contractStart;

  /**
   * 合同结束日期
   */
  @NotNull(message = "合同结束日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date contractEnd;

  /**
   * 报价
   */
  @NotBlank(message = "报价不能为空", groups = {AddGroup.class, EditGroup.class})
  private String quotation;

  /**
   * 发票信息
   */
  @NotBlank(message = "发票信息不能为空", groups = {AddGroup.class, EditGroup.class})
  private String invoiceInfo;

  /**
   * 自定义编号
   */
  @NotBlank(message = "自定义编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String customNo;

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


}
