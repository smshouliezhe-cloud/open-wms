package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inbound.domain.in.InDamagedOrder;
import com.yiruantong.inbound.domain.in.vo.InDamagedOrderVo;
import com.yiruantong.inbound.domain.in.bo.InDamagedOrderBo;

import java.util.Map;

/**
 * 残品入库单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-16
 */
public interface IInDamagedOrderService extends IServicePlus<InDamagedOrder, InDamagedOrderVo, InDamagedOrderBo> {
  R<Void> toInOrder(Map<String, Object> map);
}
