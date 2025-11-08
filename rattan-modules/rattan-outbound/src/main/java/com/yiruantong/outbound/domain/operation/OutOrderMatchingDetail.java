package com.yiruantong.outbound.domain.operation;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单配货明细对象 out_order_matching_detail
 *
 * @author YRT
 * @date 2023-12-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_order_matching_detail", autoResultMap = true)
public class OutOrderMatchingDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 自增ID
   */
  @TableId(value = "matching_detail_id")
  private Long matchingDetailId;

  /**
   * 配货单ID
   */
  private Long matchingId;

  /**
   * 配货位
   */
  private String allotPositionName;

  /**
   * 出库单ID
   */
  private Long orderId;

  /**
   * 订单编号
   */
  private String orderCode;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 条形码
   */
  private String productModel;

  /**
   * 订单数量
   */
  private BigDecimal quantityOrder;

  /**
   * 配货数量
   */
  private BigDecimal matchQuantity;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 库存单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 商品规格
   */
  private String productSpec;

  /**
   * 销售单价
   */
  private BigDecimal salePrice;

  /**
   * 小计金额
   */
  private BigDecimal saleAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 含税单价
   */
  private BigDecimal ratePrice;

  /**
   * 含税金额
   */
  private BigDecimal rateAmount;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 温层
   */
  private String thermocline;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 商品品牌
   */
  private String brandName;

  /**
   * 商品类别
   */
  private String typeName;

  /**
   * 商品型号
   */
  private String productBarCode;

  /**
   * 图片
   */
  private String images;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;


}
