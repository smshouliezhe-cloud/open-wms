package com.yiruantong.basic.service.tms.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.tms.bo.BaseTrailerBo;
import com.yiruantong.basic.domain.tms.vo.BaseTrailerVo;
import com.yiruantong.basic.domain.tms.BaseTrailer;
import com.yiruantong.basic.mapper.tms.BaseTrailerMapper;
import com.yiruantong.basic.service.tms.IBaseTrailerService;

/**
 * 挂车管理Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class BaseTrailerServiceImpl extends ServiceImplPlus<BaseTrailerMapper, BaseTrailer, BaseTrailerVo, BaseTrailerBo> implements IBaseTrailerService {
}
