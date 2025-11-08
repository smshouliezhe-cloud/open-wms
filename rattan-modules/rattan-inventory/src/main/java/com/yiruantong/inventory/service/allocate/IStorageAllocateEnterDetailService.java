package com.yiruantong.inventory.service.allocate;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnterDetail;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateEnterDetailVo;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateEnterDetailBo;

/**
 * 调拨入库单明细Service接口
 *
 * @author YRT
 * @date 2023-12-20
 */
public interface IStorageAllocateEnterDetailService extends IServicePlus<StorageAllocateEnterDetail, StorageAllocateEnterDetailVo, StorageAllocateEnterDetailBo> {
}
