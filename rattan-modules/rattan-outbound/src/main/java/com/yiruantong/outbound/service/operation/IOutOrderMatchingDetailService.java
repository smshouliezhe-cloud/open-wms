package com.yiruantong.outbound.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.operation.OutOrderMatchingDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingDetailVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderMatchingDetailBo;

/**
 * 订单配货明细Service接口
 *
 * @author YRT
 * @date 2023-12-09
 */
public interface IOutOrderMatchingDetailService extends IServicePlus<OutOrderMatchingDetail, OutOrderMatchingDetailVo, OutOrderMatchingDetailBo> {
}
