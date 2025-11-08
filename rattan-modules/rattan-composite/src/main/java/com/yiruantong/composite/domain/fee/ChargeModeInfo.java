package com.yiruantong.composite.domain.fee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 收费方式
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Data

public class ChargeModeInfo implements Serializable {

  /**
   * 按件/箱
   */
  private List<Unit> unit;
  /**
   * 按重量
   */
  private List<Weight> weight;
  /**
   * 按存货天数
   */
  private List<Days> days;
}
