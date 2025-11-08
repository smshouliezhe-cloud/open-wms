package com.yiruantong.system.service.core.impl;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.dto.RoleDTO;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.system.RoleAuthDataEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.domain.bo.LoadTreeBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.BuildWrapperHelper;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.redis.utils.QueueUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.generator.domain.GenTableTenant;
import com.yiruantong.generator.service.IGenTableTenantService;
import com.yiruantong.system.domain.core.SysConfig;
import com.yiruantong.system.domain.core.vo.SysMenuVo;
import com.yiruantong.system.domain.dataHandler.SysMenuApp;
import com.yiruantong.system.domain.dataHandler.vo.SysDropdownVo;
import com.yiruantong.system.domain.permission.SysRoleAuth;
import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.domain.tenant.SysTenantMenu;
import com.yiruantong.system.mapper.core.SysConfigMapper;
import com.yiruantong.system.service.core.ICommonService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.core.ISysMenuService;
import com.yiruantong.system.service.dataHandler.ISysDropdownService;
import com.yiruantong.system.service.dataHandler.ISysMenuAppService;
import com.yiruantong.system.service.permission.ISysRoleAuthDataService;
import com.yiruantong.system.service.permission.ISysRoleAuthService;
import com.yiruantong.system.service.tenant.ISysTenantMenuService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommonServiceImpl implements ICommonService {
  private final ISysDropdownService sysDropdownService;
  private final ISysMenuService sysMenuService;
  private final ISysTenantMenuService sysTenantMenuService;
  private final ISysRoleAuthService sysRoleAuthService;
  private final SqlSessionTemplate sqlSessionTemplate;
  private final SysConfigMapper sysConfigMapper;
  private final IDataAuthService dataAuthService;
  private final ISysConfigService sysConfigService;
  private final IGenTableTenantService genTableTenantService;
  private final ISysMenuAppService sysMenuAppService;
  private final ISysRoleAuthDataService sysRoleAuthDataService;

  /**
   * 根据下拉框ID集合获取下拉框数据集合
   *
   * @param queryBoList 下拉框ID集合
   * @return
   */
  @Override
  public Map<String, List<LinkedHashMap<String, Object>>> loadDropDown(List<QueryBo> queryBoList) {
    LoginUser loginUser = LoginHelper.getLoginUser();

    Map<String, List<LinkedHashMap<String, Object>>> dropdownList = new LinkedHashMap<>();
    final List<SysDropdownVo> sysDropdownVos = sysDropdownService.selectList(queryBoList);
    for (SysDropdownVo sysDropdownVo : sysDropdownVos) {
      String sqlScript = sysDropdownVo.getSqlScript();
      sqlScript = sqlScript.replace("{tenant_id}", Objects.requireNonNull(LoginHelper.getTenantId()));
      sqlScript = sqlScript.replace("{tenantId}", Objects.requireNonNull(LoginHelper.getTenantId()));
      if (loginUser.isAdministrator()) {
        sqlScript = sqlScript.replace("{AUTHWHERE}", "1=1");
      } else {
        String authWhere = dataAuthService.getDropDownWhere(sysDropdownVo.getDropdownId());
        sqlScript = sqlScript.replace("{AUTHWHERE}", authWhere);
      }
      final List<LinkedHashMap<String, Object>> mapList = new DBUtils(sqlSessionTemplate).selectList(sqlScript);

      var dropdownData =
        mapList.stream()
          .peek(
            m -> {
              final List<Map.Entry<String, Object>> entries = m.entrySet().stream().toList();
              if (entries.size() >= 3) {
                m.put("value", entries.get(0).getValue());
                m.put("code", entries.get(1).getValue());
                m.put("label", entries.get(2).getValue());
              } else if (m.entrySet().size() == 2) {
                m.put("value", entries.get(0).getValue());
                m.put("label", entries.get(1).getValue());
              } else if (m.entrySet().size() == 1) {
                m.put("value", entries.get(0).getValue());
                m.put("label", entries.get(0).getValue());
              }
            })
          .toList();

      dropdownList.put("dropdown" + sysDropdownVo.getDropdownId(), dropdownData);
    }

    return dropdownList;
  }

  /**
   * 跟模块ID获取PC模块权限点
   *
   * @param params 权限参数
   * @return 权限集合
   */
  @Override
  public Map<String, Object> getAuthNode(Map<String, Object> params) {
    Long menuId = Convert.toLong(params.get("menuId"));
    Long tableId = Convert.toLong(params.get("tableId"));
    Long fromTableTenantId = Convert.toLong(params.get("fromTableTenantId"));
    String tableTenantName = Convert.toStr(params.get("tableTenantName"));

    // 全局参数
    boolean global_closeUserUIJson = sysConfigService.getConfigBool("global_closeUserUIJson"); // 关闭自定义UI
    boolean global_openSortable = sysConfigService.getConfigBool("global_openSortable"); // 列表页面开始排序
    boolean global_editorProhibitCloseOnClickModal = sysConfigService.getConfigBool("global_editorProhibitCloseOnClickModal"); // 编辑页面禁止通过点击空白处关闭对话框
    JSONObject userUIJson = new JSONObject();
    // 开启定义UI
    if (!global_closeUserUIJson) {
      // 获取自定义UI设计
      LambdaQueryWrapper<GenTableTenant> tableTenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
      if (StringUtils.isNotEmpty(tableTenantName)) {
        tableTenantLambdaQueryWrapper
          .eq(GenTableTenant::getTableId, tableId)
          .eq(GenTableTenant::getTableTenantName, tableTenantName)
          .eq(GenTableTenant::getFromTableTenantId, "0")
          .last("limit 1");
      } else {
        tableTenantLambdaQueryWrapper
          .eq(GenTableTenant::getTableId, tableId)
          .eq(GenTableTenant::getFromTableTenantId, fromTableTenantId)
          .eq(GenTableTenant::getFromTableTenantId, "0")
          .last("limit 1");
      }

      GenTableTenant genTableTenant = genTableTenantService.getOne(tableTenantLambdaQueryWrapper);
      if (ObjectUtil.isNotNull(genTableTenant) && StringUtils.isNotEmpty(genTableTenant.getJsonData())) {
        // 获取当前自己设定的模块ID
        userUIJson = JsonUtils.parseObj(genTableTenant.getJsonData());
        menuId = Convert.toLong(userUIJson.getByPath("dataOptions.menuId"));
      }
    }

    Map<String, Object> mapResult = getAuthNodeMap(menuId);
    mapResult.put("userUIJson", userUIJson);
    mapResult.put("global_closeUserUIJson", global_closeUserUIJson);
    mapResult.put("global_editorProhibitCloseOnClickModal", global_editorProhibitCloseOnClickModal);
    mapResult.put("global_openSortable", global_openSortable);
    mapResult.put("subUserJsons", new ArrayList<>());
    return mapResult;
  }

  /**
   * 跟模块ID获取PC模块权限点
   *
   * @param menuId 模块ID
   * @return 权限集合
   */
  @Override
  public Map<String, Boolean> getAuthNodeAmis(Long menuId) {
    Map<String, Object> authNodeMap = getAuthNodeMap(menuId);

    return Convert.toMap(String.class, Boolean.class, authNodeMap.get("authNodes"));
  }

  @NotNull
  private Map<String, Object> getAuthNodeMap(Long menuId) {
    Map<String, Object> mapResult = new HashMap<>();
    Map<String, Boolean> mapAuthNodes = new HashMap<>();
    SysMenuVo sysMenuVo;
    if (StringUtils.equals(TenantHelper.getTenantId(), TenantConstants.DEFAULT_TENANT_ID)) {
      sysMenuVo = sysMenuService.selectMenuById(menuId);
    } else {
      SysTenantMenu sysTenantMenu = sysTenantMenuService.selectMenuById(menuId);
      sysMenuVo = MapstructUtils.convert(sysTenantMenu, SysMenuVo.class);
    }
    if (ObjectUtil.isEmpty(sysMenuVo)) {
      mapResult.put("authNodes", mapAuthNodes);
      return mapResult;
    }

    if (LoginHelper.isSuperAdmin()) {
      // 如果是超级管理员，获取全部权限点为true
      Optional.ofNullable(sysMenuVo.getPerms()).map(m -> m.split(",")).map(Arrays::stream).ifPresent(m -> m.forEach(x -> {
        var nodes = x.split("=");
        var nodeName = StringUtils.toCamelCase(nodes[0]);
        mapAuthNodes.put(nodeName, true);
      }));
    } else {
      // 普通用户功能权限点
      List<Long> roleIds = Objects.requireNonNull(LoginHelper.getLoginUser()).getRoles().stream().map(RoleDTO::getRoleId).toList();
      var wrapper = new LambdaQueryWrapper<SysRoleAuth>()
        .in(SysRoleAuth::getRoleId, roleIds)
        .eq(SysRoleAuth::getMenuId, menuId);
      List<SysRoleAuth> roleAuths = sysRoleAuthService.list(wrapper);
      for (var item : roleAuths) {
        Optional.ofNullable(item.getAuthValue()).map(m -> m.split(",")).map(Arrays::stream).ifPresent(p -> p.forEach(nodeItem -> {
          // nodeItem 格式为：add=1,delete=0
          Optional.ofNullable(nodeItem).map(s -> s.split("=")).ifPresent(nodes -> {
            if (nodes.length == 2) {
              var nodeName = StringUtils.toCamelCase(nodes[0]);
              var val = nodes[1];
              // 合并权限，如果已存在，且为true时，不在做更新，多个角色合并保留true
              if (ObjectUtil.isEmpty(mapAuthNodes.get(nodeName))) {
                mapAuthNodes.put(nodeName, val.equals("1"));
              }
            }
          });
        }));
      }
    }

    mapResult.put("authNodes", mapAuthNodes);

    return mapResult;
  }

  /**
   * 跟模块ID获取APP权限点
   *
   * @param params 权限参数
   * @return 权限集合
   */
  @Override
  public Map<String, Object> getAuthNodeApp(Map<String, Object> params) {
    Map<String, Object> mapResult = new HashMap<>();
    Map<String, Boolean> mapAuthNodes = new HashMap<>();
    Long menuId = Convert.toLong(params.get("menuId"));

    SysMenuApp sysMenuApp = sysMenuAppService.getById(menuId);
    if (ObjectUtil.isEmpty(sysMenuApp)) {
      mapResult.put("authNodes", mapAuthNodes);
      return mapResult;
    }

    if (LoginHelper.isSuperAdmin()) {
      // 如果是超级管理员，获取全部权限点为true
      Optional.ofNullable(sysMenuApp.getVueAuth()).map(m -> m.split(",")).map(Arrays::stream).ifPresent(m -> m.forEach(x -> {
        var nodes = x.split("=");
        var nodeName = StringUtils.toCamelCase(nodes[0]);
        mapAuthNodes.put(nodeName, true);
      }));
    } else {
      // 普通用户功能权限点
      List<Long> roleIds = Objects.requireNonNull(LoginHelper.getLoginUser()).getRoles().stream().map(RoleDTO::getRoleId).toList();
      var wrapper = new LambdaQueryWrapper<SysRoleAuthData>()
        .in(SysRoleAuthData::getRoleId, roleIds)
        .eq(SysRoleAuthData::getModuleId, RoleAuthDataEnum.APP_MENU.getId());
      List<SysRoleAuthData> roleAuths = sysRoleAuthDataService.list(wrapper);
      for (var item : roleAuths) {
        Optional.ofNullable(item.getAuthValue()).map(m -> m.split(",")).map(Arrays::stream).ifPresent(p -> p.forEach(nodeItem -> {
          // nodeItem 格式为：add=1,delete=0
          Optional.ofNullable(nodeItem).map(s -> s.split("=")).ifPresent(nodes -> {
            if (nodes.length == 2) {
              var nodeName = StringUtils.toCamelCase(nodes[0]);
              var val = nodes[1];
              // 合并权限，如果已存在，且为true时，不在做更新，多个角色合并保留true
              if (ObjectUtil.isEmpty(mapAuthNodes.get(nodeName))) {
                mapAuthNodes.put(nodeName, val.equals("1"));
              }
            }
          });
        }));
      }
    }

    mapResult.put("authNodes", mapAuthNodes);

    return mapResult;
  }

  /**
   * 根据大模块ID获取APP模块列表
   *
   * @param params 权限参数
   * @return 权限集合
   */
  @Override
  public List<Map<String, Object>> getAppBigModules(Map<String, Object> params) {
    List<Map<String, Object>> mapResult = new ArrayList<>();
    Long menuId = Convert.toLong(params.get("menuId"));

    // 获取app一级目录
    List<Map<String, Object>> parentList = sysMenuAppService.getMapListByParentId(menuId);
    if (CollUtil.isEmpty(parentList)) {
      return mapResult;
    }

    // 加载下一级菜单
    List<Map<String, Object>> childrenMenuList = new ArrayList<>();
    for (var parent : parentList) {
      Long subMenuId = Convert.toLong(parent.get("menuId"));
      var children = sysMenuAppService.getMapListByParentId(subMenuId);
      parent.put("parentId", 0L);
      childrenMenuList.add(parent);
      childrenMenuList.addAll(children);
    }

    mapResult = childrenMenuList.stream().map(m -> {
      Map<String, Object> map = new HashMap<>();
      map.put("menuId", m.get("menuId"));
      map.put("label", m.get("menuName"));
      map.put("parentId", m.get("parentId"));
      map.put("topNum", m.get("topNum"));
      map.put("orderNum", m.get("orderNum"));
      map.put("url", m.get("vueUrl"));
      map.put("icon", m.get("icon"));
      map.put("iconColor", m.get("iconColor"));
      return map;
    }).toList();

    return mapResult;
  }

  @Override
  public List<String> getMsg(Map<String, Object> params) {
    Object keyObj = params.get("key");
    String key = ObjectUtil.isNotEmpty(keyObj) ? Convert.toStr(keyObj) : "queueKey";

    int size = QueueUtils.getClient().getBlockingDeque(key).size();
    if (size > 50) size = 50;
    List<String> msgList = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      String value = QueueUtils.getQueueObject(key);
      if (StringUtils.isNotEmpty(value)) {
        msgList.add(value);
      }
    }
    return msgList;
  }

  //#region loadTreeNode

  /**
   * 查询tree结构数据
   */
  @SaIgnore // 忽略接口鉴权
  public List<Map<String, Object>> loadTreeNode(LoadTreeBo loadTreeBo) {
    // 获取未使用的数据
    if (loadTreeBo.getWhereList().stream().anyMatch(m -> m.getColumn().equals("parentId") && m.getValues().equals("-1"))) {
      AtomicReference<String> where = new AtomicReference<>(StrUtil.EMPTY);
      loadTreeBo.getWhereList().stream()
        .filter(f -> !StringUtils.equals(f.getColumn(), "parentId"))
        .forEach(queryBo -> {
          switch (queryBo.getQueryType()) {
            case EQ -> {
              where.set(StringUtils.toUnderScoreCase(queryBo.getColumn()) + "='" + queryBo.getValues() + "'");
            }
            case NE -> {
              where.set(StringUtils.toUnderScoreCase(queryBo.getColumn()) + "<>'" + queryBo.getValues() + "'");
            }
            default -> {
            }
          }
        });

      if (StringUtils.isNotEmpty(where.get())) where.set(" AND " + where);
      var sql = """
        WITH RECURSIVE recursiveInfo AS
        (
          SELECT {keyName} FROM {tableName} where {parentName}=0 {where}
          UNION ALL
          SELECT a.{keyName} FROM {tableName} AS a INNER JOIN recursiveInfo AS b ON a.{parentName}=b.{keyName} {where}
        )
        SELECT {keyName} AS {keyNameAlias}, {nodeName} AS {nodeNameAlias}, 0 as hasChild {extendColumns} FROM {tableName}
        WHERE {keyName}
        not in
        (
          SELECT {keyName} FROM recursiveInfo
        ) {where}
        limit 50""";
      var keyName = StringUtils.toUnderScoreCase(loadTreeBo.getKeyName());
      var tableName = StringUtils.toUnderScoreCase(loadTreeBo.getTableName());
      var nodeName = StringUtils.toUnderScoreCase(loadTreeBo.getNodeName());
      var parentName = StringUtils.toUnderScoreCase(loadTreeBo.getParentName());
      var extendColumns = loadTreeBo.getExtendColumns();
      if (StringUtils.isNotEmpty(extendColumns)) {
        String[] extendCols = StringUtils.split(loadTreeBo.getExtendColumns(), ",");
        extendColumns = "," + Arrays.stream(extendCols).map(col -> StringUtils.toUnderScoreCase(col) + " AS " + col).collect(Collectors.joining(","));
      }

      Map<String, String> map = new HashMap<>();
      map.put("tableName", tableName);
      map.put("keyName", keyName);
      map.put("keyNameAlias", loadTreeBo.getKeyName());
      map.put("parentName", parentName);
      map.put("nodeName", nodeName);
      map.put("nodeNameAlias", loadTreeBo.getNodeName());
      map.put("where", where.get());
      map.put("extendColumns", extendColumns);
      sql = StrUtil.format(sql, map);

      return SqlRunner.db().selectList(sql);
    }

    List<QueryBo> queryBoList = loadTreeBo.getWhereList();
    // 构建查询条件
    QueryWrapper<SysConfig> queryWrapper = BuildWrapperHelper.createWrapper(queryBoList);

    // 构建查找字段
    StringJoiner selectFields = new StringJoiner(",");
    String keyName = loadTreeBo.getKeyName();
    String nodeName = loadTreeBo.getNodeName();
    selectFields.add(StringUtils.toUnderScoreCase(keyName) + " AS " + keyName);
    selectFields.add(StringUtils.toUnderScoreCase(nodeName) + " AS " + nodeName);
    selectFields.add(StringUtils.toUnderScoreCase(keyName) + " AS value");
    selectFields.add(StringUtils.toUnderScoreCase(nodeName) + " AS label");

    String hasChild = "";
    if (loadTreeBo.isFixHasChild()) {
      selectFields.add("(1) AS hasChild");
    } else if (StringUtils.isNotEmpty(StringUtils.toUnderScoreCase(loadTreeBo.getParentName()))) {
      selectFields.add(
        "(case when (Select Count(1) from "
          + StringUtils.toUnderScoreCase(loadTreeBo.getTableName())
          + " sub Where m."
          + StringUtils.toUnderScoreCase(loadTreeBo.getKeyName())
          + "=sub."
          + StringUtils.toUnderScoreCase(loadTreeBo.getParentName())
          + ")>0 then 1 else 0 end) AS hasChild");
    }

    if (StringUtils.isNotEmpty(loadTreeBo.getExtendColumns())) {
      String[] extendCols = StringUtils.split(loadTreeBo.getExtendColumns(), ",");
      for (String col : extendCols) {
        selectFields.add(StringUtils.toUnderScoreCase(col) + " AS " + col);
      }
    }
    queryWrapper.select(StringUtils.split(selectFields.toString(), ","));

    // 构建排序
    BuildWrapperHelper.createOrderBy(queryWrapper, loadTreeBo.getOrderByList());

    String tableName = StringUtils.toUnderScoreCase(loadTreeBo.getTableName());
    return this.sysConfigMapper.loadTreeNode(tableName, queryWrapper);
  }
  //#endregion

  /**
   * 查询完整tree结构数据
   */
  public List<Map<String, Object>> loadTreeNodeAll(LoadTreeBo loadTreeBo) {
    var mapList = this.loadTreeNode(loadTreeBo);
    for (var map : mapList) {
      int hasChild = Convert.toInt(map.get("hasChild"));
      String idValue = Convert.toStr(map.get(loadTreeBo.getKeyName()));
      if (B.isEqual(hasChild, 1)) {
        // 获取子集集合
        List<QueryBo> queryBoList = new ArrayList<>();
        QueryBo queryBo = new QueryBo();
        queryBo.setValues(idValue);
        queryBo.setQueryType(QueryTypeEnum.EQ);
        queryBo.setColumn(loadTreeBo.getParentName());
        queryBo.setDataType(DataTypeEnum.INT);
        queryBoList.add(queryBo);
        if (loadTreeBo.getSubWhereList() != null) {
          queryBoList.addAll(loadTreeBo.getSubWhereList());
        }
        loadTreeBo.setWhereList(queryBoList);
        var subMapList = this.loadTreeNodeAll(loadTreeBo);
        map.put("children", subMapList);
      }
    }

    return mapList;
  }
//#endregion

  @Override
  public boolean executeSql(String sql) {
    return new DBUtils(sqlSessionTemplate).executeSql(sql);
  }

  @Override
  public List<LinkedHashMap<String, Object>> selectList(String sql) {
    return new DBUtils(sqlSessionTemplate).selectList(sql);
  }
}
