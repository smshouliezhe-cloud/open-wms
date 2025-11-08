package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanActionEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderPlan;
import com.yiruantong.inbound.domain.in.InOrderPlanStatusHistory;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanStatusHistoryVo;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanStatusHistoryBo;

/**
 * 入库计划状态轨迹Service接口
 *
 * @author YRT
 * @date 2024-09-19
 */
public interface IInOrderPlanStatusHistoryService extends IServicePlus<InOrderPlanStatusHistory, InOrderPlanStatusHistoryVo, InOrderPlanStatusHistoryBo> {


  void addHistoryInfo(InOrderPlan inOrderPlanInfo, InOrderPlanActionEnum actionEnum, InOrderPlanStatusEnum fromStatus, InOrderPlanStatusEnum toStatus, LoginUser loginUser, String remark);

  void addHistoryInfo(InOrderPlan inOrderPlanInfo, String action, String fromStatus, String toStatus, LoginUser loginUser, String remark);
}
