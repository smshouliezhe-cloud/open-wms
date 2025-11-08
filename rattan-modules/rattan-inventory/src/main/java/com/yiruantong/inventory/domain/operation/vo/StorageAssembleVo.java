package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageAssemble;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 商品拆装单视图对象 storage_assemble
 *
 * @author YRT
 * @date 2024-01-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageAssemble.class)
public class StorageAssembleVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 组装单ID
   */
  @ExcelProperty(value = "组装单ID")
  private Long assembleId;

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
   * 合计出库数量
   */
  @ExcelProperty(value = "合计出库数量")
  private BigDecimal totalOuterQuantity;

  /**
   * 合计出库金额
   */
  @ExcelProperty(value = "合计出库金额")
  private BigDecimal totalOuterAmount;

  /**
   * 合计入库数量
   */
  @ExcelProperty(value = "合计入库数量")
  private BigDecimal totalEnterQuantity;

  /**
   * 合计入库金额
   */
  @ExcelProperty(value = "合计入库金额")
  private BigDecimal totalEnterAmount;

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
   * 主商品数量
   */
  @ExcelProperty(value = "主商品数量")
  private BigDecimal mainQuantity;

  /**
   * 主商品编号
   */
  @ExcelProperty(value = "主商品编号")
  private String mainProductCode;

  /**
   * 主商品名称
   */
  @ExcelProperty(value = "主商品名称")
  private String mainProductName;


}
