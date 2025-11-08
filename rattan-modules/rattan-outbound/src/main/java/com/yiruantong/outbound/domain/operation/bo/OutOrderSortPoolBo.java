package com.yiruantong.outbound.domain.operation.bo;

import com.yiruantong.outbound.domain.operation.OutOrderSortPool;
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
 * 分拣池业务对象 out_order_sort_pool
 *
 * @author YRT
 * @date 2024-05-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderSortPool.class, reverseConvertGenerate = false)
public class OutOrderSortPoolBo extends BaseEntity {

  /**
   * 分拣池ID
   */
  @NotNull(message = "分拣池ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sortPoolId;

  /**
   * 订单ID
   */
  @NotNull(message = "订单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 池状态
   */
  @NotNull(message = "池状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte poolState;

  /**
   * 权重
   */
  @NotNull(message = "权重不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 出库单号
   */
  @NotBlank(message = "出库单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

  /**
   * ERP单号
   */
  @NotBlank(message = "ERP单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storeOrderCode;


}
