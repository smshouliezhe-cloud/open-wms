package com.yiruantong.common.mybatis.core.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class EditorBo {
  /**
   * 主键字段名
   */
  private String idField;

  /**
   * 主键值
   */
  private Long idValue;

  /**
   * 主键值List
   */
  private List<Long> idValues;

  /**
   * 模块ID
   */
  private int menuId;

  /**
   * 表ID
   */
  private Long tableId;

  /**
   * 主键字段名
   */
  private String folder;

  /**
   * 路由前缀
   */
  private String prefixRouter;

  /**
   * 编码字段
   */
  private String codeRegular;

  /**
   * 明细集合
   */
  List<EditorDetailBo> detailParams;
}
