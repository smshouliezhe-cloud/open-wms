package com.yiruantong.composite.domain.fee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 阶梯价的参数
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Data

public class LevelPriceList implements Serializable {
  /**
   * 数量
   */
  private Long number;
  /**
   * 金额
   */
  private BigDecimal price;
}
