package com.yiruantong.inventory.domain.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ProduceDateDto {
  /**
   * 生产日期
   */
  Date produceDate;
}
