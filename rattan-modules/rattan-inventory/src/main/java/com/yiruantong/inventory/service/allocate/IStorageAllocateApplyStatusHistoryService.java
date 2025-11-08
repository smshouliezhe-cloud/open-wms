package com.yiruantong.inventory.service.allocate;

import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyActionEnum;
import com.yiruantong.common.core.enums.inventory.StorageAllocateApplyStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyStatusHistory;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyStatusHistoryBo;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyStatusHistoryVo;

/**
 * 调拨申请单轨迹Service接口
 *
 * @author YRT
 * @date 2023-12-22
 */
public interface IStorageAllocateApplyStatusHistoryService extends IServicePlus<StorageAllocateApplyStatusHistory, StorageAllocateApplyStatusHistoryVo, StorageAllocateApplyStatusHistoryBo> {
  /**
   *
   * @param storageAllocateApply 入库记录单
   * @param actionEnum 动作
   * @param fromStatus  变更前事件
   * @param toStatus 变更后事件
   */
  void addHistoryInfo(StorageAllocateApply storageAllocateApply, StorageAllocateApplyActionEnum actionEnum, StorageAllocateApplyStatusEnum fromStatus, StorageAllocateApplyStatusEnum toStatus);
  /**
   *
   * @param storageAllocateApply 入库记录单
   * @param actionEnum 动作
   * @param fromStatus  变更前事件
   * @param toStatus 变更后事件
   */
  void addHistoryInfo(StorageAllocateApply storageAllocateApply, StorageAllocateApplyActionEnum actionEnum, StorageAllocateApplyStatusEnum fromStatus, StorageAllocateApplyStatusEnum toStatus,String remark);

  /**
   *
   * @param storageAllocateApply 入库记录单
   * @param actionEnum 动作
   * @param toStatus 变更后事件
   */
  void addHistoryInfo(StorageAllocateApply storageAllocateApply, StorageAllocateApplyActionEnum actionEnum, StorageAllocateApplyStatusEnum toStatus);
   void addHistoryInfo(StorageAllocateApply storageAllocateApply, StorageAllocateApplyActionEnum actionEnum, String fromStatus, String toStatus, LoginUser loginUser,String remark);
}
