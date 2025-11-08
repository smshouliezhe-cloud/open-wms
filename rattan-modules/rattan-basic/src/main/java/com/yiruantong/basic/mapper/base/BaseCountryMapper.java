package com.yiruantong.basic.mapper.base;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.base.BaseCountry;
import com.yiruantong.basic.domain.base.vo.BaseCountryVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 国家信息Mapper接口
 *
 * @author YRT
 * @date 2024-06-06
 */
public interface BaseCountryMapper extends BaseMapperPlus<BaseCountry, BaseCountryVo> {

}
