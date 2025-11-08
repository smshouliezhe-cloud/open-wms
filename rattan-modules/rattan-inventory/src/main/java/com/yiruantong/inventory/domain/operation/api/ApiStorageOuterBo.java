package com.yiruantong.inventory.domain.operation.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 其他出库单业务对象 storage_outer
 *
 * @author YRT
 * @date 2024-11-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiStorageOuterBo {

  List<ApiStorageOuterDetailBo> detailList;

  /**
   * 其他出库单ID
   */
  private Long outerId;

  /**
   * 出库单号
   */
  private String outerCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 部门ID
   */
  private Long deptId;

  /**
   * 部门
   */
  private String deptName;

  /**
   * 出库日期
   */
  private Date applyDate;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户名称
   */
  private String clientShortName;

  /**
   * 合计数量
   */
  private BigDecimal totalOuterQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 优惠额度
   */
  private BigDecimal favourAmount;

  /**
   * 优惠后金额
   */
  private BigDecimal factAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 合计价税
   */
  private BigDecimal totalRateAmount;

  /**
   * 物件数量
   */
  private BigDecimal materialCount;

  /**
   * 包装方式
   */
  private String packageMode;

  /**
   * 出库状态
   */
  private String outerStatus;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 审核备注
   */
  private String auditRemark;

  /**
   * 退货状态
   */
  private String returnStatus;

  /**
   * 日期
   */
  private Date date;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 货位类型
   */
  private Long positionType;

  /**
   * 订单类型
   */
  @NotBlank(message = "订单类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 合计毛重
   */
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 一次性收费项
   */
  private String feeItemIds;

  /**
   * 其他出库打印次数
   */
  private Long printCount;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源ID
   */
  private Long sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;


}
