package com.yiruantong.outbound.service.order;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.order.OutRetail;
import com.yiruantong.outbound.domain.order.vo.OutRetailVo;
import com.yiruantong.outbound.domain.order.bo.OutRetailBo;

/**
 * 订货单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
public interface IOutRetailService extends IServicePlus<OutRetail, OutRetailVo, OutRetailBo> {
}
