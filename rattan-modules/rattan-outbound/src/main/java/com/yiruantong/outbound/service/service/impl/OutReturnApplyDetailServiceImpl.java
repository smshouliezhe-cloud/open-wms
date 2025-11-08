package com.yiruantong.outbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.service.bo.OutReturnApplyDetailBo;
import com.yiruantong.outbound.domain.service.vo.OutReturnApplyDetailVo;
import com.yiruantong.outbound.domain.service.OutReturnApplyDetail;
import com.yiruantong.outbound.mapper.service.OutReturnApplyDetailMapper;
import com.yiruantong.outbound.service.service.IOutReturnApplyDetailService;

/**
 * 出库退货申请单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class OutReturnApplyDetailServiceImpl extends ServiceImplPlus<OutReturnApplyDetailMapper, OutReturnApplyDetail, OutReturnApplyDetailVo, OutReturnApplyDetailBo> implements IOutReturnApplyDetailService {
}
