package com.yiruantong.basic.controller.product;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.product.BaseProductSecurityHistory;
import com.yiruantong.basic.domain.product.vo.BaseProductSecurityHistoryVo;
import com.yiruantong.basic.domain.product.bo.BaseProductSecurityHistoryBo;
import com.yiruantong.basic.mapper.product.BaseProductSecurityHistoryMapper;
import com.yiruantong.basic.service.product.IBaseProductSecurityHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 防伪码轨迹
 *
 * @author YRT
 * @date 2024-04-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/productSecurityHistory")
public class BaseProductSecurityHistoryController extends AbstractController<BaseProductSecurityHistoryMapper, BaseProductSecurityHistory, BaseProductSecurityHistoryVo, BaseProductSecurityHistoryBo> {
}
