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
 * 每日库存快照对象 stat_storage_day
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "stat_storage_day", autoResultMap = true)
public class StatStorageDay extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 每日统计ID
   */
  @TableId(value = "storage_day_id")
  private Long storageDayId;

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
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 原始毛重
   */
  private BigDecimal rowWeightOrigin;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 成本额
   */
  private BigDecimal purchaseAmount;

  /**
   * 采购入库数量
   */
  private BigDecimal scanInQuantity;

  /**
   * 采购上架数量
   */
  private BigDecimal scanShelveQuantity;

  /**
   * 其他入库数量
   */
  private BigDecimal otherInQuantity;

  /**
   * 借入入库数量
   */
  private BigDecimal borrowInQuantity;

  /**
   * 退货入库数量
   */
  private BigDecimal returnQuantity;

  /**
   * 盘盈入库数量
   */
  private BigDecimal checkInQuantity;

  /**
   * 借出出库数量
   */
  private BigDecimal borrowOutQuantity;

  /**
   * 订单出库数量
   */
  private BigDecimal scanOutQuantity;

  /**
   * 其他出库数量
   */
  private BigDecimal otherOutQuantity;

  /**
   * 盘亏出库数量
   */
  private BigDecimal checkOutQuantity;

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
   * 平均库存
   */
  private BigDecimal avgStorage;

  /**
   * 库存周转率
   */
  private BigDecimal turnoverRate;

  /**
   * 周转天数
   */
  private BigDecimal turnoverDays;

  /**
   * 当前库存可销售时间
   */
  private BigDecimal inventorytime;

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
   * 采购入库重量
   */
  private BigDecimal scanInWeight;

  /**
   * 采购上架数量
   */
  private BigDecimal scanShelveWeight;

  /**
   * 出库重量
   */
  private BigDecimal scanOutWeight;

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
   * 有效期至
   */
  private Date limitDate;

  /**
   * 生成日期
   */
  private Date produceDate;

  /**
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 其他入库数量
   */
  private BigDecimal otherInWeight;

  /**
   * 借入入库数量
   */
  private BigDecimal borrowInWeight;

  /**
   * 退货入库数量
   */
  private BigDecimal returnWeight;

  /**
   * 盘盈入库数量
   */
  private BigDecimal checkInWeight;

  /**
   * 借出出库数量
   */
  private BigDecimal borrowOutWeight;

  /**
   * 其他出库数量
   */
  private BigDecimal otherOutWeight;

  /**
   * 盘亏出库数量
   */
  private BigDecimal checkOutWeight;

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
   * 占位量
   */
  private BigDecimal holderStorage;


}
