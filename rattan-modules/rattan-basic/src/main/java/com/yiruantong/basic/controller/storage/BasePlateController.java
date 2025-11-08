package com.yiruantong.basic.controller.storage;

import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.basic.domain.storage.api.ApiBasePlateBo;
import com.yiruantong.basic.domain.storage.bo.BasePlateBo;
import com.yiruantong.basic.domain.storage.bo.BasePlateListBo;
import com.yiruantong.basic.domain.storage.vo.BasePlateVo;
import com.yiruantong.basic.mapper.storage.BasePlateMapper;
import com.yiruantong.basic.service.storage.IBasePlateService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.system.domain.dataHandler.vo.SysParamValueVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 容器管理
 *
 * @author YiRuanTong
 * @date 2023-10-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/storage/plate")
public class BasePlateController extends AbstractController<BasePlateMapper, BasePlate, BasePlateVo, BasePlateBo> {
  private final IBasePlateService basePlateService;

  /**
   * 生成容器号
   *
   * @param basePlateListBo 前端传递参数
   * @return
   */
  @PostMapping("/createPlantCode")
  public R<Void> createPlantCode(@RequestBody BasePlateListBo basePlateListBo) {
    return basePlateService.createPlantCode(basePlateListBo);
  }

  /**
   * 查询容器规格
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getPlateSpec")
  public R<List<Map<String, Object>>> getPlateSpec(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> basePlateList = basePlateService.getPlateSpec(map);
    return R.ok(basePlateList);
  }

  /**
   * 查询容器类型
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getPlateType")
  public R<List<SysParamValueVo>> getPlateType(@RequestBody Map<String, Object> map) {
    List<SysParamValueVo> baseTypeList = basePlateService.getPlateType(map);
    return R.ok(baseTypeList);
  }

  /**
   * 查询容器重量体积
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getWeightCube")
  public R<BasePlate> getWeightCube(@RequestBody Map<String, Object> map) {
    BasePlate weightCubeInfo = basePlateService.getWeightCube(map);
    return R.ok(weightCubeInfo);
  }


  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiBasePlateBo bo) {
    return basePlateService.add(bo);
  }

}
