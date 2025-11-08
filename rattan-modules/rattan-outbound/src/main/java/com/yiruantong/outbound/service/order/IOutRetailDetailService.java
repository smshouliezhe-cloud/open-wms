package com.yiruantong.outbound.service.order;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.order.OutRetailDetail;
import com.yiruantong.outbound.domain.order.vo.OutRetailDetailVo;
import com.yiruantong.outbound.domain.order.bo.OutRetailDetailBo;

/**
 * 订货单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
public interface IOutRetailDetailService extends IServicePlus<OutRetailDetail, OutRetailDetailVo, OutRetailDetailBo> {
}
