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
import java.util.Date;
import java.util.Map;

/**
 * 防伪标签对象 base_product_security
 *
 * @author YRT
 * @date 2024-04-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_product_security", autoResultMap = true)
public class BaseProductSecurity extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 防伪标签id
   */
  @TableId(value = "security_id")
  private Long securityId;

  /**
   * 产品ID
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
   * 原产地
   */
  private String originPlace;

  /**
   * 生产企业
   */
  private String produceEnterPrise;

  /**
   * 批号名称
   */
  private String batchNumber;

  /**
   * 批号编号
   */
  private String batchCode;

  /**
   * 防伪码数量
   */
  private Long quantity;

  /**
   * 单位
   */
  private String unit;

  /**
   * 换算关系
   */
  private String unitConvert;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核状态
   */
  private Long auditing;

  /**
   * 审核时间
   */
  private Date auditDate;

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
   * 上传图片
   */
  private String productImageUrl;


}
