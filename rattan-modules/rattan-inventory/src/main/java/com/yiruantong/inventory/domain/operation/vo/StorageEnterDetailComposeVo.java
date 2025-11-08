package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class StorageEnterDetailComposeVo extends StorageEnterDetailVo implements Serializable {

  /**
   * 其他入库单编号
   */
  @ExcelProperty(value = "其他入库单编号")
  private String enterCode;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

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
   * 入库日期
   */
  @ExcelProperty(value = "入库日期")
  private Date applyDate;

  /**
   * 采购商ID
   */
  @ExcelProperty(value = "采购商ID")
  private Long providerId;

  /**
   * 采购商编号
   */
  @ExcelProperty(value = "采购商编号")
  private String providerCode;

  /**
   * 采购商简称
   */
  @ExcelProperty(value = "采购商简称")
  private String providerShortName;


  /**
   * 付款期限
   */
  @ExcelProperty(value = "付款期限")
  private Date payLimitDate;

  /**
   * 入库状态
   */
  @ExcelProperty(value = "入库状态")
  private String enterStatus;


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
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;


  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;


  /**
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源ID
   */
  @ExcelProperty(value = "来源ID")
  private String sourceId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

}
