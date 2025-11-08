package com.yiruantong.system.service.core;

import com.yiruantong.common.mybatis.core.domain.bo.LoadTreeBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ICommonService {
  /**
   * 获取下拉框值
   *
   * @param queryBoList 下拉框ID集合
   * @return 返回下拉框值集合
   */
  Map<String, List<LinkedHashMap<String, Object>>> loadDropDown(List<QueryBo> queryBoList);

  /**
   * 获取PC模块权限
   *
   * @param params 获取权限参数
   * @return 返回权限集合
   */
  Map<String, Object> getAuthNode(Map<String, Object> params);

  /**
   * 获取AMIS权限
   *
   * @param menuId 模块ID
   * @return Map
   */
  Map<String, Boolean> getAuthNodeAmis(Long menuId);

  /**
   * 获取APP大模块权限集合
   *
   * @param params map参数
   * @return APP大模块权限集合
   */
  List<Map<String, Object>> getAppBigModules(Map<String, Object> params);

  /**
   * 获取APP模块权限
   *
   * @param params 获取权限参数
   * @return 返回权限集合
   */
  Map<String, Object> getAuthNodeApp(Map<String, Object> params);

  /**
   * 获取导入消息
   *
   * @param params
   * @return
   */
  List<String> getMsg(Map<String, Object> params);

  /**
   * 查询tree结构数据
   */
  List<Map<String, Object>> loadTreeNode(LoadTreeBo loadTreeBo);

  /**
   * 查询完整tree结构数据
   */
  List<Map<String, Object>> loadTreeNodeAll(LoadTreeBo loadTreeBo);

  /**
   * 执行原生sql语句
   *
   * @param sql 原生sql语句
   * @return boolean
   */
  boolean executeSql(String sql);

  /**
   * 执行SQL，返回List
   *
   * @param sql
   * @return
   */
  List<LinkedHashMap<String, Object>> selectList(String sql);
}
