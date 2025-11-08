package com.yiruantong.outbound.domain.operation.bo;

import com.yiruantong.outbound.domain.operation.OutOrderMatchingDetail;
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
 * 订单配货明细业务对象 out_order_matching_detail
 *
 * @author YRT
 * @date 2023-12-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderMatchingDetail.class, reverseConvertGenerate = false)
public class OutOrderMatchingDetailBo extends BaseEntity {

  /**
   * 自增ID
   */
  @NotNull(message = "自增ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long matchingDetailId;

  /**
   * 配货单ID
   */
  @NotNull(message = "配货单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long matchingId;

  /**
   * 配货位
   */
  @NotBlank(message = "配货位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String allotPositionName;

  /**
   * 出库单ID
   */
  @NotNull(message = "出库单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderId;

  /**
   * 订单编号
   */
  @NotBlank(message = "订单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderCode;

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
   * 订单数量
   */
  @NotNull(message = "订单数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal quantityOrder;

  /**
   * 配货数量
   */
  @NotNull(message = "配货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal matchQuantity;

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
   * SN号
   */
  @NotBlank(message = "SN号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

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
   * 库存单位
   */
  @NotBlank(message = "库存单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String smallUnit;

  /**
   * 大单位
   */
  @NotBlank(message = "大单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bigUnit;

  /**
   * 商品规格
   */
  @NotBlank(message = "商品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 销售单价
   */
  @NotNull(message = "销售单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal salePrice;

  /**
   * 小计金额
   */
  @NotNull(message = "小计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal saleAmount;

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
   * 含税金额
   */
  @NotNull(message = "含税金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rateAmount;

  /**
   * 单位体积
   */
  @NotNull(message = "单位体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @NotNull(message = "小计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowCube;

  /**
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 温层
   */
  @NotBlank(message = "温层不能为空", groups = {AddGroup.class, EditGroup.class})
  private String thermocline;

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
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 换算关系
   */
  @NotNull(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitConvert;


}
