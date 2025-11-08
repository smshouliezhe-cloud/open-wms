package com.yiruantong.common.mybatis.core.domain.bo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SaveEditorBo<B> {
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
  private List<Long> idValueList;

  /**
   * 模块ID
   */
  private int menuId;

  /**
   * 表名
   */
  private String tableName;

  /**
   * 路由前缀
   */
  private String prefixRouter;

  /**
   * 编码字段
   */
  private String codeRegular;

  /**
   * 是否新建
   */
  private boolean isAdd;

  /**
   * 空只不做更新操作
   */
  private boolean fieldStrategy;

  /**
   * 其他数据
   */
  private Map<String, Object> otherData;

  /**
   * 明细表信息
   */
  private List<EditorDetailBo> detailParams;

  /**
   * 保存数据结构
   */
  private SaveDataBo<B> data;
}
