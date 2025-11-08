package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.system.domain.dataHandler.SysMenuApp;
import com.yiruantong.system.domain.dataHandler.bo.SysMenuAppBo;
import com.yiruantong.system.domain.dataHandler.vo.SysMenuAppVo;
import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.mapper.dataHandler.SysMenuAppMapper;
import com.yiruantong.system.service.dataHandler.ISysMenuAppService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.dto.RoleDTO;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.system.RoleTypeEnum;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ${author}
 * @date 2024-01-18
 */
@RequiredArgsConstructor
@Service
public class SysMenuAppServiceImpl extends ServiceImplPlus<SysMenuAppMapper, SysMenuApp, SysMenuAppVo, SysMenuAppBo> implements ISysMenuAppService {
  @Override
  public List<SysMenuAppVo> getListByParentId(Long parentId) {
    LambdaQueryWrapper<SysMenuApp> menuAppLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuAppLambdaQueryWrapper
      .eq(SysMenuApp::getParentId, parentId)
      .eq(SysMenuApp::getEnable, EnableEnum.ENABLE.getId())
      .orderByDesc(SysMenuApp::getOrderNum)
      .orderByAsc(SysMenuApp::getMenuId);

    return this.selectList(menuAppLambdaQueryWrapper);
  }

  @Override
  public List<Map<String, Object>> getMapListByParentId(Long parentId) {
    MPJLambdaWrapper<SysMenuApp> menuAppMPJLambdaWrapper = new MPJLambdaWrapper<>();
    List<RoleDTO> roles = Objects.requireNonNull(LoginHelper.getLoginUser()).getRoles();
    if (ObjectUtil.isNull(roles)) {
      roles = new ArrayList<>();
      RoleDTO roleDTO = new RoleDTO();
      roleDTO.setRoleId(0L);
      roles.add(roleDTO);
    }
    List<RoleDTO> finalRoles = roles;

    if (!LoginHelper.isSuperAdmin()) {
      menuAppMPJLambdaWrapper
        .selectAll(SysMenuApp.class)
        .select(SysRoleAuthData::getAuthValue)
        .innerJoin(SysRoleAuthData.class, on -> on.in(SysRoleAuthData::getRoleId, finalRoles.stream().map(RoleDTO::getRoleId).toList())
          .eq(SysRoleAuthData::getModuleId, RoleTypeEnum.APP_MODULE.getId())
          .eq(SysRoleAuthData::getNodeId, SysMenuApp::getMenuId))
        .eq(SysMenuApp::getParentId, parentId)
        .eq(SysMenuApp::getEnable, EnableEnum.ENABLE.getId())
        .like(SysRoleAuthData::getAuthValue, "=1")
        .orderByDesc(SysMenuApp::getOrderNum)
        .orderByAsc(SysMenuApp::getMenuId);

      return this.selectJoinMaps(menuAppMPJLambdaWrapper);
    } else {
      // 超级管理员
      menuAppMPJLambdaWrapper
        .selectAll(SysMenuApp.class)
        .eq(SysMenuApp::getParentId, parentId)
        .eq(SysMenuApp::getEnable, EnableEnum.ENABLE.getId())
        .orderByDesc(SysMenuApp::getOrderNum)
        .orderByAsc(SysMenuApp::getMenuId);

      return this.selectJoinMaps(menuAppMPJLambdaWrapper);
    }
  }

  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<SysMenuAppBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }
    SysMenuApp dataInfo = this.getById(saveEditorBo.getIdValue());
    // 保存主表
    dataInfo.setMenuId(null);
    this.save(dataInfo);

    return R.ok("复制成功");
  }

  @Override
  public R<List<Map<String, Object>>> searchTree(String filterText) {
    Assert.isFalse(StringUtils.isEmpty(filterText), "搜索关键词不能为空");

    LambdaQueryWrapper<SysMenuApp> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper.like(SysMenuApp::getMenuName, filterText).last("limit 10");
    List<SysMenuApp> menuList = this.list(menuLambdaQueryWrapper);

    List<Map<String, Object>> mapList = new ArrayList<>();
    for (var menu : menuList) {
      String menuName = menu.getMenuName();
      Function<Long, String> parentNode = getParentNode();
      String parentNameAll = parentNode.apply(menu.getParentId());
      parentNameAll = parentNameAll + "&gt;" + menuName;

      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("menuId", menu.getMenuId());
      resultMap.put("menuName", menu.getMenuName());
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
        LambdaQueryWrapper<SysMenuApp> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMenuLambdaQueryWrapper.eq(SysMenuApp::getMenuId, parentId);
        SysMenuApp sysMenu = the.getById(parentId);
        String parentName = StringUtils.EMPTY;
        if (ObjectUtil.isNotNull(sysMenu)) {
          String _parentName = self.apply(sysMenu.getParentId()); // 递归获取父级菜单
          parentName = _parentName + (StringUtils.isNotEmpty(_parentName) ? "&gt;" : "") + sysMenu.getMenuName();
        }
        return parentName;
      }
    };
  }
}
