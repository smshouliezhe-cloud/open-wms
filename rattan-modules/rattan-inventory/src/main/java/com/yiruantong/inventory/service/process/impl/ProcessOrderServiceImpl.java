package com.yiruantong.inventory.service.process.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.process.bo.ProcessOrderBo;
import com.yiruantong.inventory.domain.process.vo.ProcessOrderVo;
import com.yiruantong.inventory.domain.process.ProcessOrder;
import com.yiruantong.inventory.mapper.process.ProcessOrderMapper;
import com.yiruantong.inventory.service.process.IProcessOrderService;

/**
 * 加工列Service业务层处理
 *
 * @author YRT
 * @date 2025-01-17
 */
@RequiredArgsConstructor
@Service
public class ProcessOrderServiceImpl extends ServiceImplPlus<ProcessOrderMapper, ProcessOrder, ProcessOrderVo, ProcessOrderBo> implements IProcessOrderService {
}
