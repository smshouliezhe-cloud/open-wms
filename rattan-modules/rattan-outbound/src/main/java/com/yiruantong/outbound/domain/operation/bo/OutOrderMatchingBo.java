package com.yiruantong.outbound.domain.operation.bo;

import com.yiruantong.outbound.domain.operation.OutOrderMatching;
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
 * 订单配货业务对象 out_order_matching
 *
 * @author YRT
 * @date 2023-12-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderMatching.class, reverseConvertGenerate = false)
public class OutOrderMatchingBo extends BaseEntity {

  /**
   * 配货单ID
   */
  @NotNull(message = "配货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long matchingId;

  /**
   * 配货单编号
   */
  @NotBlank(message = "配货单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String matchingCode;

  /**
   * 波次单ID
   */
  @NotNull(message = "波次单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderWaveId;

  /**
   * 波次单号
   */
  @NotBlank(message = "波次单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderWaveCode;

  /**
   * 单据类型
   */
  @NotBlank(message = "单据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 配货人ID
   */
  @NotNull(message = "配货人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 配货人
   */
  @NotBlank(message = "配货人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 开始时间
   */
  @NotNull(message = "开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date startDate;

  /**
   * 结束时间
   */
  @NotNull(message = "结束时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date endDate;

  /**
   * 持续时间
   */
  @NotBlank(message = "持续时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private String spanTime;

  /**
   * 拣货数量
   */
  @NotNull(message = "拣货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantityOrder;

  /**
   * 配货数量
   */
  @NotNull(message = "配货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalMatchQuantity;

  /**
   * 订单数
   */
  @NotNull(message = "订单数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderCount;

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
   * 配货状态
   */
  @NotBlank(message = "配货状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String matchStatus;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

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
   * 合计体积
   */
  @NotNull(message = "合计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQtyTotal;


}
