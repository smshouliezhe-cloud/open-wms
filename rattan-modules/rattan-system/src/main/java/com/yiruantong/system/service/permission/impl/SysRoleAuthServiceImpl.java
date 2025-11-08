package com.yiruantong.system.service.permission.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.yiruantong.system.domain.permission.SysRoleAuth;
import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.domain.permission.SysRoleAuthModule;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthModuleVo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthVo;
import com.yiruantong.system.mapper.permission.SysRoleAuthMapper;
import com.yiruantong.system.service.permission.ISysRoleAuthDataService;
import com.yiruantong.system.service.permission.ISysRoleAuthModuleService;
import com.yiruantong.system.service.permission.ISysRoleAuthService;
import com.yiruantong.system.service.permission.ISysRoleService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.system.DataAuthNodeTypeEnum;
import com.yiruantong.common.core.enums.system.RoleAuthDataEnum;
import com.yiruantong.common.core.enums.system.RoleTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 功能权限Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@RequiredArgsConstructor
@Service
public class SysRoleAuthServiceImpl extends ServiceImplPlus<SysRoleAuthMapper, SysRoleAuth, SysRoleAuthVo, SysRoleAuthBo> implements ISysRoleAuthService {
  private final ISysRoleService sysRoleService;
  private final ISysRoleAuthModuleService sysRoleAuthModuleService;
  private final ISysRoleAuthDataService sysRoleAuthDataService;

  @Override
  public R<Void> saveAuthMenu(Map<String, Object> map) {
    Byte roleType = Convert.toByte(map.get("roleType")); // 权限类型
    List<Map<String, Object>> menuList = StreamUtils.toList(map.get("menuList"));
    RoleTypeEnum roleTypeEnum = RoleTypeEnum.matchingEnumById(roleType);
    if (ObjectUtil.isNull(roleTypeEnum)) return R.fail("角色类型不正确");

    R<Void> result = R.ok();
    switch (roleTypeEnum) {
      case MODULE: //功能权限
      case PDA: //功能权限
        result = saveMenuAuth(menuList);
        break;
      case APP_MODULE:
        result = saveMenuAuthApp(menuList);
        break;
      default:
        break;
    }
    return result;
  }

  /**
   * 保存角色菜单权限
   *
   * @param menuList 菜单权限集合
   */
  private R<Void> saveMenuAuth(List<Map<String, Object>> menuList) {
    Long roleId = Convert.toLong(menuList.get(0).get("roleId"));
    var roleInfo = sysRoleService.getById(roleId);

    if (ObjectUtil.isNull(roleInfo)) {
      return R.fail("没有权限操作角色");
    }

    try {
      for (Map<String, Object> menuInfo : menuList) {
        LambdaQueryWrapper<SysRoleAuth> roleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Long menuId = Convert.toLong(menuInfo.get("menuId"));
        String authValue = Convert.toStr(menuInfo.get("authValue"));
        roleAuthLambdaQueryWrapper.eq(SysRoleAuth::getRoleId, roleId)
          .eq(SysRoleAuth::getMenuId, menuId);
        var roleAuthInfo = this.getOne(roleAuthLambdaQueryWrapper);

        if (ObjectUtil.isNotNull(roleAuthInfo)) {
          roleAuthInfo.setAuthValue(authValue);
          this.updateById(roleAuthInfo);
        } else {
          roleAuthInfo = new SysRoleAuth();
          roleAuthInfo.setAuthValue(authValue);
          roleAuthInfo.setRoleId(roleId);
          roleAuthInfo.setMenuId(menuId);
          this.getBaseMapper().insert(roleAuthInfo);
        }
      }

      return R.ok("保存成功");
    } catch (Exception error) {
      return R.fail("保存失败，" + error.getMessage());
    }
  }

