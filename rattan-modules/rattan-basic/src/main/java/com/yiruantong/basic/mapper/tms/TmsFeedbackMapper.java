package com.yiruantong.basic.mapper.tms;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.tms.TmsFeedback;
import com.yiruantong.basic.domain.tms.vo.TmsFeedbackVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 司机反馈Mapper接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface TmsFeedbackMapper extends BaseMapperPlus<TmsFeedback, TmsFeedbackVo> {

}
