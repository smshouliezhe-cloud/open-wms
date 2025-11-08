package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateOut;
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
 * 容器借出主业务对象 base_plate_out
 *
 * @author YRT
 * @date 2024-05-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateOut.class, reverseConvertGenerate = false)
public class BasePlateOutBo extends BaseEntity {

  /**
   * 容器借出Id
   */
  @NotNull(message = "容器借出Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long outId;

  /**
   * 借出编号
   */
  @NotBlank(message = "借出编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String outCode;

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
   * 类型
   */
  @NotBlank(message = "类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 状态
   */
  @NotBlank(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusText;

  /**
   * 合计借出数量
   */
  @NotNull(message = "合计借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalOutQty;

  /**
   * 借出日期
   */
  @NotNull(message = "借出日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date outDate;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

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
   * 客户Id
   */
  @NotNull(message = "客户Id不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 仓库Id
   */
  @NotNull(message = "仓库Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 来源id
   */
  @NotNull(message = "来源id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 来源类型
   */
  @NotBlank(message = "来源类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 销售组织
   */
  @NotBlank(message = "销售组织不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  @NotBlank(message = "销售组织编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  @NotNull(message = "销售组织ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorIdSale;

  /**
   * 已归还数量
   */
  @NotNull(message = "已归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalReturnedQty;

  /**
   * 未归还数量
   */
  @NotNull(message = "未归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalUnreturnedQty;

  /**
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 客户单号
   */
  @NotBlank(message = "客户单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String customerOrderCode;

  /**
   * 订单日期
   */
  @NotNull(message = "订单日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date applyDate;


}
