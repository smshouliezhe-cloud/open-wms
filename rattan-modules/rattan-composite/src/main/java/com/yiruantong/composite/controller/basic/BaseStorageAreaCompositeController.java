package com.yiruantong.composite.controller.basic;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.basic.IBaseStorageAreaCompositeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库区操作
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/basic/baseStorageAreaComposite")
class BaseStorageAreaCompositeController extends BaseController {
  private final IBaseStorageAreaCompositeService baseStorageAreaCompositeService;

  /**
   * 删除前事件
   *
   * @param ids 日志ids
   */
  @Log(title = "删除数据", businessType = BusinessType.DELETE)
  @DeleteMapping("remove/{ids}")
  public R<Void> remove(@PathVariable Long[] ids) {
    return toAjax(baseStorageAreaCompositeService.deleteByIds(ids));
  }
}
