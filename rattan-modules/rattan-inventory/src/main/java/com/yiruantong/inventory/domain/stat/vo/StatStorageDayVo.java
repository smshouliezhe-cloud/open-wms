package com.yiruantong.inventory.domain.stat.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import com.yiruantong.inventory.domain.stat.StatStorageDay;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 每日库存快照视图对象 stat_storage_day
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StatStorageDay.class)
public class StatStorageDayVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 每日统计ID
   */
  @ExcelProperty(value = "每日统计ID")
  private Long storageDayId;

  /**
   * 库存日期
   */
  @ExcelProperty(value = "库存日期")
  private Date storageDay;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   * 货主编号
   */
  @ExcelProperty(value = "货主编号")
  private String consignorCode;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String consignorName;

  /**
   * 产品ID
   */
  @ExcelProperty(value = "产品ID")
  private Long productId;

  /**
   * 产品编号
   */
  @ExcelProperty(value = "产品编号")
  private String productCode;

  /**
   * 产品名称
   */
  @ExcelProperty(value = "产品名称")
  private String productName;

  /**
   * 条形码
   */
  @ExcelProperty(value = "条形码")
  private String productModel;

  /**
   * 产品规格
   */
  @ExcelProperty(value = "产品规格")
  private String productSpec;

  /**
   * 库存量
   */
  @ExcelProperty(value = "库存量")
  private BigDecimal productStorage;

  /**
   * 有效库存量
   */
  @ExcelProperty(value = "有效库存量")
  private BigDecimal validStorage;

  /**
   * 原始库存量
   */
  @ExcelProperty(value = "原始库存量")
  private BigDecimal originStorage;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal rowWeight;

  /**
   * 原始毛重
   */
  @ExcelProperty(value = "原始毛重")
  private BigDecimal rowWeightOrigin;

  /**
   * 成本价
   */
  @ExcelProperty(value = "成本价")
  private BigDecimal purchasePrice;

  /**
   * 成本额
   */
  @ExcelProperty(value = "成本额")
  private BigDecimal purchaseAmount;

  /**
   * 采购入库数量
   */
  @ExcelProperty(value = "采购入库数量")
  private BigDecimal scanInQuantity;

  /**
   * 采购上架数量
   */
  @ExcelProperty(value = "采购上架数量")
  private BigDecimal scanShelveQuantity;

  /**
   * 其他入库数量
   */
  @ExcelProperty(value = "其他入库数量")
  private BigDecimal otherInQuantity;

  /**
   * 借入入库数量
   */
  @ExcelProperty(value = "借入入库数量")
  private BigDecimal borrowInQuantity;

  /**
   * 退货入库数量
   */
  @ExcelProperty(value = "退货入库数量")
  private BigDecimal returnQuantity;

  /**
   * 盘盈入库数量
   */
  @ExcelProperty(value = "盘盈入库数量")
  private BigDecimal checkInQuantity;

  /**
   * 借出出库数量
   */
  @ExcelProperty(value = "借出出库数量")
  private BigDecimal borrowOutQuantity;

  /**
   * 订单出库数量
   */
  @ExcelProperty(value = "订单出库数量")
  private BigDecimal scanOutQuantity;

  /**
   * 其他出库数量
   */
  @ExcelProperty(value = "其他出库数量")
  private BigDecimal otherOutQuantity;

  /**
   * 盘亏出库数量
   */
  @ExcelProperty(value = "盘亏出库数量")
  private BigDecimal checkOutQuantity;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 类别名称
   */
  @ExcelProperty(value = "类别名称")
  private String typeName;

  /**
   * 货主名称
   */
  @ExcelProperty(value = "货主名称")
  private String brandName;

  /**
   * 平均库存
   */
  @ExcelProperty(value = "平均库存")
  private BigDecimal avgStorage;

  /**
   * 库存周转率
   */
  @ExcelProperty(value = "库存周转率")
  private BigDecimal turnoverRate;

  /**
   * 周转天数
   */
  @ExcelProperty(value = "周转天数")
  private BigDecimal turnoverDays;

  /**
   * 当前库存可销售时间
   */
  @ExcelProperty(value = "当前库存可销售时间")
  private BigDecimal inventorytime;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 采购入库重量
   */
  @ExcelProperty(value = "采购入库重量")
  private BigDecimal scanInWeight;

  /**
   * 采购上架数量
   */
  @ExcelProperty(value = "采购上架数量")
  private BigDecimal scanShelveWeight;

  /**
   * 出库重量
   */
  @ExcelProperty(value = "出库重量")
  private BigDecimal scanOutWeight;

  /**
   * 保质期天数
   */
  @ExcelProperty(value = "保质期天数")
  private BigDecimal shelfLifeDay;

  /**
   * 库存保质期
   */
  @ExcelProperty(value = "库存保质期")
  private Date shelfLifeDate;

  /**
   * 最长库存天数
   */
  @ExcelProperty(value = "最长库存天数")
  private Long validShelfLifeDay;

  /**
   * 有效期至
   */
  @ExcelProperty(value = "有效期至")
  private Date limitDate;

  /**
   * 生成日期
   */
  @ExcelProperty(value = "生成日期")
  private Date produceDate;

  /**
   * 单位重量
   */
  @ExcelProperty(value = "单位重量")
  private BigDecimal weight;

  /**
   * 其他入库数量
   */
  @ExcelProperty(value = "其他入库数量")
  private BigDecimal otherInWeight;

  /**
   * 借入入库数量
   */
  @ExcelProperty(value = "借入入库数量")
  private BigDecimal borrowInWeight;

  /**
   * 退货入库数量
   */
  @ExcelProperty(value = "退货入库数量")
  private BigDecimal returnWeight;

  /**
   * 盘盈入库数量
   */
  @ExcelProperty(value = "盘盈入库数量")
  private BigDecimal checkInWeight;

  /**
   * 借出出库数量
   */
  @ExcelProperty(value = "借出出库数量")
  private BigDecimal borrowOutWeight;

  /**
   * 其他出库数量
   */
  @ExcelProperty(value = "其他出库数量")
  private BigDecimal otherOutWeight;

  /**
   * 盘亏出库数量
   */
  @ExcelProperty(value = "盘亏出库数量")
  private BigDecimal checkOutWeight;

  /**
   * 净重（克）
   */
  @ExcelProperty(value = "净重", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "克=")
  private BigDecimal netWeight;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 净重重量KG
   */
  @ExcelProperty(value = "净重重量KG")
  private BigDecimal rowNetWeight;

  /**
   * 原始重量
   */
  @ExcelProperty(value = "原始重量")
  private BigDecimal rowNetWeightOrigin;

  /**
   * 重量吨
   */
  @ExcelProperty(value = "重量吨")
  private BigDecimal rowNetWeightTon;

  /**
   * 占位量
   */
  @ExcelProperty(value = "占位量")
  private BigDecimal holderStorage;


}
