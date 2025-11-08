package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageAdjust;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 库存调整单Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageAdjustMapper extends BaseMapperPlus<StorageAdjust, StorageAdjustVo> {

}
