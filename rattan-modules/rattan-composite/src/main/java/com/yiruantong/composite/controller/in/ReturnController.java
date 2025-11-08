package com.yiruantong.composite.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.in.IReturnService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/in/return")
class ReturnController extends BaseController {

  private final  IReturnService returnService;
  /**
   * 确认出库
   *
   * @param map 参数
   * @return 返回结果
   */
  @PostMapping("/toOutOrder")
  public R<Void> toOutOrder(@RequestBody Map<String, Object> map) {
    return returnService.toOutOrder(map);
  }


}
