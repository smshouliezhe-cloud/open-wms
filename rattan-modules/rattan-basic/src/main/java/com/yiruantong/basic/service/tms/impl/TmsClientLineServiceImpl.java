package com.yiruantong.basic.service.tms.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.tms.bo.TmsClientLineBo;
import com.yiruantong.basic.domain.tms.vo.TmsClientLineVo;
import com.yiruantong.basic.domain.tms.TmsClientLine;
import com.yiruantong.basic.mapper.tms.TmsClientLineMapper;
import com.yiruantong.basic.service.tms.ITmsClientLineService;

/**
 * 客户线路规则Service业务层处理
 *
 * @author YRT
 * @date 2024-03-08
 */
@RequiredArgsConstructor
@Service
public class TmsClientLineServiceImpl extends ServiceImplPlus<TmsClientLineMapper, TmsClientLine, TmsClientLineVo, TmsClientLineBo> implements ITmsClientLineService {
}
