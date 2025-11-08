package com.yiruantong.composite.service.inventory;

import jakarta.servlet.http.HttpServletRequest;
import com.yiruantong.common.core.domain.model.LoginUser;

public interface IStatStorageDayCompositeService {

  /**
   * 生成每日库存
   *
   * @param request   请求参数req
   * @param loginUser 登录用户信息
   */
  void currentDayStorage(HttpServletRequest request, LoginUser loginUser);
}
