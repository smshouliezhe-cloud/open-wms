package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InArrivalProcessDetail;
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
 * 到货加工明细业务对象 in_arrival_process_detail
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InArrivalProcessDetail.class, reverseConvertGenerate = false)
public class InArrivalProcessDetailBo extends BaseEntity {

  /**
   * 加工明细ID
   */
  @NotNull(message = "加工明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long processDetailId;

  /**
   * 加工ID
   */
  @NotNull(message = "加工ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long processId;

  /**
   * 采购明细ID
   */
  @NotNull(message = "采购明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailId;

  /**
   * 采购单ID
   */
  @NotNull(message = "采购单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 产品名称
   */
  @NotBlank(message = "产品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 条形码
   */
  @NotBlank(message = "条形码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 产品规格
   */
  @NotBlank(message = "产品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 数量
   */
  @NotNull(message = "数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantity;

  /**
   * 过程类型
   */
  @NotBlank(message = "过程类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String processType;

  /**
   * 清关数量
   */
  @NotNull(message = "清关数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal clearanceQauntity;

  /**
   * 车牌编号
   */
  @NotBlank(message = "车牌编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

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
