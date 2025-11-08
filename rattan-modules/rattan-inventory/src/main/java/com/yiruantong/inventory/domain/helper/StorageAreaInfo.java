package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 按库区推荐 StorageAreaInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class StorageAreaInfo {

  /**
   * 库区
   */
  private List<String> areas;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}
