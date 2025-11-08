package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageEnterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 其他入库单明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageEnterDetailMapper extends BaseMapperPlus<StorageEnterDetail, StorageEnterDetailVo> {

}
