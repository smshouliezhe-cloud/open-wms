package com.yiruantong.composite.domain.fee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 按件/箱
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Data

public class Unit implements Serializable {

  /**
   * 是否开启阶梯价
   */
  private Boolean isLevelPrice;
  /**
   * 计费类别
   */
  private String unitPaneName;
  /**
   * 金额
   */
  private BigDecimal price;
  /**
   * 包装单位
   */
  private String type;
  /**
   * 类别
   */
  private String typeName;
  /**
   * 阶梯数量类型
   */
  private String numberType;
  /**
   *  阶梯金额计算方式：按最大值、按阶梯价
   */
  private String levelType;
  /**
   *  阶梯价配置的信息
   */
  private List<LevelPriceList> levelPriceList;
}
