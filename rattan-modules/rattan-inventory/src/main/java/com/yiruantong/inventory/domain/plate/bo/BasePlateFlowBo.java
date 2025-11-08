package com.yiruantong.inventory.domain.plate.bo;

import com.yiruantong.inventory.domain.plate.BasePlateFlow;
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
 * 容器流水记录业务对象 base_plate_flow
 *
 * @author YRT
 * @date 2024-03-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = BasePlateFlow.class, reverseConvertGenerate = false)
public class BasePlateFlowBo extends BaseEntity {

  /**
   * 流水ID
   */
  @NotNull(message = "流水ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long flowId;

  /**
   * 流水单号
   */
  @NotBlank(message = "流水单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String flowCode;

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
   * 容器类别
   */
  @NotBlank(message = "容器类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 归还数量
   */
  @NotNull(message = "归还数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal returnQty;

  /**
   * 借出数量
   */
  @NotNull(message = "借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal outerQty;

  /**
   * 总借出数量
   */
  @NotNull(message = "总借出数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalouterQty;

  /**
   * 操作前数量
   */
  @NotNull(message = "操作前数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal beforeBalanceQty;

  /**
   * 操作后数量
   */
  @NotNull(message = "操作后数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal balanceQty;

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
   * 部门Id
   */
  @NotNull(message = "部门Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 部门
   */
  @NotBlank(message = "部门不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 来源类型
   */
  @NotBlank(message = "来源类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

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
   * 容器名称
   */
  @NotBlank(message = "容器名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateName;

  /**
   * 容器编号
   */
  @NotBlank(message = "容器编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 容器id
   */
  @NotNull(message = "容器id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long plateId;

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
   * 容器规格
   */
  @NotBlank(message = "容器规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateSpec;


}
