package com.yiruantong.inbound.domain.in.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.in.InEnterDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 入库管理明细视图对象 in_enter_detail
 *
 * @author YiRuanTong
 * @date 2024-12-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InEnterDetail.class)
public class InEnterDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 入库单明细ID
   */
  @ExcelProperty(value = "入库单明细ID")
  private Long enterDetailId;

  /**
   * 入库单ID
   */
  @ExcelProperty(value = "入库单ID")
  private Long enterId;

  /**
   * 预到货明细ID
   */
  @ExcelProperty(value = "预到货明细ID")
  private Long orderDetailId;

  /**
   * 预到货单ID
   */
  @ExcelProperty(value = "预到货单ID")
  private Long orderId;

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
   * 入库数量
   */
  @ExcelProperty(value = "入库数量")
  private BigDecimal enterQuantity;

  /**
   * 上架数量
   */
  @ExcelProperty(value = "上架数量")
  private BigDecimal shelvedQuantity;

  /**
   * 换算关系
   */
  @ExcelProperty(value = "换算关系")
  private BigDecimal unitConvert;

  /**
   * 换算关系文本
   */
  @ExcelProperty(value = "换算关系文本")
  private String unitConvertText;

  /**
   * 单价
   */
  @ExcelProperty(value = "单价")
  private BigDecimal purchasePrice;

  /**
   * 货款金额
   */
  @ExcelProperty(value = "货款金额")
  private BigDecimal purchaseAmount;

  /**
   * 优惠金额
   */
  @ExcelProperty(value = "优惠金额")
  private BigDecimal favourAmount;

  /**
   * 实付金额
   */
  @ExcelProperty(value = "实付金额")
  private BigDecimal factAmount;

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
   * 未完成订货数量
   */
  @ExcelProperty(value = "未完成订货数量")
  private BigDecimal unfinishedQuantity;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

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
   * 生成日期
   */
  @ExcelProperty(value = "生成日期")
  private Date produceDate;

  /**
   * 生成编号
   */
  @ExcelProperty(value = "生成编号")
  private String plateCode;

  /**
   * 关联码
   */
  @ExcelProperty(value = "关联码")
  private String relationCode;

  /**
   * 申报单号
   */
  @ExcelProperty(value = "申报单号")
  private String declareNo;

  /**
   * 明细状态
   */
  @ExcelProperty(value = "明细状态")
  private String detailStatus;

  /**
   * 到期日期
   */
  @ExcelProperty(value = "到期日期")
  private Date limitDate;

  /**
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal rowWeight;

  /**
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String originPlace;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 小计件数
   */
  @ExcelProperty(value = "小计件数")
  private BigDecimal rowPackage;

  /**
   * 单位件数
   */
  @ExcelProperty(value = "单位件数")
  private BigDecimal unitPackage;

  /**
   * 建议拍数
   */
  @ExcelProperty(value = "建议拍数")
  private BigDecimal advicePaiQty;

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
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQty;

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
   * 类别编号
   */
  @ExcelProperty(value = "类别编号")
  private Long typeId;

  /**
   * 类别名称
   */
  @ExcelProperty(value = "类别名称")
  private String typeName;

  /**
   * 产品型号
   */
  @ExcelProperty(value = "产品型号")
  private String productBarCode;

  /**
   * 品牌ID
   */
  @ExcelProperty(value = "品牌ID")
  private Long brandId;

  /**
   * 品牌名
   */
  @ExcelProperty(value = "品牌名")
  private String brandName;

  /**
   * 图片
   */
  @ExcelProperty(value = "图片")
  private String images;

  /**
   * 温层
   */
  @ExcelProperty(value = "温层")
  private String thermocLine;

  /**
   * 合计重量(吨)
   */
  @ExcelProperty(value = "合计重量(吨)")
  private BigDecimal rowWeightTon;

  /**
   * 净重重量(吨)
   */
  @ExcelProperty(value = "净重重量(吨)")
  private BigDecimal rowNetWeightTon;

  /**
   * 铅封号
   */
  @ExcelProperty(value = "铅封号")
  private String sealNo;

  /**
   * 次品数量
   */
  @ExcelProperty(value = "次品数量")
  private Long defectiveQty;

  /**
   * 分拣次数
   */
  @ExcelProperty(value = "分拣次数")
  private Long sortingCount;

  /**
   * 保质期天数
   */
  @ExcelProperty(value = "保质期天数")
  private BigDecimal shelfLifeDay;

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

  /**
   * 包数
   */
  @ExcelProperty(value = "包数")
  private Long parcelQuantity;

  /**
   * 均重
   */
  @ExcelProperty(value = "均重")
  private BigDecimal parcelAverageWeight;


}
