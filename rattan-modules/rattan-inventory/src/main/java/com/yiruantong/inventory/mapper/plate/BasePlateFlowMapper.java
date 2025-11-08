package com.yiruantong.inventory.mapper.plate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.plate.BasePlateFlow;
import com.yiruantong.inventory.domain.plate.vo.BasePlateFlowVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 容器流水记录Mapper接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface BasePlateFlowMapper extends BaseMapperPlus<BasePlateFlow, BasePlateFlowVo> {

}
