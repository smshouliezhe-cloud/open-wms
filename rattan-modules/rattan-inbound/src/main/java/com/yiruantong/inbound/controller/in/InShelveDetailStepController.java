package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InShelveDetailStep;
import com.yiruantong.inbound.domain.in.vo.InShelveDetailStepVo;
import com.yiruantong.inbound.domain.in.bo.InShelveDetailStepBo;
import com.yiruantong.inbound.mapper.in.InShelveDetailStepMapper;
import com.yiruantong.inbound.service.in.IInShelveDetailStepService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品上架明细的明细
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/shelveDetailStep")
public class InShelveDetailStepController extends AbstractController<InShelveDetailStepMapper, InShelveDetailStep, InShelveDetailStepVo, InShelveDetailStepBo> {
}
