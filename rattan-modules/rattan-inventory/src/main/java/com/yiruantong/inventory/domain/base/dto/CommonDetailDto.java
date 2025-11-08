package com.yiruantong.inventory.domain.base.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.core.enums.in.InCheckingStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 库存明细操作DTO
 */
@Data
@NoArgsConstructor
public class CommonDetailDto {
  /**
   * 货位转移明细ID
   */
  private Long detailId;

  /**
   * 货位转移ID
   */
  private Long mainId;

  /**
   * 执行单号
   */
  private String billCode;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * ERP单号
   */
  private String storeOrderCode;

  /**
   * 来源单号2
   */
  private String sourceCode2;

  /**
   * 来源单号3
   */
  private String sourceCode3;

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
   * 商品类别
   */
  private String typeName;

  /**
   * 成本单价
   */
  private BigDecimal purchasePrice;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 成本税价
   */
  private BigDecimal ratePrice;

  /**
   * 销售单价
   */
  private BigDecimal salePrice;

  /**
   * 出库数量
   */
  private BigDecimal outQuantity;

  /**
   * 入库数量
   */
  private BigDecimal inQuantity;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;
  /**
   * 成本金额
   */
  private BigDecimal purchaseAmount;

  /**
   * 含税金额
   */
  private BigDecimal rateAmount;

  /**
   * 销售金额
   */
  private BigDecimal saleAmount;

  /**
   * 出库货位
   */
  private String positionNameOut;

  /**
   * 入库货位
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
   * 到期日期
   */
  private Date limitDate;

  /**
   * 保质期天数
   */
  private BigDecimal shelfLifeDay;

  /**
   * 库存保质期
   */
  private Date shelfLifeDate;

  /**
   * 拍号
   */
  private String plateCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库编号
   */
  private String storageCode;

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
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商简称
   */
  private String providerShortName;

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
  private Map<String, Object> expandFields;
  /**
   * 明细扩展字段
   */
  private Map<String, Object> detailExpandFields;

  /**
   * 备注
   */
  private String remark;

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
   * 图片
   */
  private String images;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;
  /**
   * 原产地
   */
  private String originPlace;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 铅封号
   */
  private String sealNo;

  /**
   * 温层
   */
  private String thermocLine;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 入库时间
   */
  private Date inStorageDate;

  /**
   * 分拣预占ID
   */
  private Long holderId;

  /**
   * 库存ID
   */
  private Long inventoryId;

  /**
   * 配货位
   */
  private String allotPositionName;

  /**
   * 管理SN
   */
  private Byte isManageSn;

  /**
   * 项目号
   */
  @ExcelProperty(value = "项目号")
  private String projectCode;

  /**
   * 箱号
   */
  @ExcelProperty(value = "箱号")
  private String caseNumber;

  /**
   * 质检状态
   */
  @ExcelProperty(value = "质检状态")
  private InCheckingStatusEnum checkingStatusEnum;

  /**
   * 包数
   */
  private Long parcelQuantity;

  /**
   * 均重
   */
  private BigDecimal parcelAverageWeight;
}
