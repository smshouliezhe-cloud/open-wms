package com.yiruantong.inbound.service.service.impl;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InReturnActionEnum;
import com.yiruantong.common.core.enums.in.InReturnEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnStatusHistory;
import com.yiruantong.inbound.domain.service.bo.InReturnStatusHistoryBo;
import com.yiruantong.inbound.domain.service.vo.InReturnStatusHistoryVo;
import com.yiruantong.inbound.mapper.service.InReturnStatusHistoryMapper;
import com.yiruantong.inbound.service.service.IInReturnStatusHistoryService;
import org.springframework.stereotype.Service;

/**
 * 退货单流水Service业务层处理
 *
 * @author YRT
 * @date 2024-01-27
 */
@RequiredArgsConstructor
@Service
public class InReturnStatusHistoryServiceImpl extends ServiceImplPlus<InReturnStatusHistoryMapper, InReturnStatusHistory, InReturnStatusHistoryVo, InReturnStatusHistoryBo> implements IInReturnStatusHistoryService {
  @Override
  public void addHistoryInfo(InReturn inReturn, InReturnActionEnum actionEnum, InReturnEnum fromStatus, InReturnEnum toStatus, String remark) {
      LoginUser loginUser= LoginHelper.getLoginUser();
      addHistoryInfo(inReturn,actionEnum,fromStatus.getName(),toStatus.getName(),loginUser,remark);
  }

  @Override
  public void addHistoryInfo(InReturn inReturn, InReturnActionEnum actionEnum, InReturnEnum toStatus, String remark) {
    LoginUser loginUser= LoginHelper.getLoginUser();
    addHistoryInfo(inReturn,actionEnum,"",toStatus.getName(),loginUser,remark);
  }
  @Override
  public void addHistoryInfo(InReturn inReturn, InReturnActionEnum actionEnum, String fromStatus, String toStatus, LoginUser loginUser, String remark) {
      if(ObjectUtil.isNull(loginUser)){
        loginUser=new LoginUser();
        loginUser.setUserId(1L);
        loginUser.setNickname("超级管理员");
      }
      InReturnStatusHistory inOrderStatusHistory = new InReturnStatusHistory();
      inOrderStatusHistory.setReturnId(inReturn.getReturnId());
      inOrderStatusHistory.setBillId(inReturn.getReturnId());
      inOrderStatusHistory.setBillCode(inReturn.getReturnCode());
      inOrderStatusHistory.setStatusType("单据状态");
      inOrderStatusHistory.setOperationType(actionEnum.getName());
      inOrderStatusHistory.setFromStatus(fromStatus);
      inOrderStatusHistory.setToStatus(toStatus);
      inOrderStatusHistory.setCreateBy(loginUser.getUserId());
      inOrderStatusHistory.setCreateByName(loginUser.getNickname());
      inOrderStatusHistory.setCreateTime(DateUtils.getNowDate());
      inOrderStatusHistory.setRemark(remark);
      this.save(inOrderStatusHistory);
  }
}
