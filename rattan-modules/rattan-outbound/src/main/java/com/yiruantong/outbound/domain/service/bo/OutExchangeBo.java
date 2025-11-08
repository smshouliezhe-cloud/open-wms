package com.yiruantong.outbound.domain.service.bo;

import com.yiruantong.outbound.domain.service.OutExchange;
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
 * 换货管理业务对象 out_exchange
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutExchange.class, reverseConvertGenerate = false)
public class OutExchangeBo extends BaseEntity {

  /**
   * 换货单ID
   */
  @NotNull(message = "换货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long exchangeId;

  /**
   * 换货单号
   */
  @NotBlank(message = "换货单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String exchangeCode;

  /**
   * 出库仓库ID
   */
  @NotNull(message = "出库仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 出库仓库
   */
  @NotBlank(message = "出库仓库不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 入库仓库ID
   */
  @NotNull(message = "入库仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageIdIn;

  /**
   * 入库仓库
   */
  @NotBlank(message = "入库仓库不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageNameIn;

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
   * 换货日期
   */
  @NotNull(message = "换货日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date applyDate;

  /**
   * 税率
   */
  @NotNull(message = "税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rate;

  /**
   * 入库合计数量
   */
  @NotNull(message = "入库合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalEnterQuantity;

  /**
   * 入库合计金额
   */
  @NotNull(message = "入库合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalEnterAmount;

  /**
   * 出库合计数量
   */
  @NotNull(message = "出库合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalOuterQuantity;

  /**
   * 出库合计金额
   */
  @NotNull(message = "出库合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalOuterAmount;

  /**
   * 换货差额
   */
  @NotNull(message = "换货差额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal differenceAmount;

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
   * 实收金额
   */
  @NotNull(message = "实收金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal receiveAmount;

  /**
   * 分拣日期
   */
  @NotNull(message = "分拣日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date sortingDate;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sortingStatus;

  /**
   * 换货状态
   */
  @NotBlank(message = "换货状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String exchangeStatus;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
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
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   *
   */
  @NotBlank(message = "不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

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
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
