package com.yiruantong.outbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.service.OutReturnApply;
import com.yiruantong.outbound.domain.service.vo.OutReturnApplyVo;
import com.yiruantong.outbound.domain.service.bo.OutReturnApplyBo;
import com.yiruantong.outbound.mapper.service.OutReturnApplyMapper;
import com.yiruantong.outbound.service.service.IOutReturnApplyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出库退货申请单
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/service/returnApply")
public class OutReturnApplyController extends AbstractController<OutReturnApplyMapper, OutReturnApply, OutReturnApplyVo, OutReturnApplyBo> {
}
