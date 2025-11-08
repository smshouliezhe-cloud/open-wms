package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InArrivalProcess;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 到货加工业务对象 in_arrival_process
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InArrivalProcess.class, reverseConvertGenerate = false)
public class InArrivalProcessBo extends BaseEntity {

  /**
   * 加工ID
   */
  @NotNull(message = "加工ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long processId;

  /**
   * 加工编号
   */
  @NotBlank(message = "加工编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String processCode;

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
   * 采购单ID
   */
  @NotNull(message = "采购单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 采购单编号
   */
  @NotBlank(message = "采购单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

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
   * 加工人ID
   */
  @NotNull(message = "加工人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 加工人
   */
  @NotBlank(message = "加工人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 完成时间
   */
  @NotNull(message = "完成时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date endDate;

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
   * 审核备注
   */
  @NotBlank(message = "审核备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditRemark;

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


}
