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
 * 商品类目管理对象 base_product_type
 *
 * @author YiRuanTong
 * @date 2024-10-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_product_type", autoResultMap = true)
public class BaseProductType extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ERP类目ID
   */
  @TableId(value = "type_id")
  private Long typeId;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 类目名称
   */
  private String typeName;

  /**
   * WEB类目名称
   */
  private String webTypeName;

  /**
   * 英文申报名
   */
  private String ciqName;

  /**
   * 中文申报名
   */
  private String ciqNameCn;

  /**
   * 根目录ID
   */
  private Long rootId;

  /**
   * 根目录名称
   */
  private String rootName;

  /**
   * 类型ID
   */
  private String fullTypeId;

  /**
   * 类型名称
   */
  private String fullTypeName;

  /**
   * 节点级别
   */
  private Long nodeLevel;

  /**
   * 预制类型
   */
  private String precutPosition;

  /**
   * 店铺类目ID
   */
  private Long categoryId;

  /**
   * 是否有子集
   */
  private Long hasChild;

  /**
   * 状态
   */
  private String state;

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
   * 质检方案
   */
  private String qualityPlan;

  /**
   * 质检比例
   */
  private BigDecimal qualityProportion;

  /**
   * 是否推荐
   */
  private Long isRecommend;

  /**
   * 类型URL
   */
  private String typeUrl;

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
   * 类目编号
   */
  private String typeCode;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 类别图片
   */
  private String images;


}
