package com.yiruantong.basic.controller.product;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProductType;
import com.yiruantong.basic.domain.product.bo.BaseProductTypeBo;
import com.yiruantong.basic.domain.product.vo.BaseProductTypeVo;
import com.yiruantong.basic.mapper.product.BaseProductTypeMapper;
import com.yiruantong.basic.service.product.IBaseProductTypeService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 商品类目管理
 *
 * @author YiRuanTong
 * @date 2023-10-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/productType")
public class BaseProductTypeController extends AbstractController<BaseProductTypeMapper, BaseProductType, BaseProductTypeVo, BaseProductTypeBo> {
  private final IBaseProductTypeService baseProductTypeService;

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BaseProductTypeBo bo) {
    return baseProductTypeService.add(bo);
  }
}
