package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.ErpOutOrder;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 其他出库单视图对象 erp_out_order
 *
 * @author YRT
 * @date 2024-06-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ErpOutOrder.class)
public class ErpOutOrderVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 其他出库单ID
   */
  @ExcelProperty(value = "其他出库单ID")
  private Long outerId;

  /**
   * 出库单号
   */
  @ExcelProperty(value = "出库单号")
  private String outerCode;

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
   * 出库日期
   */
  @ExcelProperty(value = "出库日期")
  private Date applyDate;

  /**
   * 客户ID
   */
  @ExcelProperty(value = "客户ID")
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
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalOuterQuantity;

  /**
   * 合计金额
   */
  @ExcelProperty(value = "合计金额")
  private BigDecimal totalAmount;

  /**
   * 优惠额度
   */
  @ExcelProperty(value = "优惠额度")
  private BigDecimal favourAmount;

  /**
   * 优惠后金额
   */
  @ExcelProperty(value = "优惠后金额")
  private BigDecimal factAmount;

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
   * 物件数量
   */
  @ExcelProperty(value = "物件数量")
  private BigDecimal materialCount;

  /**
   * 包装方式
   */
  @ExcelProperty(value = "包装方式")
  private String packageMode;

  /**
   * 出库状态
   */
  @ExcelProperty(value = "出库状态")
  private String outerStatus;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Byte sortingStatus;

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
  private Byte auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

  /**
   * 审核备注
   */
  @ExcelProperty(value = "审核备注")
  private String auditRemark;

  /**
   * 退货状态
   */
  @ExcelProperty(value = "退货状态")
  private String returnStatus;

  /**
   * 日期
   */
  @ExcelProperty(value = "日期")
  private Date date;

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
   * 货位类型
   */
  @ExcelProperty(value = "货位类型")
  private Byte positionType;

  /**
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * 合计毛重
   */
  @ExcelProperty(value = "合计毛重")
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 一次性收费项
   */
  @ExcelProperty(value = "一次性收费项")
  private String feeItemIds;

  /**
   * 其他出库打印次数
   */
  @ExcelProperty(value = "其他出库打印次数")
  private Long printCount;

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
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQtyTotal;


}
