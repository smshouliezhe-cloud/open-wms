package com.yiruantong.basic.domain.storage.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RowConfigsBo {
  /**
   * 行号
   */
  private String rowNo;
  /**
   * 货位编码规则
   */
  private String positionRegular;
}
