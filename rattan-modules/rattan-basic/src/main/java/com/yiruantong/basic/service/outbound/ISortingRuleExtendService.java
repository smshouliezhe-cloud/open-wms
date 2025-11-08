package com.yiruantong.basic.service.outbound;

import com.yiruantong.basic.domain.outbound.SortingRuleExtendVo;

import java.util.List;

/**
 * 出库分拣规则扩展服务
 */
public interface ISortingRuleExtendService {
  /**
   * 查询规则列表
   *
   * @param ruleType
   * @param orderId
   * @param detailId
   * @return
   */
  List<SortingRuleExtendVo> selectRuleList(String ruleType, Long orderId, Long detailId);
}
