package com.yiruantong.inbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.service.bo.InRefuseBo;
import com.yiruantong.inbound.domain.service.vo.InRefuseVo;
import com.yiruantong.inbound.domain.service.InRefuse;
import com.yiruantong.inbound.mapper.service.InRefuseMapper;
import com.yiruantong.inbound.service.service.IInRefuseService;

/**
 * 拒收单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class InRefuseServiceImpl extends ServiceImplPlus<InRefuseMapper, InRefuse, InRefuseVo, InRefuseBo> implements IInRefuseService {
}
