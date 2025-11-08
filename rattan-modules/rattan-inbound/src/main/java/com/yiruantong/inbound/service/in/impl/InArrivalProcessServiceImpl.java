package com.yiruantong.inbound.service.in.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.in.bo.InArrivalProcessBo;
import com.yiruantong.inbound.domain.in.vo.InArrivalProcessVo;
import com.yiruantong.inbound.domain.in.InArrivalProcess;
import com.yiruantong.inbound.mapper.in.InArrivalProcessMapper;
import com.yiruantong.inbound.service.in.IInArrivalProcessService;

/**
 * 到货加工Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@RequiredArgsConstructor
@Service
public class InArrivalProcessServiceImpl extends ServiceImplPlus<InArrivalProcessMapper, InArrivalProcess, InArrivalProcessVo, InArrivalProcessBo> implements IInArrivalProcessService {
}
