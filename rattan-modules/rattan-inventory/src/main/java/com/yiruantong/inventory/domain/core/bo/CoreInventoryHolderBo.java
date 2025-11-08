package com.yiruantong.inventory.domain.core.bo;

import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
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
 * 库存占位查询(异常)业务对象 core_inventory_holder
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoreInventoryHolder.class, reverseConvertGenerate = false)
public class CoreInventoryHolderBo extends BaseEntity {

  /**
   * 分拣预占ID
   */
  @NotNull(message = "分拣预占ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long holderId;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 编号
   */
  @NotBlank(message = "编号不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 库存ID
   */
  @NotNull(message = "库存ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long inventoryId;

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
   * 进货价
   */
  @NotNull(message = "进货价不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal purchasePrice;

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
   * 入库时间
   */
  @NotNull(message = "入库时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date inStorageDate;

  /**
   * 预占数量
   */
  @NotNull(message = "预占数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal holderStorage;

  /**
   * 原始占位量
   */
  @NotNull(message = "原始占位量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal orignHolderStorage;

  /**
   * 退货对冲量
   */
  @NotNull(message = "退货对冲量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long usedStorage;

  /**
   * 未出库数量
   */
  @NotNull(message = "未出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long unouterStorage;

  /**
   * 店铺订单号
   */
  @NotBlank(message = "店铺订单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storeOrderCode;

  /**
   * SN
   */
  @NotBlank(message = "SN不能为空", groups = {AddGroup.class, EditGroup.class})
  private String singleSignCode;

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
   * 报检单号
   */
  @NotBlank(message = "报检单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String declareNo;

  /**
   * 整拣单
   */
  @NotNull(message = "整拣单不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isFullContainerLoad;

  /**
   * 预占位重量
   */
  @NotNull(message = "预占位重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal holderWeight;

  /**
   * 原始占位重量
   */
  @NotNull(message = "原始占位重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal orignHolderWeight;

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
   * 排序类型
   */
  @NotBlank(message = "排序类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sortType;

  /**
   * 实占数量
   */
  @NotNull(message = "实占数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal factHolderStorage;

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
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}
