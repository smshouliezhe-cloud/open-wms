package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageCheckDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 盘点单明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageCheckDetailMapper extends BaseMapperPlus<StorageCheckDetail, StorageCheckDetailVo> {

}
