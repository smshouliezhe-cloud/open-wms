package com.yiruantong.composite.domain.inventory.Bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryBo;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StorageLowerReplenishmentBo extends CoreInventoryBo {

  /**
   * 缺货数量
   */
  private BigDecimal diffStorage;

  /**
   * 缺货数量
   */
  private Byte isCreateReplenishment;

}
