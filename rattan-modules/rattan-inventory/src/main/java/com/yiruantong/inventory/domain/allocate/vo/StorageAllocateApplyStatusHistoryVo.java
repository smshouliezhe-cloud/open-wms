package com.yiruantong.inventory.domain.allocate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyStatusHistory;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 调拨申请单轨迹视图对象 storage_allocate_apply_status_history
 *
 * @author YRT
 * @date 2023-12-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageAllocateApplyStatusHistory.class)
public class StorageAllocateApplyStatusHistoryVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 轨迹ID
   */
  @ExcelProperty(value = "轨迹ID")
  private Long historyId;

  /**
   * 调拨ID
   */
  @ExcelProperty(value = "调拨ID")
  private Long allocateApplyId;

  /**
   * 状态类型
   */
  @ExcelProperty(value = "状态类型")
  private String statusType;

  /**
   * 操作类型
   */
  @ExcelProperty(value = "操作类型")
  private String operationType;

  /**
   * 变更前状态
   */
  @ExcelProperty(value = "变更前状态")
  private String fromStatus;

  /**
   * 变更后状态
   */
  @ExcelProperty(value = "变更后状态")
  private String toStatus;

  /**
   * 单据ID
   */
  @ExcelProperty(value = "单据ID")
  private Long billId;

  /**
   * 单据号
   */
  @ExcelProperty(value = "单据号")
  private String billCode;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

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


}
