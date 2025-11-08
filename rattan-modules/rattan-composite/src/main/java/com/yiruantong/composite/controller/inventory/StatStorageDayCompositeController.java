package com.yiruantong.composite.controller.inventory;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.redis.utils.QueueUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.inventory.IInventoryService;
import com.yiruantong.composite.service.inventory.IStatStorageDayCompositeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/statStorageDayComposite")
public class StatStorageDayCompositeController extends BaseController {

  private final IStatStorageDayCompositeService statStorageDayCompositeService;

  //#region 生成当天库存快照
  /**
   * 生成当天库存快照
   * @return 提示信息
   */
  @PostMapping(value = "/currentDayStorage")
  public R<Void> currentDayStorage(HttpServletRequest request) {
    try {
      statStorageDayCompositeService.currentDayStorage(request, LoginHelper.getLoginUser());
    } catch (Exception e) {
      return R.fail("执行错误：" + e.getMessage());
    }
    return R.ok("开始执行...");
  }
  //#endregion
}
