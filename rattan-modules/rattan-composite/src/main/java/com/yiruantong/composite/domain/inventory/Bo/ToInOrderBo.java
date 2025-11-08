package com.yiruantong.composite.domain.inventory.Bo;

import lombok.Data;
import com.yiruantong.common.core.domain.model.BaseBo;

import java.math.BigDecimal;

/**
 * 基础Bo对象
 */
@Data
public class ToInOrderBo extends BaseBo {
  /**
   * 优惠金额
   */
  private BigDecimal discountAmount;
  /**
   * 结算方式
   */
  private String settlementType;
  /**
   * 优惠金额
   */
  private BigDecimal totalAmount;
  /**
   * 付款条件
   */
  private String paymentType;
}
