package com.yiruantong.common.mybatis.core.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yiruantong.common.core.enums.system.RoleAuthDataEnum;
import com.yiruantong.common.mybatis.core.domain.bo.LoadTreeBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xietb
 * @version 1.0.0
 * @date 2024-01-10
 * @description 数据权限
 */
public interface IDataAuthService {
  /**
   * 获取数据权限
   *
   * @param pageQuery 查询元数据
   * @param <T>       泛型实体
   */
  <T> void getDataAuth(PageQuery pageQuery);

  /**
   * 货主权限
   *
   * @param wrapper 查询包装器
   * @param <T>     泛型实体
   */
  <T> void getConsignorAuth(QueryWrapper<T> wrapper);

  /**
   * 货主权限
   *
   * @param pageQuery 查询条件
   * @param <T>       泛型实体
   */
  <T> void getConsignorAuth(PageQuery pageQuery);

  /**
   * 仓库权限
   *
   * @param wrapper 查询包装器
   * @param <T>     泛型实体
   */
  <T> void getStorageAuth(QueryWrapper<T> wrapper);

  /**
   * 仓库权限
   *
   * @param pageQuery 查询条件
   * @param <T>       泛型实体
   */
  <T> void getStorageAuth(PageQuery pageQuery);

  /**
   * 供应商权限
   *
   * @param wrapper 查询包装器
   * @param <T>     泛型实体
   */
  <T> void getProviderAuth(QueryWrapper<T> wrapper);

  /**
   * 供应商权限
   *
   * @param pageQuery 查询包装器
   * @param <T>       泛型实体
   */
  <T> void getProviderAuth(PageQuery pageQuery);

  /**
   * 获取下拉框权限
   *
   * @param dropDownId 下拉框ID
   * @return 返回下拉框权限
   */
  String getDropDownWhere(Long dropDownId);

  /**
   * 获取下拉框Tree权限
   *
   * @param loadTreeBo  tree加载条件
   * @param queryBoList 查询条件集合
   * @return 返回下拉框权限
   */
  void getTreeWhere(LoadTreeBo loadTreeBo, List<QueryBo> queryBoList);

  boolean isAllAuth(RoleAuthDataEnum roleAuthDataEnum);

  ArrayList<Long> getAuthTeamId();

  List<Long> getTypeId();
}
