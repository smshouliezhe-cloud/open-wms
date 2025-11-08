package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.TmsClientLine;
import com.yiruantong.basic.domain.tms.vo.TmsClientLineVo;
import com.yiruantong.basic.domain.tms.bo.TmsClientLineBo;
import com.yiruantong.basic.mapper.tms.TmsClientLineMapper;
import com.yiruantong.basic.service.tms.ITmsClientLineService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户线路规则
 *
 * @author YRT
 * @date 2024-03-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/clientLine")
public class TmsClientLineController extends AbstractController<TmsClientLineMapper, TmsClientLine, TmsClientLineVo, TmsClientLineBo> {
}
