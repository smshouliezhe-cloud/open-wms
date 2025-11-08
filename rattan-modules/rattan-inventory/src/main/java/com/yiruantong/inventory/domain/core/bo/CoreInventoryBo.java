package com.yiruantong.inventory.domain.core.bo;

import com.yiruantong.inventory.domain.core.CoreInventory;
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
 * 库存调整选择器业务对象 core_inventory
 *
 * @author YiRuanTong
 * @date 2025-03-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoreInventory.class, reverseConvertGenerate = false)
public class CoreInventoryBo extends BaseEntity {

  /**
   * 库存ID
   */
  @NotNull(message = "库存ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inventoryId;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源主表ID
   */
  @NotNull(message = "来源主表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long mainId;

  /**
   * 来源明细ID
   */
  @NotNull(message = "来源明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long detailId;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 默认货位
   */
  @NotBlank(message = "默认货位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 产品ID
   */
  @NotNull(message = "产品ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productId;

  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productCode;

  /**
   * 产品名称
   */
  @NotBlank(message = "产品名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productName;

  /**
   * 条形码
   */
  @NotBlank(message = "条形码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productModel;

  /**
   * 产品规格
   */
  @NotBlank(message = "产品规格不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productSpec;

  /**
   * 定制唯一码
   */
  @NotBlank(message = "定制唯一码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 入库时间
   */
  @NotNull(message = "入库时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date inStorageDate;

  /**
   * 供应商ID
   */
  @NotNull(message = "供应商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 供应商编号
   */
  @NotBlank(message = "供应商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 供应商简称
   */
  @NotBlank(message = "供应商简称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 库存量
   */
  @NotNull(message = "库存量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal productStorage;

  /**
   * 原始库存量
   */
  @NotNull(message = "原始库存量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal originStorage;

  /**
   * 进货价
   */
  @NotNull(message = "进货价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

  /**
   * 进货总额
   */
  @NotNull(message = "进货总额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchaseAmount;

  /**
   * 税率
   */
  @NotNull(message = "税率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rate;

  /**
   * 税价
   */
  @NotNull(message = "税价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @NotNull(message = "价税合计不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rateAmount;

  /**
   * 移动平均价
   */
  @NotNull(message = "移动平均价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal avgPrice;

  /**
   * 执行单号
   */
  @NotBlank(message = "执行单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

  /**
   * 入库单号
   */
  @NotBlank(message = "入库单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String enterCode;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 生成日期
   */
  @NotNull(message = "生成日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 拍号
   */
  @NotBlank(message = "拍号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 关联号
   */
  @NotBlank(message = "关联号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String relationCode;

  /**
   * 保质期天数
   */
  @NotNull(message = "保质期天数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal shelfLifeDay;

  /**
   * 库存保质期
   */
  @NotNull(message = "库存保质期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date shelfLifeDate;

  /**
   * 最长库存天数
   */
  @NotNull(message = "最长库存天数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal validShelfLifeDay;

  /**
   * 库存状态
   */
  @NotBlank(message = "库存状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageStatus;

  /**
   * 产品属性
   */
  @NotBlank(message = "产品属性不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productAttribute;

  /**
   * 到期日期
   */
  @NotNull(message = "到期日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date limitDate;

  /**
   * 单位毛重
   */
  @NotNull(message = "单位毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @NotNull(message = "小计毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 原始毛重
   */
  @NotNull(message = "原始毛重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeightOrigin;

  /**
   * 原产地
   */
  @NotBlank(message = "原产地不能为空", groups = {AddGroup.class, EditGroup.class})
  private String originPlace;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 明细扩展字段
   */
  @NotNull(message = "明细扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> detailExpandFields;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 单位净重
   */
  @NotNull(message = "单位净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  @NotNull(message = "小计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeight;

  /**
   * 原始净重
   */
  @NotNull(message = "原始净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeightOrigin;

  /**
   * 单位体积
   */
  @NotNull(message = "单位体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @NotNull(message = "小计体积不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQty;

  /**
   * 图片
   */
  @NotBlank(message = "图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String images;

  /**
   * 温层
   */
  @NotBlank(message = "温层不能为空", groups = {AddGroup.class, EditGroup.class})
  private String thermocLine;

  /**
   * 占位量
   */
  @NotNull(message = "占位量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal holderStorage;

  /**
   * 有效库存
   */
  @NotNull(message = "有效库存不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal validStorage;

  /**
   * 合计重量(吨)
   */
  private BigDecimal rowWeightTon;

  /**
   * 铅封号
   */
  @NotBlank(message = "铅封号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sealNo;

  /**
   * 项目号
   */
  @NotBlank(message = "项目号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String projectCode;

  /**
   * 箱号
   */
  @NotBlank(message = "箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String caseNumber;

  /**
   * 包数
   */
  @NotNull(message = "包数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal parcelQuantity;

  /**
   * 均重
   */
  @NotNull(message = "均重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal parcelAverageWeight;

  /**
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
