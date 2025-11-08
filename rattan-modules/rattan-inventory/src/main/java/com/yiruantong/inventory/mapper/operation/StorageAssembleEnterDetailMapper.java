package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageAssembleEnterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleEnterDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 商品拆装单入库明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageAssembleEnterDetailMapper extends BaseMapperPlus<StorageAssembleEnterDetail, StorageAssembleEnterDetailVo> {

}