  /**
   * 保存App菜单权限
   *
   * @param menuList 菜单权限集合
   */
  private R<Void> saveMenuAuthApp(List<Map<String, Object>> menuList) {
    Long roleId = Convert.toLong(menuList.get(0).get("roleId"));
    var roleInfo = sysRoleService.getById(roleId);

    if (ObjectUtil.isNull(roleInfo)) {
      return R.fail("没有权限操作角色");
    }

    try {
      for (Map<String, Object> menuInfo : menuList) {
        LambdaQueryWrapper<SysRoleAuthData> roleAuthLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String authValue = Convert.toStr(menuInfo.get("authValue"));

        Long menuId = Convert.toLong(menuInfo.get("menuId"));
        roleAuthLambdaQueryWrapper.eq(SysRoleAuthData::getRoleId, roleId)
          .eq(SysRoleAuthData::getNodeId, menuId)
          .eq(SysRoleAuthData::getModuleId, RoleAuthDataEnum.APP_MENU.getId());
        var roleAuthInfo = sysRoleAuthDataService.getOne(roleAuthLambdaQueryWrapper);

        if (ObjectUtil.isNotNull(roleAuthInfo)) {
          roleAuthInfo.setAuthValue(authValue);
          sysRoleAuthDataService.updateById(roleAuthInfo);
        } else {
          roleAuthInfo = new SysRoleAuthData();
          roleAuthInfo.setAuthValue(authValue);
          roleAuthInfo.setRoleId(roleId);
          roleAuthInfo.setModuleId(RoleAuthDataEnum.APP_MENU.getId().longValue());
          roleAuthInfo.setNodeId(menuId);
          sysRoleAuthDataService.getBaseMapper().insert(roleAuthInfo);
        }
      }

      return R.ok("保存成功");
    } catch (Exception error) {
      return R.fail("保存失败，" + error.getMessage());
    }
  }

  @Override
  public R<List<Map<String, Object>>> getDataAuth(Map<String, Object> map) {
    try {
      Long userId = Convert.toLong(map.get("userId"));
      String moduleType = Convert.toStr(map.get("moduleType"));
      Assert.isFalse(StringUtils.isEmpty(moduleType), "模块不存在，请设置！");

      LambdaQueryWrapper<SysRoleAuthModule> moduleLambdaQueryWrapper = new LambdaQueryWrapper<>();
      moduleLambdaQueryWrapper
        .eq(SysRoleAuthModule::getEnable, EnableEnum.ENABLE.getId())
        .eq(SysRoleAuthModule::getModuleType, moduleType)
        .orderByDesc(SysRoleAuthModule::getOrderNum)
        .orderByAsc(SysRoleAuthModule::getModuleId);
      var moduleList = sysRoleAuthModuleService.selectList(moduleLambdaQueryWrapper);

      LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
      authDataLambdaQueryWrapper.eq(SysRoleAuthData::getUserId, userId);
      authDataLambdaQueryWrapper.in(SysRoleAuthData::getModuleId, moduleList.stream().map(SysRoleAuthModuleVo::getModuleId).toList());

      var authDataList = sysRoleAuthDataService.selectList(authDataLambdaQueryWrapper);

      List<Map<String, Object>> resultDataList = new ArrayList<>();
      for (var s : moduleList) {
        List<Map<String, Object>> authNodeList = new ArrayList<>();
        if (Objects.equals(s.getEditType(), "选择器")) {
          // 分组数据
          List<Map<String, Object>> mapList = authDataList.stream()
            .filter(item -> NumberUtils.equals(item.getModuleId(), s.getModuleId()) && !B.isEqual(item.getNodeId()))
            .map(item -> {
              Map<String, Object> node = new HashMap<>();
              node.put("nodeId", item.getNodeId());
              node.put("nodeName", item.getNodeName());
              node.put("nodeType", item.getNodeType());
              node.put("isCheck", 1);
              return node;
            }).toList();
          authNodeList.addAll(mapList);
        } else {
          String sql = s.getSqlScript().replace("{tenant_id}", LoginHelper.getTenantId());
          List<Map<String, Object>> itemList = SqlRunner.db().selectList(sql);
          for (var item : itemList) {
            Long id = Convert.toLong(item.get("id"));
            String name = Convert.toStr(item.get("name"));
            var itemAuth = authDataList.stream()
              .filter(w -> Objects.equals(w.getModuleId(), s.getModuleId()) && Objects.equals(w.getNodeId(), id))
              .findAny().orElse(null);
            Map<String, Object> node = new HashMap<>();
            node.put("nodeId", id);
            node.put("nodeName", name);
            node.put("isCheck", itemAuth != null && Objects.equals(itemAuth.getAuthValue(), "1"));
            authNodeList.add(node);
          }
        }
        // 全部复选框
        var allNode = authDataList.stream().anyMatch(node -> Objects.equals(node.getModuleId(), s.getModuleId()) && B.isEqual(node.getNodeId()));
        if (allNode) {
          Map<String, Object> node = new HashMap<>();
          node.put("nodeId", 0);
          node.put("nodeName", "全部");
          node.put("isCheck", true);
          authNodeList.add(node);
        }
        Map<String, Object> moduleNode = new HashMap<>();
        moduleNode.put("moduleId", s.getModuleId());
        moduleNode.put("moduleName", s.getModuleName());
        moduleNode.put("editType", s.getEditType());
        moduleNode.put("authNodeList", authNodeList);
        resultDataList.add(moduleNode);
      }

      return R.ok(resultDataList);
    } catch (Exception error) {
      return R.fail("错误消息：" + error.getMessage());
    }
  }

