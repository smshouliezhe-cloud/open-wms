package com.yiruantong.composite.controller.bigScreen;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.bigScreen.HomeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * PC首页大屏
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/bigScreen/home")
public class HomeController extends BaseController {
  private final HomeService homeService;

  /**
   * 查询库存预警
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/inventoryAlert")
  public R<Map<String, Object>> inventoryAlert(@RequestBody Map<String, Object> map) {
    return homeService.inventoryAlert(map);
  }

  /**
   * 查询库存状况
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/inventoryStatus")
  public R<Map<String, Object>> inventoryStatus(@RequestBody Map<String, Object> map) {
    return homeService.inventoryStatus(map);
  }

  /**
   * 查询库存金额占比（饼图）
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/inventoryAmount")
  public R<Map<String, Object>> inventoryAmount(@RequestBody Map<String, Object> map) {
    return homeService.inventoryAmount(map);
  }

  /**
   * 仓库使用状况
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/storageStatus")
  public R<Map<String, Object>> storageStatus(@RequestBody Map<String, Object> map) {
    return homeService.storageStatus(map);
  }

  /**
   * 货位库存统计
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/inventoryStatistics")
  public R<Map<String, Object>> inventoryStatistics(@RequestBody Map<String, Object> map) {
    return homeService.inventoryStatistics(map);
  }
  /**
   * 库存商品类别
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/inventoryProductType")
  public R<Map<String, Object>> inventoryProductType(@RequestBody Map<String, Object> map) {
    return homeService.inventoryProductType(map);
  }
}
