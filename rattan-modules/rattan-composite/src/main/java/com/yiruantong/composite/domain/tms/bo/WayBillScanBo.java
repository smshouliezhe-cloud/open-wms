package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  前端运单扫描数据BO
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WayBillScanBo {
  /**
   * 车辆ID
   */
  private Long vehicleId;

  /**
   * 派车单号
   */
  private String distributionCode;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 司机姓名
   */
  private String driverName;

  /**
   * 是否新建
   */
  private Boolean isAdd;

  /**
   * 扫描明细
   */
  List<WayBillScanTableBo> tableData;

}
