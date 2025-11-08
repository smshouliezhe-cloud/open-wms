package com.yiruantong.composite.domain.fee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 按存货天数
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Data

public class Days implements Serializable {
  /**
   * 是否开启阶梯价
   */
  private Boolean isLevelPrice;
  /**
   * 金额
   */
  private BigDecimal price;
  /**
   * 类别
   */
  private String typeName;
  /**
   *  阶梯金额计算方式：按最大值、按阶梯价
   */
  private String levelType;
  /**
   *  阶梯价配置的信息
   */
  private List<LevelPriceList> levelPriceList;

}
