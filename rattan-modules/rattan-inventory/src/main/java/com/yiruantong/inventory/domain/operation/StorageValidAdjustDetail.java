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
 * 效期信息调整明细对象 storage_valid_adjust_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_valid_adjust_detail", autoResultMap = true)
public class StorageValidAdjustDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 效期调整ID
   */
  @TableId(value = "valid_adjust_detail_id")
  private Long validAdjustDetailId;

  /**
   * 效期调整ID
   */
  private Long validAdjustId;

  /**
   * 库存ID
   */
  private Long inventoryId;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 库存量
   */
  private BigDecimal productStorage;

  /**
   * 占位量
   */
  private BigDecimal placeholderStorage;

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
   * 保质期天数
   */
  private Long shelfLifeDay;

  /**
   * 原生产日期
   */
  private Date produceDate;

  /**
   * 原到期日期
   */
  private Date limitDate;

  /**
   * 目标生产日期
   */
  private Date produceDateTarget;

  /**
   * 目标到期日期
   */
  private Date limitDateTarget;

  /**
   * 原批次号
   */
  private String batchNumber;

  /**
   * 目标批次号
   */
  private String batchNumberTarget;

  /**
   * 合计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

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
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;


}
