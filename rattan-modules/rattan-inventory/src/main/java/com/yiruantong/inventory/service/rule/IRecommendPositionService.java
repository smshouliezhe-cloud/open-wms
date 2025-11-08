package com.yiruantong.inventory.service.rule;

import com.yiruantong.inventory.domain.helper.RecommendPositionDto;

/**
 * 入库上架推荐货位
 */
public interface IRecommendPositionService {
  String getRecommendPosition(RecommendPositionDto recommendPositionDto);
}
