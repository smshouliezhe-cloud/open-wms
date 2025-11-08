package com.yiruantong.outbound.service.order;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.order.OutSendBill;
import com.yiruantong.outbound.domain.order.vo.OutSendBillVo;
import com.yiruantong.outbound.domain.order.bo.OutSendBillBo;

/**
 * 发货单明细Service接口
 *
 * @author YRT
 * @date 2024-01-06
 */
public interface IOutSendBillService extends IServicePlus<OutSendBill, OutSendBillVo, OutSendBillBo> {
}
