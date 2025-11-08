package com.yiruantong.common.rabbitmq.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;

import java.util.HashMap;

/**
 * rabbitmq数据结构
 */
@Data
@NoArgsConstructor
public class DoStepDto {
  /**
   * 其他参数
   */
  HashMap<String, Object> otherField;
  /**
   * 数据类型
   */
  private RabbitmqTypeEnum rabbitmqType;
  /**
   * 是否完成
   */
  private boolean isFinished;
  /**
   * 单据ID
   */
  private Long billId;
  /**
   * 单据号
   */
  private String billCode;
  /**
   * 来源ID
   */
  private String sourceId;
  /**
   * 来源单号
   */
  private String sourceCode;
}
