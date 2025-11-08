package com.yiruantong.outbound.domain.order;

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
 * 订货单明细对象 out_retail_detail
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_retail_detail", autoResultMap = true)
public class OutRetailDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 零售明细ID
   */
  @TableId(value = "retail_detail_id")
  private Long retailDetailId;

  /**
   * 零售单ID
   */
  private Long retailId;

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
   * 商品规格
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
   * 订货数量
   */
  private BigDecimal quantityOrder;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 销售单价
   */
  private BigDecimal salePrice;

  /**
   * 销售金额
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
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 单位重量
   */
  private Long weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 原产地
   */
  private String originPlace;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 有效库存
   */
  private Long validQuantity;

  /**
   * 采购单价
   */
  private BigDecimal purchasePrice;

  /**
   * 采购金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 采购商ID
   */
  private Long providerId;

  /**
   * 采购商编号
   */
  private String providerCode;

  /**
   * 采购商名称
   */
  private String providerShortName;

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
