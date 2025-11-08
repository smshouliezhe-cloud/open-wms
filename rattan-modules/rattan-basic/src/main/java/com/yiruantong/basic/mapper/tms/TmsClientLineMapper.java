package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.TmsClientLine;
import com.yiruantong.basic.domain.tms.vo.TmsClientLineVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 客户线路规则Mapper接口
 *
 * @author YRT
 * @date 2024-03-08
 */
public interface TmsClientLineMapper extends BaseMapperPlus<TmsClientLine, TmsClientLineVo> {

}
