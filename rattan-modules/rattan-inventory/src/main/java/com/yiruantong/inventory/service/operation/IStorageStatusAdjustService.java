package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageStatusAdjust;
import com.yiruantong.inventory.domain.operation.vo.StorageStatusAdjustVo;
import com.yiruantong.inventory.domain.operation.bo.StorageStatusAdjustBo;

/**
 * 状态属性调整Service接口
 *
 * @author YRT
 * @date 2025-02-14
 */
public interface IStorageStatusAdjustService extends IServicePlus<StorageStatusAdjust, StorageStatusAdjustVo, StorageStatusAdjustBo> {
}
