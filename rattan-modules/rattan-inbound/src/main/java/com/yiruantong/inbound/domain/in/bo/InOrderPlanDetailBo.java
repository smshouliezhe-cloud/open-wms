package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InOrderPlanDetail;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 收货计划单明细业务对象 in_order_plan_detail
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InOrderPlanDetail.class, reverseConvertGenerate = false)
public class InOrderPlanDetailBo extends BaseEntity {

  /**
   * 入库计划明细ID
   */
  private Long planDetailId;

  /**
   * 入库计划单ID
   */
  private Long planId;

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
   * 产品规格
   */
  private String productSpec;

  /**
   * 数量
   */
  private BigDecimal quantityOrder;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 销售单价
   */
  private BigDecimal salePrice;

  /**
   * 采购单价
   */
  private BigDecimal purchasePrice;

  /**
   * 销售金额
   */
  private BigDecimal rowSaleAmount;

  /**
   * 采购金额
   */
  private BigDecimal rowPurchaseAmount;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 原产地
   */
  private String originPlace;

  /**
   * 生成日期
   */
  private Date produceDate;

  /**
   * 打包配置
   */
  private String unitPackage;

  /**
   * 建议拍数
   */
  private String plateCount;

  /**
   * 总件数
   */
  private BigDecimal totalPackageQty;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
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
   * 合计重量(吨)
   */
  private BigDecimal rowWeightTon;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 未入库数量
   */
  private BigDecimal surplusQuantity;

  /**
   * 已入库数量
   */
  private BigDecimal inQuantity;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;

  /**
   * 货主Id
   */
  @NotNull(message = "货主Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;

  /**
   * 来源明细编号
   */
  @NotBlank(message = "来源明细编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceDetailCode;


}
