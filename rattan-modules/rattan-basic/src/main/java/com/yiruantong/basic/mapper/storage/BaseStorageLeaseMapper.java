package com.yiruantong.basic.mapper.storage;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.storage.BaseStorageLease;
import com.yiruantong.basic.domain.storage.vo.BaseStorageLeaseVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 租赁管理Mapper接口
 *
 * @author YRT
 * @date 2024-03-09
 */
public interface BaseStorageLeaseMapper extends BaseMapperPlus<BaseStorageLease, BaseStorageLeaseVo> {

}
