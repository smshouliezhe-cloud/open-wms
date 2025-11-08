package com.yiruantong.basic.domain.client.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContractStatusBo {
  /**
   * 状态
   */
  private String contractStatus;
  /**
   * ids
   */
  private List<Long> contractIds;
}
