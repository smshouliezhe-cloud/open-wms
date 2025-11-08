package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.inventory.domain.helper.model.Type;

import java.util.List;

/**
 * 按商品类别 TypeInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class TypeInfo {

  /**
   * 类别
   */
  private List<Type> types;

  /**
   * 是否选中
   */
  private Boolean isSelect;

}
