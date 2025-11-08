package com.yiruantong.inventory.domain.stat.bo;

import com.yiruantong.inventory.domain.stat.StatStorageDayDetail;
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
 * 每日库存快照明细业务对象 stat_storage_day_detail
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StatStorageDayDetail.class, reverseConvertGenerate = false)
public class StatStorageDayDetailBo extends BaseEntity {

  /**
   * 每日统计ID
   */
  @NotNull(message = "每日统计ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageDayDetailId;

  /**
   * 库存日期
   */
  @NotNull(message = "库存日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date storageDay;

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
   * 供应商
   */
  @NotBlank(message = "供应商不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 库存量
   */
  @NotNull(message = "库存量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal productStorage;

  /**
   * 有效库存量
   */
  @NotNull(message = "有效库存量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal validStorage;

  /**
   * 原始库存量
   */
  @NotNull(message = "原始库存量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal originStorage;

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
   * 库存属性
   */
  @NotBlank(message = "库存属性不能为空", groups = {AddGroup.class, EditGroup.class})
  private String productAttribute;

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
   * 关联码
   */
  @NotBlank(message = "关联码不能为空", groups = {AddGroup.class, EditGroup.class})
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
  private Long validShelfLifeDay;

  /**
   * 库存状态
   */
  @NotBlank(message = "库存状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageStatus;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 类别名称
   */
  @NotBlank(message = "类别名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String typeName;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String brandName;

  /**
   * 集装箱号
   */
  @NotBlank(message = "集装箱号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String containerNo;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 库存重量
   */
  @NotNull(message = "库存重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeight;

  /**
   * 有效重量
   */
  @NotNull(message = "有效重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal validWeight;

  /**
   * 原始重量
   */
  @NotNull(message = "原始重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowWeightOrigin;

  /**
   * 有效期至
   */
  @NotNull(message = "有效期至不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date limitDate;

  /**
   * 单位重量
   */
  @NotNull(message = "单位重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 入库时间
   */
  @NotNull(message = "入库时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date inStorageDate;

  /**
   * 净重（克）
   */
  @NotNull(message = "净重（克）不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal netWeight;

  /**
   * 净重重量KG
   */
  @NotNull(message = "净重重量KG不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeight;

  /**
   * 原始重量
   */
  @NotNull(message = "原始重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeightOrigin;

  /**
   * 重量吨
   */
  @NotNull(message = "重量吨不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal rowNetWeightTon;

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
   * 占位量
   */
  @NotNull(message = "占位量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal holderStorage;

  /**
   * 铅封号
   */
  @NotBlank(message = "铅封号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sealNo;


}
