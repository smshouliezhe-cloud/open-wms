package com.yiruantong.basic.controller.base;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.base.BasePark;
import com.yiruantong.basic.domain.base.vo.BaseParkVo;
import com.yiruantong.basic.domain.base.bo.BaseParkBo;
import com.yiruantong.basic.mapper.base.BaseParkMapper;
import com.yiruantong.basic.service.base.IBaseParkService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物流园区
 *
 * @author YRT
 * @date 2024-03-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/base/park")
public class BaseParkController extends AbstractController<BaseParkMapper, BasePark, BaseParkVo, BaseParkBo> {
}
