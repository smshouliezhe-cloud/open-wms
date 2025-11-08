package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.TmsLine;
import com.yiruantong.basic.domain.tms.vo.TmsLineVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 线路管理Mapper接口
 *
 * @author YRT
 * @date 2023-12-28
 */
public interface TmsLineMapper extends BaseMapperPlus<TmsLine, TmsLineVo> {

}
