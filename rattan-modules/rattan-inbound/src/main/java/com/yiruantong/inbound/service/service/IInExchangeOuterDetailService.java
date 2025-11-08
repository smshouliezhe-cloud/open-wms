package com.yiruantong.inbound.service.service;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.service.InExchangeOuterDetail;
import com.yiruantong.inbound.domain.service.vo.InExchangeOuterDetailVo;
import com.yiruantong.inbound.domain.service.bo.InExchangeOuterDetailBo;

/**
 * 换货单出库明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IInExchangeOuterDetailService extends IServicePlus<InExchangeOuterDetail, InExchangeOuterDetailVo, InExchangeOuterDetailBo> {
}
