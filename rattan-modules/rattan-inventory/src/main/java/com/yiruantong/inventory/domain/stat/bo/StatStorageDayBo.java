package com.yiruantong.inventory.domain.stat.bo;

import com.yiruantong.inventory.domain.stat.StatStorageDay;
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
 * 每日库存快照业务对象 stat_storage_day
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StatStorageDay.class, reverseConvertGenerate = false)
public class StatStorageDayBo extends BaseEntity {

  /**
   * 每日统计ID
   */
  @NotNull(message = "每日统计ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageDayId;

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
   * 采购入库数量
   */
  @NotNull(message = "采购入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal scanInQuantity;

  /**
   * 采购上架数量
   */
  @NotNull(message = "采购上架数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal scanShelveQuantity;

  /**
   * 其他入库数量
   */
  @NotNull(message = "其他入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal otherInQuantity;

  /**
   * 借入入库数量
   */
  @NotNull(message = "借入入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal borrowInQuantity;

  /**
   * 退货入库数量
   */
  @NotNull(message = "退货入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal returnQuantity;

  /**
   * 盘盈入库数量
   */
  @NotNull(message = "盘盈入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal checkInQuantity;

  /**
   * 借出出库数量
   */
  @NotNull(message = "借出出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal borrowOutQuantity;

  /**
   * 订单出库数量
   */
  @NotNull(message = "订单出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal scanOutQuantity;

  /**
   * 其他出库数量
   */
  @NotNull(message = "其他出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal otherOutQuantity;

  /**
   * 盘亏出库数量
   */
  @NotNull(message = "盘亏出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal checkOutQuantity;

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
   * 平均库存
   */
  @NotNull(message = "平均库存不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal avgStorage;

  /**
   * 库存周转率
   */
  @NotNull(message = "库存周转率不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal turnoverRate;

  /**
   * 周转天数
   */
  @NotNull(message = "周转天数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal turnoverDays;

  /**
   * 当前库存可销售时间
   */
  @NotNull(message = "当前库存可销售时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal inventorytime;

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
   * 采购入库重量
   */
  @NotNull(message = "采购入库重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal scanInWeight;

  /**
   * 采购上架数量
   */
  @NotNull(message = "采购上架数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal scanShelveWeight;

  /**
   * 出库重量
   */
  @NotNull(message = "出库重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal scanOutWeight;

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
   * 有效期至
   */
  @NotNull(message = "有效期至不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date limitDate;

  /**
   * 生成日期
   */
  @NotNull(message = "生成日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date produceDate;

  /**
   * 单位重量
   */
  @NotNull(message = "单位重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal weight;

  /**
   * 其他入库数量
   */
  @NotNull(message = "其他入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal otherInWeight;

  /**
   * 借入入库数量
   */
  @NotNull(message = "借入入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal borrowInWeight;

  /**
   * 退货入库数量
   */
  @NotNull(message = "退货入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal returnWeight;

  /**
   * 盘盈入库数量
   */
  @NotNull(message = "盘盈入库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal checkInWeight;

  /**
   * 借出出库数量
   */
  @NotNull(message = "借出出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal borrowOutWeight;

  /**
   * 其他出库数量
   */
  @NotNull(message = "其他出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal otherOutWeight;

  /**
   * 盘亏出库数量
   */
  @NotNull(message = "盘亏出库数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal checkOutWeight;

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
   * 占位量
   */
  @NotNull(message = "占位量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal holderStorage;


}
