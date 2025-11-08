package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.operation.OutOrderPickingDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 订单拣货查询明细视图对象 out_order_picking_detail
 *
 * @author YRT
 * @date 2023-12-16
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderPickingDetail.class)
public class OutOrderPickingDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 出库明细ID
   */
  @ExcelProperty(value = "出库明细ID")
  private Long orderPickingDetailId;

  /**
   * 出库单ID
   */
  @ExcelProperty(value = "出库单ID")
  private Long orderPickingId;

  /**
   * 货车编号
   */
  @ExcelProperty(value = "货车编号")
  private String cartCode;

  /**
   * 销售单ID
   */
  @ExcelProperty(value = "销售单ID")
  private Long orderId;

  /**
   * 销售单编号
   */
  @ExcelProperty(value = "销售单编号")
  private String orderCode;

  /**
   * 销售明细ID
   */
  @ExcelProperty(value = "销售明细ID")
  private Long orderDetailId;

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
   * SN码
   */
  @ExcelProperty(value = "SN码")
  private String singleSignCode;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

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
   * 商品规格
   */
  @ExcelProperty(value = "商品规格")
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
   * 采购价
   */
  @ExcelProperty(value = "采购价")
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  @ExcelProperty(value = "销售价")
  private BigDecimal salePrice;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 下架货位
   */
  @ExcelProperty(value = "下架货位")
  private String offPosition;

  /**
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

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
   * 波次单ID
   */
  @ExcelProperty(value = "波次单ID")
  private Long orderWaveId;

  /**
   * 波次明细ID
   */
  @ExcelProperty(value = "波次明细ID")
  private Long orderWaveDetailId;

  /**
   * 数量
   */
  @ExcelProperty(value = "数量")
  private BigDecimal quantityOrder;

  /**
   * 成本金额
   */
  @ExcelProperty(value = "成本金额")
  private BigDecimal purchaseAmount;

  /**
   * 销售总额
   */
  @ExcelProperty(value = "销售总额")
  private BigDecimal saleAmount;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal rowWeight;

  /**
   * 打印批次编号
   */
  @ExcelProperty(value = "打印批次编号")
  private String subOrderWaveCode;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocline;

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
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

  /**
   * 托盘号
   */
  @ExcelProperty(value = "托盘号")
  private String plateCode;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 含税单价
   */
  @ExcelProperty(value = "含税单价")
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  @ExcelProperty(value = "价税合计")
  private BigDecimal rateAmount;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;


}
