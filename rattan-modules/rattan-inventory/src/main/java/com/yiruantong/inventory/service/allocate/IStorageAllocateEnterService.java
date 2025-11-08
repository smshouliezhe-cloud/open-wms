package com.yiruantong.inventory.service.allocate;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnter;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateEnterVo;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateEnterBo;

/**
 * 调拨入库单Service接口
 *
 * @author YRT
 * @date 2023-12-20
 */
public interface IStorageAllocateEnterService extends IServicePlus<StorageAllocateEnter, StorageAllocateEnterVo, StorageAllocateEnterBo> {
}
