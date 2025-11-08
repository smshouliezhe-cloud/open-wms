package com.yiruantong.composite.controller.bigScreen;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.bigScreen.InService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 入库大屏
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/bigScreen/in")
public class InController extends BaseController {
  private final InService inService;
  /**
   * 查询入库统计
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/inOrderStat")
  public R<Map<String, Object>> inOrderStat(@RequestBody Map<String, Object> map) {
    return inService.inOrderStat(map);
  }

}
