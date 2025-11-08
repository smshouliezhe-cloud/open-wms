package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageEnter;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 其他入库单Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageEnterMapper extends BaseMapperPlus<StorageEnter, StorageEnterVo> {

}
