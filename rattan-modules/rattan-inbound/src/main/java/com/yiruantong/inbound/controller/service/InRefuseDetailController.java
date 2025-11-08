package com.yiruantong.inbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.service.InRefuseDetail;
import com.yiruantong.inbound.domain.service.vo.InRefuseDetailVo;
import com.yiruantong.inbound.domain.service.bo.InRefuseDetailBo;
import com.yiruantong.inbound.mapper.service.InRefuseDetailMapper;
import com.yiruantong.inbound.service.service.IInRefuseDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 拒收单明细
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/service/refuseDetail")
public class InRefuseDetailController extends AbstractController<InRefuseDetailMapper, InRefuseDetail, InRefuseDetailVo, InRefuseDetailBo> {
}
