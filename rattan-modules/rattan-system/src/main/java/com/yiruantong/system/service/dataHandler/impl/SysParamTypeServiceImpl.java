package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.yiruantong.system.domain.dataHandler.SysParamType;
import com.yiruantong.system.domain.dataHandler.bo.SysParamTypeBo;
import com.yiruantong.system.domain.dataHandler.vo.SysParamTypeVo;
import com.yiruantong.system.mapper.dataHandler.SysParamTypeMapper;
import com.yiruantong.system.service.dataHandler.ISysParamTypeService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.StringUtils;
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
 * @date 2023-07-28
 */
@RequiredArgsConstructor
@Service
public class SysParamTypeServiceImpl extends ServiceImplPlus<SysParamTypeMapper, SysParamType, SysParamTypeVo, SysParamTypeBo> implements ISysParamTypeService {

  @Override
  public R<List<Map<String, Object>>> searchTree(String filterText) {
    Assert.isFalse(StringUtils.isEmpty(filterText), "搜索关键词不能为空");

    LambdaQueryWrapper<SysParamType> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper.like(SysParamType::getTypeName, filterText).last("limit 10");
    List<SysParamType> paramTypeList = this.list(menuLambdaQueryWrapper);

    List<Map<String, Object>> mapList = new ArrayList<>();
    for (var paramType : paramTypeList) {
      String menuName = paramType.getTypeName();
      Function<Long, String> parentNode = getParentNode();
      String parentNameAll = parentNode.apply(paramType.getParentId());
      parentNameAll = parentNameAll + "&gt;" + menuName;

      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("typeId", paramType.getTypeId());
      resultMap.put("typeName", paramType.getTypeName());
      resultMap.put("parentNameAll", parentNameAll);
      mapList.add(resultMap);
    }


    return R.ok(mapList);
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
        LambdaQueryWrapper<SysParamType> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMenuLambdaQueryWrapper.eq(SysParamType::getTypeId, parentId);
        SysParamType sysParamType = the.getById(parentId);
        String parentName = StringUtils.EMPTY;
        if (ObjectUtil.isNotNull(sysParamType)) {
          String _parentName = self.apply(sysParamType.getParentId()); // 递归获取父级菜单
          parentName = _parentName + (StringUtils.isNotEmpty(_parentName) ? "&gt;" : "") + sysParamType.getTypeName();
        }
        return parentName;
      }
    };
  }
}
