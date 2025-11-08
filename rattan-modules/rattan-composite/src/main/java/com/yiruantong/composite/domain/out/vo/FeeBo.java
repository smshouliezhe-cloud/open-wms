package com.yiruantong.composite.domain.out.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FeeBo {
  /**
   * 时间
   */
  private String times;

  //数据信息
  private List<TableDataBo> tableData;

  /**
   * 仓库Id
   */
  private Long storageId;

  /**
   * 月份
   */
  private Long month;

  /**
   * 页面是否显示全部
   */
  private boolean showAll;

}

