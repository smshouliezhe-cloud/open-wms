package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.basic.service.common.ICommonOperationLogService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderStatusHistory;
import com.yiruantong.outbound.domain.out.bo.OutOrderStatusHistoryBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderStatusHistoryVo;
import com.yiruantong.outbound.mapper.out.OutOrderStatusHistoryMapper;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 订单状态历史记录Service业务层处理
 *
 * @author YRT
 * @date 2023-11-10
 */
@RequiredArgsConstructor
@Service
public class OutOrderStatusHistoryServiceImpl extends ServiceImplPlus<OutOrderStatusHistoryMapper, OutOrderStatusHistory, OutOrderStatusHistoryVo, OutOrderStatusHistoryBo> implements IOutOrderStatusHistoryService {
  private final ICommonOperationLogService commonOperationLogService;

  @Override
  public void AddHistory(OutOrder outOrder, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum toStatus, String remark) {

    LoginUser loginUser = LoginHelper.getLoginUser();
    this.AddHistory(outOrder, operationTypeEnum, toStatus, loginUser, remark);
  }

  //#region 添加出库单操作轨迹
  @Override
  public void AddHistory(OutOrder outOrder, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum toStatus) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    this.AddHistory(outOrder, operationTypeEnum, toStatus, loginUser, outOrder.getRemark());
  }

  @Override
  public void AddHistory(OutOrder outOrder, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum toStatus, LoginUser loginUser) {
    this.AddHistory(outOrder, operationTypeEnum, toStatus, loginUser, outOrder.getRemark());
  }

  public void AddHistory(OutOrder outOrder, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum toStatus, LoginUser loginUser, String remark) {
    OutOrderStatusEnum fromStatus = OutOrderStatusEnum.matchingEnum(outOrder.getOrderStatus());

    OutOrderStatusHistory history = new OutOrderStatusHistory();
    history.setOperationType(operationTypeEnum.getName());
    history.setBillId(outOrder.getOrderId());
    history.setBillCode(outOrder.getOrderCode());
    if (ObjectUtil.isNotNull(fromStatus)) {
      history.setFromStatus(fromStatus.getName());
    }
    history.setToStatus(toStatus.getName());
    history.setRemark(remark);
    history.setCreateBy(loginUser.getUserId());
    history.setCreateByName(loginUser.getNickname());
    this.save(history);
    this.commonOperationLogService.addOutOrderLog(outOrder.getOrderId(), outOrder.getOrderCode(), operationTypeEnum, fromStatus, toStatus, remark);
  }
  //#endregion
}
