package com.yiruantong.inbound.domain.api;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 质检单明细业务对象 InQualityCheckDetail
 *
 * @author YRT
 * @date 2024-10-29
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiInQualityCheckDetailBo extends BaseEntity {
  /**
   * 质检明细ID
   */
  private Long qualityCheckDetailId;

  /**
   * 质检ID
   */
  private Long qualityCheckId;

  /**
   * 采购明细ID
   */
  private Long orderDetailId;

  /**
   * 采购单ID
   */
  private Long orderId;

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
   * 数量
   */
  private BigDecimal quantity;

  /**
   * 质检类型
   */
  private String checkType;

  /**
   * 质检数量
   */
  private BigDecimal checkQuantity;

  /**
   * 次品数量
   */
  private BigDecimal defectiveQuantity;

  /**
   * 合格率
   */
  private BigDecimal qualifiedRate;

  /**
   * 生产日期
   */
  private Date productionDate;

  /**
   * 保质期天数
   */
  private Long shelfLifeDay;

  /**
   * 次品原因
   */
  private String defectiveRemark;

  /**
   * 拍号
   */
  private String plateCode;

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
