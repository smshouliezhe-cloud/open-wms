package com.yiruantong.outbound.domain.operation.bo;

import com.yiruantong.outbound.domain.operation.OutOrderPicking;
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
 * 订单拣货查询业务对象 out_order_picking
 *
 * @author YRT
 * @date 2023-12-16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderPicking.class, reverseConvertGenerate = false)
public class OutOrderPickingBo extends BaseEntity {

  /**
   * 拣货单ID
   */
  @NotNull(message = "拣货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderPickingId;

  /**
   * 拣货单编号
   */
  @NotBlank(message = "拣货单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderPickingCode;

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
   * 订单类型
   */
  @NotBlank(message = "订单类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 拣货人ID
   */
  @NotNull(message = "拣货人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 拣货人
   */
  @NotBlank(message = "拣货人不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 打印批次ID
   */
  @NotNull(message = "打印批次ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderWaveId;

  /**
   * 打印批次编号
   */
  @NotBlank(message = "打印批次编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderWaveCode;

  /**
   * 拣货数量
   */
  @NotNull(message = "拣货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuanityOrder;

  /**
   * 状态
   */
  @NotBlank(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pickingStatus;

  /**
   * 子波次号
   */
  @NotBlank(message = "子波次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subOrderWaveCode;

  /**
   * 成本金额
   */
  @NotNull(message = "成本金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalPurchaseAmount;

  /**
   * 销售总额
   */
  @NotNull(message = "销售总额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalsaleAmount;

  /**
   * 小计毛重
   */
  @NotNull(message = "小计毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 拣货类型
   */
  @NotBlank(message = "拣货类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pickingType;


}
