package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransfer;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 货位转移Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageConsignorTransferMapper extends BaseMapperPlus<StorageConsignorTransfer, StorageConsignorTransferVo> {

}
