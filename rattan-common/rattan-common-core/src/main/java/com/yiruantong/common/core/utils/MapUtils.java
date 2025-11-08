package com.yiruantong.common.core.utils;

import cn.hutool.core.map.MapUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串工具类
 *
 * @author YiRuanTong
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapUtils extends MapUtil {
  /**
   * 复制map对象
   *
   * @param paramsMap 被拷贝对象
   * @param resultMap 拷贝后的对象
   * @explain 将paramsMap中的键值对全部拷贝到resultMap中；
   * paramsMap中的内容不会影响到resultMap（深拷贝）
   */
  public static void mapCopy(Map<String, Object> resultMap, Map<String, Object> paramsMap) {
    if (resultMap == null) resultMap = new HashMap<>();
    if (paramsMap == null) return;

    for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
      String key = entry.getKey();
      resultMap.put(key, paramsMap.get(key) != null ? paramsMap.get(key) : "");
    }
  }

}
