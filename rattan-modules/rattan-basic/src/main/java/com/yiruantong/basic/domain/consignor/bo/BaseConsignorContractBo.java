package com.yiruantong.basic.domain.consignor.bo;

import com.yiruantong.basic.domain.consignor.BaseConsignorContract;
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
 * 货主合同业务对象 base_consignor_contract
 *
 * @author YiRuanTong
 * @date 2024-03-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BaseConsignorContract.class, reverseConvertGenerate = false)
public class BaseConsignorContractBo extends BaseEntity {

  /**
   * 合同ID
   */
  @NotNull(message = "合同ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long contractId;

  /**
   * 合同编号
   */
  @NotBlank(message = "合同编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractCode;

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
   * 所属货主
   */
  @NotBlank(message = "所属货主不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 上级负责人
   */
  @NotBlank(message = "上级负责人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String superioraGent;

  /**
   * 合同签署公司
   */
  @NotBlank(message = "合同签署公司不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractSign;

  /**
   * 合同类型
   */
  @NotBlank(message = "合同类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractType;

  /**
   * 账期
   */
  @NotBlank(message = "账期不能为空", groups = {AddGroup.class, EditGroup.class})
  private String accountPeriod;

  /**
   * 出具账单最晚时间
   */
  @NotNull(message = "出具账单最晚时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date latestTimePayment;

  /**
   * 最终对账完成时间
   */
  @NotNull(message = "最终对账完成时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date finalTime;

  /**
   * 最晚开票时间
   */
  @NotNull(message = "最晚开票时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date latestBillingTime;

  /**
   * 最晚结款时间
   */
  @NotNull(message = "最晚结款时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date latestPayTime;

  /**
   * 销售人员
   */
  @NotBlank(message = "销售人员不能为空", groups = {AddGroup.class, EditGroup.class})
  private String salesName;

  /**
   * 合同开始日期
   */
  @NotNull(message = "合同开始日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date contractStartTime;

  /**
   * 合同结束日期
   */
  @NotNull(message = "合同结束日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date contractEndTime;

  /**
   * 是否自动续签
   */
  @NotNull(message = "是否自动续签不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isAutoRenew;

  /**
   * 合同状态
   */
  @NotBlank(message = "合同状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String contractSatus;

  /**
   * 附件
   */
  @NotBlank(message = "附件不能为空", groups = {AddGroup.class, EditGroup.class})
  private String annex;

  /**
   * 仓储税率
   */
  @NotNull(message = "仓储税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal storageRate;

  /**
   * 配送税率
   */
  @NotNull(message = "配送税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal deliveryRate;

  /**
   * 是否流量计费
   */
  @NotNull(message = "是否流量计费不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isToCharge;

  /**
   * 流量比例
   */
  @NotNull(message = "流量比例不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal flowRate;

  /**
   * 开票信息
   */
  @NotBlank(message = "开票信息不能为空", groups = {AddGroup.class, EditGroup.class})
  private String invoiceInfo;

  /**
   * 自定义号
   */
  @NotBlank(message = "自定义号不能为空", groups = {AddGroup.class, EditGroup.class})
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

  /**
   * 合同天数
   */
  @NotNull(message = "合同天数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long contractDays;

  /**
   * 剩余有效天数
   */
  @NotNull(message = "剩余有效天数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long effetiveDays;


}
