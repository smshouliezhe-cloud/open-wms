package com.yiruantong.basic.controller.common;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.common.CommonComment;
import com.yiruantong.basic.domain.common.vo.CommonCommentVo;
import com.yiruantong.basic.domain.common.bo.CommonCommentBo;
import com.yiruantong.basic.mapper.common.CommonCommentMapper;
import com.yiruantong.basic.service.common.ICommonCommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面单据评论
 *
 * @author YRT
 * @date 2025-03-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/common/comment")
public class CommonCommentController extends AbstractController<CommonCommentMapper, CommonComment, CommonCommentVo, CommonCommentBo> {
}
