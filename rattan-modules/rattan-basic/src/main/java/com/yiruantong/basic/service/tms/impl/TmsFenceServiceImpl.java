package com.yiruantong.basic.service.tms.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.tms.bo.TmsFenceBo;
import com.yiruantong.basic.domain.tms.vo.TmsFenceVo;
import com.yiruantong.basic.domain.tms.TmsFence;
import com.yiruantong.basic.mapper.tms.TmsFenceMapper;
import com.yiruantong.basic.service.tms.ITmsFenceService;

/**
 * 围栏管理Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class TmsFenceServiceImpl extends ServiceImplPlus<TmsFenceMapper, TmsFence, TmsFenceVo, TmsFenceBo> implements ITmsFenceService {
}
