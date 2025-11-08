package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageAssemble;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleVo;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleBo;

/**
 * 商品拆装单Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageAssembleService extends IServicePlus<StorageAssemble, StorageAssembleVo, StorageAssembleBo> {
}
