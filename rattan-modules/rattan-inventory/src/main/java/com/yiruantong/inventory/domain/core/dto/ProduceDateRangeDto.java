package com.yiruantong.inventory.domain.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ProduceDateRangeDto {
  /**
   * 生产日期开始
   */
  Date produceDateStart;
  /**
   * 生产日期结束
   */
  Date produceDateEnd;
}
