package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.domain.storage.bo.BaseStorageBo;
import com.yiruantong.basic.domain.storage.vo.BaseStorageVo;
import com.yiruantong.basic.mapper.storage.BaseStorageMapper;
import com.yiruantong.basic.service.storage.IBaseStorageService;
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
 * 仓库管理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/storage")
public class BaseStorageController extends AbstractController<BaseStorageMapper, BaseStorage, BaseStorageVo, BaseStorageBo> {
  private final IBaseStorageService baseStorageService;

  /**
   * 查询仓库
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = baseStorageService.getList(map);
    return R.ok(list);
  }

  /**
   * 根据仓库名称，带出目的地值（所属网点）
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/changePlaceDestination")
  public R<BaseStorage> changePlaceDestination(@RequestBody Map<String, Object> map) {
    BaseStorage weightCubeInfo = baseStorageService.changePlaceDestination(map);
    return R.ok(weightCubeInfo);
  }


  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BaseStorageBo bo) {
    return baseStorageService.add(bo);
  }
}
