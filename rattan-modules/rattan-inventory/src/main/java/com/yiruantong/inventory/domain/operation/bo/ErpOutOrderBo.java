package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.ErpOutOrder;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 其他出库单业务对象 erp_out_order
 *
 * @author YRT
 * @date 2024-06-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ErpOutOrder.class, reverseConvertGenerate = false)
public class ErpOutOrderBo extends BaseEntity {

  /**
   * 其他出库单ID
   */
  @NotNull(message = "其他出库单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outerId;

  /**
   * 出库单号
   */
  @NotBlank(message = "出库单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outerCode;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 经手人ID
   */
  @NotNull(message = "经手人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 经手人
   */
  @NotBlank(message = "经手人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 部门ID
   */
  @NotNull(message = "部门ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 部门
   */
  @NotBlank(message = "部门不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 出库日期
   */
  @NotNull(message = "出库日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date applyDate;

  /**
   * 客户ID
   */
  @NotNull(message = "客户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long clientId;

  /**
   * 客户编号
   */
  @NotBlank(message = "客户编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientCode;

  /**
   * 客户名称
   */
  @NotBlank(message = "客户名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String clientShortName;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalOuterQuantity;

  /**
   * 合计金额
   */
  @NotNull(message = "合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;

  /**
   * 优惠额度
   */
  @NotNull(message = "优惠额度不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal favourAmount;

  /**
   * 优惠后金额
   */
  @NotNull(message = "优惠后金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal factAmount;

  /**
   * 税率
   */
  @NotNull(message = "税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rate;

  /**
   * 合计价税
   */
  @NotNull(message = "合计价税不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalRateAmount;

  /**
   * 物件数量
   */
  @NotNull(message = "物件数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal materialCount;

  /**
   * 包装方式
   */
  @NotBlank(message = "包装方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packageMode;

  /**
   * 出库状态
   */
  @NotBlank(message = "出库状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outerStatus;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte sortingStatus;

  /**
   * 分拣日期
   */
  @NotNull(message = "分拣日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date sortingDate;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 审核备注
   */
  @NotBlank(message = "审核备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditRemark;

  /**
   * 退货状态
   */
  @NotBlank(message = "退货状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String returnStatus;

  /**
   * 日期
   */
  @NotNull(message = "日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date date;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 货位类型
   */
  @NotNull(message = "货位类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte positionType;

  /**
   * 订单类型
   */
  @NotBlank(message = "订单类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 合计毛重
   */
  @NotNull(message = "合计毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 一次性收费项
   */
  @NotBlank(message = "一次性收费项不能为空", groups = {AddGroup.class, EditGroup.class})
  private String feeItemIds;

  /**
   * 其他出库打印次数
   */
  @NotNull(message = "其他出库打印次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long printCount;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源ID
   */
  @NotBlank(message = "来源ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 合计净重
   */
  @NotNull(message = "合计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalNetWeight;

  /**
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQtyTotal;


}
