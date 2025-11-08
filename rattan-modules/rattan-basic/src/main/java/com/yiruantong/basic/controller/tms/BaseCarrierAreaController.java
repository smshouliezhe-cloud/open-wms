package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.BaseCarrierArea;
import com.yiruantong.basic.domain.tms.vo.BaseCarrierAreaVo;
import com.yiruantong.basic.domain.tms.bo.BaseCarrierAreaBo;
import com.yiruantong.basic.mapper.tms.BaseCarrierAreaMapper;
import com.yiruantong.basic.service.tms.IBaseCarrierAreaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 承运商管辖区域
 *
 * @author YRT
 * @date 2025-02-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/carrierArea")
public class BaseCarrierAreaController extends AbstractController<BaseCarrierAreaMapper, BaseCarrierArea, BaseCarrierAreaVo, BaseCarrierAreaBo> {
}
