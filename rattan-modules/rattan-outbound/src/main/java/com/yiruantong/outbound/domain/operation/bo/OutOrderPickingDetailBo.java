package com.yiruantong.outbound.domain.operation.bo;

import com.yiruantong.outbound.domain.operation.OutOrderPickingDetail;
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
 * 订单拣货查询明细业务对象 out_order_picking_detail
 *
 * @author YRT
 * @date 2023-12-16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderPickingDetail.class, reverseConvertGenerate = false)
public class OutOrderPickingDetailBo extends BaseEntity {

  /**
   * 出库明细ID
   */
  @NotNull(message = "出库明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderPickingDetailId;

  /**
   * 出库单ID
   */
  @NotNull(message = "出库单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderPickingId;

  /**
   * 货车编号
   */
  @NotBlank(message = "货车编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cartCode;

  /**
   * 销售单ID
   */
  @NotNull(message = "销售单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 销售单编号
   */
  @NotBlank(message = "销售单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

  /**
   * 销售明细ID
   */
  @NotNull(message = "销售明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderDetailId;

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
   * SN码
   */
  @NotBlank(message = "SN码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

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
   * 小单位
   */
  @NotBlank(message = "小单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String smallUnit;

  /**
   * 大单位
   */
  @NotBlank(message = "大单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bigUnit;

  /**
   * 采购价
   */
  @NotNull(message = "采购价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @NotNull(message = "销售价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal salePrice;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 下架货位
   */
  @NotBlank(message = "下架货位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String offPosition;

  /**
   * 单位毛重
   */
  @NotNull(message = "单位毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

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
   * 波次单ID
   */
  @NotNull(message = "波次单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderWaveId;

  /**
   * 波次明细ID
   */
  @NotNull(message = "波次明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderWaveDetailId;

  /**
   * 数量
   */
  @NotNull(message = "数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantityOrder;

  /**
   * 成本金额
   */
  @NotNull(message = "成本金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchaseAmount;

  /**
   * 销售总额
   */
  @NotNull(message = "销售总额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal saleAmount;

  /**
   * 小计毛重
   */
  @NotNull(message = "小计毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 打印批次编号
   */
  @NotBlank(message = "打印批次编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subOrderWaveCode;

  /**
   * 温层
   */
  private String thermocline;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @NotNull(message = "小计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQty;

  /**
   * 商品品牌
   */
  @NotBlank(message = "商品品牌不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandName;

  /**
   * 商品类别
   */
  @NotBlank(message = "商品类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String typeName;

  /**
   * 商品型号
   */
  @NotBlank(message = "商品型号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productBarCode;

  /**
   * 图片
   */
  @NotBlank(message = "图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String images;

  /**
   * 生产日期
   */
  @NotNull(message = "生产日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 托盘号
   */
  @NotBlank(message = "托盘号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 税率
   */
  @NotNull(message = "税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rate;

  /**
   * 含税单价
   */
  @NotNull(message = "含税单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @NotNull(message = "价税合计不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rateAmount;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;


}
