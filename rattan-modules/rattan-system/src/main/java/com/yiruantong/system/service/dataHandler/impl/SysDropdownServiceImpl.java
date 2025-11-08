package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.dataHandler.SysDropdown;
import com.yiruantong.system.domain.dataHandler.SysParamValue;
import com.yiruantong.system.domain.dataHandler.bo.SysDropdownBo;
import com.yiruantong.system.domain.dataHandler.vo.SysDropdownVo;
import com.yiruantong.system.mapper.dataHandler.SysDropdownMapper;
import com.yiruantong.system.service.dataHandler.ISysDropdownService;
import com.yiruantong.system.service.dataHandler.ISysParamValueService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 下拉框设置Service业务层处理
 *
 * @author YRT
 * @date 2023-07-27
 */
@RequiredArgsConstructor
@Service
public class SysDropdownServiceImpl extends ServiceImplPlus<SysDropdownMapper, SysDropdown, SysDropdownVo, SysDropdownBo> implements ISysDropdownService {

  private final ISysParamValueService sysParamValueService;

  //#region loadDropDown
  @Override
  public R<Map<String, Object>> loadDropDown(Map<String, Object> map) {
    Assert.isFalse(ObjectUtil.isEmpty(map.get("typeId")), "id为空");

    LambdaQueryWrapper<SysParamValue> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysParamValue::getTypeId, map.get("typeId"));

    List<SysParamValue> list = sysParamValueService.list(queryWrapper);
    Map<String, Object> result = new HashMap<>();
    result.put("dataList", list);
    return R.ok(result);
  }
  //#endregion

  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<SysDropdownBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    SysDropdown sysDropdown = this.getById(saveEditorBo.getIdValue());

    // 保存主表
    sysDropdown.setDropdownId(null);
    this.save(sysDropdown);

    return R.ok("复制成功");
  }

  @Override
  public R<List<Map<String, Object>>> searchTree(String filterText) {
    com.baomidou.mybatisplus.core.toolkit.Assert.isFalse(StringUtils.isEmpty(filterText), "搜索关键词不能为空");

    LambdaQueryWrapper<SysDropdown> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper.like(SysDropdown::getCnName, filterText).last("limit 10");
    List<SysDropdown> dropdownList = this.list(menuLambdaQueryWrapper);

    List<Map<String, Object>> mapList = new ArrayList<>();
    for (var dropdown : dropdownList) {
      String menuName = dropdown.getCnName();
      Function<Long, String> parentNode = getParentNode();
      String parentNameAll = parentNode.apply(dropdown.getParentId());
      parentNameAll = parentNameAll + "&gt;" + menuName;

      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("dropdownId", dropdown.getDropdownId());
      resultMap.put("cnName", dropdown.getCnName());
      resultMap.put("parentNameAll", parentNameAll);
      mapList.add(resultMap);
    }


    return R.ok(mapList);
  }

  @Override
  public R<Map<String, Object>> loadDropDownById(Long id) {
    Assert.isFalse(ObjectUtil.isNull(id), "下拉框id为空");

    LambdaQueryWrapper<SysParamValue> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysParamValue::getTypeId, id);

    List<SysParamValue> list = sysParamValueService.list(queryWrapper);
    Map<String, Object> result = new HashMap<>();
    result.put("dataList", list);
    return R.ok(result);
  }

  @NotNull
  private Function<Long, String> getParentNode() {
    var the = this;
    return new Function<>() {
      final Function<Long, String> self = this; // 使用局部变量引用

      /**
       * Function构造函数
       *
       * @param parentId    父级ID
       */
      @Override
      public String apply(Long parentId) {
        LambdaQueryWrapper<SysDropdown> dropdownLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dropdownLambdaQueryWrapper.eq(SysDropdown::getDropdownId, parentId);
        SysDropdown sysDropdown = the.getById(parentId);
        String parentName = StringUtils.EMPTY;
        if (ObjectUtil.isNotNull(sysDropdown)) {
          String _parentName = self.apply(sysDropdown.getParentId()); // 递归获取父级菜单
          parentName = _parentName + (StringUtils.isNotEmpty(_parentName) ? "&gt;" : "") + sysDropdown.getCnName();
        }
        return parentName;
      }
    };
  }
}
