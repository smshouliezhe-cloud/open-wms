package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.TmsFence;
import com.yiruantong.basic.domain.tms.vo.TmsFenceVo;
import com.yiruantong.basic.domain.tms.bo.TmsFenceBo;
import com.yiruantong.basic.mapper.tms.TmsFenceMapper;
import com.yiruantong.basic.service.tms.ITmsFenceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 围栏管理
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/fence")
public class TmsFenceController extends AbstractController<TmsFenceMapper, TmsFence, TmsFenceVo, TmsFenceBo> {
}
