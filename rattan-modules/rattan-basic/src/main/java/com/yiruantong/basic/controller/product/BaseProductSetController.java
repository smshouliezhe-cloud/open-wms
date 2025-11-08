package com.yiruantong.basic.controller.product;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.product.BaseProductSet;
import com.yiruantong.basic.domain.product.vo.BaseProductSetVo;
import com.yiruantong.basic.domain.product.bo.BaseProductSetBo;
import com.yiruantong.basic.mapper.product.BaseProductSetMapper;
import com.yiruantong.basic.service.product.IBaseProductSetService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品套装主
 *
 * @author YRT
 * @date 2023-12-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/productSet")
public class BaseProductSetController extends AbstractController<BaseProductSetMapper, BaseProductSet, BaseProductSetVo, BaseProductSetBo> {
}
