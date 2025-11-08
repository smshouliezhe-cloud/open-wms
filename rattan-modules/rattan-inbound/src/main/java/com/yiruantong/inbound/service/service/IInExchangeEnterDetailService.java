package com.yiruantong.inbound.service.service;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.service.InExchangeEnterDetail;
import com.yiruantong.inbound.domain.service.vo.InExchangeEnterDetailVo;
import com.yiruantong.inbound.domain.service.bo.InExchangeEnterDetailBo;

/**
 * 换货单入库明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IInExchangeEnterDetailService extends IServicePlus<InExchangeEnterDetail, InExchangeEnterDetailVo, InExchangeEnterDetailBo> {
}
