package com.yiruantong.basic.service.common.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.common.bo.CommonCommentBo;
import com.yiruantong.basic.domain.common.vo.CommonCommentVo;
import com.yiruantong.basic.domain.common.CommonComment;
import com.yiruantong.basic.mapper.common.CommonCommentMapper;
import com.yiruantong.basic.service.common.ICommonCommentService;

/**
 * 页面单据评论Service业务层处理
 *
 * @author YRT
 * @date 2025-03-08
 */
@RequiredArgsConstructor
@Service
public class CommonCommentServiceImpl extends ServiceImplPlus<CommonCommentMapper, CommonComment, CommonCommentVo, CommonCommentBo> implements ICommonCommentService {
}
