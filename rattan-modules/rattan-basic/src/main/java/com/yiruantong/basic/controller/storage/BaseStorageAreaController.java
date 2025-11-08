package com.yiruantong.basic.controller.storage;

import cn.hutool.core.convert.Convert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BaseStorageArea;
import com.yiruantong.basic.domain.storage.bo.BaseStorageAreaBo;
import com.yiruantong.basic.domain.storage.bo.SvgBo;
import com.yiruantong.basic.domain.storage.vo.BaseStorageAreaVo;
import com.yiruantong.basic.mapper.storage.BaseStorageAreaMapper;
import com.yiruantong.basic.service.storage.IBaseStorageAreaService;
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
 * 库区管理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/storageArea")
public class BaseStorageAreaController extends AbstractController<BaseStorageAreaMapper, BaseStorageArea, BaseStorageAreaVo, BaseStorageAreaBo> {
  private final IBaseStorageAreaService baseStorageAreaService;

  /**
   * 获取库区
   *
   * @param map 参数
   */
  @PostMapping("/getAreaCodes")
  public R<List<String>> getAreaCodes(@RequestBody Map<String, Object> map) {
    return baseStorageAreaService.getAreaCodes(map);
  }

  @PostMapping("/getAreaList")
  public R<List<BaseStorageArea>> getAreaList(@RequestBody Map<String, Object> map) {
    return baseStorageAreaService.getAreaList(map);
  }

  /**
   * 获取库区数据
   *
   * @param map 参数
   */
  @PostMapping("/getStorageAreaInfo")
  public R<BaseStorageArea> getStorageAreaInfo(@RequestBody Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    String areaCode = Convert.toStr(map.get("areaCode"));
    BaseStorageArea baseStorageArea = baseStorageAreaService.getStorageAreaInfo(storageId, areaCode);
    return R.ok(baseStorageArea);
  }

  /**
   * 加载货架数据
   *
   * @param map 参数
   */
  @PostMapping("/loadShelveList")
  public R<BaseStorageArea> loadShelveList(@RequestBody Map<String, Object> map) {
    Long storageId = Convert.toLong(map.get("storageId"));
    String areaCode = Convert.toStr(map.get("areaCode"));
    BaseStorageArea baseStorageArea = baseStorageAreaService.loadShelveList(storageId, areaCode);
    return R.ok(baseStorageArea);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BaseStorageAreaBo bo) {
    return baseStorageAreaService.add(bo);
  }


  @PostMapping("/saveSvg")
  public R<Void> saveSvg(@RequestBody SvgBo svg) {
    return baseStorageAreaService.saveSvg(svg);
  }
}
