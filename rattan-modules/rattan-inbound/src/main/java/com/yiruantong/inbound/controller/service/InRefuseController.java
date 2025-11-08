package com.yiruantong.inbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.service.InRefuse;
import com.yiruantong.inbound.domain.service.vo.InRefuseVo;
import com.yiruantong.inbound.domain.service.bo.InRefuseBo;
import com.yiruantong.inbound.mapper.service.InRefuseMapper;
import com.yiruantong.inbound.service.service.IInRefuseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 拒收单
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/service/refuse")
public class InRefuseController extends AbstractController<InRefuseMapper, InRefuse, InRefuseVo, InRefuseBo> {
}
