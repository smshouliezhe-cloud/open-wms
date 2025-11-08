package com.yiruantong.outbound.mapper.order;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.outbound.domain.order.OutSendBill;
import com.yiruantong.outbound.domain.order.vo.OutSendBillVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 发货单明细Mapper接口
 *
 * @author YRT
 * @date 2024-01-06
 */
public interface OutSendBillMapper extends BaseMapperPlus<OutSendBill, OutSendBillVo> {

}
