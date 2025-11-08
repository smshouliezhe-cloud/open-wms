package com.yiruantong.basic.service.tms.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.tms.bo.TmsSubsidyBo;
import com.yiruantong.basic.domain.tms.vo.TmsSubsidyVo;
import com.yiruantong.basic.domain.tms.TmsSubsidy;
import com.yiruantong.basic.mapper.tms.TmsSubsidyMapper;
import com.yiruantong.basic.service.tms.ITmsSubsidyService;

/**
 * 挂车管理Service业务层处理
 *
 * @author YRT
 * @date 2023-11-03
 */
@RequiredArgsConstructor
@Service
public class TmsSubsidyServiceImpl extends ServiceImplPlus<TmsSubsidyMapper, TmsSubsidy, TmsSubsidyVo, TmsSubsidyBo> implements ITmsSubsidyService {
}
