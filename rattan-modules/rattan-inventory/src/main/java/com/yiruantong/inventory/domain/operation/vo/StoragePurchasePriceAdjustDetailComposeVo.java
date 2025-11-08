package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class StoragePurchasePriceAdjustDetailComposeVo extends StorageOuterDetailVo implements Serializable {
  /**
   * 成本价调整单号
   */
  @ExcelProperty(value = "成本价调整单号")
  private String purchasePriceAdjustCode;

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
   * 调整日期
   */
  @ExcelProperty(value = "调整日期")
  private Date applyDate;

  /**
   * 调整状态
   */
  @ExcelProperty(value = "调整状态")
  private String adjustStatus;

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
   * 合计毛重
   */
  @ExcelProperty(value = "合计毛重")
  private BigDecimal totalWeight;

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
  private Long sourceId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;


  /**
   * 分拣时间
   */
  @ExcelProperty(value = "分拣时间")
  private Date sortingDate;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Byte sortingStatus;

}
