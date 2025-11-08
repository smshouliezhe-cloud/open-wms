package com.yiruantong.basic.service.common.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.basic.domain.common.CommonOperationLog;
import com.yiruantong.basic.domain.common.bo.CommonOperationLogBo;
import com.yiruantong.basic.domain.common.vo.CommonOperationLogVo;
import com.yiruantong.basic.mapper.common.CommonOperationLogMapper;
import com.yiruantong.basic.service.common.ICommonOperationLogService;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.out.OutWaveOperationTypeEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 业务操作日志Service业务层处理
 *
 * @author YRT
 * @date 2025-03-08
 */
@RequiredArgsConstructor
@Service
public class CommonOperationLogServiceImpl extends ServiceImplPlus<CommonOperationLogMapper, CommonOperationLog, CommonOperationLogVo, CommonOperationLogBo> implements ICommonOperationLogService {
  //#region 底层通用日志
  @Override
  public void addCommonLog(Long billId, String billCode, String action, String fromStatus, String toStatus, LoginUser loginUser, Integer menuId, String billType, String remark) {
    if (ObjectUtil.isNull(loginUser)) {
      loginUser = new LoginUser();
      loginUser.setUserId(1L);
      loginUser.setNickname("超级管理员");
    }
    CommonOperationLog operationLog = new CommonOperationLog();
    operationLog.setBillId(billId);
    operationLog.setBillCode(billCode);
    operationLog.setStatusType("单据状态");
    operationLog.setOperationType(action);
    operationLog.setFromStatus(fromStatus);
    operationLog.setToStatus(toStatus);
    operationLog.setCreateBy(loginUser.getUserId());
    operationLog.setCreateByName(loginUser.getNickname());
    operationLog.setCreateTime(DateUtils.getNowDate());
    operationLog.setRemark(remark);
    operationLog.setMenuId(menuId.longValue());
    operationLog.setBillType(billType);
    this.save(operationLog);
  }
  //#endregion

  //#region 添加预到货日志
  @Override
  public void addInOrderLog(Long billId, String billCode, String action, String fromStatus, String toStatus, LoginUser loginUser, Integer menuId, String billType, String remark) {
    this.addCommonLog(billId, billCode, action, fromStatus, toStatus, loginUser, menuId, billType, remark);
  }

  @Override
  public void addInOrderLog(Long billId, String billCode, String action, String fromStatus, String toStatus, MenuEnum menuEnum, String remark) {
    this.addInOrderLog(billId, billCode, action, fromStatus, toStatus, LoginHelper.getLoginUser(), menuEnum.getId(), menuEnum.getName(), remark);
  }

  @Override
  public void addInOrderLog(Long billId, String billCode, InOrderActionEnum actionEnum, InOrderStatusEnum fromStatus, InOrderStatusEnum toStatus) {
    this.addInOrderLog(billId, billCode, actionEnum.getName(), fromStatus.getName(), toStatus.getName(), MenuEnum.MENU_1001, null);
  }
  //#endregion

  //#region 添加出库单日志
  @Override
  public void addOutOrderLog(Long billId, String billCode, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum fromStatus, OutOrderStatusEnum toStatus, String remark) {
    this.addCommonLog(billId, billCode, operationTypeEnum.getName(), Optional.ofNullable(fromStatus).map(OutOrderStatusEnum::getName).orElse(null), toStatus.getName(), LoginHelper.getLoginUser(), MenuEnum.MENU_1671.getId(), MenuEnum.MENU_1671.getName(), remark);
  }

  @Override
  public void addOutOrderLog(Long billId, String billCode, OutOperationTypeEnum operationTypeEnum, OutOrderStatusEnum fromStatus, OutOrderStatusEnum toStatus) {
    this.addOutOrderLog(billId, billCode, operationTypeEnum, fromStatus, toStatus, null);
  }
  //#endregion

  //#region 添加波次日志
  @Override
  public void addOutOrderWaveLog(Long billId, String billCode, OutWaveOperationTypeEnum outWaveOperationTypeEnum, OutOrderStatusEnum fromStatus, OutOrderStatusEnum toStatus, String remark) {
    this.addCommonLog(billId, billCode, outWaveOperationTypeEnum.getName(), fromStatus.getName(), toStatus.getName(), LoginHelper.getLoginUser(), MenuEnum.MENU_1681.getId(), MenuEnum.MENU_1681.getName(), remark);
  }

  @Override
  public void addOutOrderWaveLog(Long billId, String billCode, OutWaveOperationTypeEnum outWaveOperationTypeEnum, OutOrderStatusEnum fromStatus, OutOrderStatusEnum toStatus) {
    this.addOutOrderWaveLog(billId, billCode, outWaveOperationTypeEnum, fromStatus, toStatus, null);
  }
  //#endregion
}
