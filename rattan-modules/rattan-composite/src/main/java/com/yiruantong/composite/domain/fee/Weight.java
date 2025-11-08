package com.yiruantong.composite.domain.fee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 按重量
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Data

public class Weight implements Serializable {

  /**
   * 是否开启阶梯价
   */
  private Boolean isLevelPrice;
  /**
   * 金额
   */
  private BigDecimal price;
  /**
   * 计费类别
   */
  private String weightPaneName;
  /**
   *  阶梯价配置的信息
   */
  private List<LevelPriceList> levelPriceList;
  /**
   * 类别
   */
  private String typeName;
  /**
   * 重量类型
   */
  private String weightType;

  /**
   * 校验单位
   */
  private String calUnit;
  /**
   * 阶梯数量类型
   */
  private String numberType;
  /**
   * 单位
   */
  private String type;
  /**
   *  阶梯金额计算方式
   */
  private String levelType;
}
