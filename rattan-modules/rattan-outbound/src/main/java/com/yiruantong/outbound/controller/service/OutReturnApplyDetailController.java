package com.yiruantong.outbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.service.OutReturnApplyDetail;
import com.yiruantong.outbound.domain.service.vo.OutReturnApplyDetailVo;
import com.yiruantong.outbound.domain.service.bo.OutReturnApplyDetailBo;
import com.yiruantong.outbound.mapper.service.OutReturnApplyDetailMapper;
import com.yiruantong.outbound.service.service.IOutReturnApplyDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出库退货申请单明细
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/service/returnApplyDetail")
public class OutReturnApplyDetailController extends AbstractController<OutReturnApplyDetailMapper, OutReturnApplyDetail, OutReturnApplyDetailVo, OutReturnApplyDetailBo> {
}
