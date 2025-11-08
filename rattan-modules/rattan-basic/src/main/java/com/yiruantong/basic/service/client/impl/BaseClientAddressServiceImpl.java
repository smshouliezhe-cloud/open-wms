package com.yiruantong.basic.service.client.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.client.bo.BaseClientAddressBo;
import com.yiruantong.basic.domain.client.vo.BaseClientAddressVo;
import com.yiruantong.basic.domain.client.BaseClientAddress;
import com.yiruantong.basic.mapper.client.BaseClientAddressMapper;
import com.yiruantong.basic.service.client.IBaseClientAddressService;

/**
 * 客户地址管理Service业务层处理
 *
 * @author YRT
 * @date 2023-10-26
 */
@RequiredArgsConstructor
@Service
public class BaseClientAddressServiceImpl extends ServiceImplPlus<BaseClientAddressMapper, BaseClientAddress, BaseClientAddressVo, BaseClientAddressBo> implements IBaseClientAddressService {
}
