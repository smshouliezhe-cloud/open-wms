package com.yiruantong.composite.controller.basic;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.vo.BaseProductVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.basic.IBaseProductCompositeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品信息操作
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/basic/baseProductComposite")
class BaseProductCompositeController extends BaseController {
  private final IBaseProductCompositeService baseProductCompositeService;

  /**
   * 删除前事件
   *
   * @param ids 日志ids
   */
  @Log(title = "删除数据", businessType = BusinessType.DELETE)
  @DeleteMapping("remove/{ids}")
  public R<Void> remove(@PathVariable Long[] ids) {
    return toAjax(baseProductCompositeService.deleteByIds(ids));
  }


  /**
   * APP查询 商品信息
   *
   * @param pageQuery 条件
   */
  @PostMapping("/getProductList")
  public TableDataInfo<BaseProductVo> getProductList(@RequestBody PageQuery pageQuery) {
    return baseProductCompositeService.getProductList(pageQuery);
  }
}
