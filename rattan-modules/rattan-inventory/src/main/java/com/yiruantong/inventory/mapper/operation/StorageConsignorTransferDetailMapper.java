package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransferDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 货位转移明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageConsignorTransferDetailMapper extends BaseMapperPlus<StorageConsignorTransferDetail, StorageConsignorTransferDetailVo> {

}
