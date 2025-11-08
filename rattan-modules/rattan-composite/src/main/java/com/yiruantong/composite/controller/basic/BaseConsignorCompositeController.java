package com.yiruantong.composite.controller.basic;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.bo.LevelSettingBo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.basic.IBaseConsignorCompositeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 货主信息操作
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/basic/baseConsignorComposite")
class BaseConsignorCompositeController extends BaseController {
  private final IBaseConsignorCompositeService baseConsignorCompositeService;

  /**
   * 删除前事件
   *
   * @param ids 日志ids
   */
  @Log(title = "删除数据", businessType = BusinessType.DELETE)
  @DeleteMapping("remove/{ids}")
  public R<Void> remove(@PathVariable Long[] ids) {
    return toAjax(baseConsignorCompositeService.deleteByIds(ids));
  }


  /**
   * 销售等级设置
   *
   * @param bo 等级设置
   */
  @PostMapping("/saveGrade")
  public R<Map<String, Object>> saveGrade(@RequestBody LevelSettingBo bo) {
    return baseConsignorCompositeService.saveGrade(bo);
  }

  /**
   * 获取销售等级
   */
  @PostMapping("/getGrade")
  public R<Map<String, Object>> getGrade() {
    return baseConsignorCompositeService.getGrade();
  }

  /**
   * 销售等级匹配
   *
   * @param loginUser 等级设置
   */
  @PostMapping(value = "/matchGrade")
  public R<Void> matchGrade(LoginUser loginUser) {
    try {
      baseConsignorCompositeService.matchGrade(loginUser);
    } catch (Exception e) {
      return R.fail("执行错误：" + e.getMessage());
    }
    return R.ok("开始执行...");
  }
}
