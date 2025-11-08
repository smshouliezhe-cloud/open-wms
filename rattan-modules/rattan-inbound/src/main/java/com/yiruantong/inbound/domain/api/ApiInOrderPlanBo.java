package com.yiruantong.inbound.domain.api;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
 * 收货计划单业务对象 in_order_plan
 *
 * @author YiRuanTong
 * @date 2024-10-31
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiInOrderPlanBo {

  /**
   * 入库计划明细
   */
  List<ApiInOrderPlanDetailBo> detailList;
  /**
   * 入库计划单ID
   */
  private Long planId;
  /**
   * 入库计划单
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
   * 仓库编号
   */
  private String storageCode;
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
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
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
   * 是否越库
   */
  private Byte crossDocking;

}
