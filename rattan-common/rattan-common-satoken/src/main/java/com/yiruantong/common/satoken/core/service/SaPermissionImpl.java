package com.yiruantong.common.satoken.core.service;

import cn.dev33.satoken.stp.StpInterface;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.user.UserTypeEnum;
import com.yiruantong.common.satoken.utils.LoginHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token 权限管理实现类
 *
 * @author YiRuanTong
 */
public class SaPermissionImpl implements StpInterface {

  /**
   * 获取菜单权限列表
   */
  @Override
  public List<String> getPermissionList(Object loginId, String loginType) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    UserTypeEnum userTypeEnum = LoginHelper.getUserType();
    if (userTypeEnum == UserTypeEnum.SYS_USER) {
      return new ArrayList<>(loginUser.getMenuPermission());
    } else if (userTypeEnum == UserTypeEnum.APP_USER) {
      // 其他端 自行根据业务编写
    }
    return new ArrayList<>();
  }

  /**
   * 获取角色权限列表
   */
  @Override
  public List<String> getRoleList(Object loginId, String loginType) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    UserTypeEnum userTypeEnum = UserTypeEnum.getUserType(loginUser.getUserType());
    if (userTypeEnum == UserTypeEnum.SYS_USER) {
      return new ArrayList<>(loginUser.getRolePermission());
    } else if (userTypeEnum == UserTypeEnum.APP_USER) {
      // 其他端 自行根据业务编写
    }
    return new ArrayList<>();
  }
}
