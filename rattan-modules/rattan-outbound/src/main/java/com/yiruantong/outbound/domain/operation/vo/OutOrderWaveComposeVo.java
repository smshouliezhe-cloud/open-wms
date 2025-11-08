package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 出库单波次视图对象 out_order_wave
 *
 * @author YRT
 * @date 2024-08-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OutOrderWaveComposeVo extends OutOrderWaveVo implements Serializable {

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long subId;

  /**
   * 子波次单号
   */
  @ExcelProperty(value = "子波次单号")
  private String subOrderWaveCode;

  /**
   * 库区
   */
  @ExcelProperty(value = "库区")
  private String areaCode;

  /**
   * 拣货人ID
   */
  @ExcelProperty(value = "主单拣货人ID")
  private Long mainPickUserId;

  /**
   * 拣货人
   */
  @ExcelProperty(value = "主单拣货人")
  private String mainPickNickName;

  /**
   * 子波次状态
   */
  @ExcelProperty(value = "子波次状态")
  private String subWaveStatus;
}
