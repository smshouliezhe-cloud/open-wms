package com.yiruantong.outbound.domain.operation.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库单波次明细业务对象 out_order_wave_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderWaveDetail.class, reverseConvertGenerate = false)
public class OutOrderWaveDetailBo extends BaseEntity {

  /**
   * 波次明细ID
   */
  @NotNull(message = "波次明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderWaveDetailId;

  /**
   * 波次单ID
   */
  @NotNull(message = "波次单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderWaveId;

  /**
   * 分拣占位ID
   */
  @NotNull(message = "分拣占位ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long holderId;

  /**
   * 配货位
   */
  @NotBlank(message = "配货位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String allotPositionName;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 订单ID
   */
  @NotNull(message = "订单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 订单明细编号
   */
  @NotNull(message = "订单明细编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailId;

  /**
   * 订单编号
   */
  @NotBlank(message = "订单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

  /**
   * 店铺订单编号
   */
  @NotBlank(message = "店铺订单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storeOrderCode;

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
   * 商品规格
   */
  @NotBlank(message = "商品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 订单数量
   */
  @NotNull(message = "订单数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantityOrder;

  /**
   * 打包出库数量
   */
  @NotNull(message = "打包出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantityOuted;

  /**
   * 配货数量
   */
  @NotNull(message = "配货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal matchedQuantity;

  /**
   * 冻结数量
   */
  @NotNull(message = "冻结数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal freezeQuantity;

  /**
   * SN号
   */
  @NotBlank(message = "SN号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 快递ID
   */
  @NotNull(message = "快递ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long expressCorpId;

  /**
   * 快递公司名称
   */
  @NotBlank(message = "快递公司名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCorpName;

  /**
   * 快递公司编号
   */
  @NotBlank(message = "快递公司编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String expressCode;

  /**
   * 拣货数量
   */
  @NotNull(message = "拣货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal pickQuantity;

  /**
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 生成日期
   */
  @NotNull(message = "生成日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 托盘号
   */
  @NotBlank(message = "托盘号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 关联码
   */
  @NotBlank(message = "关联码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String relationCode;

  /**
   * 整拣单
   */
  @NotNull(message = "整拣单不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isFullContainerLoad;

  /**
   * 原始订单数量
   */
  @NotNull(message = "原始订单数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantityOrderOrigin;

  /**
   * 单位毛重
   */
  @NotNull(message = "单位毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @NotNull(message = "小计毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 货位类型
   */
  @NotNull(message = "货位类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte positionType;

  /**
   * 子波次单号
   */
  @NotBlank(message = "子波次单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subOrderWaveCode;

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
   * 单位净重
   */
  @NotNull(message = "单位净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  @NotNull(message = "小计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源主表ID
   */
  @NotBlank(message = "来源主表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  @NotBlank(message = "来源明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceDetailId;

  /**
   * 线路Id
   */
  @NotNull(message = "线路Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long lineId;

  /**
   * 线路编号
   */
  @NotBlank(message = "线路编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineCode;

  /**
   * 线路名称
   */
  @NotBlank(message = "线路名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lineName;

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
   * 已发货数量
   */
  @NotNull(message = "已发货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantityShipped;

  /**
   * 项目号
   */
  @NotBlank(message = "项目号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String projectCode;

  /**
   * 箱号
   */
  @NotBlank(message = "箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String caseNumber;


}
