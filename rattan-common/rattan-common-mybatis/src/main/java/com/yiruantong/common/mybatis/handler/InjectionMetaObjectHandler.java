package com.yiruantong.common.mybatis.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.yiruantong.common.satoken.utils.LoginHelper;

import java.util.Date;

/**
 * MP注入处理器
 *
 * @author YiRuanTong
 * @date 2021/4/25
 */
@Slf4j
public class InjectionMetaObjectHandler implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    try {
      if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
        baseEntity.setCreateTime(new Date());
        baseEntity.setUpdateTime(new Date());
        LoginUser loginUser = getLoginUser();
        if (ObjectUtil.isNotNull(loginUser)) {
          // 用户ID
          baseEntity.setCreateBy(loginUser.getUserId());
          // 用户名
          baseEntity.setCreateByName(loginUser.getNickname());
        }
      }
    } catch (Exception e) {
      throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    try {
      if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof TenantEntity tenantEntity) {
        LoginUser loginUser = getLoginUser();
        // 当前已登录 更新人填充(不管为不为空)
        if (ObjectUtil.isNotNull(loginUser)) {
          tenantEntity.setTenantId(loginUser.getTenantId());
        }
      }

      if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
        // 更新时间填充(不管为不为空)
        baseEntity.setUpdateTime(new Date());
        LoginUser loginUser = getLoginUser();
        // 当前已登录 更新人填充(不管为不为空)
        if (ObjectUtil.isNotNull(loginUser)) {
          baseEntity.setUpdateBy(loginUser.getUserId());
          baseEntity.setUpdateByName(loginUser.getNickname());
        }
      }
    } catch (Exception e) {
      throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
    }
  }

  /**
   * 获取登录用户名
   */
  private LoginUser getLoginUser() {
    LoginUser loginUser;
    try {
      loginUser = LoginHelper.getLoginUser();
    } catch (Exception e) {
      log.warn("自动注入警告 => 用户未登录");
      return null;
    }
    return loginUser;
  }

}
