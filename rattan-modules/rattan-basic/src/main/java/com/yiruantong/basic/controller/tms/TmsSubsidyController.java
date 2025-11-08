package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.TmsSubsidy;
import com.yiruantong.basic.domain.tms.vo.TmsSubsidyVo;
import com.yiruantong.basic.domain.tms.bo.TmsSubsidyBo;
import com.yiruantong.basic.mapper.tms.TmsSubsidyMapper;
import com.yiruantong.basic.service.tms.ITmsSubsidyService;
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
@RequestMapping("/basic/tms/subsidy")
public class TmsSubsidyController extends AbstractController<TmsSubsidyMapper, TmsSubsidy, TmsSubsidyVo, TmsSubsidyBo> {
}
