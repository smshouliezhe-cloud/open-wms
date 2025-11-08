package com.yiruantong.inbound.tool;

public interface IShelveRecommendPositionService {
  /**
   * 获取推荐货位
   * @param storageId 仓库ID
   * @param productId 商品ID
   * @return 返回推荐货位
   */
  String getRecommendPosition(Long storageId, Long productId);
}
