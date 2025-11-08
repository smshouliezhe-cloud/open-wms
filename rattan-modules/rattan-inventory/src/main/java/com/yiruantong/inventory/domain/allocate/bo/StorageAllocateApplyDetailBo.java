package com.yiruantong.inventory.domain.allocate.bo;

import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 调拨申请单明细业务对象 storage_allocate_apply_detail
 *
 * @author YRT
 * @date 2024-01-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageAllocateApplyDetail.class, reverseConvertGenerate = false)
public class StorageAllocateApplyDetailBo extends BaseEntity {

  /**
   * 调拨申请明细ID
   */
  @NotNull(message = "调拨申请明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long allocateApplyDetailId;

  /**
   * 调拨申请单ID
   */
  @NotNull(message = "调拨申请单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long allocateApplyId;

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
   * 小单位
   */
  @NotBlank(message = "小单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String smallUnit;

  /**
   * 大单位
   */
  @NotBlank(message = "大单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bigUnit;

  /**
   * 调入数量
   */
  @NotNull(message = "调入数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal applyQuantity;

  /**
   * 换算关系
   */
  @NotNull(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitConvert;

  /**
   * 单位换算文本
   */
  @NotBlank(message = "单位换算文本不能为空", groups = {AddGroup.class, EditGroup.class})
  private String unitConvertText;

  /**
   * 成本价
   */
  @NotNull(message = "成本价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

  /**
   * 成本额
   */
  @NotNull(message = "成本额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchaseAmount;

  /**
   * 缺货数量
   */
  @NotNull(message = "缺货数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal lackStorage;

  /**
   * 入库数量
   */
  @NotNull(message = "入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enterQuantity;

  /**
   * 批次号
   */
  @NotBlank(message = "批次号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String batchNumber;

  /**
   * 有效库存量
   */
  @NotNull(message = "有效库存量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal validQuantity;

  /**
   * 单位毛量
   */
  @NotNull(message = "单位毛量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 小计毛量
   */
  @NotNull(message = "小计毛量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 损失量
   */
  @NotBlank(message = "损失量不能为空", groups = {AddGroup.class, EditGroup.class})
  private String lossQuantity;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

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
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源主表ID
   */
  @NotBlank(message = "来源主表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  @NotBlank(message = "来源明细ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceDetailId;

  /**
   * 温层
   */
  @NotBlank(message = "温层不能为空", groups = {AddGroup.class, EditGroup.class})
  private String thermocline;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 拍号
   */
  @NotBlank(message = "拍号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String positionName;

  /**
   * 合计重量吨
   */
  @NotNull(message = "合计重量吨不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeightTon;

  /**
   * 图片
   */
  @NotBlank(message = "图片不能为空", groups = {AddGroup.class, EditGroup.class})
  private String images;

  /**
   * 来源ID
   */
  @NotNull(message = "来源ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 入库时间
   */
  @NotNull(message = "入库时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date inStorageDate;

  /**
   * 生产日期
   */
  @NotNull(message = "生产日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 原始重量
   */
  @NotNull(message = "原始重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeightOrign;

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
   * 到期日期
   */
  @NotNull(message = "到期日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date limitDate;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQty;

  /**
   * 采购商ID
   */
  @NotNull(message = "采购商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 采购商编号
   */
  @NotBlank(message = "采购商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 采购商名称
   */
  @NotBlank(message = "采购商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 拍数
   */
  @NotNull(message = "拍数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal paiQty;

  /**
   * 税率%
   */
  @NotNull(message = "税率%不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rate;

  /**
   * 含税单价
   */
  @NotNull(message = "含税单价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal ratePrice;

  /**
   * 含税金额
   */
  @NotNull(message = "含税金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rateAmount;

  /**
   * SN
   */
  @NotBlank(message = "SN不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

  /**
   * 产品型号
   */
  @NotBlank(message = "产品型号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productBarCode;

  /**
   * 品牌ID
   */
  @NotNull(message = "品牌ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long brandId;

  /**
   * 品牌
   */
  @NotBlank(message = "品牌不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandName;

  /**
   * 类别
   */
  @NotBlank(message = "类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String typeName;

  /**
   * 原产地
   */
  @NotBlank(message = "原产地不能为空", groups = {AddGroup.class, EditGroup.class})
  private String originPlace;

  /**
   * 原始净重
   */
  @NotNull(message = "原始净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeightOrign;

  /**
   * 分拣状态
   */
  @NotNull(message = "分拣状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long sortingStatus;

  /**
   * 出库数量
   */
  @NotNull(message = "出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal outQuantity;

  /**
   * 入库数量
   */
  @NotNull(message = "入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal inQuantity;


}
