package com.yiruantong.inventory.domain.operation;

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
 * 商品拆装单出库明细对象 storage_assemble_outer_detail
 *
 * @author YRT
 * @date 2023-10-24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_assemble_outer_detail", autoResultMap = true)
public class StorageAssembleOuterDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 组装出库明细ID
   */
  @TableId(value = "assemble_package_detail_id")
  private Long assembleOuterDetailId;

  /**
   * 组装单ID
   */
  private Long assembleId;

  /**
   * 明细ID
   */
  private Long productSplitDetailId;

  /**
   * 商品拆分ID
   */
  private Long productSplitId;

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
   * 出库数量
   */
  private BigDecimal outerQuantity;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位换算文本
   */
  private String unitConvertText;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 成本额
   */
  private BigDecimal purchaseAmount;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产时间
   */
  private Date produceDate;

  /**
   * 货位名称
   */
  private String positionName;

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
   * 合计净重
   */
  private BigDecimal totalNetWeight;

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
