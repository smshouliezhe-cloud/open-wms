package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageValidAdjustDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 效期信息调整明细视图对象 storage_valid_adjust_detail
 *
 * @author YRT
 * @date 2023-12-05
 */
@Data
public class StorageValidAdjustDetailComposeVo extends StorageValidAdjustDetailVo implements Serializable {

  /**
   * 效期调整编号
   */
  @ExcelProperty(value = "效期调整编号")
  private String validAdjustCode;

  /**
   * 调整仓库ID
   */
  @ExcelProperty(value = "调整仓库ID")
  private Long storageId;

  /**
   * 调整仓库
   */
  @ExcelProperty(value = "调整仓库")
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
   * 调整状态
   */
  @ExcelProperty(value = "调整状态")
  private String adjustStatus;

  /**
   * 调整日期
   */
  @ExcelProperty(value = "调整日期")
  private Date adjustDate;

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
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

}
