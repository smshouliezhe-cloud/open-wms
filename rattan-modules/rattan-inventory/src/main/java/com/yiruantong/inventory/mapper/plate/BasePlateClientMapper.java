package com.yiruantong.inventory.mapper.plate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.plate.BasePlateClient;
import com.yiruantong.inventory.domain.plate.vo.BasePlateClientVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 客户容器管理Mapper接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface BasePlateClientMapper extends BaseMapperPlus<BasePlateClient, BasePlateClientVo> {

}
