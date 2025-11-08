package com.yiruantong.inventory.service.allocate.impl;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyActionEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyStatusHistoryBo;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyStatusHistoryVo;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyStatusHistory;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateApplyStatusHistoryMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyStatusHistoryService;

/**
 * 调拨申请单轨迹Service业务层处理
 *
 * @author YRT
 * @date 2023-12-22
 */
@RequiredArgsConstructor
@Service
public class StorageAllocateApplyStatusHistoryServiceImpl extends ServiceImplPlus<StorageAllocateApplyStatusHistoryMapper, StorageAllocateApplyStatusHistory, StorageAllocateApplyStatusHistoryVo, StorageAllocateApplyStatusHistoryBo> implements IStorageAllocateApplyStatusHistoryService {
  /**
   *
   * @param storageAllocateApply 入库记录单
   * @param actionEnum 动作
   * @param fromStatus  变更前事件
   * @param toStatus 变更后事件
   */
  @Override
  public void addHistoryInfo(StorageAllocateApply storageAllocateApply, StorageAllocateApplyActionEnum actionEnum, StorageAllocateApplyStatusEnum fromStatus, StorageAllocateApplyStatusEnum toStatus) {
    LoginUser loginUser= LoginHelper.getLoginUser();
    addHistoryInfo(storageAllocateApply,actionEnum,fromStatus.getName(),toStatus.getName(),loginUser,"");
  }

  @Override
  public void addHistoryInfo(StorageAllocateApply storageAllocateApply, StorageAllocateApplyActionEnum actionEnum, StorageAllocateApplyStatusEnum fromStatus, StorageAllocateApplyStatusEnum toStatus, String remark) {
    LoginUser loginUser= LoginHelper.getLoginUser();
    addHistoryInfo(storageAllocateApply,actionEnum,fromStatus.getName(),toStatus.getName(),loginUser,remark);
  }

  @Override
  public void addHistoryInfo(StorageAllocateApply storageAllocateApply, StorageAllocateApplyActionEnum actionEnum, StorageAllocateApplyStatusEnum toStatus) {
    LoginUser loginUser= LoginHelper.getLoginUser();
    addHistoryInfo(storageAllocateApply,actionEnum,"",toStatus.getName(),loginUser,"");
  }

  @Override
  public void addHistoryInfo(StorageAllocateApply storageAllocateApply, StorageAllocateApplyActionEnum actionEnum, String fromStatus, String toStatus, LoginUser loginUser,String remark) {
    if(ObjectUtil.isNull(loginUser)){
      loginUser=new LoginUser();
      loginUser.setUserId(1L);
      loginUser.setNickname("超级管理员");
    }
    StorageAllocateApplyStatusHistory inOrderStatusHistory = new StorageAllocateApplyStatusHistory();
    inOrderStatusHistory.setAllocateApplyId(storageAllocateApply.getAllocateApplyId());
    inOrderStatusHistory.setBillId(storageAllocateApply.getAllocateApplyId());
    inOrderStatusHistory.setBillCode(storageAllocateApply.getAllocateApplyCode());
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
