package com.yiruantong.inbound.service.service;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.service.InExchange;
import com.yiruantong.inbound.domain.service.vo.InExchangeVo;
import com.yiruantong.inbound.domain.service.bo.InExchangeBo;

/**
 * 换货单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IInExchangeService extends IServicePlus<InExchange, InExchangeVo, InExchangeBo> {
}
