package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageOuterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 盘点单明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageOuterDetailMapper extends BaseMapperPlus<StorageOuterDetail, StorageOuterDetailVo> {

}
