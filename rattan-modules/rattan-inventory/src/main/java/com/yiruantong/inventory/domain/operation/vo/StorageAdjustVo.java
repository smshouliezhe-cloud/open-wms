package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageAdjust;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 库存调整单视图对象 storage_adjust
 *
 * @author YRT
 * @date 2024-11-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageAdjust.class)
public class StorageAdjustVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 调整单ID
   */
  @ExcelProperty(value = "调整单ID")
  private Long adjustId;

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
   * 合计账面库存量
   */
  @ExcelProperty(value = "合计账面库存量")
  private BigDecimal totalProductStorage;

  /**
   * 合计账面成本额
   */
  @ExcelProperty(value = "合计账面成本额")
  private BigDecimal totalPurchaseAmount;

  /**
   * 合计盘点数量
   */
  @ExcelProperty(value = "合计盘点数量")
  private BigDecimal totalCheckQuantity;

  /**
   * 合计盘盈数量
   */
  @ExcelProperty(value = "合计盘盈数量")
  private BigDecimal totalProfitQuantity;

  /**
   * 合计盘盈金额
   */
  @ExcelProperty(value = "合计盘盈金额")
  private BigDecimal totalProfitAmount;

  /**
   * 合计盘亏数量
   */
  @ExcelProperty(value = "合计盘亏数量")
  private BigDecimal totalLossQuantity;

  /**
   * 合计盘亏金额
   */
  @ExcelProperty(value = "合计盘亏金额")
  private BigDecimal totalLossAmount;

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
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private Byte auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

  /**
   * 审核
   */
  @ExcelProperty(value = "审核")
  private String auditor;

  /**
   * 分拣日期
   */
  @ExcelProperty(value = "分拣日期")
  private Date sortingDate;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Byte sortingStatus;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String auditRemark;

  /**
   * 合计毛重
   */
  @ExcelProperty(value = "合计毛重")
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
   * 审核备注
   */
  @ExcelProperty(value = "审核备注")
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
  private Long sourceId;

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
