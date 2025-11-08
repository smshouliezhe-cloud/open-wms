package com.yiruantong.common.mybatis.core.page;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.sql.SqlUtil;
import com.yiruantong.common.mybatis.core.domain.bo.EditorDetailBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.dto.SumColumnNameBo;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 分页查询实体类
 *
 * @author YiRuanTong
 */

@Data
public class PageQuery implements Serializable {

  /**
   * 当前记录起始索引 默认值
   */
  public static final int DEFAULT_PAGE_NUM = 1;
  /**
   * 每页显示记录数 默认值 默认查15条
   */
  public static final int DEFAULT_PAGE_SIZE = 15;
  @Serial
  private static final long serialVersionUID = 1L;
  /**
   * 查询条件集合
   */
  List<QueryBo> queryBoList;
  /**
   * 求和字段
   */
  List<SumColumnNameBo> sumColumnNames;
  /**
   * 明细集合
   */
  List<EditorDetailBo> detailParams;
  /**
   * 模块ID
   */
  private Integer menuId;
  /**
   * 路由前缀
   */
  private String prefixRouter;
  /**
   * 分页大小
   */
  private Integer pageSize;
  /**
   * 当前页数
   */
  private Integer pageIndex;
  /**
   * 排序列
   */
  private String orderByColumn;
  /**
   * 表名称
   */
  private String tableName;
  /**
   * 自定义方法
   */
  private String listMethod;
  /**
   * 排序的方向desc或者asc
   */
  private String isAsc;
  /**
   * 查询字段，多个字段用逗号分隔
   */
  private String selectColumns;
  /**
   * 其他附加参数
   */
  private Map<String, Object> otherParams;
  /**
   * 查询表实体class列表
   */
  private List<Class<?>> fieldClassList;
  /**
   * 自定义排序函数
   */
  private Function<String, String> customOrderBy;
  /**
   * 主键字段名
   */
  private String idField;
  /**
   * 是否是PDA 查询
   */
  private boolean isPda;

  /**
   * 新增查询条件
   *
   * @param queryBo 查询条件项
   * @return 返回查询条件集合
   */
  public List<QueryBo> addQueryBo(QueryBo queryBo) {
    if (ObjectUtil.isNull(queryBoList)) {
      queryBoList = new ArrayList<>();
    }
    queryBoList.add(queryBo);

    return queryBoList;
  }

  /**
   * 构建分页
   *
   * @param <T> 泛型实体
   * @return 返回分页
   */
  public <T> Page<T> build() {
    Integer pageIndex = ObjectUtil.defaultIfNull(getPageIndex(), DEFAULT_PAGE_NUM);
    Integer pageSize = ObjectUtil.defaultIfNull(getPageSize(), DEFAULT_PAGE_SIZE);
    if (pageIndex <= 0) {
      pageIndex = DEFAULT_PAGE_NUM;
    }
    Page<T> page = new Page<>(pageIndex, pageSize);
    List<OrderItem> orderItems = buildOrderItem();
    if (CollUtil.isNotEmpty(orderItems)) {
      page.addOrder(orderItems);
    }
    if (ObjectUtil.isNull(queryBoList)) {
      queryBoList = new ArrayList<>();
    }
    return page;
  }

  /**
   * 构建排序
   * <p>
   * 支持的用法如下:
   * {isAsc:"asc",orderByColumn:"id"} order by id asc
   * {isAsc:"asc",orderByColumn:"id,createTime"} order by id asc,create_time asc
   * {isAsc:"desc",orderByColumn:"id,createTime"} order by id desc,create_time desc
   * {isAsc:"asc,desc",orderByColumn:"id,createTime"} order by id asc,create_time desc
   */
  private List<OrderItem> buildOrderItem() {
    if (StringUtils.isBlank(orderByColumn) || StringUtils.isBlank(isAsc)) {
      return null;
    }
    String orderBy = SqlUtil.escapeOrderBySql(orderByColumn);
    orderBy = StringUtils.toUnderScoreCase(orderBy);

    // 兼容前端排序类型
    isAsc = StringUtils.replaceEach(isAsc, new String[]{"ascending", "descending"}, new String[]{"asc", "desc"});

    String[] orderByArr = orderBy.split(StringUtils.SEPARATOR_COMMA);
    String[] isAscArr = isAsc.split(StringUtils.SEPARATOR_COMMA);
    if (isAscArr.length != 1 && isAscArr.length != orderByArr.length) {
      throw new ServiceException("排序参数有误");
    }

    List<OrderItem> list = new ArrayList<>();
    // 每个字段各自排序
    for (int i = 0; i < orderByArr.length; i++) {
      String orderByStr = orderByArr[i];
      orderByStr = getTableAlias(orderByStr);
      String isAscStr = isAscArr.length == 1 ? isAscArr[0] : isAscArr[i];

      if (customOrderBy != null) {
        // 自定义排序字段
        orderByStr = customOrderBy.apply(orderByStr);
        if ("asc".equalsIgnoreCase(isAscStr)) {
          list.add(OrderItem.asc(orderByStr));
          continue;
        } else if ("desc".equalsIgnoreCase(isAscStr)) {
          list.add(OrderItem.desc(orderByStr));
          continue;
        }
      }
      if ("asc".equalsIgnoreCase(isAscStr)) {
        list.add(OrderItem.asc(orderByStr));
      } else if ("desc".equalsIgnoreCase(isAscStr)) {
        list.add(OrderItem.desc(orderByStr));
      } else {
        throw new ServiceException("排序参数有误");
      }
    }
    return list;
  }

  //#region getTableAlias
  private String getTableAlias(String finalColumnName) {
    String fieldName = StringUtils.toCamelCase(finalColumnName);
    String tableAlias = StringUtils.EMPTY;
    int index = 0;
    if (ObjectUtil.isEmpty(this.fieldClassList)) return finalColumnName;

    for (var fieldClass : this.fieldClassList) {
      List<Field> fieldList = TableInfoHelper.getAllFields(fieldClass);
      if (index > 0 && fieldList.stream().anyMatch(a -> a.getName().equals(fieldName))) {
        tableAlias = "t" + index + ".";
        break;
      } else if (fieldList.stream().anyMatch(a -> a.getName().equals(fieldName))) {
        tableAlias = "t.";
        break;
      }
      index++;
    }

    return tableAlias + finalColumnName;
  }
  //#endregion

}
