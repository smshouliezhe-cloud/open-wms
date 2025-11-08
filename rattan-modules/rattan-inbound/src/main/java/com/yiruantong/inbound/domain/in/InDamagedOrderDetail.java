package com.yiruantong.inbound.domain.in;

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
 * 残品入库单明细对象 in_damaged_order_detail
 *
 * @author YiRuanTong
 * @date 2024-01-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_damaged_order_detail", autoResultMap = true)
public class InDamagedOrderDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 残品明细ID
   */
  @TableId(value = "damaged_order_detail_id")
  private Long damagedOrderDetailId;

  /**
   * 残品单ID
   */
  private Long damagedOrderId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 条形码
   */
  private String productModel;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 产品规格
   */
  private String productSpec;

  /**
   * 关联编号
   */
  private String relationCode;

  /**
   * 残品数量
   */
  private BigDecimal quantity;

  /**
   * 成本单价
   */
  private BigDecimal purchasePrice;

  /**
   * 成本金额
   */
  private BigDecimal rowAmount;

  /**
   * 报残日期
   */
  private Date damagedDate;

  /**
   * 批次号
   */
  private String batchOrder;

  /**
   * 生产日期
   */
  private Date productDate;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

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
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源主表ID
   */
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  private String sourceDetailId;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 大单位
   */
  private String bigUnit;

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
   * 产品型号
   */
  private String productBarCode;

  /**
   * 图片
   */
  private String images;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 含税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 温层
   */
  private String thermocLine;


}
