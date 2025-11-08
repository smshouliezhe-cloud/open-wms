package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 紧急度
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TmsDegreesBo {

  /**
   * 紧急度
   */
  private String degrees;

  /**
   * 运单主表ID
   */
  private List<Long> wayBillIds;

}
