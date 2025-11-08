package com.yiruantong.inventory.domain.allocate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 调拨入库单视图对象 storage_allocate_enter
 *
 * @author YRT
 * @date 2023-12-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageAllocateEnter.class)
public class StorageAllocateEnterVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 调拨单ID
   */
  @ExcelProperty(value = "调拨单ID")
  private Long allocateEnterId;

  /**
   * 调拨单编号
   */
  @ExcelProperty(value = "调拨单编号")
  private String allocateEnterCode;

  /**
   * 调拨申请单ID
   */
  @ExcelProperty(value = "调拨申请单ID")
  private Long allocateApplyId;

  /**
   * 调拨申请单编号
   */
  @ExcelProperty(value = "调拨申请单编号")
  private String allocateApplyCode;

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
   * 入库日期
   */
  @ExcelProperty(value = "入库日期")
  private Date enterDate;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 入库名称
   */
  @ExcelProperty(value = "入库名称")
  private String storageName;

  /**
   * 调入仓库ID
   */
  @ExcelProperty(value = "调入仓库ID")
  private Long storageIdIn;

  /**
   * 调入仓库名称
   */
  @ExcelProperty(value = "调入仓库名称")
  private String storageNameIn;

  /**
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal enterQuantity;

  /**
   * 合计金额
   */
  @ExcelProperty(value = "合计金额")
  private BigDecimal totalAmount;

  /**
   * 调拨状态
   */
  @ExcelProperty(value = "调拨状态")
  private String enterStatus;

  /**
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核
   */
  @ExcelProperty(value = "审核")
  private Byte auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

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
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

  /**
   * 毛重合计
   */
  @ExcelProperty(value = "毛重合计")
  private BigDecimal totalWeight;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

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

  /**
   * 合计净重
   */
  @ExcelProperty(value = "合计净重")
  private BigDecimal totalNetWeight;

  /**
   * 含税金额
   */
  @ExcelProperty(value = "含税金额")
  private BigDecimal taxAmount;


}
