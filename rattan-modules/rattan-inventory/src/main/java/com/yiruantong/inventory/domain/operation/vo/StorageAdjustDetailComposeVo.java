package com.yiruantong.inventory.domain.operation.vo;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 库存调整单明细视图对象 storage_adjust
 *
 * @author YRT
 * @date 2023-12-14
 */
@Data
public class StorageAdjustDetailComposeVo extends StorageAdjustDetailVo implements Serializable {

  /**
   * 调整单编号
   */
  @ExcelProperty(value = "调整单编号")
  private String adjustCode;

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
   * 校验类型
   */
  @ExcelProperty(value = "校验类型")
  private String checkType;

  /**
   * 盘点日期
   */
  @ExcelProperty(value = "盘点日期")
  private Date applyDate;

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
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String adjustStatus;

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
   * 审核备注
   */
  @ExcelProperty(value = "审核备注")
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

}

