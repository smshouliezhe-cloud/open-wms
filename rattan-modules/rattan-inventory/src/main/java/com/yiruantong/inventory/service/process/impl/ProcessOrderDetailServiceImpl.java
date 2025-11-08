package com.yiruantong.inventory.service.process.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.process.bo.ProcessOrderDetailBo;
import com.yiruantong.inventory.domain.process.vo.ProcessOrderDetailVo;
import com.yiruantong.inventory.domain.process.ProcessOrderDetail;
import com.yiruantong.inventory.mapper.process.ProcessOrderDetailMapper;
import com.yiruantong.inventory.service.process.IProcessOrderDetailService;

/**
 * 加工列明细Service业务层处理
 *
 * @author YRT
 * @date 2025-01-17
 */
@RequiredArgsConstructor
@Service
public class ProcessOrderDetailServiceImpl extends ServiceImplPlus<ProcessOrderDetailMapper, ProcessOrderDetail, ProcessOrderDetailVo, ProcessOrderDetailBo> implements IProcessOrderDetailService {
}
