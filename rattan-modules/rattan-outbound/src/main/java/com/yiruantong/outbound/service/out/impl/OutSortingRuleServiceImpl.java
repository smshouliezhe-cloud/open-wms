package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.outbound.SortingRuleExtendVo;
import com.yiruantong.basic.service.outbound.ISortingRuleExtendService;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.bo.OutSortingRuleBo;
import com.yiruantong.outbound.domain.out.vo.OutSortingRuleVo;
import com.yiruantong.outbound.mapper.out.OutSortingRuleMapper;
import com.yiruantong.outbound.service.out.IOutSortingRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单特殊分拣规则Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class OutSortingRuleServiceImpl extends ServiceImplPlus<OutSortingRuleMapper, OutSortingRule, OutSortingRuleVo, OutSortingRuleBo> implements IOutSortingRuleService, ISortingRuleExtendService {
  @Override
  public OutSortingRule getOneByDetailId(Long detailId) {
    LambdaQueryWrapper<OutSortingRule> ruleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    ruleLambdaQueryWrapper.eq(OutSortingRule::getOrderDetailId, detailId).last("limit 1");

    return this.getOne(ruleLambdaQueryWrapper);
  }

  @Override
  public List<SortingRuleExtendVo> selectRuleList(String ruleType, Long orderId, Long detailId) {
    // 查到当前明细数据
    LambdaQueryWrapper<OutSortingRule> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(OutSortingRule::getOrderDetailId, detailId)
      .eq(OutSortingRule::getOrderDetailId, detailId)
      .and(a ->
        a.isNotNull(OutSortingRule::getPositionName)
          .or().isNotNull(OutSortingRule::getPlateCode)
          .or().isNotNull(OutSortingRule::getBatchNumber)
          .or().isNotNull(OutSortingRule::getInventoryId)
          .or().isNotNull(OutSortingRule::getSingleSignCode)
          .or().isNotNull(OutSortingRule::getProduceDate)
          .or().isNotNull(OutSortingRule::getProduceDateGt)
          .or().isNotNull(OutSortingRule::getProjectCode)
      )
    ;

    // 明细中设置的分拣规则
    List<OutSortingRuleVo> sortingRuleList = this.selectList(queryWrapper);
    return Convert.toList(SortingRuleExtendVo.class, sortingRuleList);
  }
}
