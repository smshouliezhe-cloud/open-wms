package com.yiruantong.composite.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.out.IOutReturnsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/out/return")
class OutReturnsController extends BaseController {

  private final IOutReturnsService outReturnsService;

  //#region 生成预到货单

  /**
   * 生成预到货单
   *
   * @param map 数据集合
   * @return 提示信息
   */
  @PostMapping(value = "/toInOrder")
  public R<Void> toInOrder(@RequestBody Map<String, Object> map) {
    return outReturnsService.toInOrder(map);
  }
  //#endregion

  //#region 确认入库

  /**
   * 确认入库
   *
   * @param ids 数据集合
   * @return 提示信息
   */
  @PostMapping(value = "/saveCheck")
  public R<Void> saveCheck(@RequestBody List<Long> ids) {
    return outReturnsService.saveCheck(ids);
  }
  //#endregion
}
