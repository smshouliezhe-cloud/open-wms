package com.yiruantong.common.satoken.utils;

import cn.dev33.satoken.temp.SaTempUtil;
import com.yiruantong.common.core.enums.system.TokenTypeEum;

import java.util.HashMap;
import java.util.Map;

/**
 * 临时登录token加密
 */
public class TempTokenUtil {
  public static String getTempToken(TokenTypeEum tokenTypeEum, String redirect, String username, String phoneNumber, long timeout) {
    Map<String, String> map = new HashMap<>();
    map.put("tenantId", LoginHelper.getTenantId());
    map.put("tokenType", tokenTypeEum.getName());
    map.put("redirect", redirect);
    map.put("username", username);
    map.put("phoneNumber", phoneNumber);

    return SaTempUtil.createToken(map, timeout);
  }
}
