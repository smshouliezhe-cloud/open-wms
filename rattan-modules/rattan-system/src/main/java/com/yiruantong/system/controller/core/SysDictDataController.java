package com.yiruantong.system.controller.core;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.system.domain.core.bo.SysDictDataBo;
import com.yiruantong.system.domain.core.vo.SysDictDataVo;
import com.yiruantong.system.service.core.ISysDictDataService;
import com.yiruantong.system.service.core.ISysDictTypeService;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.excel.utils.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/core/dictData")
public class SysDictDataController extends BaseController {

  private final ISysDictDataService dictDataService;
  private final ISysDictTypeService dictTypeService;

  /**
   * 查询字典数据列表
   */
  @SaCheckPermission("system:dict:list")
  @GetMapping("/list")
  public TableDataInfo<SysDictDataVo> list(SysDictDataBo dictData, PageQuery pageQuery) {
    return dictDataService.selectPageDictDataList(dictData, pageQuery);
  }

  /**
   * 导出字典数据列表
   */
  @Log(title = "字典数据", businessType = BusinessType.EXPORT)
  @SaCheckPermission("system:dict:export")
  @PostMapping("/export")
  public void export(SysDictDataBo dictData, HttpServletResponse response) {
    List<SysDictDataVo> list = dictDataService.selectDictDataList(dictData);
    ExcelUtil.exportExcel(list, "字典数据", SysDictDataVo.class, response);
  }

  /**
   * 查询字典数据详细
   *
   * @param dictCode 字典code
   */
  @SaCheckPermission("system:dict:query")
  @GetMapping(value = "/{dictCode}")
  public R<SysDictDataVo> getInfo(@PathVariable Long dictCode) {
    return R.ok(dictDataService.selectDictDataById(dictCode));
  }

  /**
   * 根据字典类型查询字典数据信息
   *
   * @param dictType 字典类型
   */
  @GetMapping(value = "/type/{dictType}")
  public R<List<SysDictDataVo>> dictType(@PathVariable String dictType) {
    List<SysDictDataVo> data = dictTypeService.selectDictDataByType(dictType);
    if (ObjectUtil.isNull(data)) {
      data = new ArrayList<>();
    }
    return R.ok(data);
  }

  /**
   * 新增字典类型
   */
  @SaCheckPermission("system:dict:add")
  @Log(title = "字典数据", businessType = BusinessType.INSERT)
  @PostMapping
  public R<Void> add(@Validated @RequestBody SysDictDataBo dict) {
    dictDataService.insertDictData(dict);
    return R.ok();
  }

  /**
   * 修改保存字典类型
   */
  @SaCheckPermission("system:dict:edit")
  @Log(title = "字典数据", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> edit(@Validated @RequestBody SysDictDataBo dict) {
    dictDataService.updateDictData(dict);
    return R.ok();
  }

  /**
   * 删除字典类型
   *
   * @param dictCodes 字典code串
   */
  @SaCheckPermission("system:dict:remove")
  @Log(title = "字典类型", businessType = BusinessType.DELETE)
  @DeleteMapping("/{dictCodes}")
  public R<Void> remove(@PathVariable Long[] dictCodes) {
    dictDataService.deleteDictDataByIds(dictCodes);
    return R.ok();
  }
}
