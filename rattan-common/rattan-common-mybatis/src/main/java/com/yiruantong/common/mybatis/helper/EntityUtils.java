package com.yiruantong.common.mybatis.helper;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yiruantong.common.core.utils.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

public class EntityUtils {

  public static String getPrimaryKey(Object et) {
    // 反射获取实体类
    Class<?> clazz = et.getClass();
    // 不含有表名的实体就默认通过
    if (!clazz.isAnnotationPresent(TableName.class)) {
      return null;
    }
    // 获取表名
    TableName tableName = clazz.getAnnotation(TableName.class);
    String tbName = tableName.value();
    if (StringUtils.isBlank(tbName)) {
      return null;
    }
    String pkName = null;
    String pkValue = null;
    // 获取实体所有字段
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      // 设置些属性是可以访问的
      field.setAccessible(true);
      if (field.isAnnotationPresent(TableId.class)) {
        // 获取主键
        pkName = field.getName();
        try {
          // 获取主键值
          pkValue = field.get(et).toString();
        } catch (Exception e) {
          pkValue = null;
        }

      }
    }
    return pkValue;
  }
}
