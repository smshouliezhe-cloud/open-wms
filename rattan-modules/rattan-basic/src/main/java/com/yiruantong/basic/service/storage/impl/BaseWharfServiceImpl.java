package com.yiruantong.basic.service.storage.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.storage.bo.BaseWharfBo;
import com.yiruantong.basic.domain.storage.vo.BaseWharfVo;
import com.yiruantong.basic.domain.storage.BaseWharf;
import com.yiruantong.basic.mapper.storage.BaseWharfMapper;
import com.yiruantong.basic.service.storage.IBaseWharfService;

/**
 * 码头管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@RequiredArgsConstructor
@Service
public class BaseWharfServiceImpl extends ServiceImplPlus<BaseWharfMapper, BaseWharf, BaseWharfVo, BaseWharfBo> implements IBaseWharfService {
}
