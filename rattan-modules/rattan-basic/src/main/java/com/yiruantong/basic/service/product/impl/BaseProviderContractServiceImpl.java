package com.yiruantong.basic.service.product.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.product.bo.BaseProviderContractBo;
import com.yiruantong.basic.domain.product.vo.BaseProviderContractVo;
import com.yiruantong.basic.domain.product.BaseProviderContract;
import com.yiruantong.basic.mapper.product.BaseProviderContractMapper;
import com.yiruantong.basic.service.product.IBaseProviderContractService;

/**
 * 供应商合同管理Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-16
 */
@RequiredArgsConstructor
@Service
public class BaseProviderContractServiceImpl extends ServiceImplPlus<BaseProviderContractMapper, BaseProviderContract, BaseProviderContractVo, BaseProviderContractBo> implements IBaseProviderContractService {
}
