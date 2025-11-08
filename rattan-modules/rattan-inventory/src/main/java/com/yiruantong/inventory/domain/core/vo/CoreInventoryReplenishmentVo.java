package com.yiruantong.inventory.domain.core.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 库存调整选择器对象 core_inventory
 *
 * @author YiRuanTong
 * @date 2024-03-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CoreInventoryReplenishmentVo extends CoreInventoryComposeVo {
  /**
   * 已生成补货单
   */
  private Byte isCreateReplenishment;

  /**
   * 缺货数量
   */
  private BigDecimal diffStorage;

}
