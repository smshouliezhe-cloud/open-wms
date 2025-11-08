package com.yiruantong.common.mongodb.utils;

import cn.hutool.json.JSONUtil;
import com.anwen.mongo.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MongoUtil {
  private static BaseMapper staticBaseMapper;

  @Autowired
  public MongoUtil(BaseMapper baseMapper) {
    MongoUtil.staticBaseMapper = baseMapper;
  }

  /**
   * 单挑实体保存
   *
   * @param t   实体数据
   * @param <T> 实体
   */
  public static <T> void addLog(T t) {
    try {
      staticBaseMapper.save(t);
    } catch (Exception e) {
      log.error("mongodb日志错误：" + e.getMessage() + "，map=" + JSONUtil.toJsonStr(t));
    }
  }

  /**
   * 实体集合批量保存
   *
   * @param list 实体集合
   * @param <T>  实体
   */
  public static <T> void addLogList(List<T> list) {
    try {
      staticBaseMapper.saveBatch(list);
    } catch (Exception e) {
      log.error("mongodb日志错误：" + e.getMessage() + "，map=" + JSONUtil.toJsonStr(list));
    }
  }

  /**
   * 单挑保存日志Map<String, Object>
   *
   * @param collectionName 集合名称（也就是表名称）
   * @param map            行数据
   */
  public static void addLog(String collectionName, Map<String, Object> map) {
    try {
      staticBaseMapper.save(collectionName, map);
    } catch (Exception e) {
      log.error("mongodb日志错误：" + e.getMessage() + "，map=" + JSONUtil.toJsonStr(map));
    }
  }

  /**
   * 批量保存日志List<Map<String, Object>>
   *
   * @param collectionName 集合名称（也就是表名称）
   * @param mapList        行数据集合
   */
  public static void addLogList(String collectionName, List<Map<String, Object>> mapList) {
    try {
      staticBaseMapper.saveBatch(collectionName, mapList);
    } catch (Exception e) {
      log.error("mongodb日志错误：" + e.getMessage() + "，map=" + JSONUtil.toJsonStr(mapList));
    }
  }
}
