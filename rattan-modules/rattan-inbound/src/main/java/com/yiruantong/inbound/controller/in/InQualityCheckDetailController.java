package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InQualityCheckDetail;
import com.yiruantong.inbound.domain.in.vo.InQualityCheckDetailVo;
import com.yiruantong.inbound.domain.in.bo.InQualityCheckDetailBo;
import com.yiruantong.inbound.mapper.in.InQualityCheckDetailMapper;
import com.yiruantong.inbound.service.in.IInQualityCheckDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 质检管理明细
 *
 * @author YiRuanTong
 * @date 2023-10-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/qualityCheckDetail")
public class InQualityCheckDetailController extends AbstractController<InQualityCheckDetailMapper, InQualityCheckDetail, InQualityCheckDetailVo, InQualityCheckDetailBo> {
}
