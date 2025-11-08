package com.yiruantong.common.mybatis.core.page;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yiruantong.common.core.constant.DbConstants;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.dto.OrderbyBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.OrderByTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.mybatis.helper.DataBaseHelper;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuildWrapperHelper {
  //#region createWrapper

  /**
   * 组装查询条件 增加枚举类型条件筛选转换
   *
   * @return
   */
  public static <T> QueryWrapper<T> createWrapper(List<QueryBo> queryBoList) {
    return createWrapper(queryBoList, null);
  }
  //endregion

  //#region createWrapper

  /**
   * 组装查询条件 增加枚举类型条件筛选转换
   *
   * @return
   */
  public static <T> QueryWrapper<T> createWrapper(List<QueryBo> queryBoList, Class<T> modelClass) {
    QueryWrapper<T> queryWrapper = Wrappers.query();
    if (queryBoList == null || queryBoList.isEmpty()) {
      return queryWrapper;
    }
    // 获取所有字段，包含父类字段
    List<Field> fieldList = new ArrayList<>();

    if (modelClass != null) {
      Class tempClass = modelClass;
      while (tempClass != null) { // 当父类为null的时候说明到达了最上层的父类(Object类).
        fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
        tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
      }
    }
    createQueryBoList(queryBoList, queryWrapper, fieldList);
    return queryWrapper;
  }
  //endregion

  //#region createQueryBoList

  /**
   * 处理查询条件
   *
   * @param queryBoList  前端查询条件
   * @param queryWrapper mybatis-plus查询条件
   * @param <T>          实体对象
   */
  public static <T> void createQueryBoList(List<QueryBo> queryBoList, QueryWrapper<T> queryWrapper) {
    createQueryBoList(queryBoList, queryWrapper, null);
  }
  //endregion

  //#region createQueryBoList

  /**
   * 处理查询条件
   *
   * @param queryBoList  前端查询条件
   * @param queryWrapper mybatis-plus查询条件
   * @param fieldList    可查询字段
   * @param <T>          实体对象
   */
  public static <T> void createQueryBoList(List<QueryBo> queryBoList, QueryWrapper<T> queryWrapper, List<Field> fieldList) {
    queryBoList.forEach(
      condition -> {
        try {
          String column = condition.getColumn();
          String columnName = StrUtil.toUnderlineCase(column);
          String values = condition.getValues();

          if (ObjectUtil.isNotNull(fieldList)) {
            Class type;
            Field field = fieldList.stream()
              .filter(field1 -> field1.getName().equals(column))
              .findFirst()
              .orElse(null);
//            Assert.isFalse(ObjectUtil.isNull(field), column + "字段无效");
            if (ObjectUtil.isNotEmpty(field)) {
              type = field.getType();
              // 如果为枚举则需把值转换成枚举值【数据库里的值】
              if (EnumUtil.isEnum(type)) {
                List<String> stringValList = StrUtil.split(values, StrUtil.C_COMMA, true, true);
                Class finalType = type;
                List<Serializable> collect =
                  stringValList.stream()
                    .map(val -> {
                      IEnum anEnum = (IEnum) EnumUtil.fromString(finalType, val);
                      return anEnum.getValue();
                    })
                    .collect(Collectors.toList());
                values = CollUtil.join(collect, ",");
              }
            }
          }

          Object _values = convertData(condition);
          if (condition.isOr() && queryWrapper.isNonEmptyOfWhere()) {
            queryWrapper.or(); // or查询
          }
          List<Object> valList;
          switch (condition.getQueryType()) {
            case SELECT ->
              queryWrapper.select(StringUtils.splitList(condition.getValues()).stream().map(StringUtils::toUnderScoreCase).toList());
            case EQ -> queryWrapper.eq(StrUtil.isNotBlank(values), columnName, _values);
            case NE -> queryWrapper.ne(StrUtil.isNotBlank(values), columnName, _values);
            case GT -> queryWrapper.gt(StrUtil.isNotBlank(values), columnName, _values);
            case GE -> queryWrapper.ge(StrUtil.isNotBlank(values), columnName, _values);
            case LT -> queryWrapper.lt(StrUtil.isNotBlank(values), columnName, _values);
            case LE -> queryWrapper.le(StrUtil.isNotBlank(values), columnName, _values);
            case LIKE -> queryWrapper.like(StrUtil.isNotBlank(values), columnName, _values);
            case NOTLIKE -> queryWrapper.notLike(StrUtil.isNotBlank(values), columnName, _values);
            case LIKELEFT -> queryWrapper.likeLeft(StrUtil.isNotBlank(values), columnName, _values);
            case LIKERIGHT -> queryWrapper.likeRight(StrUtil.isNotBlank(values), columnName, _values);
            case BETWEEN -> {
              valList = Arrays.asList(StrUtil.split(values, StrUtil.C_COMMA, true, true).toArray());
              if (isNumber(condition)) {
                valList = Arrays.asList(valList.stream().map(Convert::toBigDecimal).toArray());
              } else if (condition.getDataType() == DataTypeEnum.DATETIME || condition.getDataType() == DataTypeEnum.DATE) {
                valList = Arrays.asList(valList.stream().map(Convert::toDate).toArray());
              }
              queryWrapper.between(NumberUtil.equals(valList.size(), 2), columnName, valList.get(0), valList.get(1));
            }
            case NOTBETWEEN -> {
              valList = Arrays.asList(StrUtil.split(values, StrUtil.C_COMMA, true, true).toArray());
              if (isNumber(condition)) {
                valList = Arrays.asList(valList.stream().map(Convert::toBigDecimal).toArray());
              } else if (condition.getDataType() == DataTypeEnum.DATETIME || condition.getDataType() == DataTypeEnum.DATE) {
                valList = Arrays.asList(valList.stream().map(Convert::toDate).toArray());
              }
              queryWrapper.notBetween(NumberUtil.equals(valList.size(), 2), columnName, valList.get(0), valList.get(1));
            }
            case ISNULL -> queryWrapper.isNull(values);
            case ISNOTNULL -> queryWrapper.isNotNull(columnName);
            case ORDERBYASC -> queryWrapper.orderByAsc(StrUtil.toUnderlineCase(values));
            case ORDERBYDESC -> queryWrapper.orderByDesc(StrUtil.toUnderlineCase(values));
            case IN -> {
              valList = Arrays.asList(StrUtil.split(values, StrUtil.C_COMMA, true, true).toArray());
              if (isNumber(condition)) {
                valList = Arrays.asList(valList.stream().map(Convert::toBigDecimal).toArray());
              } else if (condition.getDataType() == DataTypeEnum.DATETIME || condition.getDataType() == DataTypeEnum.DATE) {
                valList = Arrays.asList(valList.stream().map(Convert::toDate).toArray());
              }
              queryWrapper.in(!valList.isEmpty(), columnName, valList);
            }
            case NOTIN -> {
              valList = Arrays.asList(StrUtil.split(values, StrUtil.C_COMMA, true, true).toArray());
              if (isNumber(condition)) {
                valList = Arrays.asList(valList.stream().map(Convert::toBigDecimal).toArray());
              } else if (condition.getDataType() == DataTypeEnum.DATETIME || condition.getDataType() == DataTypeEnum.DATE) {
                valList = Arrays.asList(valList.stream().map(Convert::toDate).toArray());
              }
              queryWrapper.notIn(!valList.isEmpty(), columnName, valList);
            }
            case SUBQUERY ->
              // 嵌套处理查询AND子查询条件
              queryWrapper.and(sub -> createQueryBoList(condition.getSubQueryBo(), sub, fieldList));
            case EXISTS -> queryWrapper.exists(values);
            case NOTEXISTS -> queryWrapper.notExists(values);
            case TREE -> {
              List<String> strings = StringUtils.splitList(columnName);
              String tableName = StringUtils.toUnderScoreCase(strings.get(0));
              String keyName = StringUtils.toUnderScoreCase(strings.get(1));
              var ids = DBUtils.getSelfAndChildId(tableName, keyName, values);
              queryWrapper.in(keyName, ids);
            }
            case EXPANDFIELDS -> {
              final List<String> stringList = StringUtils.splitList(values);
              String apply = "";
              for (String value : stringList) {
                if (StringUtils.isNotEmpty(apply)) {
                  apply += " or ";
                }
                apply += DataBaseHelper.jsonValue(DbConstants.EXPAND_FIELDS, StringUtils.toCamelCase(columnName), value);
              }
              apply = "(" + apply + ")";
              queryWrapper.apply(apply); // 拼接多个值
            }
            default -> {
            }
          }
        } catch (Exception e) {
          Console.log(queryBoList);
          throw new RuntimeException("查询条件错误，" + e.getMessage());
        }
      });
  }
  //endregion

  //#region convertData

  /**
   * 数据类型转换
   *
   * @param condition 查询条件对象
   * @return
   */
  public static Object convertData(QueryBo condition) {
    String values = condition.getValues();
    Object _values = values;
    // 数据类型转换
    switch ((condition.getDataType())) {
      case INT:
      case INTEGER:
        _values = Convert.toInt(values);
        break;
      case FLOAT:
        _values = Convert.toFloat(values);
        break;
      case DOUBLE:
        _values = Convert.toDouble(values);
        break;
      case LONG:
        _values = Convert.toLong(values);
      case BIGDECIMAL:
        _values = Convert.toBigDecimal(values);
        break;
      case DATETIME:
      case DATE:
        _values = Convert.toDate(values);
        break;
      default:
        break;
    }

    return _values;
  }
  //endregion

  //#region mpjWrapperQuery

  /**
   * 查询条件的拼接
   *
   * @param queryBoList    查询条件
   * @param wrapper        查询对象
   * @param fieldClassList 反射字段
   * @return
   */
  public static <T> void mpjWrapperQuery(List<QueryBo> queryBoList, MPJLambdaWrapper<T> wrapper, Class<?>... fieldClassList) {

    for (QueryBo queryBo : queryBoList) {
      if (queryBo.getQueryType() == QueryTypeEnum.SUBQUERY) {
        // 嵌套处理查询AND子查询条件
        wrapper.and(sub -> {
          for (var subItem : queryBo.getSubQueryBo()) {
            sub.or();
            String finalColumnName = subItem.getColumn();
            String tableAlias = StringUtils.isNotEmpty(subItem.getTableAlias()) ? subItem.getTableAlias() + "." : "t.";

            getTableAlias(sub, subItem, finalColumnName, tableAlias, fieldClassList);
          }
        });
      } else {
        String finalColumnName = queryBo.getColumn();
        String tableAlias = StringUtils.isNotEmpty(queryBo.getTableAlias()) ? queryBo.getTableAlias() + "." : "t.";
        getTableAlias(wrapper, queryBo, finalColumnName, tableAlias, fieldClassList);
      }
    }
  }
  //endregion

  //#region getTableAlias
  private static <T> void getTableAlias(MPJLambdaWrapper<T> sub, QueryBo subItem, String finalColumnName, String tableAlias, Class<?>[] fieldClassList) {
    // 指定别名
    if (StringUtils.isNotEmpty(subItem.getTableAlias())) {
      tableAlias = subItem.getTableAlias() + ".";
    } else {
      int index = 0;
      for (var fieldClass : fieldClassList) {
        List<Field> fieldList = TableInfoHelper.getAllFields(fieldClass);
        String fieldName = StringUtils.toCamelCase(finalColumnName);
        if (index > 0 && fieldList.stream().anyMatch(a -> a.getName().equals(fieldName))) {
          tableAlias = "t" + index + ".";
          break;
        } else if (fieldList.stream().anyMatch(a -> a.getName().equals(fieldName))) {
          tableAlias = "t.";
          break;
        }
        index++;
      }
    }

    String columnName = StrUtil.toUnderlineCase(subItem.getColumn());
    String values = subItem.getValues();
    Object _values = BuildWrapperHelper.convertData(subItem);
    List<Object> valList;
    switch (subItem.getQueryType()) {
      case EQ -> sub.eq(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case NE -> sub.ne(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case GT -> sub.gt(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case GE -> sub.ge(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case LT -> sub.lt(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case LE -> sub.le(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case LIKE -> sub.like(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case NOTLIKE -> sub.notLike(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case LIKELEFT -> sub.likeLeft(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case LIKERIGHT -> sub.likeRight(StrUtil.isNotBlank(values), tableAlias + columnName, _values);
      case BETWEEN -> {
        valList = Arrays.asList(StrUtil.split(values, StrUtil.C_COMMA, true, true).toArray());
        if (isNumber(subItem)) {
          valList = Arrays.asList(valList.stream().map(Convert::toBigDecimal).toArray());
        } else if (subItem.getDataType() == DataTypeEnum.DATETIME || subItem.getDataType() == DataTypeEnum.DATE) {
          valList = Arrays.asList(valList.stream().map(Convert::toDate).toArray());
        }
        sub.between(NumberUtil.equals(valList.size(), 2), tableAlias + columnName, valList.get(0), valList.get(1));
      }
      case NOTBETWEEN -> {
        valList = Arrays.asList(StrUtil.split(values, StrUtil.C_COMMA, true, true).toArray());
        if (isNumber(subItem)) {
          valList = Arrays.asList(valList.stream().map(Convert::toBigDecimal).toArray());
        } else if (subItem.getDataType() == DataTypeEnum.DATETIME || subItem.getDataType() == DataTypeEnum.DATE) {
          valList = Arrays.asList(valList.stream().map(Convert::toDate).toArray());
        }
        sub.notBetween(NumberUtil.equals(valList.size(), 2), tableAlias + columnName, valList.get(0), valList.get(1));
      }
      case ISNULL -> sub.isNull(tableAlias + columnName);
      case ISNOTNULL -> sub.isNotNull(tableAlias + columnName);
      case ORDERBYASC -> sub.orderByAsc(StrUtil.toUnderlineCase(subItem.getColumn()));
      case ORDERBYDESC -> sub.orderByDesc(StrUtil.toUnderlineCase(subItem.getColumn()));
      case IN -> {
        valList = Arrays.asList(StrUtil.split(values, StrUtil.C_COMMA, true, true).toArray());
        if (isNumber(subItem)) {
          valList = Arrays.asList(valList.stream().map(Convert::toBigDecimal).toArray());
        } else if (subItem.getDataType() == DataTypeEnum.DATETIME || subItem.getDataType() == DataTypeEnum.DATE) {
          valList = Arrays.asList(valList.stream().map(Convert::toDate).toArray());
        }
        sub.in(!valList.isEmpty(), tableAlias + columnName, valList);
      }
      case NOTIN -> {
        valList = Arrays.asList(StrUtil.split(values, StrUtil.C_COMMA, true, true).toArray());
        if (isNumber(subItem)) {
          valList = Arrays.asList(valList.stream().map(Convert::toBigDecimal).toArray());
        } else if (subItem.getDataType() == DataTypeEnum.DATETIME || subItem.getDataType() == DataTypeEnum.DATE) {
          valList = Arrays.asList(valList.stream().map(Convert::toDate).toArray());
        }
        sub.notIn(!valList.isEmpty(), tableAlias + columnName, valList);
      }
      case GROUPBY -> sub.groupBy(tableAlias + columnName);
      case MAX -> sub.selectMax(m -> columnName, columnName);
      case MIN -> sub.selectMin(m -> columnName, columnName);
      case TREE -> {
        List<String> strings = StringUtils.splitList(columnName);
        String tableName = StringUtils.toUnderScoreCase(strings.get(0));
        String keyName = StringUtils.toUnderScoreCase(strings.get(1));
        var ids = DBUtils.getSelfAndChildId(tableName, keyName, values);
        sub.in(tableAlias + columnName, ids);
      }
      case EXPANDFIELDS -> {
        final List<String> stringList = StringUtils.splitList(values);
        String apply = "";
        for (String value : stringList) {
          if (StringUtils.isNotEmpty(apply)) {
            apply += " or ";
          }
          apply += DataBaseHelper.jsonValue(tableAlias + DbConstants.EXPAND_FIELDS, StringUtils.toCamelCase(columnName), value);
        }
        apply = "(" + apply + ")";
        sub.apply(apply); // 拼接多个值
      }
      default -> {
      }
    }
  }
  //endregion

  //#region isNumber

  /**
   * 判断是否为数字
   *
   * @param queryBo
   * @return
   */
  private static boolean isNumber(QueryBo queryBo) {
    return switch ((queryBo.getDataType())) {
      case INT, FLOAT, DOUBLE, LONG -> true;
      default -> false;
    };
  }
  //endregion

  //#region createOrderBy

  /**
   * 构建排序
   *
   * @param queryWrapper
   * @param orderByList
   * @param <T>
   * @return
   */
  public static <T> QueryWrapper<T> createOrderBy(
    QueryWrapper<T> queryWrapper, List<OrderbyBo> orderByList) {
    for (OrderbyBo orderbyBo : orderByList) {
      if (orderbyBo.getOrderByType() == OrderByTypeEnum.ASC) {
        queryWrapper.orderByAsc(StringUtils.toUnderScoreCase(orderbyBo.getColumn()));
      } else {
        queryWrapper.orderByDesc(StringUtils.toUnderScoreCase(orderbyBo.getColumn()));
      }
    }

    return queryWrapper;
  }
  //endregion

  //#region mpjWrapperGroup 构建分组查询

  /**
   * 查询条件的拼接
   *
   * @param selectFields   查询条件
   * @param sumFields      求和条件
   * @param groupFields    分组条件
   * @param wrapper        查询对象
   * @param fieldClassList 反射字段
   * @return
   */
  public static <T> void mpjWrapperGroup(String selectFields, String sumFields, String groupFields, MPJLambdaWrapper<T> wrapper, Class<?>... fieldClassList) {
    List<String> selectFieldList = StringUtils.splitList(selectFields);
    List<String> sumFieldList = StringUtils.splitList(sumFields);
    List<String> groupFieldList = StringUtils.splitList(groupFields);

    // 构建查询字段
    for (String field : selectFieldList) {
      String column = StringUtils.toUnderScoreCase(field);

      boolean isExist = false;
      int index = 0;
      for (var fieldClass : fieldClassList) {
        List<Field> fieldList = TableInfoHelper.getAllFields(fieldClass);
        String tableAlias = "t.";
        if (index > 0) {
          tableAlias = "t" + index + ".";
        }
        if (StringUtils.contains(field, ".")) {
          List<String> fields = StringUtils.splitList(field, ".");
          tableAlias = fields.get(0) + ".";
          field = fields.get(1);
          column = StringUtils.toUnderScoreCase(fields.get(1));

        }
        String finalField = field;
        if (fieldList.stream().anyMatch(m -> StringUtils.equals(m.getName(), finalField))) {
          isExist = true;
          if (sumFieldList.stream().anyMatch(m -> StringUtils.equals(m, finalField))) {
            continue; // 求和字段跳过
          }
          if (groupFieldList.stream().anyMatch(m -> StringUtils.equals(m, finalField))) {

            wrapper.select(tableAlias + column + " AS " + field);
          } else {
            wrapper.select("MAX(" + tableAlias + column + ") AS " + field);
          }
        }
        if (isExist) break;
        index++;
      }
      if (!isExist) {
        throw new ServiceException(field + "查询字段不存在");
      }
    }

    // 构建求和字段
    for (String field : sumFieldList) {
      String column = StringUtils.toUnderScoreCase(field);
      boolean isExist = false;
      int index = 0;
      for (var fieldClass : fieldClassList) {
        List<Field> fieldList = TableInfoHelper.getAllFields(fieldClass);
        String tableAlias = "t.";
        if (index > 0) {
          tableAlias = "t" + index + ".";
        }
        if (StringUtils.contains(field, ".")) {
          List<String> fields = StringUtils.splitList(field, ".");
          tableAlias = fields.get(0) + ".";
          field = fields.get(1);
          column = StringUtils.toUnderScoreCase(fields.get(1));

        }
        String finalField = field;
        if (fieldList.stream().anyMatch(m -> StringUtils.equals(m.getName(), finalField))) {
          isExist = true;
          wrapper.select("SUM(" + tableAlias + column + ") AS " + field);
        }
        if (isExist) break;
        index++;
      }
      if (!isExist) {
        throw new ServiceException(field + "求和字段不存在");
      }
    }

    // 构建分组字段
    for (String field : groupFieldList) {
      String column = StringUtils.toUnderScoreCase(field);
      boolean isExist = false;
      int index = 0;
      for (var fieldClass : fieldClassList) {
        List<Field> fieldList = TableInfoHelper.getAllFields(fieldClass);
        String tableAlias = "t.";
        if (index > 0) {
          tableAlias = "t" + index + ".";
        }
        if (StringUtils.contains(field, ".")) {
          List<String> fields = StringUtils.splitList(field, ".");
          tableAlias = fields.get(0) + ".";
          field = fields.get(1);
          column = StringUtils.toUnderScoreCase(fields.get(1));

        }
        String finalField = field;
        if (fieldList.stream().anyMatch(m -> StringUtils.equals(m.getName(), finalField))) {
          isExist = true;
          wrapper.groupBy(tableAlias + column);
        }
        if (isExist) break;
        index++;
      }
      if (!isExist) {
        throw new ServiceException(field + "分组字段不存在");
      }
    }
  }
  //endregion
}
