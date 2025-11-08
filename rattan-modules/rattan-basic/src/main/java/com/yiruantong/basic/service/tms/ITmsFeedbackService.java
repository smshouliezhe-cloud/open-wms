package com.yiruantong.basic.service.tms;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.tms.TmsFeedback;
import com.yiruantong.basic.domain.tms.vo.TmsFeedbackVo;
import com.yiruantong.basic.domain.tms.bo.TmsFeedbackBo;

/**
 * 司机反馈Service接口
 *
 * @author YRT
 * @date 2023-11-03
 */
public interface ITmsFeedbackService extends IServicePlus<TmsFeedback, TmsFeedbackVo, TmsFeedbackBo> {
}
