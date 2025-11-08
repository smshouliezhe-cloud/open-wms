package com.yiruantong.basic.controller.base;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.base.BaseConsignorSales;
import com.yiruantong.basic.domain.base.vo.BaseConsignorSalesVo;
import com.yiruantong.basic.domain.base.bo.BaseConsignorSalesBo;
import com.yiruantong.basic.mapper.base.BaseConsignorSalesMapper;
import com.yiruantong.basic.service.base.IBaseConsignorSalesService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门店明细
 *
 * @author YRT
 * @date 2024-12-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/base/consignorSales")
public class BaseConsignorSalesController extends AbstractController<BaseConsignorSalesMapper, BaseConsignorSales, BaseConsignorSalesVo, BaseConsignorSalesBo> {
}
