package com.yiruantong.inbound.service.service;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InReturnActionEnum;
import com.yiruantong.common.core.enums.in.InReturnEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnStatusHistory;
import com.yiruantong.inbound.domain.service.bo.InReturnStatusHistoryBo;
import com.yiruantong.inbound.domain.service.vo.InReturnStatusHistoryVo;

/**
 * 退货单流水Service接口
 *
 * @author YRT
 * @date 2024-01-27
 */
public interface IInReturnStatusHistoryService extends IServicePlus<InReturnStatusHistory, InReturnStatusHistoryVo, InReturnStatusHistoryBo> {
  /**
   *
   * @param inReturn 退货记录单
   * @param actionEnum 动作
   * @param fromStatus  变更前事件
   * @param toStatus 变更后事件
   */
  void addHistoryInfo(InReturn inReturn, InReturnActionEnum actionEnum, InReturnEnum fromStatus, InReturnEnum toStatus, String remark);

  /**
   *
   * @param inReturn 退货记录单
   * @param actionEnum 动作
   * @param toStatus 变更后事件
   */
  void addHistoryInfo(InReturn inReturn, InReturnActionEnum actionEnum, InReturnEnum toStatus, String remark);

  void addHistoryInfo(InReturn inReturn, InReturnActionEnum actionEnum, String fromStatus, String toStatus, LoginUser loginUser, String remark);
}
