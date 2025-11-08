package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageCheck;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 盘点单视图对象 storage_check
 *
 * @author YRT
 * @date 2023-10-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageCheck.class)
public class StorageCheckVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 盘点单ID
   */
  @ExcelProperty(value = "盘点单ID")
  private Long checkId;

  /**
   * 盘点单编号
   */
  @ExcelProperty(value = "盘点单编号")
  private String checkCode;

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
   * 盘点日期
   */
  @ExcelProperty(value = "盘点日期")
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
   * 盘点状态
   */
  @ExcelProperty(value = "盘点状态")
  private String checkStatus;

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
   * 审核人
   */
  @ExcelProperty(value = "审核人")
  private String auditor;

  /**
   * 审核
   */
  @ExcelProperty(value = "审核")
  private Long auditing;

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
   * 盘点单类型
   */
  @ExcelProperty(value = "盘点单类型")
  private String checkType;

  /**
   * 差异天数
   */
  @ExcelProperty(value = "差异天数")
  private Long diffDate;

  /**
   * 是否盲盘
   */
  @ExcelProperty(value = "是否盲盘")
  private Long isBlind;

  /**
   * 盈亏单ID
   */
  @ExcelProperty(value = "盈亏单ID")
  private Long profitLossId;

  /**
   * 原始盘点单ID
   */
  @ExcelProperty(value = "原始盘点单ID")
  private Long fromCheckId;

  /**
   * 原始盘点单编号
   */
  @ExcelProperty(value = "原始盘点单编号")
  private String fromCheckCode;

  /**
   * 库区
   */
  @ExcelProperty(value = "库区")
  private String areaCode;

  /**
   * 账面库存毛重合计
   */
  @ExcelProperty(value = "账面库存毛重合计")
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


}
