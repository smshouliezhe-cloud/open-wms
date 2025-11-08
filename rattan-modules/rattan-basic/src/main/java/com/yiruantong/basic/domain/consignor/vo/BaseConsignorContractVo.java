package com.yiruantong.basic.domain.consignor.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.consignor.BaseConsignorContract;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 货主合同视图对象 base_consignor_contract
 *
 * @author YiRuanTong
 * @date 2024-03-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseConsignorContract.class)
public class BaseConsignorContractVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 合同ID
   */
  @ExcelProperty(value = "合同ID")
  private Long contractId;

  /**
   * 合同编号
   */
  @ExcelProperty(value = "合同编号")
  private String contractCode;

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
   * 所属货主
   */
  @ExcelProperty(value = "所属货主")
  private String consignorName;

  /**
   * 上级负责人
   */
  @ExcelProperty(value = "上级负责人")
  private String superioraGent;

  /**
   * 合同签署公司
   */
  @ExcelProperty(value = "合同签署公司")
  private String contractSign;

  /**
   * 合同类型
   */
  @ExcelProperty(value = "合同类型")
  private String contractType;

  /**
   * 账期
   */
  @ExcelProperty(value = "账期")
  private String accountPeriod;

  /**
   * 出具账单最晚时间
   */
  @ExcelProperty(value = "出具账单最晚时间")
  private Date latestTimePayment;

  /**
   * 最终对账完成时间
   */
  @ExcelProperty(value = "最终对账完成时间")
  private Date finalTime;

  /**
   * 最晚开票时间
   */
  @ExcelProperty(value = "最晚开票时间")
  private Date latestBillingTime;

  /**
   * 最晚结款时间
   */
  @ExcelProperty(value = "最晚结款时间")
  private Date latestPayTime;

  /**
   * 销售人员
   */
  @ExcelProperty(value = "销售人员")
  private String salesName;

  /**
   * 合同开始日期
   */
  @ExcelProperty(value = "合同开始日期")
  private Date contractStartTime;

  /**
   * 合同结束日期
   */
  @ExcelProperty(value = "合同结束日期")
  private Date contractEndTime;

  /**
   * 是否自动续签
   */
  @ExcelProperty(value = "是否自动续签")
  private Long isAutoRenew;

  /**
   * 合同状态
   */
  @ExcelProperty(value = "合同状态")
  private String contractSatus;

  /**
   * 附件
   */
  @ExcelProperty(value = "附件")
  private String annex;

  /**
   * 仓储税率
   */
  @ExcelProperty(value = "仓储税率")
  private BigDecimal storageRate;

  /**
   * 配送税率
   */
  @ExcelProperty(value = "配送税率")
  private BigDecimal deliveryRate;

  /**
   * 是否流量计费
   */
  @ExcelProperty(value = "是否流量计费")
  private Long isToCharge;

  /**
   * 流量比例
   */
  @ExcelProperty(value = "流量比例")
  private BigDecimal flowRate;

  /**
   * 开票信息
   */
  @ExcelProperty(value = "开票信息")
  private String invoiceInfo;

  /**
   * 自定义号
   */
  @ExcelProperty(value = "自定义号")
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

  /**
   * 合同天数
   */
  @ExcelProperty(value = "合同天数")
  private Long contractDays;

  /**
   * 剩余有效天数
   */
  @ExcelProperty(value = "剩余有效天数")
  private Long effetiveDays;


}
