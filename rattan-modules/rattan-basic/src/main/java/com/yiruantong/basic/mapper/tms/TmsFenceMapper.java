package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.TmsFence;
import com.yiruantong.basic.domain.tms.vo.TmsFenceVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 围栏管理Mapper接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface TmsFenceMapper extends BaseMapperPlus<TmsFence, TmsFenceVo> {

}
