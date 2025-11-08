package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.TmsSite;
import com.yiruantong.basic.domain.tms.vo.TmsSiteVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 网点管理Mapper接口
 *
 * @author YRT
 * @date 2024-03-08
 */
public interface TmsSiteMapper extends BaseMapperPlus<TmsSite, TmsSiteVo> {

}
