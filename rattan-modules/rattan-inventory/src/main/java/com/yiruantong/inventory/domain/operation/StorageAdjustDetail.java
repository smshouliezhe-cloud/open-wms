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
 * 库存调整单明细对象 storage_adjust_detail
 *
 * @author YRT
 * @date 2024-11-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_adjust_detail", autoResultMap = true)
public class StorageAdjustDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 调整单明细ID
   */
  @TableId(value = "adjust_detail_id")
  private Long adjustDetailId;

  /**
   * 调整单ID
   */
  private Long adjustId;

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
   * 产品规格
   */
  private String productSpec;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 账面库存量
   */
  private BigDecimal productStorage;

  /**
   * 进货价
   */
  private BigDecimal purchasePrice;

  /**
   * 账面成本额
   */
  private BigDecimal purchaseAmount;

  /**
   * 盘点数量
   */
  private BigDecimal checkQuantity;

  /**
   * 盘盈数量
   */
  private BigDecimal profitQuantity;

  /**
   * 盘盈金额
   */
  private BigDecimal profitAmount;

  /**
   * 盘亏数量
   */
  private BigDecimal lossQuantity;

  /**
   * 盘亏金额
   */
  private BigDecimal lossAmount;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

  /**
   * 仓库状态
   */
  private String storageStatus;

  /**
   * 产品属性
   */
  private String productAttribute;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 保质期天数
   */
  private Long shelfLifeDay;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 条形码
   */
  private String productModel;

  /**
   * 占位数量
   */
  private BigDecimal holderStorage;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 单位毛量
   */
  private BigDecimal weight;

  /**
   * 库存毛量
   */
  private BigDecimal rowWeight;

  /**
   * 原产地
   */
  private String originPlace;

  /**
   * 入库日期
   */
  private Date inStorageDate;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 盘盈重量
   */
  private BigDecimal profitWeight;

  /**
   * 原始毛重
   */
  private BigDecimal totalWeightOrign;

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
   * 库存ID
   */
  private Long inventoryId;

  /**
   * 到期日期
   */
  private Date limitDate;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;


}
