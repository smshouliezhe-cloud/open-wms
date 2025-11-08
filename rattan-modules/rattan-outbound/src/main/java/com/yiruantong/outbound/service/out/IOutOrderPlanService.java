package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutOrderPlan;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanVo;

import java.util.List;
import java.util.Map;

/**
 * 出库计划单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
public interface IOutOrderPlanService extends IServicePlus<OutOrderPlan, OutOrderPlanVo, OutOrderPlanBo> {
  /**
   * 出库计划转出库单
   *
   * @param ids 前端参数
   */
  R<Void> toOutOrder(List<Long> ids);

  /**
   * 确认重量
   *
   * @param map 前端参数
   */
  R<Void> confirmTheWeight(Map<String, Object> map);
}
