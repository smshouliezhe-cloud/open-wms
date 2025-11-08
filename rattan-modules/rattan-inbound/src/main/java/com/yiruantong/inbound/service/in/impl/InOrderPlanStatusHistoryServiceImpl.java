package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanActionEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderPlan;
import com.yiruantong.inbound.domain.in.InOrderStatusHistory;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanStatusHistoryBo;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanStatusHistoryVo;
import com.yiruantong.inbound.domain.in.InOrderPlanStatusHistory;
import com.yiruantong.inbound.mapper.in.InOrderPlanStatusHistoryMapper;
import com.yiruantong.inbound.service.in.IInOrderPlanStatusHistoryService;

/**
 * 入库计划状态轨迹Service业务层处理
 *
 * @author YRT
 * @date 2024-09-19
 */
@RequiredArgsConstructor
@Service
public class InOrderPlanStatusHistoryServiceImpl extends ServiceImplPlus<InOrderPlanStatusHistoryMapper, InOrderPlanStatusHistory, InOrderPlanStatusHistoryVo, InOrderPlanStatusHistoryBo> implements IInOrderPlanStatusHistoryService {


  //#region 添加入库计划单  把多态的添加轨迹 在提取出一个方法
  // 不写对应的接口 只在本方法进行使用
  @Override
  public void addHistoryInfo(InOrderPlan inOrderPlanInfo, InOrderPlanActionEnum actionEnum, InOrderPlanStatusEnum fromStatus, InOrderPlanStatusEnum toStatus, LoginUser loginUser, String remark) {
    String _fromStatus = ObjectUtil.isNull(fromStatus)?null: fromStatus.getName();
    String _toStatus = ObjectUtil.isNull(toStatus)?null: toStatus.getName();
    this.addHistoryInfo(inOrderPlanInfo, actionEnum.getName(), _fromStatus, _toStatus, loginUser, remark);
  }


  @Override
  public void addHistoryInfo(InOrderPlan inOrderPlanInfo, String action, String fromStatus, String toStatus, LoginUser loginUser, String remark) {
    if (ObjectUtil.isNull(loginUser)) {
      loginUser = new LoginUser();
      loginUser.setUserId(1L);
      loginUser.setNickname("超级管理员");
    }
    InOrderPlanStatusHistory inOrderPlanStatusHistory = new InOrderPlanStatusHistory();
    inOrderPlanStatusHistory.setOrderId(inOrderPlanInfo.getPlanId());
    inOrderPlanStatusHistory.setBillId(inOrderPlanInfo.getPlanId());
    inOrderPlanStatusHistory.setBillCode(inOrderPlanInfo.getPlanCode());
    inOrderPlanStatusHistory.setStatusType("单据状态");
    inOrderPlanStatusHistory.setOperationType(action);
    if (ObjectUtil.isNotNull(fromStatus)) {
      inOrderPlanStatusHistory.setFromStatus(fromStatus);
    }
    Assert.isFalse(ObjectUtil.isNull(toStatus), "入库计划结束状态不能为空");
    inOrderPlanStatusHistory.setToStatus(toStatus);
    inOrderPlanStatusHistory.setRemark(inOrderPlanInfo.getRemark());
    inOrderPlanStatusHistory.setCreateBy(loginUser.getUserId());
    inOrderPlanStatusHistory.setCreateByName(loginUser.getNickname());
    inOrderPlanStatusHistory.setCreateTime(DateUtils.getNowDate());
    inOrderPlanStatusHistory.setRemark(remark);
    this.save(inOrderPlanStatusHistory);
  }
  //#endregion
}
