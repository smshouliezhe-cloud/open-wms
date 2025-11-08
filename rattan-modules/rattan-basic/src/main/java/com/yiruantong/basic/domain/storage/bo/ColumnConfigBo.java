package com.yiruantong.basic.domain.storage.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ColumnConfigBo {
  /**
   * 列号
   */
  private String colNo;
  /**
   * 货位编码规则
   */
  private String positionRegular;

}
