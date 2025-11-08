package com.yiruantong.inventory.mapper.plate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 容器借出主Mapper接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface BasePlateOutMapper extends BaseMapperPlus<BasePlateOut, BasePlateOutVo> {

}
