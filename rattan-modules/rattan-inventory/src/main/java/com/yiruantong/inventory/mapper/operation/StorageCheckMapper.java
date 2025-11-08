package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageCheck;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 盘点单Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageCheckMapper extends BaseMapperPlus<StorageCheck, StorageCheckVo> {

}
