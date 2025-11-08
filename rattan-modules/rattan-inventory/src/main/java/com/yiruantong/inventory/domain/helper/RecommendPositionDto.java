package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 推荐货位参数 RecommendPositionDto
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class RecommendPositionDto {

  /**
   * 仓库Id
   */
  private Long storageId;

  /**
   * 商品Id
   */
  private Long productId;

  /**
   * 入库数量
   */
  private BigDecimal inQty;

  /**
   * 产品规格
   */
  private String productSpec;

  /**
   * 供应商ID
   */
  private Long providerId;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 重量
   */
  private BigDecimal weight;

  /**
   * 体积
   */
  private BigDecimal unitCube;
}
