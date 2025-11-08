package com.yiruantong.basic.mapper.service;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.service.TmsServicePart;
import com.yiruantong.basic.domain.service.vo.TmsServicePartVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 维修配件管理Mapper接口
 *
 * @author YRT
 * @date 2024-03-09
 */
public interface TmsServicePartMapper extends BaseMapperPlus<TmsServicePart, TmsServicePartVo> {

}
