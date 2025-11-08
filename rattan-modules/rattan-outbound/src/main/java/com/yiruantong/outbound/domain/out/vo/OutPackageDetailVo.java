package com.yiruantong.outbound.domain.out.vo;

  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.outbound.domain.out.OutPackageDetail;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 打包单明细视图对象 out_package_detail
 *
 * @author YRT
 * @date 2025-04-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutPackageDetail.class)
public class OutPackageDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 出库明细ID
       */
      @ExcelProperty(value = "出库明细ID")
    private Long packageDetailId;

      /**
       * 出库单ID
       */
      @ExcelProperty(value = "出库单ID")
    private Long packageId;

      /**
       * 销售单ID
       */
      @ExcelProperty(value = "销售单ID")
    private Long orderId;

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
       * 打包数量
       */
      @ExcelProperty(value = "打包数量")
    private BigDecimal packageQuantity;

      /**
       * 换算关系
       */
      @ExcelProperty(value = "换算关系")
    private BigDecimal unitConvert;

      /**
       * 单位换算
       */
      @ExcelProperty(value = "单位换算")
    private String unitConvertText;

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
       * 折扣金额
       */
      @ExcelProperty(value = "折扣金额")
    private BigDecimal discountAmount;

      /**
       * 应收总额
       */
      @ExcelProperty(value = "应收总额")
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
       * 出库单号
       */
      @ExcelProperty(value = "出库单号")
    private String orderCode;

      /**
       * 箱号
       */
      @ExcelProperty(value = "箱号")
    private String caseNumber;

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
       * 生产日期
       */
      @ExcelProperty(value = "生产日期")
    private Date produceDate;

      /**
       * 大单位数量
       */
      @ExcelProperty(value = "大单位数量")
    private BigDecimal bigQty;

      /**
       * 合计重量(吨)
       */
      @ExcelProperty(value = "合计重量(吨)")
    private BigDecimal rowWeightTon;

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
       * 小计包裹数
       */
      @ExcelProperty(value = "小计包裹数")
    private BigDecimal rowPackage;

      /**
       * 区域id
       */
      @ExcelProperty(value = "区域id")
    private Long consignorIdSale;

      /**
       * 区域
       */
      @ExcelProperty(value = "区域")
    private String consignorNameSale;

      /**
       * 项目号
       */
      @ExcelProperty(value = "项目号")
    private String projectCode;

      /**
       * 拍号
       */
      @ExcelProperty(value = "拍号")
    private String plateCode;

  
}
