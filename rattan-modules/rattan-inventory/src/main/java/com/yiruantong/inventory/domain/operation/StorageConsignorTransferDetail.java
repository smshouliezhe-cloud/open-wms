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
 * 货位转移明细对象 storage_consignor_transfer_detail
 *
 * @author YRT
 * @date 2024-11-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_consignor_transfer_detail", autoResultMap = true)
public class StorageConsignorTransferDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 货位转移明细ID
   */
  @TableId(value = "consignor_transfer_detail_id")
  private Long consignorTransferDetailId;

  /**
   * 货位转移ID
   */
  private Long consignorTransferId;

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
   * 目标货主ID
   */
  private Long consignorIdTarget;

  /**
   * 目标货主编号
   */
  private String consignorCodeTarget;

  /**
   * 目标货主名称
   */
  private String consignorNameTarget;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 入库货位
   */
  private String positionNameIn;

  /**
   * 商品ID
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
   * 进货价
   */
  private BigDecimal purchasePrice;

  /**
   * 转移数量
   */
  private BigDecimal transferQuantity;

  /**
   * 金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产时间
   */
  private Date produceDate;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * 货位状态
   */
  private String transferStatus;

  /**
   * 产品属性
   */
  private String productAttribute;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;

  /**
   * 毛重合计
   */
  private BigDecimal rowWeight;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 原产地
   */
  private String originPlace;

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
   * 入库日期
   */
  private Date inStorageDate;

  /**
   * 分拣状态
   */
  private Byte sortingStatus;

  /**
   * 温层
   */
  private String thermocLine;

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
   * 商品品牌
   */
  private String brandName;

  /**
   * 商品类别
   */
  private String typeName;

  /**
   * 商品型号
   */
  private String productBarCode;

  /**
   * 图片
   */
  private String images;

  /**
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 库存ID
   */
  private Long inventoryId;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;


}
