package com.yiruantong.outbound.service.service;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.service.OutReturnApplyDetail;
import com.yiruantong.outbound.domain.service.vo.OutReturnApplyDetailVo;
import com.yiruantong.outbound.domain.service.bo.OutReturnApplyDetailBo;

/**
 * 出库退货申请单明细Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IOutReturnApplyDetailService extends IServicePlus<OutReturnApplyDetail, OutReturnApplyDetailVo, OutReturnApplyDetailBo> {
}
