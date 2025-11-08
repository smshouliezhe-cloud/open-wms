package com.yiruantong.basic.service.tms.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.tms.bo.TmsFeedbackBo;
import com.yiruantong.basic.domain.tms.vo.TmsFeedbackVo;
import com.yiruantong.basic.domain.tms.TmsFeedback;
import com.yiruantong.basic.mapper.tms.TmsFeedbackMapper;
import com.yiruantong.basic.service.tms.ITmsFeedbackService;

/**
 * 司机反馈Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class TmsFeedbackServiceImpl extends ServiceImplPlus<TmsFeedbackMapper, TmsFeedback, TmsFeedbackVo, TmsFeedbackBo> implements ITmsFeedbackService {
}
