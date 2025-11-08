package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageSnAdjust;
import com.yiruantong.inventory.domain.operation.vo.StorageSnAdjustVo;
import com.yiruantong.inventory.domain.operation.bo.StorageSnAdjustBo;

/**
 * SN调整Service接口
 *
 * @author YRT
 * @date 2024-09-05
 */
public interface IStorageSnAdjustService extends IServicePlus<StorageSnAdjust, StorageSnAdjustVo, StorageSnAdjustBo> {
}
