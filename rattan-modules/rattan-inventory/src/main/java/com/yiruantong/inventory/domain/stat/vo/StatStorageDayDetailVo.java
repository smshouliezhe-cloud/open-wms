package com.yiruantong.inventory.domain.stat.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import com.yiruantong.inventory.domain.stat.StatStorageDayDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 每日库存快照明细视图对象 stat_storage_day_detail
 *
 * @author YRT
 * @date 2024-03-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StatStorageDayDetail.class)
public class StatStorageDayDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 每日统计ID
   */
  @ExcelProperty(value = "每日统计ID")
  private Long storageDayDetailId;

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
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

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
   * 供应商ID
   */
  @ExcelProperty(value = "供应商ID")
  private Long providerId;

  /**
   * 供应商编号
   */
  @ExcelProperty(value = "供应商编号")
  private String providerCode;

  /**
   * 供应商
   */
  @ExcelProperty(value = "供应商")
  private String providerShortName;

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
   * 库存属性
   */
  @ExcelProperty(value = "库存属性")
  private String productAttribute;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 生成日期
   */
  @ExcelProperty(value = "生成日期")
  private Date produceDate;

  /**
   * 拍号
   */
  @ExcelProperty(value = "拍号")
  private String plateCode;

  /**
   * 关联码
   */
  @ExcelProperty(value = "关联码")
  private String relationCode;

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
   * 库存状态
   */
  @ExcelProperty(value = "库存状态")
  private String storageStatus;

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
   * 库存重量
   */
  @ExcelProperty(value = "库存重量")
  private BigDecimal rowWeight;

  /**
   * 有效重量
   */
  @ExcelProperty(value = "有效重量")
  private BigDecimal validWeight;

  /**
   * 原始重量
   */
  @ExcelProperty(value = "原始重量")
  private BigDecimal rowWeightOrigin;

  /**
   * 有效期至
   */
  @ExcelProperty(value = "有效期至")
  private Date limitDate;

  /**
   * 单位重量
   */
  @ExcelProperty(value = "单位重量")
  private BigDecimal weight;

  /**
   * 入库时间
   */
  @ExcelProperty(value = "入库时间")
  private Date inStorageDate;

  /**
   * 净重（克）
   */
  @ExcelProperty(value = "净重", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "克=")
  private BigDecimal netWeight;

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
   * 单位体积
   */
  @ExcelProperty(value = "单位体积")
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  @ExcelProperty(value = "小计体积")
  private BigDecimal rowCube;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQty;

  /**
   * 占位量
   */
  @ExcelProperty(value = "占位量")
  private BigDecimal holderStorage;

  /**
   * 铅封号
   */
  @ExcelProperty(value = "铅封号")
  private String sealNo;


}
