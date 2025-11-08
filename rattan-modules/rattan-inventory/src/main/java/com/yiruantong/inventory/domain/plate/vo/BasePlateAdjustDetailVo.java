package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateAdjustDetail;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 容器调整明细视图对象 base_plate_adjust_detail
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateAdjustDetail.class)
public class BasePlateAdjustDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器调整明细Id
   */
  @ExcelProperty(value = "容器调整明细Id")
  private Long adjustDetailId;

  /**
   * 容器调整Id
   */
  @ExcelProperty(value = "容器调整Id")
  private Long adjustId;

  /**
   * 容器类别
   */
  @ExcelProperty(value = "容器类别")
  private String plateType;

  /**
   * 现借出数量
   */
  @ExcelProperty(value = "现借出数量")
  private Long nowOutQty;

  /**
   * 调整借出数量
   */
  @ExcelProperty(value = "调整借出数量")
  private Long adjustOutQty;

  /**
   * 调整归还数量
   */
  @ExcelProperty(value = "调整归还数量")
  private Long adjustReturnQty;

  /**
   * 剩余借出数量
   */
  @ExcelProperty(value = "剩余借出数量")
  private Long surplusOutQty;

  /**
   * SN
   */
  @ExcelProperty(value = "SN")
  private String singleSignCode;

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
   * 容器规格
   */
  @ExcelProperty(value = "容器规格")
  private String plateSpec;

  /**
   * 容器名称
   */
  @ExcelProperty(value = "容器名称")
  private String plateName;


}
