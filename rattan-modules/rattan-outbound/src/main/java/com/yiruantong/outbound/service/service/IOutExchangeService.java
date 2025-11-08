package com.yiruantong.outbound.service.service;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.service.OutExchange;
import com.yiruantong.outbound.domain.service.vo.OutExchangeComposeVo;
import com.yiruantong.outbound.domain.service.vo.OutExchangeVo;
import com.yiruantong.outbound.domain.service.bo.OutExchangeBo;

/**
 * 换货管理Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-23
 */
public interface IOutExchangeService extends IServicePlus<OutExchange, OutExchangeVo, OutExchangeBo> {
  R<Void> PdaAdd(OutExchangeComposeVo compose);
}
