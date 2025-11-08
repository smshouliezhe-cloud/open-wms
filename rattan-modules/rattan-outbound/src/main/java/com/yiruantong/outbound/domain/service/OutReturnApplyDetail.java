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
 * 出库退货申请单明细对象 out_return_apply_detail
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_return_apply_detail", autoResultMap = true)
public class OutReturnApplyDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 退货单明细ID
   */
  @TableId(value = "return_apply_detail_id")
  private Long returnApplyDetailId;

  /**
   * 退货单ID
   */
  private Long returnApplyId;

  /**
   * 销售ID
   */
  private Long orderId;

  /**
   * 销售明细ID
   */
  private Long orderDetailId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编码
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
   * 商品规格
   */
  private String productSpec;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 订购数量
   */
  private BigDecimal orderQuantity;

  /**
   * 退货数量
   */
  private BigDecimal returnQuantity;

  /**
   * 已出货数量
   */
  private Long outedQuantity;

  /**
   * 重量
   */
  private BigDecimal weight;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 商品金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 基准税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * 退货售价
   */
  private BigDecimal salePrice;

  /**
   * 退货金额
   */
  private BigDecimal saleAmount;

  /**
   * 缺货数量
   */
  private Long lackStorage;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

  /**
   * 定制唯一码
   */
  private String singleSignCode;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 批次号
   */
  private String batchNumber;

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
