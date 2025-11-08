package com.yiruantong.outbound.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveDetailBo;
import com.yiruantong.outbound.mapper.operation.OutOrderWaveDetailMapper;
import com.yiruantong.outbound.service.operation.IOutOrderWaveDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出库单波次明细
 *
 * @author YRT
 * @date 2023-11-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/operation/orderWaveDetail")
public class OutOrderWaveDetailController extends AbstractController<OutOrderWaveDetailMapper, OutOrderWaveDetail, OutOrderWaveDetailVo, OutOrderWaveDetailBo> {
}
