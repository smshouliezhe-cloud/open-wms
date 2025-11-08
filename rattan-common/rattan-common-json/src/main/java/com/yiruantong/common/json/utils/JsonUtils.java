package com.yiruantong.common.json.utils;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON 工具类
 *
 * @author 芋道源码
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils extends JSONUtil {

  private static final ObjectMapper OBJECT_MAPPER = SpringUtils.getBean(ObjectMapper.class);

  public static ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }

  public static String toJsonString(Object object) {
    if (ObjectUtil.isNull(object)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T parseObject(String text, Class<T> clazz) {
    if (StringUtils.isEmpty(text)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(text, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
    if (ArrayUtil.isEmpty(bytes)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(bytes, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T parseObject(String text, TypeReference<T> typeReference) {
    if (StringUtils.isBlank(text)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(text, typeReference);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Dict parseMap(String text) {
    if (StringUtils.isBlank(text)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructType(Dict.class));
    } catch (MismatchedInputException e) {
      // 类型不匹配说明不是json
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 转为Map<String, Object>
   *
   * @param text JSON字符串
   * @return Map<String, Object>
   */
  public static Map<String, Object> toMap(String text) {
    Map<String, Object> map = new HashMap<>();
    // 最外层解析
    JSONObject json = JsonUtils.parseObj(text);
    for (var k : json.keySet()) {
      Object v = json.get(k);
      // 如果内层还是数组的话，继续解析
      if (v instanceof JSONArray) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (var item : (JSONArray) v) {
          JSONObject json2 = (JSONObject) item;
          list.add(toMap(json2.toString()));
        }
        map.put(k, list);
      } else {
        map.put(k, v);
      }
    }
    return map;
  }

  public static List<Dict> parseArrayMap(String text) {
    if (StringUtils.isBlank(text)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Dict.class));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> List<T> parseArray(String text, Class<T> clazz) {
    if (StringUtils.isEmpty(text)) {
      return new ArrayList<>();
    }
    try {
      return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
