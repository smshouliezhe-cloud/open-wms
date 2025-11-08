package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 入库管理业务对象 in_enter
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InEnter.class, reverseConvertGenerate = false)
public class InEnterBo extends BaseEntity {

  /**
   * 入库单ID
   */
  @NotNull(message = "入库单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enterId;

  /**
   * 入库单编号
   */
  @NotBlank(message = "入库单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String enterCode;

  /**
   * 预到货单ID
   */
  @NotNull(message = "预到货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 预到货单号
   */
  @NotBlank(message = "预到货单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

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
   * 部门名称
   */
  @NotBlank(message = "部门名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 入库日期
   */
  @NotNull(message = "入库日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date applyDate;

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
   * 采购商ID
   */
  @NotNull(message = "采购商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 采购商编号
   */
  @NotBlank(message = "采购商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 采购商名称
   */
  @NotBlank(message = "采购商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalEnterQuantity;

  /**
   * 合计金额
   */
  @NotNull(message = "合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;

  /**
   * 合计优惠金额
   */
  @NotNull(message = "合计优惠金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalFavourAmount;

  /**
   * 合计实付金额
   */
  @NotNull(message = "合计实付金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalFactAmount;

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
   * 付款期限
   */
  @NotNull(message = "付款期限不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date payLimitDate;

  /**
   * 入库状态
   */
  @NotBlank(message = "入库状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String enterStatus;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 已结算金额
   */
  @NotNull(message = "已结算金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal settleAmount;

  /**
   * 未已结算金额
   */
  @NotNull(message = "未已结算金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unSettleAmount;

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
   * 是否已打印
   */
  @NotNull(message = "是否已打印不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isPrint;

  /**
   * 订单类型
   */
  @NotBlank(message = "订单类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * IPN编号
   */
  @NotBlank(message = "IPN编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lpnCode;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNos;

  /**
   * 装卸道口
   */
  @NotBlank(message = "装卸道口不能为空", groups = {AddGroup.class, EditGroup.class})
  private String dockCrossing;

  /**
   * 铅封号
   */
  @NotBlank(message = "铅封号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sealNos;

  /**
   * 测温温度
   */
  @NotBlank(message = "测温温度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String surveyDegree;

  /**
   * 储存温度
   */
  @NotBlank(message = "储存温度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageDegree;

  /**
   * 是否使用装卸
   */
  @NotBlank(message = "是否使用装卸不能为空", groups = {AddGroup.class, EditGroup.class})
  private String isDock;

  /**
   * 异常
   */
  @NotBlank(message = "异常不能为空", groups = {AddGroup.class, EditGroup.class})
  private String abnormal;

  /**
   * 异常件数
   */
  @NotBlank(message = "异常件数不能为空", groups = {AddGroup.class, EditGroup.class})
  private String abnormalPieces;

  /**
   * 其他服务
   */
  @NotBlank(message = "其他服务不能为空", groups = {AddGroup.class, EditGroup.class})
  private String otherServices;

  /**
   * 其他服务
   */
  @NotBlank(message = "其他服务不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 联系方式
   */
  @NotBlank(message = "联系方式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 合计上架数量
   */
  @NotNull(message = "合计上架数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalShelvedQuantity;

  /**
   * 跟踪单号
   */
  @NotBlank(message = "跟踪单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String trackingNumber;

  /**
   * 扫描类型
   */
  @NotBlank(message = "扫描类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String scanInType;

  /**
   * 是否已上架
   */
  @NotBlank(message = "是否已上架不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shelveStatus;

  /**
   * 上传文件
   */
  @NotBlank(message = "上传文件不能为空", groups = {AddGroup.class, EditGroup.class})
  private String uploadFile;

  /**
   * 合计件数
   */
  @NotNull(message = "合计件数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPackage;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQtyTotal;

  /**
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 费用项ID
   */
  @NotBlank(message = "费用项ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String feeItemIds;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
