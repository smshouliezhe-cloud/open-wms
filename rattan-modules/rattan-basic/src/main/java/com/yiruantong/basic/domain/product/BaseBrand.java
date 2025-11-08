package com.yiruantong.basic.domain.product;

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
 * 品牌管理对象 base_brand
 *
 * @author YiRuanTong
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_brand", autoResultMap = true)
public class BaseBrand extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 品牌ID
   */
  @TableId(value = "brand_id")
  private Long brandId;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 大类别ID
   */
  private Long bigTypeId;

  /**
   * 大类别名称
   */
  private String bigTypeName;

  /**
   * 类型
   */
  private String modeType;

  /**
   * 品牌名
   */
  private String brandName;

  /**
   * 品牌英文名
   */
  private String brandNameEn;

  /**
   * 地址
   */
  private String address;

  /**
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商简称
   */
  private String providerShortName;

  /**
   * 采购人ID
   */
  private Long purchaseId;

  /**
   * 采购人
   */
  private String purchaseName;

  /**
   * 周期(天)
   */
  private Long cycle;

  /**
   * logo
   */
  private String logo;

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
   * 质检计划
   */
  private String qualityPlan;

  /**
   * 质检比例
   */
  private BigDecimal qualityProportion;

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
   * 是否可用
   */
  private Byte enable;


}
