package com.yiruantong.common.translation.core.impl;

import lombok.AllArgsConstructor;
import com.yiruantong.common.core.service.UserService;
import com.yiruantong.common.translation.annotation.TranslationType;
import com.yiruantong.common.translation.constant.TransConstant;
import com.yiruantong.common.translation.core.TranslationInterface;

/**
 * 角色名翻译实现
 *
 * @author YiRuanTong
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.ROLE_IDS_TO_NAMES)
public class RoleNamesTranslationImpl implements TranslationInterface<String> {

  private final UserService userService;

  @Override
  public String translation(Object key, String other) {
    if (key instanceof Long userId) {
      return userService.selectRoleNamesById(userId);
    }
    return null;
  }
}
