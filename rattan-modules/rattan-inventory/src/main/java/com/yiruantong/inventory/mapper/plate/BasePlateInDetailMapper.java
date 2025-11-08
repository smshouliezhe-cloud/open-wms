package com.yiruantong.inventory.mapper.plate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.plate.BasePlateInDetail;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 容器归还明细Mapper接口
 *
 * @author YRT
 * @date 2023-12-21
 */
public interface BasePlateInDetailMapper extends BaseMapperPlus<BasePlateInDetail, BasePlateInDetailVo> {

}
