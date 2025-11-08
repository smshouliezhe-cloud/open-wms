package com.yiruantong.inventory.domain.allocate;

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
 * 调拨申请单明细对象 storage_allocate_apply_detail
 *
 * @author YRT
 * @date 2024-01-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_allocate_apply_detail", autoResultMap = true)
public class StorageAllocateApplyDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 调拨申请明细ID
   */
  @TableId(value = "allocate_apply_detail_id")
  private Long allocateApplyDetailId;

  /**
   * 调拨申请单ID
   */
  private Long allocateApplyId;

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
   * 调入数量
   */
  private BigDecimal applyQuantity;

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
   * 入库数量
   */
  private Long enterQuantity;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 有效库存量
   */
  private BigDecimal validQuantity;

  /**
   * 单位毛量
   */
  private BigDecimal weight;

  /**
   * 小计毛量
   */
  private BigDecimal rowWeight;

  /**
   * 损失量
   */
  private String lossQuantity;

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
   * 温层
   */
  private String thermocline;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 合计重量吨
   */
  private BigDecimal rowWeightTon;

  /**
   * 图片
   */
  private String images;

  /**
   * 来源ID
   */
  private Long sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 入库时间
   */
  private Date inStorageDate;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 原始重量
   */
  private BigDecimal rowWeightOrign;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 到期日期
   */
  private Date limitDate;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 采购商ID
   */
  private Long providerId;

  /**
   * 采购商编号
   */
  private String providerCode;

  /**
   * 采购商名称
   */
  private String providerShortName;

  /**
   * 拍数
   */
  private BigDecimal paiQty;

  /**
   * 税率%
   */
  private BigDecimal rate;

  /**
   * 含税单价
   */
  private BigDecimal ratePrice;

  /**
   * 含税金额
   */
  private BigDecimal rateAmount;

  /**
   * SN
   */
  private String singleSignCode;

  /**
   * 产品型号
   */
  private String productBarCode;

  /**
   * 品牌ID
   */
  private Long brandId;

  /**
   * 品牌
   */
  private String brandName;

  /**
   * 类别
   */
  private String typeName;

  /**
   * 原产地
   */
  private String originPlace;

  /**
   * 原始净重
   */
  private BigDecimal rowNetWeightOrign;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

  /**
   * 出库数量
   */
  private BigDecimal outQuantity;

  /**
   * 入库数量
   */
  private BigDecimal inQuantity;


}
