package com.yiruantong.basic.service.tms.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.tms.bo.TmsFencedetailBo;
import com.yiruantong.basic.domain.tms.vo.TmsFencedetailVo;
import com.yiruantong.basic.domain.tms.TmsFencedetail;
import com.yiruantong.basic.mapper.tms.TmsFencedetailMapper;
import com.yiruantong.basic.service.tms.ITmsFencedetailService;

/**
 * 围栏管理明细Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class TmsFencedetailServiceImpl extends ServiceImplPlus<TmsFencedetailMapper, TmsFencedetail, TmsFencedetailVo, TmsFencedetailBo> implements ITmsFencedetailService {
}
