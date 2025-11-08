package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.BaseTrailer;
import com.yiruantong.basic.domain.tms.vo.BaseTrailerVo;
import com.yiruantong.basic.domain.tms.bo.BaseTrailerBo;
import com.yiruantong.basic.mapper.tms.BaseTrailerMapper;
import com.yiruantong.basic.service.tms.IBaseTrailerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 挂车管理
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/trailer")
public class BaseTrailerController extends AbstractController<BaseTrailerMapper, BaseTrailer, BaseTrailerVo, BaseTrailerBo> {
}
