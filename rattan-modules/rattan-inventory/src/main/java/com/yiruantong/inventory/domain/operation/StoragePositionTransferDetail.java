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
 * 货位转移明细对象 storage_position_transfer_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_position_transfer_detail", autoResultMap = true)
public class StoragePositionTransferDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 货位转移明细ID
   */
  @TableId(value = "transfer_detail_id")
  private Long transferDetailId;

  /**
   * 货位转移ID
   */
  private Long transferId;

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
   * 商品规格
   */
  private String productSpec;

  /**
   * 成本单据
   */
  private BigDecimal purchasePrice;

  /**
   * 转移数量
   */
  private BigDecimal transferQuantity;

  /**
   * 成本金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 转移货位
   */
  private String positionNameIn;

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
   * 拍号
   */
  private String plateCode;

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
   * 仓库状态
   */
  private String storageStatus;

  /**
   * 库存属性
   */
  private String productAttribute;

  /**
   * SN码
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
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;


}
