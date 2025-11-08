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
 * 调拨入库单明细对象 storage_allocate_enter_detail
 *
 * @author YRT
 * @date 2023-12-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_allocate_enter_detail", autoResultMap = true)
public class StorageAllocateEnterDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 调拨入库明细ID
   */
  @TableId(value = "allocate_enter_detail_id")
  private Long allocateEnterDetailId;

  /**
   * 调拨单ID
   */
  private Long allocateEnterId;

  /**
   * 调拨申请单ID
   */
  private Long allocateApplyId;

  /**
   * 调拨申请明细ID
   */
  private Long allocateApplyDetailId;

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
  private BigDecimal enterQuantity;

  /**
   * 货位名称
   */
  private String positionName;

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
   * 批次号
   */
  private String batchNumber;

  /**
   * 毛重合计
   */
  private BigDecimal rowWeight;

  /**
   * 总重量
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


}
