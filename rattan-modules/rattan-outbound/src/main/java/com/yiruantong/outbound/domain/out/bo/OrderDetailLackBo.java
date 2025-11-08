package com.yiruantong.outbound.domain.out.bo;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 缺货转遇到货 Bo
 *
 * @author QZH
 * @date 2023-12-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDetailLackBo extends BaseEntity {

  /**
   * 销售明细ID
   */
  @ExcelProperty(value = "明细ID")
  private Long orderDetailId;

  /**
   * 供应商ID
   */
  @ExcelProperty(value = "供应商ID")
  private Long providerId;

  /**
   * 供应商编号
   */
  @ExcelProperty(value = "供应商编号")
  private String providerCode;

  /**
   * 供应商名称
   */
  @ExcelProperty(value = "供应商名称")
  private String providerShortName;

  /**
   * 出货仓库ID
   */
  @ExcelProperty(value = "出货仓库ID")
  private Long storageId;

  /**
   * 出货仓库名称
   */
  @ExcelProperty(value = "出货仓库名称")
  private String storageName;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 货主编号
   */
  @ExcelProperty(value = "货主编号")
  private String consignorCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;
  /**
   * 出库编号
   */
  @ExcelProperty(value = "出库编号")
  private String orderCode;

  /**
   * 经手人ID
   */
  @ExcelProperty(value = "经手人ID")
  private Long userId;

  /**
   * 经手人
   */
  @ExcelProperty(value = "经手人")
  private String nickName;

  /**
   * 部门ID
   */
  @ExcelProperty(value = "部门ID")
  private Long deptId;

  /**
   * 部门
   */
  @ExcelProperty(value = "部门")
  private String deptName;

  /**
   * 采购数量
   */
  @ExcelProperty(value = "采购数量")
  private BigDecimal purchaseStorage;

  /**
   * 折扣单价
   */
  private BigDecimal purchasePrice;

}
