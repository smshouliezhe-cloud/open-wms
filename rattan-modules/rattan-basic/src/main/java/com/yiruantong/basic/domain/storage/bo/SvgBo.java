package com.yiruantong.basic.domain.storage.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 货架数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SvgBo {


  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 库区
   */
  private String areaCode;

  /**
   * svg地址
   */
  private String url;
}
