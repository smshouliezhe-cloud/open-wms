package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageValidAdjust;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustVo;
import com.yiruantong.inventory.domain.operation.bo.StorageValidAdjustBo;

/**
 * 效期信息调整Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageValidAdjustService extends IServicePlus<StorageValidAdjust, StorageValidAdjustVo, StorageValidAdjustBo> {
}
