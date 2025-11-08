package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.BaseDriver;
import com.yiruantong.basic.domain.tms.vo.BaseDriverVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 司机管理Mapper接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface BaseDriverMapper extends BaseMapperPlus<BaseDriver, BaseDriverVo> {

}
