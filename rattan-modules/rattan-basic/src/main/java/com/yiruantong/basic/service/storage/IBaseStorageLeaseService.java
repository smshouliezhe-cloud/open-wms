package com.yiruantong.basic.service.storage;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.storage.BaseStorageLease;
import com.yiruantong.basic.domain.storage.vo.BaseStorageLeaseVo;
import com.yiruantong.basic.domain.storage.bo.BaseStorageLeaseBo;

/**
 * 租赁管理Service接口
 *
 * @author YRT
 * @date 2024-03-09
 */
public interface IBaseStorageLeaseService extends IServicePlus<BaseStorageLease, BaseStorageLeaseVo, BaseStorageLeaseBo> {
}
