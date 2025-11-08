package com.yiruantong.basic.controller.product;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.product.BaseProductSetDetail;
import com.yiruantong.basic.domain.product.vo.BaseProductSetDetailVo;
import com.yiruantong.basic.domain.product.bo.BaseProductSetDetailBo;
import com.yiruantong.basic.mapper.product.BaseProductSetDetailMapper;
import com.yiruantong.basic.service.product.IBaseProductSetDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品套装明细
 *
 * @author YRT
 * @date 2023-12-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/productSetDetail")
public class BaseProductSetDetailController extends AbstractController<BaseProductSetDetailMapper, BaseProductSetDetail, BaseProductSetDetailVo, BaseProductSetDetailBo> {
}
