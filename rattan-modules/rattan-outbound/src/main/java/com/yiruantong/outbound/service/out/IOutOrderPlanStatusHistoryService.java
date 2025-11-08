package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InOrderPlanActionEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderPlanActionEnum;
import com.yiruantong.common.core.enums.out.OutOrderPlanStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.out.OutOrderPlan;
import com.yiruantong.outbound.domain.out.OutOrderPlanStatusHistory;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanStatusHistoryVo;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanStatusHistoryBo;

/**
 * 出库计划状态轨迹Service接口
 *
 * @author YRT
 * @date 2024-09-20
 */
public interface IOutOrderPlanStatusHistoryService extends IServicePlus<OutOrderPlanStatusHistory, OutOrderPlanStatusHistoryVo, OutOrderPlanStatusHistoryBo> {



  void addHistoryInfo(OutOrderPlan outOrderPlanInfo, OutOrderPlanActionEnum actionEnum, OutOrderPlanStatusEnum fromStatus, OutOrderPlanStatusEnum toStatus, LoginUser loginUser, String remark);

  void addHistoryInfo(OutOrderPlan outOrderPlanInfo, String action, String fromStatus, String toStatus, LoginUser loginUser, String remark);
}
