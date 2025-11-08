package com.yiruantong.basic.controller.tms;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.tms.TmsClientLineDetail;
import com.yiruantong.basic.domain.tms.vo.TmsClientLineDetailVo;
import com.yiruantong.basic.domain.tms.bo.TmsClientLineDetailBo;
import com.yiruantong.basic.mapper.tms.TmsClientLineDetailMapper;
import com.yiruantong.basic.service.tms.ITmsClientLineDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户线路关系明细
 *
 * @author YRT
 * @date 2024-03-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/clientLineDetail")
public class TmsClientLineDetailController extends AbstractController<TmsClientLineDetailMapper, TmsClientLineDetail, TmsClientLineDetailVo, TmsClientLineDetailBo> {
}
