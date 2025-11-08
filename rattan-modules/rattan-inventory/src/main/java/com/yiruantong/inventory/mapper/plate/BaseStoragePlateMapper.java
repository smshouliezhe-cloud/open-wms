package com.yiruantong.inventory.mapper.plate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.plate.BaseStoragePlate;
import com.yiruantong.inventory.domain.plate.vo.BaseStoragePlateVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 仓库容器查询Mapper接口
 *
 * @author YRT
 * @date 2024-03-06
 */
public interface BaseStoragePlateMapper extends BaseMapperPlus<BaseStoragePlate, BaseStoragePlateVo> {

}
