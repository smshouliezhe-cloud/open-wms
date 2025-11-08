package com.yiruantong.composite.service.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiruantong.common.core.domain.dto.RoleDTO;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.system.RoleAuthDataEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.LoadTreeBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.service.IDataAuthService;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.permission.SysRoleAuth;
import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.service.core.ISysMenuService;
import com.yiruantong.system.service.permission.ISysRoleAuthDataService;
import com.yiruantong.system.service.permission.ISysRoleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xtb
 * @CreateTime: 2024-01-10
 * @Description: 数据权限实现
 * @Version: 1.0
 */
@RequiredArgsConstructor
@Service
public class DataAuthService implements IDataAuthService {
  private ISysMenuService sysMenuService;
  private ISysRoleAuthDataService sysRoleAuthDataService;

  @Override
  public <T> void getDataAuth(PageQuery pageQuery) {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);
    if (ObjectUtil.isNull(pageQuery.getMenuId())) return;

    MenuEnum menuEnum = MenuEnum.getEnumById(pageQuery.getMenuId());
    if (ObjectUtil.isNull(menuEnum)) return;
    var longinUser = LoginHelper.getLoginUser();
    if (longinUser == null) return;
    if (LoginHelper.getLoginUser().isAdministrator()) return;

    // 仓库、货主权限
    switch (menuEnum) {
      /**
       * 入库单
       */
      case MENU_1001: // 预到货单
      case MENU_1002: // 残品入库
      case MENU_1003: // 入库计划
      case MENU_1638: // 缺货转预到货
      case MENU_2067: // 建议采购转预到货
      case MENU_1641: // 质检管理
      case MENU_1650: // 入库记录查询
      case MENU_1652: // 生成上架单
      case MENU_1660: // 上架记录查询
      case MENU_1661: // 到货退货单

        /**
         * 出库单
         */
      case MENU_2065: // 生成波次
      case MENU_1681: // 波次查询
      case MENU_1733: // 出库退货单
      case MENU_1744: // 拣货下架记录
      case MENU_1746: // 出库单拣货记录
        /**
         * 库存
         */
      case MENU_1032: // 库存明细查询
      case MENU_1770: // 库存SN查询
      case MENU_1771: // 商品库存查询
      case MENU_1772: // 库存监测记录
      case MENU_1774: // 库存占位查询

      case MENU_1042: // 货位转移查询
      case MENU_1044: // 生成盘点单
      case MENU_1601: // 库存盘点查询
      case MENU_1565: // 盘点盈亏单
      case MENU_1043: // 其它入库单
      case MENU_1600: // 其它出库单
      case MENU_1783: // 库存成本调整
      case MENU_1784: // 商品拆装单
      case MENU_1785: // 库存调整
      case MENU_1788: // 效期消息调整

      case MENU_1794: // 在途入库单
      case MENU_2079: // 调拨入库单
      case MENU_2080: // 在途出库单
      case MENU_2081: // 调拨出库单

        /**
         * 统计
         */
      case MENU_2073: // 保质期预警
      case MENU_2074: // 库龄预警
      case MENU_2075: // 货位最低库存预警
      case MENU_500: // 入库计划明细查询
      case MENU_501: // 残品入库明细查询
      case MENU_1639: // 预到货单明细查询
      case MENU_1640: // 入库记录明细查询
      case MENU_1653: // 上架记录明细查询
      case MENU_1673: // 到货退货明细查询

      case MENU_1674: // 出库计划明细查询
      case MENU_1675: // 出库单明细查询
      case MENU_1676: // 拣货下架明细查询
      case MENU_1883: // 出库记录明细查询

      case MENU_2056: // 货位转移明细查询
      case MENU_2057: // 盘点单明细查询
      case MENU_2058: // 盈亏单明细查询
      case MENU_2059: // 其他入库明细查询
      case MENU_2060: // 其他出库明细查询
      case MENU_2061: // 库存成本调整明细
      case MENU_2066: // 库存调整明细
      case MENU_2068: // 货主过户明细
      case MENU_2069: // 效期信息明细
      case MENU_2070: // 商品拆装入库明细
      case MENU_2071: // 商品拆装出库明细
      case MENU_1671: // 出库单
      case MENU_1696: // 打包单
        this.getStorageAuth(pageQuery);
        this.getConsignorAuth(pageQuery);
        break;
      case MENU_1758: // 商品信息
      default:
        break;
    }
    // 只有生产班组权限
    switch (menuEnum) {
      /**
       * 生产管理
       */
      case MENU_2204: // 报工记录
      case MENU_2206: // 生产任务单
        this.getTeamAuth(pageQuery);
        break;
    }

