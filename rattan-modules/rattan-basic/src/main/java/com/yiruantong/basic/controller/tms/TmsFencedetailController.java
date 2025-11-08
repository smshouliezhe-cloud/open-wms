package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.TmsFencedetail;
import com.yiruantong.basic.domain.tms.vo.TmsFencedetailVo;
import com.yiruantong.basic.domain.tms.bo.TmsFencedetailBo;
import com.yiruantong.basic.mapper.tms.TmsFencedetailMapper;
import com.yiruantong.basic.service.tms.ITmsFencedetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 围栏管理明细
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/fenceDetail")
public class TmsFencedetailController extends AbstractController<TmsFencedetailMapper, TmsFencedetail, TmsFencedetailVo, TmsFencedetailBo> {
}
