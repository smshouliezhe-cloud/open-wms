package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.TmsFeedback;
import com.yiruantong.basic.domain.tms.vo.TmsFeedbackVo;
import com.yiruantong.basic.domain.tms.bo.TmsFeedbackBo;
import com.yiruantong.basic.mapper.tms.TmsFeedbackMapper;
import com.yiruantong.basic.service.tms.ITmsFeedbackService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 司机反馈
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/feedback")
public class TmsFeedbackController extends AbstractController<TmsFeedbackMapper, TmsFeedback, TmsFeedbackVo, TmsFeedbackBo> {
}
