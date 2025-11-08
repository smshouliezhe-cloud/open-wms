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
 * 盈亏单明细对象 storage_profit_loss_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_profit_loss_detail", autoResultMap = true)
public class StorageProfitLossDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 报损明细ID
   */
  @TableId(value = "profit_loss_detail_id")
  private Long profitLossDetailId;

  /**
   * 报损单ID
   */
  private Long profitLossId;

  /**
   * 盘点单ID
   */
  private Long checkId;

  /**
   * 盘点明细ID
   */
  private Long checkDetailId;

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
   * 关联码
   */
  private String relationCode;

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
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  private String unitConvertText;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 账面库存量
   */
  private BigDecimal productStorage;

  /**
   * 平均成本价
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
   * 初盘差异(生成盈亏单的时候计算出来的？)
   */
  private BigDecimal checkDiff;

  /**
   * 复盘数量
   */
  private BigDecimal reCheckQuantity;

  /**
   * 复盘差异
   */
  private BigDecimal reCheckDiff;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产时间
   */
  private Date produceDate;

  /**
   * 限制日期
   */
  private Date limitDate;

  /**
   * 单位毛重
   */
  private BigDecimal rowWeight;

  /**
   * 毛重小计
   */
  private BigDecimal totalWeight;

  /**
   * 盘盈重量
   */
  private BigDecimal profitWeight;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * SN唯一码
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
   * 库存Id
   */
  private Long inventoryId;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;


}
