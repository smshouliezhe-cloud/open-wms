package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageValidAdjust;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 效期信息调整Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageValidAdjustMapper extends BaseMapperPlus<StorageValidAdjust, StorageValidAdjustVo> {

}
