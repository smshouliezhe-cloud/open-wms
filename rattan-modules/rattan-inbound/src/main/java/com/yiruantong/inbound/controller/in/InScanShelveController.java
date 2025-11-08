package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.vo.BasePositionVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.service.in.IInScanShelveService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 入库扫描
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/inScanShelve")
public class InScanShelveController extends BaseController {
  private final IInScanShelveService inScanShelveService;

  /**
   * 常规扫描上架 - 获取扫描上架数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getShelveData")
  public R<List<Map<String, Object>>> getShelveData(@RequestBody Map<String, Object> map) {
    return inScanShelveService.getShelveData(map);
  }

  /**
   * 常规扫描上架 - 获取上架货位列表
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getShelvePositionList")
  public R<List<Map<String, Object>>> getShelvePositionList(@RequestBody Map<String, Object> map) {
    return inScanShelveService.getShelvePositionList(map);
  }

  /**
   * 常规扫描上架 - 获取上架货位列表
   *
   * @param inScanOrderBo 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/shelveSave")
  public R<Void> shelveSave(@RequestBody InScanOrderBo inScanOrderBo) {
    return inScanShelveService.shelveSave(inScanOrderBo);
  }

  /**
   * 获取单号筛选上架货位
   *
   * @param map
   * @return 返回实体商品信息
   */
  @PostMapping("/searchShelvePositionList")
  public R<List<BasePositionVo>> searchShelvePositionList(@RequestBody Map<String, Object> map) {
    return R.ok(inScanShelveService.searchShelvePositionList(map));
  }

  /**
   * 无单扫描入库 - 获取数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getShelveNoBillData")
  public R<List<Map<String, Object>>> getShelveNoBillData(@RequestBody Map<String, Object> map) {
    return inScanShelveService.getShelveNoBillData(map);
  }


}
