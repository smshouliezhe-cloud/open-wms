package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;

import java.io.Serial;
import java.io.Serializable;


/**
 * 出库单波次明细视图对象 out_order_wave_detail
 *
 * @author YRT
 * @date 2023-12-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderWaveDetail.class)
public class OutOrderAndExpressVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  @ExcelProperty(value = "订单ID")
  private Long orderId;

  /**
   * 快递公司名称
   */
  @ExcelProperty(value = "快递公司名称")
  private String expressCorpName;

  /**
   * 打印模板ID
   */
  @ExcelProperty(value = "打印模板ID")
  private Long printTemplateId;

  /**
   * 模块ID
   */
  @ExcelProperty(value = "模块ID")
  private Long menuId;
}
