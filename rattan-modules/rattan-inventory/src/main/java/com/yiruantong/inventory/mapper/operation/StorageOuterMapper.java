package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageOuter;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 其他出库单Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageOuterMapper extends BaseMapperPlus<StorageOuter, StorageOuterVo> {

}
