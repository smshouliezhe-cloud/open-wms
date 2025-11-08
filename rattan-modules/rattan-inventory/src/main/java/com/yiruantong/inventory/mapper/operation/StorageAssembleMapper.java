package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageAssemble;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 商品拆装单Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageAssembleMapper extends BaseMapperPlus<StorageAssemble, StorageAssembleVo> {

}
