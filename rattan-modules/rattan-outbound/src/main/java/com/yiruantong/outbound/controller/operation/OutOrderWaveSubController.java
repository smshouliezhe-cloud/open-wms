package com.yiruantong.outbound.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.operation.OutOrderWaveSub;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveSubVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveSubBo;
import com.yiruantong.outbound.mapper.operation.OutOrderWaveSubMapper;
import com.yiruantong.outbound.service.operation.IOutOrderWaveSubService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 子波次
 *
 * @author YRT
 * @date 2024-08-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/operation/orderWaveSub")
public class OutOrderWaveSubController extends AbstractController<OutOrderWaveSubMapper, OutOrderWaveSub, OutOrderWaveSubVo, OutOrderWaveSubBo> {
}
