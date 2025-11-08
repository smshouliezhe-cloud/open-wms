package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.bo.BasePositionBo;
import com.yiruantong.basic.domain.storage.bo.BasePositionSearchBo;
import com.yiruantong.basic.domain.storage.bo.PositionConfigSaveBo;
import com.yiruantong.basic.domain.storage.vo.BasePositionVo;
import com.yiruantong.basic.mapper.storage.BasePositionMapper;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 货位管理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/position")
public class BasePositionController extends AbstractController<BasePositionMapper, BasePosition, BasePositionVo, BasePositionBo> {
  private final IBasePositionService basePositionService;

  /**
   * 查询货位
   *
   * @param positionSearchBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody BasePositionSearchBo positionSearchBo) {
    List<Map<String, Object>> list = basePositionService.getList(positionSearchBo);
    return R.ok(list);
  }

  /**
   * 查询货位
   *
   * @param positionSearchBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getPositionList")
  public R<List<Map<String, Object>>> getPositionList(@RequestBody BasePositionSearchBo positionSearchBo) {
    List<Map<String, Object>> list = basePositionService.getPositionList(positionSearchBo);
    return R.ok(list);
  }

  /* 获取库区信息
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectAreaCodeList")
  public R<List<Map<String, Object>>> selectAreaCodeList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = basePositionService.selectAreaCodeList(map);
    return R.ok(list);
  }

  /* 获取货架信息
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectShelveCodeList")
  public R<List<Map<String, Object>>> selectShelveCodeList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = basePositionService.selectShelveCodeList(map);
    return R.ok(list);
  }

  /**
   * 获取货架
   *
   * @param map 参数
   */
  @PostMapping("/getShelveCodes")
  public R<List<String>> getShelveCodes(@RequestBody Map<String, Object> map) {
    return basePositionService.getShelveCodes(map);
  }

  /**
   * 获取通道号
   *
   * @param map 参数
   */
  @PostMapping("/getChannelCodes")
  public R<List<String>> getChannelCodes(@RequestBody Map<String, Object> map) {
    return basePositionService.getChannelCodes(map);
  }

  //#regin

  /**
   * save-保存库区
   *
   * @param positionConfigSaveBo 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/saveArea")
  public R<Void> saveArea(@RequestBody PositionConfigSaveBo positionConfigSaveBo) {
    return basePositionService.saveArea(positionConfigSaveBo);
  }
  //#endregion

  //#regin

  /**
   * 锁定货位
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/lockPosition")
  public R<Void> lockPosition(@RequestBody Map<String, Object> map) {
    return basePositionService.lockPosition(map);
  }
  //#endregion

  //#regin

  /**
   * 解锁货位
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/unlockPosition")
  public R<Void> unlockPosition(@RequestBody Map<String, Object> map) {
    return basePositionService.unlockPosition(map);
  }
  //#endregion

  //#regin

  /**
   * 是否混放
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/isMixProductPosition")
  public R<Void> isMixProductPosition(@RequestBody Map<String, Object> map) {
    return basePositionService.isMixProductPosition(map);
  }
  //#endregion

  //#regin

  /**
   * 是否可用
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/enablePosition")
  public R<Void> enablePosition(@RequestBody Map<String, Object> map) {
    return basePositionService.enablePosition(map);
  }
  //#endregion

  //#regin

  /**
   * 最低库存
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/submitminCapacity")
  public R<Void> submitminCapacity(@RequestBody Map<String, Object> map) {
    return basePositionService.submitminCapacity(map);
  }
  //#endregion

  //#regin

  /**
   * loadPositionData 根据仓库和货位获得货位数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/loadPositionData")
  public R<List<Map<String, Object>>> loadPositionData(@RequestBody Map<String, Object> map) {
    return basePositionService.loadPositionData(map);
  }
  //#endregion

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BasePositionBo bo) {
    return basePositionService.add(bo);
  }
}