  @Override
  public R<List<Map<String, Object>>> saveUserDataAuth(Map<String, Object> map) {
    List<Map<String, Object>> dataList = StreamUtils.toList(map.get("dataList"));
    for (var moduleInfo : dataList) {
      Long userId = Convert.toLong(moduleInfo.get("userId"));
      Long moduleId = Convert.toLong(moduleInfo.get("moduleId"));
      String moduleName = Convert.toStr(moduleInfo.get("moduleName"));

      List<String> nodeNameList = new ArrayList<>();
      nodeNameList.add("-1");
      LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
      authDataLambdaQueryWrapper.eq(SysRoleAuthData::getUserId, userId)
        .eq(SysRoleAuthData::getModuleId, moduleId);
      var authDataList = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);

      List<SysRoleAuthData> saveDataList = new ArrayList<>();
      int rowIndex = 0;
      List<Map<String, Object>> authNodeList = StreamUtils.toList(moduleInfo.get("authNodeList"));
      for (var authData : authNodeList) {
        rowIndex++;
        String nodeName = Convert.toStr(authData.get("nodeName"));
        if (ObjectUtil.isNotEmpty(nodeName)) {
          nodeNameList.add(nodeName);
        }

        var authDataInfo = authDataList.stream().filter(item -> ObjectUtil.equal(item.getNodeId(), Convert.toLong(authData.get("nodeId")))
          && Objects.equals(item.getNodeName(), Convert.toStr(authData.get("nodeName")))).findFirst().orElse(null);
        if (ObjectUtil.isNull(authDataInfo)) {
          authDataInfo = new SysRoleAuthData();
        }
        authDataInfo.setAuthValue(Convert.toBool(authData.get("isCheck")) ? "1" : "0");
        authDataInfo.setModuleId(moduleId);
        authDataInfo.setNodeId(Convert.toLong(authData.get("nodeId")));
        authDataInfo.setNodeName(Convert.toStr(authData.get("nodeName")));
//        authDataInfo.setNodeType(Convert.toStr(authData.get("nodeType")));
        authDataInfo.setUserId(userId);
        authDataInfo.setNodeType(DataAuthNodeTypeEnum.USER.getName());
        List<String> moduleNames = new ArrayList<>(Arrays.asList("车辆", "司机"));
        if (moduleNames.contains(moduleName)) {
          authDataInfo.setNodeType(DataAuthNodeTypeEnum.DRIVER.getName());
        }
        saveDataList.add(authDataInfo);
        if (rowIndex % 50 == 1) {
          sysRoleAuthDataService.saveOrUpdateBatch(saveDataList);
          saveDataList = new ArrayList<>();
        }
      }
      if (!saveDataList.isEmpty()) {
        sysRoleAuthDataService.saveOrUpdateBatch(saveDataList);
      }

      // 删除数据
      LambdaQueryWrapper<SysRoleAuthData> roleAuthDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
      roleAuthDataLambdaQueryWrapper
        .eq(SysRoleAuthData::getUserId, moduleInfo.get("userId"))
        .eq(SysRoleAuthData::getModuleId, moduleInfo.get("moduleId"))
        .notIn(SysRoleAuthData::getNodeName, nodeNameList);
      sysRoleAuthDataService.remove(roleAuthDataLambdaQueryWrapper);
    }

    return R.ok();
  }
}
