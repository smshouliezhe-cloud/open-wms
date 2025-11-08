package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品拆装单入库明细视图对象 storage_assemble_enter_detail
 *
 * @author YRT
 * @date 2023-10-24
 */
@Data
public class StorageAssembleEnterDetailComposeVo extends StorageAssembleEnterDetailVo implements Serializable {
  /**
   * 组装单编号
   */
  @ExcelProperty(value = "组装单编号")
  private String assembleCode;

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
   * 组装日期
   */
  @ExcelProperty(value = "组装日期")
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
   * 组装状态
   */
  @ExcelProperty(value = "组装状态")
  private String assembleStatus;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Long sortingStatus;

  /**
   * 分拣日期
   */
  @ExcelProperty(value = "分拣日期")
  private Date sortingDate;

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
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

}
