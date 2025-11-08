package com.yiruantong.inventory.domain.core.bo;

import com.yiruantong.inventory.domain.core.CoreInventoryHistory;
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
 * WMS库存变化推送业务对象 core_inventory_history
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoreInventoryHistory.class, reverseConvertGenerate = false)
public class CoreInventoryHistoryBo extends BaseEntity {

  /**
   * ID
   */
  @NotNull(message = "ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long historyId;

  /**
   * 来源类型
   */
  @NotBlank(message = "来源类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

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
   * 货位名称
   */
  @NotBlank(message = "货位名称不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 入库数量
   */
  @NotNull(message = "入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal inQuantity;

  /**
   * 出库数量
   */
  @NotNull(message = "出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal outQuantity;

  /**
   * 入库金额
   */
  @NotNull(message = "入库金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal inAmount;

  /**
   * 出库金额
   */
  @NotNull(message = "出库金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal outAmount;

  /**
   * 操作前库存数量
   */
  @NotNull(message = "操作前库存数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal beforeQuantity;

  /**
   * 操作后库存数量
   */
  @NotNull(message = "操作后库存数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal afterQuantity;

  /**
   * 操作后库存金额
   */
  @NotNull(message = "操作后库存金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal afterAmount;

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
   * 生产日期
   */
  @NotNull(message = "生产日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 拍号
   */
  @NotBlank(message = "拍号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 入库重量
   */
  @NotNull(message = "入库重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal inWeight;

  /**
   * 出库重量
   */
  @NotNull(message = "出库重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal outWeight;

  /**
   * 操作前重量
   */
  @NotNull(message = "操作前重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal beforeWeight;

  /**
   * 最终重量
   */
  @NotNull(message = "最终重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal afterWeight;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

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
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 来源单号2
   */
  @NotBlank(message = "来源单号2不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode2;

  /**
   * 来源单号3
   */
  @NotBlank(message = "来源单号3不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode3;

  /**
   * 拍数
   */
  @NotNull(message = "拍数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal plateQty;

  /**
   * 类别编号
   */
  @NotNull(message = "类别编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long typeId;

  /**
   * 类别名称
   */
  @NotBlank(message = "类别名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String typeName;

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
   * 品牌名
   */
  @NotBlank(message = "品牌名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandName;

  /**
   * 大单位
   */
  @NotBlank(message = "大单位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bigUnit;

  /**
   * 大单位数量
   */
  @NotNull(message = "大单位数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal bigQty;

  /**
   * 库存ID
   */
  @NotNull(message = "库存ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inventoryId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 换算关系
   */
  @NotNull(message = "换算关系不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal unitConvert;

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
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
