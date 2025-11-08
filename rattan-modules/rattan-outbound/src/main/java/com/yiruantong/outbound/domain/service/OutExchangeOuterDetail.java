package com.yiruantong.outbound.domain.service;

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
 * 换货管理Outer明细对象 out_exchange_outer_detail
 *
 * @author YiRuanTong
 * @date 2024-11-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_exchange_outer_detail", autoResultMap = true)
public class OutExchangeOuterDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 出库明细ID
   */
  @TableId(value = "exchange_outer_detail_id")
  private Long exchangeOuterDetailId;

  /**
   * 换货单ID
   */
  private Long exchangeId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 商品编号
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
   * 产品规格
   */
  private String productSpec;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 数量
   */
  private BigDecimal enterQuantity;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 单价
   */
  private BigDecimal purchasePrice;

  /**
   * 售价
   */
  private BigDecimal salePrice;

  /**
   * 金额
   */
  private BigDecimal saleAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;

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
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

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


}
