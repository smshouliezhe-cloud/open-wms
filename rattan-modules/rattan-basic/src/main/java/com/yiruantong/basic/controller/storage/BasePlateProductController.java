package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlateProduct;
import com.yiruantong.basic.domain.storage.bo.BasePlateProductBo;
import com.yiruantong.basic.domain.storage.vo.BasePlateProductVo;
import com.yiruantong.basic.mapper.storage.BasePlateProductMapper;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品容器管理
 *
 * @author YRT
 * @date 2024-03-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/plateProduct")
public class BasePlateProductController extends AbstractController<BasePlateProductMapper, BasePlateProduct, BasePlateProductVo, BasePlateProductBo> {
}
