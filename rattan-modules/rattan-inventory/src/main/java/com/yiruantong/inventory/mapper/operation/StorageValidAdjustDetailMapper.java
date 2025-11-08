package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageValidAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 效期信息调整明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageValidAdjustDetailMapper extends BaseMapperPlus<StorageValidAdjustDetail, StorageValidAdjustDetailVo> {

}
