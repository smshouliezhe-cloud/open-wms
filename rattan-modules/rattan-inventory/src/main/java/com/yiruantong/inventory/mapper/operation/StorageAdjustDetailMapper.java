package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 库存调整单明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageAdjustDetailMapper extends BaseMapperPlus<StorageAdjustDetail, StorageAdjustDetailVo> {

}
