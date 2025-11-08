package com.yiruantong.inventory.domain.replenishment;

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
 * 补货单明细对象 storage_replenishment_detail
 *
 * @author YRT
 * @date 2024-10-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_replenishment_detail", autoResultMap = true)
public class StorageReplenishmentDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 补货单明细ID
   */
  @TableId(value = "replenishment_detail_id")
  private Long replenishmentDetailId;

  /**
   * 补货单ID
   */
  private Long replenishmentId;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 商品ID
   */
  private Long productId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
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
   * 补货数量
   */
  private BigDecimal transferQuantity;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 成本金额
   */
  private BigDecimal subTotalAmount;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 单据ID
   */
  private Long billId;

  /**
   * 已补货数量
   */
  private BigDecimal outQuantity;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

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

  /**
   * 原货位
   */
  private String sortPositionName;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;


}
