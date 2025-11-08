package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.enums.in.InEnterActionEnum;
import com.yiruantong.common.core.enums.in.InEnterStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.InEnterStatusHistory;
import com.yiruantong.inbound.domain.in.bo.InEnterStatusHistoryBo;
import com.yiruantong.inbound.domain.in.vo.InEnterStatusHistoryVo;

/**
 * 入库记录轨迹Service接口
 *
 * @author YRT
 * @date 2023-11-22
 */
public interface IInEnterStatusHistoryService extends IServicePlus<InEnterStatusHistory, InEnterStatusHistoryVo, InEnterStatusHistoryBo> {
  /**
   *
   * @param inEnterInfo 入库记录单
   * @param enterActionEnum 动作
   * @param fromStatus  变更前事件
   * @param toStatus 变更后事件
   */
  void addHistoryInfo(InEnter inEnterInfo, InEnterActionEnum enterActionEnum, InEnterStatusEnum fromStatus, InEnterStatusEnum toStatus);
  /**
   *
   * @param inEnterInfo 入库记录单
   * @param enterActionEnum 动作
   * @param toStatus 变更后事件
   */
  void addHistoryInfo(InEnter inEnterInfo, InEnterActionEnum enterActionEnum, InEnterStatusEnum toStatus);
}
