package com.yiruantong.inbound.service.in.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.service.common.ICommonOperationLogService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderStatusHistory;
import com.yiruantong.inbound.domain.in.bo.InOrderStatusHistoryBo;
import com.yiruantong.inbound.domain.in.vo.InOrderStatusHistoryVo;
import com.yiruantong.inbound.mapper.in.InOrderStatusHistoryMapper;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
import org.springframework.stereotype.Service;

/**
 * 采购订单流水记录Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@RequiredArgsConstructor
@Service
public class InOrderStatusHistoryServiceImpl extends ServiceImplPlus<InOrderStatusHistoryMapper, InOrderStatusHistory, InOrderStatusHistoryVo, InOrderStatusHistoryBo> implements IInOrderStatusHistoryService {
  private final ICommonOperationLogService commonOperationLogService;

  //#region 枚举方式添加 轨迹信息
  @Override
  public void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum fromStatus, InOrderStatusEnum toStatus) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    this.addHistoryInfo(inOrderInfo, actionEnum, fromStatus, toStatus, loginUser, "");
  }

  @Override
  public void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum toStatus) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    this.addHistoryInfo(inOrderInfo, actionEnum, null, toStatus, loginUser, "");
  }

  @Override
  public void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum toStatus, String Remark) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    this.addHistoryInfo(inOrderInfo, actionEnum, null, toStatus, loginUser, Remark);
  }

  @Override
  public void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum toStatus, LoginUser loginUser) {
    this.addHistoryInfo(inOrderInfo, actionEnum, null, toStatus, loginUser, "");
  }

  //#region 添加预到货单  把多态的添加轨迹 在提取出一个方法
  // 不写对应的接口 只在本方法进行使用
  @Override
  public void addHistoryInfo(InOrder inOrderInfo, InOrderActionEnum actionEnum, InOrderStatusEnum fromStatus, InOrderStatusEnum toStatus, LoginUser loginUser, String remark) {
    String _fromStatus = ObjectUtil.isNull(fromStatus) ? null : fromStatus.getName();
    String _toStatus = ObjectUtil.isNull(toStatus) ? null : toStatus.getName();
    this.addHistoryInfo(inOrderInfo, actionEnum.getName(), _fromStatus, _toStatus, loginUser, remark);
  }

  @Override
  public void addHistoryInfo(InOrder inOrderInfo, String action, String fromStatus, String toStatus) {
    this.addHistoryInfo(inOrderInfo, action, fromStatus, toStatus, null);
  }

  @Override
  public void addHistoryInfo(InOrder inOrderInfo, String action, String fromStatus, String toStatus, String remark) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    this.addHistoryInfo(inOrderInfo, action, fromStatus, toStatus, loginUser, remark);
  }

  @Override
  public void addHistoryInfo(InOrder inOrderInfo, String action, String fromStatus, String toStatus, LoginUser loginUser, String remark) {
    if (ObjectUtil.isNull(loginUser)) {
      loginUser = new LoginUser();
      loginUser.setUserId(1L);
      loginUser.setNickname("超级管理员");
    }
    InOrderStatusHistory inOrderStatusHistory = new InOrderStatusHistory();
    inOrderStatusHistory.setOrderId(inOrderInfo.getOrderId());
    inOrderStatusHistory.setBillId(inOrderInfo.getOrderId());
    inOrderStatusHistory.setBillCode(inOrderInfo.getOrderCode());
    inOrderStatusHistory.setStatusType("单据状态");
    inOrderStatusHistory.setOperationType(action);
    if (ObjectUtil.isNotNull(fromStatus)) {
      inOrderStatusHistory.setFromStatus(fromStatus);
    }
    Assert.isFalse(ObjectUtil.isNull(toStatus), "预到货单结束状态不能为空");
    inOrderStatusHistory.setToStatus(toStatus);
    inOrderStatusHistory.setRemark(inOrderInfo.getRemark());
    inOrderStatusHistory.setCreateBy(loginUser.getUserId());
    inOrderStatusHistory.setCreateByName(loginUser.getNickname());
    inOrderStatusHistory.setCreateTime(DateUtils.getNowDate());
    inOrderStatusHistory.setRemark(remark);
    this.save(inOrderStatusHistory);

    // 在通用日志中增加轨迹
    commonOperationLogService.addInOrderLog(inOrderInfo.getOrderId(), inOrderInfo.getOrderCode(), action, fromStatus, toStatus, MenuEnum.MENU_1001, remark);
  }
  //#endregion
}