    // 只有供应商权限
    switch (menuEnum) {
      /**
       * 入库单
       */
      case MENU_1001: // 预到货单
      case MENU_1002: // 残品入库
      case MENU_1003: // 入库计划
      case MENU_1638: // 缺货转预到货
      case MENU_2067: // 建议采购转预到货
      case MENU_1650: // 入库记录查询
      case MENU_1652: // 生成上架单
      case MENU_1660: // 上架记录查询
      case MENU_1661: // 到货退货单
        this.getProviderAuth(pageQuery);
        break;
    }

    //只看自己权限
    this.getSelfAuth(pageQuery);

    /**
     * 只有货主权限
     */
    switch (menuEnum) {
      case MENU_1669: // 出库计划
        //货主权限权限
        this.getConsignorAuth(pageQuery);
        break;
    }

    /**
     * 只有承运商权限
     */
    switch (menuEnum) {
      case MENU_1894: // 成本报账
      case MENU_1888: // 成本报账
        //承运商权限
        this.getCarrierAuth(pageQuery);
        break;
    }

    /**
     * 只有仓库权限
     */
    switch (menuEnum) {
      case MENU_1815: // 仓库信息
      case MENU_1818: // 库区信息
      case MENU_1819: // 货位信息
      case MENU_1823: // 容器管理

//      case MENU_1824: // 容器使用轨迹
      case MENU_1826: // 商品入库上架策略
      case MENU_1787: // 货主过户
        this.getStorageAuth(pageQuery);
        break;
    }

