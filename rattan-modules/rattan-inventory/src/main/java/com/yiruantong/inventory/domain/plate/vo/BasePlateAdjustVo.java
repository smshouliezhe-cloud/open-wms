package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateAdjust;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 容器调整主视图对象 base_plate_adjust
 *
 * @author YRT
 * @date 2024-03-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateAdjust.class)
public class BasePlateAdjustVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器调整Id
   */
  @ExcelProperty(value = "容器调整Id")
  private Long adjustId;

  /**
   * 容器调整编号
   */
  @ExcelProperty(value = "容器调整编号")
  private String adjustCode;

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
   * 调整类型
   */
  @ExcelProperty(value = "调整类型")
  private String adjustType;

  /**
   * 调整状态
   */
  @ExcelProperty(value = "调整状态")
  private String statusText;

  /**
   * 合计归还数量
   */
  @ExcelProperty(value = "合计归还数量")
  private Long totalReturnQty;

  /**
   * 归还日期
   */
  @ExcelProperty(value = "归还日期")
  private Date returnDate;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核状态
   */
  @ExcelProperty(value = "审核状态")
  private Byte auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

  /**
   * 客户Id
   */
  @ExcelProperty(value = "客户Id")
  private Long clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户名称
   */
  @ExcelProperty(value = "客户名称")
  private String clientShortName;

  /**
   * 仓库Id
   */
  @ExcelProperty(value = "仓库Id")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 合计借出数量
   */
  @ExcelProperty(value = "合计借出数量")
  private BigDecimal totalNowOutQty;

  /**
   * 调整后数量
   */
  @ExcelProperty(value = "调整后数量")
  private BigDecimal totalAfterQty;

  /**
   * 调整借出数量
   */
  @ExcelProperty(value = "调整借出数量")
  private BigDecimal totalOutQty;


}
