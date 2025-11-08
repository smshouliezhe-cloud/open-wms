package com.yiruantong.basic.controller.product;

import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.product.bo.BaseProductBo;
import com.yiruantong.basic.domain.product.vo.BaseProductVo;
import com.yiruantong.basic.mapper.product.BaseProductMapper;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品基础信息
 *
 * @author YRT
 * @date 2023-10-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/product/product")
public class BaseProductController extends AbstractController<BaseProductMapper, BaseProduct, BaseProductVo, BaseProductBo> {
  private final IBaseProductService baseProductService;

  /**
   * 查询商品信息
   *
   * @param getListBo
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = baseProductService.getList(getListBo);
    return R.ok(list);
  }

  //#region 审核

  /**
   * 审核
   *
   * @param ids 审核参数
   * @return
   */
  @PostMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return baseProductService.multiAuditing(ids);
  }
  //#endregion

  //#region 反审

  /**
   * 反审
   *
   * @param ids
   * @return
   */
  @PostMapping("/reAudit/{ids}")
  public R<Void> reAudit(@PathVariable Long[] ids) {
    return baseProductService.reAudit(ids);
  }
  //#endregion


  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BaseProductBo bo) {
    return baseProductService.add(bo);
  }
}
