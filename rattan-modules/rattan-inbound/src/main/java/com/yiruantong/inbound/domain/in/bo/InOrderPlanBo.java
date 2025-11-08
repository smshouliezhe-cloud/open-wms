package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InOrderPlan;
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
 * 收货计划单业务对象 in_order_plan
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InOrderPlan.class, reverseConvertGenerate = false)
public class InOrderPlanBo extends BaseEntity {

  /**
   * 入库计划单ID
   */
  private Long planId;

  /**
   * 入库计划单1
   */
  private String planCode;

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
   * 预计到货时间
   */
  private Date arrivedDate;

  /**
   * 合计数量
   */
  private BigDecimal totalQuantityOrder;

  /**
   * 合计采购额
   */
  private BigDecimal totalPurchaseAmount;

  /**
   * 合计销售额
   */
  private BigDecimal totalSaleAmount;

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
   * 计划类型
   */
  @NotBlank(message = "计划类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String planType;

  /**
   * 计划状态
   */
  @NotBlank(message = "计划状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String planStatus;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Long auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 审核备注
   */
  private String auditRemark;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 月台
   */
  private String dockCrossing;

  /**
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商名称
   */
  @NotBlank(message = "供应商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

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
  private String sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 快递类别
   */
  private Long expressCorpType;

  /**
   * 快递ID
   */
  private Long expressCorpId;

  /**
   * 快递名称
   */
  private String expressCorpName;

  /**
   * 快递编号
   */
  private String expressCode;

  /**
   * 上传文件
   */
  private String uploadFile;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
