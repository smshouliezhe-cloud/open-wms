package com.yiruantong.outbound.domain.service.bo;

import com.yiruantong.outbound.domain.service.OutReturnApply;
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
 * 出库退货申请单业务对象 out_return_apply
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutReturnApply.class, reverseConvertGenerate = false)
public class OutReturnApplyBo extends BaseEntity {

  /**
   * 客户售后ID
   */
  @NotNull(message = "客户售后ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long returnApplyId;

  /**
   * 客户售后编号
   */
  @NotBlank(message = "客户售后编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String returnApplyCode;

  /**
   * 出库iD
   */
  @NotNull(message = "出库iD不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 出库编号
   */
  @NotBlank(message = "出库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

  /**
   * 入库仓库ID
   */
  @NotNull(message = "入库仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 入库仓库名称
   */
  @NotBlank(message = "入库仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 部门名称
   */
  @NotBlank(message = "部门名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 入库时间
   */
  @NotNull(message = "入库时间不能为空", groups = {AddGroup.class, EditGroup.class})
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
  private BigDecimal totalQuantity;

  /**
   * 退款数量
   */
  @NotNull(message = "退款数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long totalReturnQuantity;

  /**
   * 退款金额
   */
  @NotNull(message = "退款金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalSaleAmount;

  /**
   * 退货金额
   */
  @NotNull(message = "退货金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalReturnAmount;

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
   * 申请状态
   */
  @NotBlank(message = "申请状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String applyStatus;

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
   * 司机姓名
   */
  @NotBlank(message = "司机姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driveName;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 发货人
   */
  @NotBlank(message = "发货人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingName;

  /**
   * 发货人地址
   */
  @NotBlank(message = "发货人地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String shippingAddress;

  /**
   * 手机
   */
  @NotBlank(message = "手机不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

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


}
