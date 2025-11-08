package com.yiruantong.basic.service.client;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.basic.domain.client.BaseClientAddress;
import com.yiruantong.basic.domain.client.vo.BaseClientAddressVo;
import com.yiruantong.basic.domain.client.bo.BaseClientAddressBo;

/**
 * 客户地址管理Service接口
 *
 * @author YRT
 * @date 2023-10-26
 */
public interface IBaseClientAddressService extends IServicePlus<BaseClientAddress, BaseClientAddressVo, BaseClientAddressBo> {
}
