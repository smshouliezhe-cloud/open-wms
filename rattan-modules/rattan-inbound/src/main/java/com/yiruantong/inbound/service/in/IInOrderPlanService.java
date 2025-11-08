package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InOrderPlan;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanBo;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanVo;

import java.util.List;

/**
 * 收货计划单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
public interface IInOrderPlanService extends IServicePlus<InOrderPlan, InOrderPlanVo, InOrderPlanBo> {
  /**
   * 入库计划转预到货
   *
   * @param ids
   */
  R<Void> toInOrder(List<Long> ids);

  InOrderPlan getBySourceCode(String sourceCode);
}
