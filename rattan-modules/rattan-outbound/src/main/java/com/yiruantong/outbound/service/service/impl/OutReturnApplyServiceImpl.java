package com.yiruantong.outbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.service.bo.OutReturnApplyBo;
import com.yiruantong.outbound.domain.service.vo.OutReturnApplyVo;
import com.yiruantong.outbound.domain.service.OutReturnApply;
import com.yiruantong.outbound.mapper.service.OutReturnApplyMapper;
import com.yiruantong.outbound.service.service.IOutReturnApplyService;

/**
 * 出库退货申请单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class OutReturnApplyServiceImpl extends ServiceImplPlus<OutReturnApplyMapper, OutReturnApply, OutReturnApplyVo, OutReturnApplyBo> implements IOutReturnApplyService {
}
