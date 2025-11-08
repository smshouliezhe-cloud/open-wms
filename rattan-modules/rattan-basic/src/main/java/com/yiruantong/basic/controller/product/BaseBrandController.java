package com.yiruantong.basic.controller.product;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.basic.domain.product.BaseBrand;
import com.yiruantong.basic.domain.product.vo.BaseBrandVo;
import com.yiruantong.basic.domain.product.bo.BaseBrandBo;
import com.yiruantong.basic.mapper.product.BaseBrandMapper;
import com.yiruantong.basic.service.product.IBaseBrandService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌管理
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/brand")
public class BaseBrandController extends AbstractController<BaseBrandMapper, BaseBrand, BaseBrandVo, BaseBrandBo> {
}
