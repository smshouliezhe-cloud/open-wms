package com.yiruantong.basic.domain.consignor;

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
 * 货主合同对象 base_consignor_contract
 *
 * @author YiRuanTong
 * @date 2024-03-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_consignor_contract", autoResultMap = true)
public class BaseConsignorContract extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 合同ID
   */
  @TableId(value = "contract_id")
  private Long contractId;

  /**
   * 合同编号
   */
  private String contractCode;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 所属货主
   */
  private String consignorName;

  /**
   * 上级负责人
   */
  private String superioraGent;

  /**
   * 合同签署公司
   */
  private String contractSign;

  /**
   * 合同类型
   */
  private String contractType;

  /**
   * 账期
   */
  private String accountPeriod;

  /**
   * 出具账单最晚时间
   */
  private Date latestTimePayment;

  /**
   * 最终对账完成时间
   */
  private Date finalTime;

  /**
   * 最晚开票时间
   */
  private Date latestBillingTime;

  /**
   * 最晚结款时间
   */
  private Date latestPayTime;

  /**
   * 销售人员
   */
  private String salesName;

  /**
   * 合同开始日期
   */
  private Date contractStartTime;

  /**
   * 合同结束日期
   */
  private Date contractEndTime;

  /**
   * 是否自动续签
   */
  private Long isAutoRenew;

  /**
   * 合同状态
   */
  private String contractSatus;

  /**
   * 附件
   */
  private String annex;

  /**
   * 仓储税率
   */
  private BigDecimal storageRate;

  /**
   * 配送税率
   */
  private BigDecimal deliveryRate;

  /**
   * 是否流量计费
   */
  private Long isToCharge;

  /**
   * 流量比例
   */
  private BigDecimal flowRate;

  /**
   * 开票信息
   */
  private String invoiceInfo;

  /**
   * 自定义号
   */
  private String customNo;

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
   * 合同天数
   */
  private Long contractDays;

  /**
   * 剩余有效天数
   */
  private Long effetiveDays;


}
