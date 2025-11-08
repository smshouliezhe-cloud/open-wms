package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class StorageCheckDetailComposeVo extends StorageCheckDetailVo implements Serializable {

  /**
   * 盘点单编号
   */
  @ExcelProperty(value = "盘点单编号")
  private String checkCode;

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
   * 部门名称
   */
  @ExcelProperty(value = "部门名称")
  private String deptName;

  /**
   * 盘点日期
   */
  @ExcelProperty(value = "盘点日期")
  private Date applyDate;

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
   * 盘点状态
   */
  @ExcelProperty(value = "盘点状态")
  private String checkStatus;

  /**
   * 分拣日期
   */
  @ExcelProperty(value = "分拣日期")
  private Date sortingDate;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Long sortingStatus;

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
   * 盘点单类型
   */
  @ExcelProperty(value = "盘点单类型")
  private String checkType;

  /**
   * 是否盲盘
   */
  @ExcelProperty(value = "是否盲盘")
  private Long isBlind;
}
