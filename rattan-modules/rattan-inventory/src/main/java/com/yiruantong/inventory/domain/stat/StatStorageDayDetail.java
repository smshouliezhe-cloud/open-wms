package com.yiruantong.inventory.domain.stat;

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
 * 每日库存快照明细对象 stat_storage_day_detail
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "stat_storage_day_detail", autoResultMap = true)
public class StatStorageDayDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 每日统计ID
   */
  @TableId(value = "storage_day_detail_id")
  private Long storageDayDetailId;

  /**
   * 库存日期
   */
  private Date storageDay;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
   * 货位名称
   */
  private String positionName;

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
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商
   */
  private String providerShortName;

  /**
   * 库存量
   */
  private BigDecimal productStorage;

  /**
   * 有效库存量
   */
  private BigDecimal validStorage;

  /**
   * 原始库存量
   */
  private BigDecimal originStorage;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 成本额
   */
  private BigDecimal purchaseAmount;

  /**
   * 库存属性
   */
  private String productAttribute;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生成日期
   */
  private Date produceDate;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 关联码
   */
  private String relationCode;

  /**
   * 保质期天数
   */
  private BigDecimal shelfLifeDay;

  /**
   * 库存保质期
   */
  private Date shelfLifeDate;

  /**
   * 最长库存天数
   */
  private Long validShelfLifeDay;

  /**
   * 库存状态
   */
  private String storageStatus;

  /**
   * 备注
   */
  private String remark;

  /**
   * 类别名称
   */
  private String typeName;

  /**
   * 货主名称
   */
  private String brandName;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 库存重量
   */
  private BigDecimal rowWeight;

  /**
   * 有效重量
   */
  private BigDecimal validWeight;

  /**
   * 原始重量
   */
  private BigDecimal rowWeightOrigin;

  /**
   * 有效期至
   */
  private Date limitDate;

  /**
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 入库时间
   */
  private Date inStorageDate;

  /**
   * 净重（克）
   */
  private BigDecimal netWeight;

  /**
   * 净重重量KG
   */
  private BigDecimal rowNetWeight;

  /**
   * 原始重量
   */
  private BigDecimal rowNetWeightOrigin;

  /**
   * 重量吨
   */
  private BigDecimal rowNetWeightTon;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 占位量
   */
  private BigDecimal holderStorage;

  /**
   * 铅封号
   */
  private String sealNo;


}
