package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageAssembleOuterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleOuterDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 商品拆装单出库明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageAssembleOuterDetailMapper extends BaseMapperPlus<StorageAssembleOuterDetail, StorageAssembleOuterDetailVo> {

}
