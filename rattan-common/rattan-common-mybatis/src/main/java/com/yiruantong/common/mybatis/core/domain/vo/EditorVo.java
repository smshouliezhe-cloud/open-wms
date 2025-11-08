package com.yiruantong.common.mybatis.core.domain.vo;

import lombok.Data;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 编辑页面视图类
 *
 * @author YiRuanTong
 */
@Data
public class EditorVo<V> implements Serializable {

  /**
   * 明细集合
   */
  List<TableDataInfo> detailList;
  /**
   * 其他数据
   */
  Map<String, Object> otherData;
  /**
   * 主表数据
   */
  private V master;
}
