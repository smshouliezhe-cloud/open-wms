package com.yiruantong.inventory.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.operation.StorageOuterDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 盘点单明细视图对象 storage_outer_detail
 *
 * @author YRT
 * @date 2024-11-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = StorageOuterDetail.class)
public class StorageOuterDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 其他出库明细ID
   */
  @ExcelProperty(value = "其他出库明细ID")
  private Long outerDetailId;

  /**
   * 其他出库单ID
   */
  @ExcelProperty(value = "其他出库单ID")
  private Long outerId;

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
   * 小单位
   */
  @ExcelProperty(value = "小单位")
  private String smallUnit;

  /**
   * 大单位
   */
  @ExcelProperty(value = "大单位")
  private String bigUnit;

  /**
   * 出库数量
   */
  @ExcelProperty(value = "出库数量")
  private BigDecimal outerQuantity;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

  /**
   * 单位关系
   */
  @ExcelProperty(value = "单位关系")
  private String unitConvertText;

  /**
   * 采购价
   */
  @ExcelProperty(value = "采购价")
  private BigDecimal purchasePrice;

  /**
   * 售价
   */
  @ExcelProperty(value = "售价")
  private BigDecimal salePrice;

  /**
   * 小计金额
   */
  @ExcelProperty(value = "小计金额")
  private BigDecimal saleAmount;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 税价
   */
  @ExcelProperty(value = "税价")
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @ExcelProperty(value = "价税合计")
  private BigDecimal rateAmount;

  /**
   * 缺货数量
   */
  @ExcelProperty(value = "缺货数量")
  private BigDecimal lackStorage;

  /**
   * 退货数量
   */
  @ExcelProperty(value = "退货数量")
  private BigDecimal returnQuantity;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 生产时间
   */
  @ExcelProperty(value = "生产时间")
  private Date produceDate;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

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
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

  /**
   * 毛重合计
   */
  @ExcelProperty(value = "毛重合计")
  private BigDecimal rowWeight;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 来源ID
   */
  @ExcelProperty(value = "来源ID")
  private Long sourceId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 定制唯一码
   */
  @ExcelProperty(value = "定制唯一码")
  private String singleSignCode;

  /**
   * 渠道ID
   */
  @ExcelProperty(value = "渠道ID")
  private Long channelId;

  /**
   * 渠道编号
   */
  @ExcelProperty(value = "渠道编号")
  private String channelCode;

  /**
   * 渠道名称
   */
  @ExcelProperty(value = "渠道名称")
  private String channelName;

  /**
   * 店铺id
   */
  @ExcelProperty(value = "店铺id")
  private Long storeId;

  /**
   * 店铺名称
   */
  @ExcelProperty(value = "店铺名称")
  private String storeName;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Byte sortingStatus;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

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
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 单位净重
   */
  @ExcelProperty(value = "单位净重")
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  @ExcelProperty(value = "小计净重")
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源主表ID
   */
  @ExcelProperty(value = "来源主表ID")
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  @ExcelProperty(value = "来源明细ID")
  private String sourceDetailId;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocLine;

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
   * 商品品牌
   */
  @ExcelProperty(value = "商品品牌")
  private String brandName;

  /**
   * 商品类别
   */
  @ExcelProperty(value = "商品类别")
  private String typeName;

  /**
   * 商品型号
   */
  @ExcelProperty(value = "商品型号")
  private String productBarCode;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String images;

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


}
