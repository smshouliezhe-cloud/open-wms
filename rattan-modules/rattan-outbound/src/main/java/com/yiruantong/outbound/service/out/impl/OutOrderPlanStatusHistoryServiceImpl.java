package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InOrderPlanActionEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderPlanActionEnum;
import com.yiruantong.common.core.enums.out.OutOrderPlanStatusEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.outbound.domain.out.OutOrderPlan;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanStatusHistoryBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanStatusHistoryVo;
import com.yiruantong.outbound.domain.out.OutOrderPlanStatusHistory;
import com.yiruantong.outbound.mapper.out.OutOrderPlanStatusHistoryMapper;
import com.yiruantong.outbound.service.out.IOutOrderPlanStatusHistoryService;

/**
 * 出库计划状态轨迹Service业务层处理
 *
 * @author YRT
 * @date 2024-09-20
 */
@RequiredArgsConstructor
@Service
public class OutOrderPlanStatusHistoryServiceImpl extends ServiceImplPlus<OutOrderPlanStatusHistoryMapper, OutOrderPlanStatusHistory, OutOrderPlanStatusHistoryVo, OutOrderPlanStatusHistoryBo> implements IOutOrderPlanStatusHistoryService {


  //#region 添加入库计划单  把多态的添加轨迹 在提取出一个方法
  // 不写对应的接口 只在本方法进行使用
  @Override
  public void addHistoryInfo(OutOrderPlan outOrderPlanInfo, OutOrderPlanActionEnum actionEnum, OutOrderPlanStatusEnum fromStatus, OutOrderPlanStatusEnum toStatus, LoginUser loginUser, String remark) {
    String _fromStatus = ObjectUtil.isNull(fromStatus)?null: fromStatus.getName();
    String _toStatus = ObjectUtil.isNull(toStatus)?null: toStatus.getName();
    this.addHistoryInfo(outOrderPlanInfo, actionEnum.getName(), _fromStatus, _toStatus, loginUser, remark);
  }


  @Override
  public void addHistoryInfo(OutOrderPlan outOrderPlanInfo, String action, String fromStatus, String toStatus, LoginUser loginUser, String remark) {
    if (ObjectUtil.isNull(loginUser)) {
      loginUser = new LoginUser();
      loginUser.setUserId(1L);
      loginUser.setNickname("超级管理员");
    }
    OutOrderPlanStatusHistory outOrderPlanStatusHistory = new OutOrderPlanStatusHistory();
    outOrderPlanStatusHistory.setOrderId(outOrderPlanInfo.getOrderPlanId());
    outOrderPlanStatusHistory.setBillId(outOrderPlanInfo.getOrderPlanId());
    outOrderPlanStatusHistory.setBillCode(outOrderPlanInfo.getOrderPlanCode());
    outOrderPlanStatusHistory.setStatusType("单据状态");
    outOrderPlanStatusHistory.setOperationType(action);
    if (ObjectUtil.isNotNull(fromStatus)) {
      outOrderPlanStatusHistory.setFromStatus(fromStatus);
    }
    Assert.isFalse(ObjectUtil.isNull(toStatus), "出库计划结束状态不能为空");
    outOrderPlanStatusHistory.setToStatus(toStatus);
    outOrderPlanStatusHistory.setRemark(outOrderPlanInfo.getRemark());
    outOrderPlanStatusHistory.setCreateBy(loginUser.getUserId());
    outOrderPlanStatusHistory.setCreateByName(loginUser.getNickname());
    outOrderPlanStatusHistory.setCreateTime(DateUtils.getNowDate());
    outOrderPlanStatusHistory.setRemark(remark);
    this.save(outOrderPlanStatusHistory);
  }
  //#endregion
}
