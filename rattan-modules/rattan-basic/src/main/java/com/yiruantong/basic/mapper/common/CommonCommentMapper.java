package com.yiruantong.basic.mapper.common;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.basic.domain.common.CommonComment;
import com.yiruantong.basic.domain.common.vo.CommonCommentVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 页面单据评论Mapper接口
 *
 * @author YRT
 * @date 2025-03-08
 */
public interface CommonCommentMapper extends BaseMapperPlus<CommonComment, CommonCommentVo> {

}
