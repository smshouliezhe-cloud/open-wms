package com.yiruantong.common.core.service;

import java.util.List;

/**
 * 通用 用户服务
 *
 * @author YiRuanTong
 */
public interface UserService {

  /**
   * 通过用户ID查询用户账户
   *
   * @param userId 用户ID
   * @return 用户账户
   */
  String selectUserNameById(Long userId);

  /**
   * 通过用户ID查询用户昵称
   *
   * @param userId 用户ID
   * @return 用户昵称
   */
  String selectNickNameById(Long userId);

  /**
   * 通过用户ID查询用户所有角色名，逗号分隔
   *
   * @param userId 用户ID
   * @return 角色名，逗号分隔
   */
  String selectRoleNamesById(Long userId);

  /**
   * 返回用户角色ID
   *
   * @param userId 用户ID
   * @return 发挥角色ID集合
   */
  List<Long> selectRoleListByUserId(long userId);

  /**
   * 返回用户岗位ID
   *
   * @param userId 用户ID
   * @return 发挥角色ID集合
   */
  List<Long> selectPostListByUserId(long userId);
}