    if (pageQuery.isPda()) {
      // PDA司机端权限
      switch (menuEnum) {
        case MENU_1880: // 提货派车单
        case MENU_1893: // 城配派车单
        case MENU_1888: // 干线运输单
          this.getDriverAuth(pageQuery, false);
          break;
        case MENU_1733: // 出库退货单
        case MENU_1734: // 换货单
          this.getDriverAuth(pageQuery, true);
          break;
      }
    }
  }

  /**
   * 获取模块是否全选权限
   *
   * @param roleAuthDataEnum
   * @return
   */
  @Override
  public boolean isAllAuth(RoleAuthDataEnum roleAuthDataEnum) {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);

    LoginUser loginUser = LoginHelper.getLoginUser();
    // 全部权限
    LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authDataLambdaQueryWrapper
      .eq(SysRoleAuthData::getModuleId, roleAuthDataEnum.getId()) // 仓库权限
      .eq(SysRoleAuthData::getUserId, loginUser.getUserId())
      .eq(SysRoleAuthData::getNodeId, 0)
      .eq(SysRoleAuthData::getAuthValue, "1");
    var allAuth = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);

    return !allAuth.isEmpty();
  }

  /**
   * 获取仓库权限
   *
   * @return 返回有权限的仓库ID
   */
  public ArrayList<Long> getAuthStorageId() {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);
    LoginUser loginUser = LoginHelper.getLoginUser();

    // 不是全选且不是管理员需要走数据权限
    LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authDataLambdaQueryWrapper
      .eq(SysRoleAuthData::getModuleId, RoleAuthDataEnum.STORAGE.getId()) // 仓库权限
      .eq(SysRoleAuthData::getUserId, loginUser.getUserId())
      .eq(SysRoleAuthData::getAuthValue, "1");
    // 获取仓库ID
    var authList = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);
    var idList = new ArrayList<>(authList.stream().map(SysRoleAuthData::getNodeId).filter(B::isGreater).toList()); // 仓库ID
    if (idList.isEmpty()) idList.add(0L);

    return idList;
  }

  /**
   * 仓库权限
   *
   * @param wrapper 查询包装器
   * @param <T>     泛型实体
   */
  public <T> void getStorageAuth(QueryWrapper<T> wrapper) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.STORAGE) && !loginUser.isAdministrator()) {
      var idList = this.getAuthStorageId(); // 获取仓库ID
      if (idList.isEmpty()) idList.add(0L);

      wrapper.in("storage_id", idList);
    }
  }

  /**
   * 仓库权限
   *
   * @param pageQuery 查询条件
   */
  public void getStorageAuth(PageQuery pageQuery) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.STORAGE) && !loginUser.isAdministrator()) {
      var idList = this.getAuthStorageId(); // 获取仓库ID
      if (idList.isEmpty()) idList.add(0L);

      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("storage_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(idList, ","));
      pageQuery.addQueryBo(queryBo);
    }
  }


  /**
   * 获取货主权限
   *
   * @return 返回有权限的货主ID
   */
  public ArrayList<Long> getAuthConsignorId() {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);
    LoginUser loginUser = LoginHelper.getLoginUser();

    // 不是全选且不是管理员需要走数据权限
    LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authDataLambdaQueryWrapper
      .eq(SysRoleAuthData::getModuleId, RoleAuthDataEnum.CONSIGNOR.getId()) // 仓库权限
      .eq(SysRoleAuthData::getUserId, loginUser.getUserId())
      .eq(SysRoleAuthData::getAuthValue, "1");
    // 获取仓库ID
    var authList = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);
    var idList = new ArrayList<>(authList.stream().map(SysRoleAuthData::getNodeId).filter(B::isGreater).toList()); // 仓库ID
    if (idList.isEmpty()) idList.add(0L);

    return idList;
  }

  /**
   * 货主权限
   *
   * @param wrapper 查询包装器
   * @param <T>     泛型实体
   */
  public <T> void getConsignorAuth(QueryWrapper<T> wrapper) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.CONSIGNOR) && !loginUser.isAdministrator()) {
      var idList = this.getAuthConsignorId(); // 获取货主ID
      if (idList.isEmpty()) idList.add(0L);

      wrapper.in("consignor_id", idList);
    }
  }


  /**
   * 生产班组权限
   *
   * @param wrapper 查询包装器
   * @param <T>     泛型实体
   */
  public <T> void getTeamAuth(QueryWrapper<T> wrapper) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.TEAM) && !loginUser.isAdministrator()) {
      var idList = this.getAuthTeamId(); // 获取生产班组ID
      if (idList.isEmpty()) idList.add(0L);

      wrapper.in("team_id", idList);
    }
  }

  /**
   * 仓库权限
   *
   * @param pageQuery 查询条件
   */
  public void getTeamAuth(PageQuery pageQuery) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.TEAM) && !loginUser.isAdministrator()) {
      var idList = this.getAuthTeamId(); // 获取仓库ID
      if (idList.isEmpty()) idList.add(0L);

      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("team_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(idList, ","));
      pageQuery.addQueryBo(queryBo);
    }
  }

  /**
   * 获取生产班组权限
   *
   * @return 返回有权限的货主ID
   */
  @Override
  public ArrayList<Long> getAuthTeamId() {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);
    LoginUser loginUser = LoginHelper.getLoginUser();

    // 不是全选且不是管理员需要走数据权限
    LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authDataLambdaQueryWrapper
      .eq(SysRoleAuthData::getModuleId, RoleAuthDataEnum.TEAM.getId()) // 仓库权限
      .eq(SysRoleAuthData::getUserId, loginUser.getUserId())
      .eq(SysRoleAuthData::getAuthValue, "1");
    // 获取生产班组ID
    var authList = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);
    var idList = new ArrayList<>(authList.stream().map(SysRoleAuthData::getNodeId).filter(B::isGreater).toList()); // 生产班组ID
    if (idList.isEmpty()) idList.add(0L);

    return idList;
  }

  /**
   * 获取货主权限
   *
   * @param pageQuery 查询条件
   */
  public void getConsignorAuth(PageQuery pageQuery) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.CONSIGNOR) && !loginUser.isAdministrator()) {
      var idList = this.getAuthConsignorId(); // 获取货主ID
      if (idList.isEmpty()) idList.add(0L);

      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("consignor_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(idList, ","));
      pageQuery.addQueryBo(queryBo);
    }
  }

  /**
   * 获取供应商权限
   *
   * @return 返回有权限的供应商ID
   */
  public ArrayList<Long> getAuthProviderId() {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);
    LoginUser loginUser = LoginHelper.getLoginUser();

    // 不是全选且不是管理员需要走数据权限
    LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authDataLambdaQueryWrapper
      .eq(SysRoleAuthData::getModuleId, RoleAuthDataEnum.PROVIDER.getId()) // 仓库权限
      .eq(SysRoleAuthData::getUserId, loginUser.getUserId())
      .eq(SysRoleAuthData::getAuthValue, "1");
    // 获取仓库ID
    var authList = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);
    var idList = new ArrayList<>(authList.stream().map(SysRoleAuthData::getNodeId).filter(B::isGreater).toList()); // 仓库ID
    if (idList.isEmpty()) idList.add(0L);

    return idList;
  }


  /**
   * 获取类别权限
   *
   * @return 返回有权限的类别D
   */
  @Override
  public List<Long> getTypeId() {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);
    LoginUser loginUser = LoginHelper.getLoginUser();

    // 不是全选且不是管理员需要走数据权限
    LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authDataLambdaQueryWrapper
      .eq(SysRoleAuthData::getModuleId, RoleAuthDataEnum.TYPE.getId()) // 仓库权限
      .eq(SysRoleAuthData::getUserId, loginUser.getUserId())
      .eq(SysRoleAuthData::getAuthValue, "1");
    // 获取仓库ID
    var authList = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);
    var idList = new ArrayList<>(authList.stream().map(SysRoleAuthData::getNodeId).filter(B::isGreater).toList()); // 仓库ID
    if (idList.isEmpty()) idList.add(0L);

    return idList.stream().toList();
  }

  /**
   * 供应商权限
   *
   * @param wrapper 查询包装器
   * @param <T>     泛型实体
   */
  public <T> void getProviderAuth(QueryWrapper<T> wrapper) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.PROVIDER) && !loginUser.isAdministrator()) {
      var idList = this.getAuthProviderId(); // 获取货主ID
      if (idList.isEmpty()) idList.add(0L);

      wrapper.in("provider_id", idList);
    }
  }

  /**
   * 获取供应商权限
   *
   * @param pageQuery 查询条件
   */
  public void getProviderAuth(PageQuery pageQuery) {
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.PROVIDER) && !LoginHelper.isAdministrator()) {
      var idList = this.getAuthProviderId(); // 获取货主ID
      if (idList.isEmpty()) idList.add(0L);

      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("provider_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(idList, ","));
      pageQuery.addQueryBo(queryBo);
    }
  }

  /**
   * 只看自己权限
   *
   * @param pageQuery 查询条件
   * @param <T>       泛型实体
   */
  public <T> void getSelfAuth(PageQuery pageQuery) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    ISysRoleAuthService sysRoleAuthService = SpringUtils.getBean(ISysRoleAuthService.class);
    ISysMenuService sysMenuService = SpringUtils.getBean(ISysMenuService.class);

    if (ObjectUtil.isNull(pageQuery.getMenuId())) return;
    MenuEnum menuEnum = MenuEnum.getEnumById(pageQuery.getMenuId());
    SysMenu sysMenu = sysMenuService.getMenuById(menuEnum);
    // 没有只看自己的权限点不用走，直接跳出
    if (ObjectUtil.isNull(sysMenu) || !StringUtils.contains(sysMenu.getPerms(), "onlySelf")) return;

    List<Long> roleIds = loginUser.getRoles().stream().map(RoleDTO::getRoleId).toList();
    // 全部权限
    LambdaQueryWrapper<SysRoleAuth> authLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authLambdaQueryWrapper
      .in(SysRoleAuth::getRoleId, roleIds) // 角色ID
      .eq(SysRoleAuth::getMenuId, pageQuery.getMenuId()); // 菜单ID

    List<SysRoleAuth> authList = sysRoleAuthService.list(authLambdaQueryWrapper);

    // 不是全选且不是管理员需要走数据权限
    if (CollUtil.isNotEmpty(authList) && !loginUser.isAdministrator()) {
      if (authList.stream().anyMatch(item -> item.getAuthValue().contains("onlySelf=1"))) {
        QueryBo queryBo = new QueryBo();
        queryBo.setQueryType(QueryTypeEnum.EQ);
        queryBo.setColumn("create_by");
        queryBo.setDataType(DataTypeEnum.LONG);
        queryBo.setValues(Convert.toStr(loginUser.getUserId()));
        pageQuery.addQueryBo(queryBo);
      }
    }
  }

  @Override
  public String getDropDownWhere(Long dropDownId) {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);
    String where = "";
    switch (Convert.toInt(dropDownId)) {
      case 797, 897 -> {
        // 获得货主权限
        ArrayList<Long> authConsignorIds = this.getAuthConsignorId();
        if (authConsignorIds.isEmpty()) authConsignorIds = CollUtil.newArrayList(0L);
        where = " consignor_id IN (" + StringUtils.join(authConsignorIds, ",") + ")";
      } // 仓库ID
      case 31, 561 -> {
        // 获得仓库权限
        ArrayList<Long> authStorageIds = this.getAuthStorageId();
        if (authStorageIds.isEmpty()) authStorageIds = CollUtil.newArrayList(0L);
        where = " storage_id IN (" + StringUtils.join(authStorageIds, ",") + ")";
      }
      case 33 -> {
        ArrayList<Long> authProviderIds = this.getAuthProviderId();
        if (authProviderIds.isEmpty()) authProviderIds = CollUtil.newArrayList(0L);
        where = " provider_id IN (" + StringUtils.join(authProviderIds, ",") + ")";
      }
      default -> where = "1=1";
    }

    return where;
  }

  @Override
  public void getTreeWhere(LoadTreeBo loadTreeBo, List<QueryBo> queryBoList) {
    if (loadTreeBo.getTableName().equals("sys_dept")) {// 获得部门权限
      ArrayList<Long> authConsignorIds = this.getAuthConsignorId();
      if (authConsignorIds.isEmpty()) authConsignorIds = CollUtil.newArrayList(0L);
      var queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("consignor_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(authConsignorIds, ","));
      queryBoList.add(queryBo);
    }
  }

  /**
   * 商品类别权限
   *
   * @param wrapper 查询包装器
   * @param <T>     泛型实体
   */
  public <T> void getTypeAuth(QueryWrapper<T> wrapper) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.TYPE) && !loginUser.isAdministrator()) {
      var idList = this.getAuthTeamId(); // 获取生产班组ID
      if (idList.isEmpty()) idList.add(0L);

      wrapper.in("type_id", idList);
    }
  }

  /**
   * 商品类别权限
   *
   * @param pageQuery 查询条件
   */
  public void getTypeAuth(PageQuery pageQuery) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.TYPE) && !loginUser.isAdministrator()) {
      var idList = this.getAuthTeamId(); // 获取仓库ID
      if (idList.isEmpty()) idList.add(0L);

      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("team_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(idList, ","));
      pageQuery.addQueryBo(queryBo);
    }
  }

  /**
   * 司机权限
   *
   * @param pageQuery 查询条件
   * @param isUserId  是否使用用户Id 作为权限配置
   */
  public void getDriverAuth(PageQuery pageQuery, boolean isUserId) {

    // 不是全选 需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.DRIVER)) {
      var driverIds = this.getDriverAuth(RoleAuthDataEnum.DRIVER); // 获取 司机Id
      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn(isUserId ? "user_id" : "driver_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(driverIds, ","));
      pageQuery.addQueryBo(queryBo);


    }
    if (!isAllAuth(RoleAuthDataEnum.VEHICLE)) {
      var vehicleIds = this.getDriverAuth(RoleAuthDataEnum.VEHICLE); // 获取 车辆Id
      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn(isUserId ? "user_id" : "vehicle_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(vehicleIds, ","));
      pageQuery.addQueryBo(queryBo);
    }
  }

  /**
   * 获取PDA 配置的权限
   *
   * @return 返回有权限的Id
   */
  public ArrayList<Long> getDriverAuth(RoleAuthDataEnum roleAuthDataEnum) {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);
    LoginUser loginUser = LoginHelper.getLoginUser();

    // 不是全选且不是管理员需要走数据权限
    LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authDataLambdaQueryWrapper
      .eq(SysRoleAuthData::getModuleId, roleAuthDataEnum.getId()) //ID
      .eq(SysRoleAuthData::getUserId, loginUser.getUserId())
      .eq(SysRoleAuthData::getAuthValue, "1");
    var authList = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);
    var idList = new ArrayList<>(authList.stream().map(SysRoleAuthData::getNodeId).filter(B::isGreater).toList());
    if (idList.isEmpty()) idList.add(0L);

    return idList;
  }

  /**
   * 获取承运商权限
   *
   * @param pageQuery 查询条件
   */
  public void getCarrierAuth(PageQuery pageQuery) {
    // 不是全选且不是管理员需要走数据权限
    if (!isAllAuth(RoleAuthDataEnum.CARRIER) && !LoginHelper.isAdministrator()) {
      var idList = this.getAuthCarrierId(); // 获取承运商ID
      if (idList.isEmpty()) idList.add(0L);

      QueryBo queryBo = new QueryBo();
      queryBo.setQueryType(QueryTypeEnum.IN);
      queryBo.setColumn("carrier_id");
      queryBo.setDataType(DataTypeEnum.LONG);
      queryBo.setValues(StringUtils.join(idList, ","));
      pageQuery.addQueryBo(queryBo);
    }
  }

  /**
   * 获取承运商ID
   *
   * @return 返回有权限的承运商ID
   */
  public ArrayList<Long> getAuthCarrierId() {
    sysRoleAuthDataService = SpringUtils.getBean(ISysRoleAuthDataService.class);

    // 不是全选且不是管理员需要走数据权限
    LambdaQueryWrapper<SysRoleAuthData> authDataLambdaQueryWrapper = new LambdaQueryWrapper<>();
    authDataLambdaQueryWrapper
      .eq(SysRoleAuthData::getModuleId, RoleAuthDataEnum.CARRIER.getId()) // 承运商权限
      .eq(SysRoleAuthData::getUserId, LoginHelper.getUserId())
      .eq(SysRoleAuthData::getAuthValue, "1");
    // 获取仓库ID
    var authList = sysRoleAuthDataService.getBaseMapper().selectList(authDataLambdaQueryWrapper);
    var idList = new ArrayList<>(authList.stream().map(SysRoleAuthData::getNodeId).filter(B::isGreater).toList()); // 承运商ID
    if (idList.isEmpty()) idList.add(0L);

    return idList;
  }
}
