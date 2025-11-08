package com.yiruantong.inbound.service.in.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.in.InEnterActionEnum;
import com.yiruantong.common.core.enums.in.InEnterStatusEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.InEnterStatusHistory;
import com.yiruantong.inbound.domain.in.bo.InEnterStatusHistoryBo;
import com.yiruantong.inbound.domain.in.vo.InEnterStatusHistoryVo;
import com.yiruantong.inbound.mapper.in.InEnterStatusHistoryMapper;
import com.yiruantong.inbound.service.in.IInEnterStatusHistoryService;
import org.springframework.stereotype.Service;

/**
 * 入库记录轨迹Service业务层处理
 *
 * @author YRT
 * @date 2023-11-22
 */
@RequiredArgsConstructor
@Service
public class InEnterStatusHistoryServiceImpl extends ServiceImplPlus<InEnterStatusHistoryMapper, InEnterStatusHistory, InEnterStatusHistoryVo, InEnterStatusHistoryBo> implements IInEnterStatusHistoryService {
  /**
   *
   * @param inEnterInfo 入库记录单
   * @param enterActionEnum 动作
   * @param fromStatus  变更前事件
   * @param toStatus 变更后事件
   */
  @Override
  public void addHistoryInfo(InEnter inEnterInfo, InEnterActionEnum enterActionEnum, InEnterStatusEnum fromStatus, InEnterStatusEnum toStatus) {
    addInfo(inEnterInfo,enterActionEnum,fromStatus.getName(),toStatus.getName());
  }

  @Override
  public void addHistoryInfo(InEnter inEnterInfo, InEnterActionEnum enterActionEnum, InEnterStatusEnum toStatus) {
    addInfo(inEnterInfo,enterActionEnum,"",toStatus.getName());

  }

  //#region 轨迹添加
  private void  addInfo(InEnter inEnterInfo, InEnterActionEnum enterActionEnum, String fromStatus, String toStatus){
    InEnterStatusHistory inOrderStatusHistory = new InEnterStatusHistory();
    inOrderStatusHistory.setEnterId(inEnterInfo.getEnterId());
    inOrderStatusHistory.setBillId(inEnterInfo.getEnterId());
    inOrderStatusHistory.setBillCode(inEnterInfo.getEnterCode());
    inOrderStatusHistory.setStatusType("单据状态");
    inOrderStatusHistory.setOperationType(enterActionEnum.getName());
    inOrderStatusHistory.setFromStatus(fromStatus);
    inOrderStatusHistory.setToStatus(toStatus);
    inOrderStatusHistory.setCreateBy(LoginHelper.getUserId());
    inOrderStatusHistory.setCreateByName(LoginHelper.getNickname());
    inOrderStatusHistory.setCreateTime(DateUtils.getNowDate());
    this.save(inOrderStatusHistory);
  }
  //#endregion
}
