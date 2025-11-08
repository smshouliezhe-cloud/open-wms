package com.yiruantong.inventory.mapper.plate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 容器借出明细Mapper接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface BasePlateOutDetailMapper extends BaseMapperPlus<BasePlateOutDetail, BasePlateOutDetailVo> {

}
