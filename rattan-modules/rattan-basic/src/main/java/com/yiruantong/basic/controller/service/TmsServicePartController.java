package com.yiruantong.basic.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.service.TmsServicePart;
import com.yiruantong.basic.domain.service.vo.TmsServicePartVo;
import com.yiruantong.basic.domain.service.bo.TmsServicePartBo;
import com.yiruantong.basic.mapper.service.TmsServicePartMapper;
import com.yiruantong.basic.service.service.ITmsServicePartService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 维修配件管理
 *
 * @author YRT
 * @date 2024-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/service/servicePart")
public class TmsServicePartController extends AbstractController<TmsServicePartMapper, TmsServicePart, TmsServicePartVo, TmsServicePartBo> {
}
