package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutOrderPickingDetailComposeVo extends OutOrderPickingDetailVo implements Serializable {
  /**
   * 拣货单编号
   */
  @ExcelProperty(value = "拣货单编号")
  private String orderPickingCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;


  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;


  /**
   * 拣货人
   */
  @ExcelProperty(value = "拣货人")
  private String nickName;

  /**
   * 开始时间
   */
  @ExcelProperty(value = "开始时间")
  private Date startDate;


  /**
   * 结束时间
   */
  @ExcelProperty(value = "结束时间")
  private Date endDate;

  /**
   * 持续时间
   */
  @ExcelProperty(value = "持续时间")
  private String spanTime;


  /**
   * 拣货状态
   */
  @ExcelProperty(value = "拣货状态")
  private String pickingStatus;

  /**
   * 波次单号
   */
  @ExcelProperty(value = "波次单号")
  private String orderWaveCode;
}
