package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageEnter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 其他入库单视图对象 storage_enter
 *
 * @author YRT
 * @date 2024-11-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageEnter.class)
public class StorageEnterVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 其他入库单ID
   */
  @ExcelProperty(value = "其他入库单ID")
  private Long enterId;

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
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalEnterQuantity;

  /**
   * 合计金额
   */
  @ExcelProperty(value = "合计金额")
  private BigDecimal totalAmount;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 合计价税
   */
  @ExcelProperty(value = "合计价税")
  private BigDecimal totalRateAmount;

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
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * 毛重合计
   */
  @ExcelProperty(value = "毛重合计")
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 关联费用项
   */
  @ExcelProperty(value = "关联费用项")
  private String feeItemIds;

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


}
