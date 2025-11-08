package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;

import java.io.Serializable;
import java.util.List;

@Data
@ExcelIgnoreUnannotated
public class BasePlateOutDetailMergeVo implements Serializable {
  private BasePlateOut order;

  private List<BasePlateOutDetail> details;

  /**
   * 仓库所属网点id
   */
  private Long siteId;
  /**
   * 仓库所属网点
   */
  private String siteName;
}
