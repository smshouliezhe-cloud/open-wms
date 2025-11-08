package com.yiruantong.system.service.dataHandler.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.system.domain.dataHandler.SysLayout;
import com.yiruantong.system.domain.dataHandler.SysMenuApp;
import com.yiruantong.system.domain.dataHandler.bo.SysLayoutBo;
import com.yiruantong.system.domain.dataHandler.vo.SysLayoutVo;
import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.mapper.dataHandler.SysLayoutMapper;
import com.yiruantong.system.service.dataHandler.ISysLayoutService;
import com.yiruantong.system.service.dataHandler.ISysMenuAppService;
import com.yiruantong.system.service.permission.ISysRoleAuthDataService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ${author}
 * @date 2024-01-18
 */
@RequiredArgsConstructor
@Service
public class SysLayoutServiceImpl extends ServiceImplPlus<SysLayoutMapper, SysLayout, SysLayoutVo, SysLayoutBo> implements ISysLayoutService {
  private final ISysRoleAuthDataService sysRoleAuthDataService;
  private final ISysMenuAppService sysMenuAppService;
  //#region InitLayout 初始化首页布局页面

  /**
   * 初始化首页布局页面
   */
  @Override
  public Map<String, Object> initLayout(Map<String, Object> map) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    try {
      LambdaQueryWrapper<SysLayout> sysLayoutLambdaQueryWrapper = new LambdaQueryWrapper<>();
      sysLayoutLambdaQueryWrapper.last("limit 1");
      var mvcInfo = this.getOne(sysLayoutLambdaQueryWrapper);

      if (ObjectUtil.isNotNull(mvcInfo)) {
        HashMap jsonData = JsonUtils.parseObject(mvcInfo.getJsonData(), HashMap.class);
        if (!loginUser.isAdministrator()) {
          // 非超级管理员走系统设置权限, 159=慕晨的代办任务，255=天勤的代办任务
          LambdaQueryWrapper<SysMenuApp> sysMenuAppLambdaQueryWrapper = new LambdaQueryWrapper<>();
          sysMenuAppLambdaQueryWrapper
            .in(SysMenuApp::getParentId, 70, 71, 159, 255);
          var menuAppList = sysMenuAppService.selectList(sysMenuAppLambdaQueryWrapper);

          List<Long> nodeIds = menuAppList.stream().map(m -> m.getMenuId()).toList();

          LambdaQueryWrapper<SysRoleAuthData> sysRoleAuthDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
          sysRoleAuthDataLambdaQueryWrapper.eq(SysRoleAuthData::getRoleId, loginUser.getRoleId())
            .eq(SysRoleAuthData::getAuthValue, "1")
            .in(SysRoleAuthData::getNodeId, nodeIds);

          var authList = sysRoleAuthDataService.selectList(sysRoleAuthDataLambdaQueryWrapper);

//          authList.forEach(item -> {
//            var menuInfo = menuAppList.stream().anyMatch(m -> NumberUtil.equals(m.getMenuId(), item.getNodeId()));
//          if (menuInfo) {
//            item["menuName"] = menuInfo.menuName;
//          }
//          });
          List<Map<String, Object>> fields = StreamUtils.toList(jsonData.get("fields"));
          fields = fields.stream().filter(item -> {
            AtomicBoolean isAuth = new AtomicBoolean(false);
            if (ObjectUtil.equal(item.get("type"), "grid")) {
              List<Map<String, Object>> columns = StreamUtils.toList(item.get("columns"));
              columns.forEach(column -> {
                List<Map<String, Object>> subFields = StreamUtils.toList(column.get("fields"));
                isAuth.set(authList.stream().anyMatch(auth ->
                  subFields.stream().anyMatch(f -> StringUtils.equals(Convert.toStr(f.get("label")), auth.getNodeName()))));
              });
            } else {
              isAuth.set(authList.stream().anyMatch(auth -> StringUtils.equals(Convert.toStr(item.get("label")), auth.getNodeName())));
            }
            return isAuth.get();
          }).toList();
          jsonData.put("fields", fields);
        }
        return jsonData;
      } else {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("fields", new ArrayList<>());
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("labelWidth", "100px"); // 标签文字宽度
        configMap.put("labelPosition", "right"); // 标签位置
        configMap.put("top", "3vh"); // 对话框底部距离
        configMap.put("width", "1200px"); // 对话框宽度
        configMap.put("visible", false); // 是否显示添加实验室对话框
        configMap.put("disabled", false); // 是否禁用
        configMap.put("title", null); // 对话框标题
        configMap.put("action", "add"); // 接收值为：add、edit
        configMap.put("formInline", false); // 行内表单
        configMap.put("saveButtonText", "保存"); // 保存按钮文字
        resultMap.put("config", configMap);

        return resultMap;
      }

    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  //#region 保存数据
  @Override
  public R<Void> saveLayout(Map<String, Object> map) {
    String jsonData = Convert.toStr(map.get("jsonData"));

    try {
      LambdaQueryWrapper<SysLayout> sysLayoutLambdaQueryWrapper = new LambdaQueryWrapper<>();
      sysLayoutLambdaQueryWrapper.eq(SysLayout::getTenantId, TenantConstants.DEFAULT_TENANT_ID);
      var mvcInfo = this.getOne(sysLayoutLambdaQueryWrapper);
      // 如果不为空，则更新
      if (ObjectUtil.isNotNull(mvcInfo)) {
        LambdaUpdateWrapper<SysLayout> sysLayoutLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        sysLayoutLambdaUpdateWrapper.set(SysLayout::getTenantId, TenantConstants.DEFAULT_TENANT_ID)
          .set(SysLayout::getJsonData, jsonData);
        this.update(sysLayoutLambdaUpdateWrapper);
      } else {
        mvcInfo = new SysLayout();
        mvcInfo.setJsonData(jsonData);
        mvcInfo.setTenantId(TenantConstants.DEFAULT_TENANT_ID);
      }
      return R.ok();
    } catch (NoSuchMessageException e) {
      throw new ServiceException("错误" + e.getMessage());
    }
  }
  //#endregion

}
