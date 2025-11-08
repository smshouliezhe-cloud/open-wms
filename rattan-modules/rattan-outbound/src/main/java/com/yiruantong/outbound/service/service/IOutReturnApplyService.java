package com.yiruantong.outbound.service.service;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.outbound.domain.service.OutReturnApply;
import com.yiruantong.outbound.domain.service.vo.OutReturnApplyVo;
import com.yiruantong.outbound.domain.service.bo.OutReturnApplyBo;

/**
 * 出库退货申请单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
public interface IOutReturnApplyService extends IServicePlus<OutReturnApply, OutReturnApplyVo, OutReturnApplyBo> {
}
