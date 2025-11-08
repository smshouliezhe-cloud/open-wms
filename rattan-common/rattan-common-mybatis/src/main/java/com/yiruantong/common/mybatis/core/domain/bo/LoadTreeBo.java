package com.yiruantong.common.mybatis.core.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.dto.OrderbyBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;

import java.util.List;

@Data
@NoArgsConstructor
public class LoadTreeBo {
  /**
   * 路由前缀
   */
  private String prfixRouter;

  /**
   * 表名
   */
  private String tableName;

  /**
   * 主键名称
   */
  private String keyName;

  /**
   * 主键值
   */
  private String keyValue;

  /**
   * 节点名称
   */
  private String nodeName;

  /**
   * 固定有子节点
   */
  private boolean fixHasChild;

  /**
   * 显示树节点外数据
   */
  private boolean showOutsideNode;

  /**
   * 父节点字段
   */
  private String parentName;

  /**
   * 排序字段
   */
  private String orderBy;

  /**
   * 扩展字段
   */
  private String extendColumns;

  /**
   * 查询条件
   */
  private List<QueryBo> whereList;

  /**
   * 子集固定查询条件
   */
  private List<QueryBo> subWhereList;

  /**
   * 查询条件
   */
  private List<OrderbyBo> orderByList;
}
