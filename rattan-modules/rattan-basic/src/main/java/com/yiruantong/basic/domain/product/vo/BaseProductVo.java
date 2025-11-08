package com.yiruantong.basic.domain.product.vo;

  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.basic.domain.product.BaseProduct;
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
 * 商品基础信息视图对象 base_product
 *
 * @author YRT
 * @date 2025-03-17
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseProduct.class)
public class BaseProductVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 产品ID
       */
      @ExcelProperty(value = "产品ID")
    private Long productId;

      /**
       * 商品编号
       */
      @ExcelProperty(value = "商品编号")
    private String productCode;

      /**
       * 商品名称
       */
      @ExcelProperty(value = "商品名称")
    private String productName;

      /**
       * 英文申报名
       */
      @ExcelProperty(value = "英文申报名")
    private String ciqName;

      /**
       * 中文申报名
       */
      @ExcelProperty(value = "中文申报名")
    private String ciqNameCn;

      /**
       * 子标题
       */
      @ExcelProperty(value = "子标题")
    private String subTitle;

      /**
       * 子标题格式
       */
      @ExcelProperty(value = "子标题格式")
    private String subTitleFormat;

      /**
       * 条形码
       */
      @ExcelProperty(value = "条形码")
    private String productModel;

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
       * 系列ID
       */
      @ExcelProperty(value = "系列ID")
    private Long brandSeriesId;

      /**
       * 系列
       */
      @ExcelProperty(value = "系列")
    private String brandSeriesName;

      /**
       * 大类别编号
       */
      @ExcelProperty(value = "大类别编号")
    private Long bigTypeId;

      /**
       * 大类别名称
       */
      @ExcelProperty(value = "大类别名称")
    private String bigTypeName;

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
       * 完全类别路径ID
       */
      @ExcelProperty(value = "完全类别路径ID")
    private String fullTypeId;

      /**
       * 完全类别路径
       */
      @ExcelProperty(value = "完全类别路径")
    private String fullTypeName;

      /**
       * 默认供应商ID
       */
      @ExcelProperty(value = "默认供应商ID")
    private Long providerId;

      /**
       * 默认供应商编号
       */
      @ExcelProperty(value = "默认供应商编号")
    private String providerCode;

      /**
       * 默认供应商
       */
      @ExcelProperty(value = "默认供应商")
    private String providerShortName;

      /**
       * 采购人ID
       */
      @ExcelProperty(value = "采购人ID")
    private Long purchaseId;

      /**
       * 采购人
       */
      @ExcelProperty(value = "采购人")
    private String purchaseName;

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
       * 换算关系
       */
      @ExcelProperty(value = "换算关系")
    private BigDecimal unitConvert;

      /**
       * 单位换算描述
       */
      @ExcelProperty(value = "单位换算描述")
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
       * 每日价
       */
      @ExcelProperty(value = "每日价")
    private BigDecimal dayPrice;

      /**
       * VIP价
       */
      @ExcelProperty(value = "VIP价")
    private BigDecimal vipPrice;

      /**
       * 活动价
       */
      @ExcelProperty(value = "活动价")
    private BigDecimal activityPrice;

      /**
       * 库存上限
       */
      @ExcelProperty(value = "库存上限")
    private Long storageUpper;

      /**
       * 库存下限
       */
      @ExcelProperty(value = "库存下限")
    private Long storageLower;

      /**
       * 是否限制购买量
       */
      @ExcelProperty(value = "是否限制购买量")
    private Byte isLimitBuy;

      /**
       * 商品在途天数
       */
      @ExcelProperty(value = "商品在途天数")
    private Long transitDays;

      /**
       * 移动平均价
       */
      @ExcelProperty(value = "移动平均价")
    private BigDecimal avgPrice;

      /**
       * 产品规格
       */
      @ExcelProperty(value = "产品规格")
    private String productSpec;

      /**
       * 标签
       */
      @ExcelProperty(value = "标签")
    private String labels;

      /**
       * 是否热销
       */
      @ExcelProperty(value = "是否热销")
    private Byte isHot;

      /**
       * 商品描述
       */
      @ExcelProperty(value = "商品描述")
    private String productDesc;

      /**
       * 点击次数
       */
      @ExcelProperty(value = "点击次数")
    private Long clickCount;

      /**
       * 商品状态
       */
      @ExcelProperty(value = "商品状态")
    private Long productStatus;

      /**
       * 促销状态
       */
      @ExcelProperty(value = "促销状态")
    private Long promoStatus;

      /**
       * 信息审核状态
       */
      @ExcelProperty(value = "信息审核状态")
    private Long auditing;

      /**
       * 审核人
       */
      @ExcelProperty(value = "审核人")
    private String auditor;

      /**
       * 审核时间
       */
      @ExcelProperty(value = "审核时间")
    private Date auditDate;

      /**
       * 拒绝审核原因
       */
      @ExcelProperty(value = "拒绝审核原因")
    private String refuseAuditingReason;

      /**
       * 开启规格
       */
      @ExcelProperty(value = "开启规格")
    private Long openSpec;

      /**
       * 重量
       */
      @ExcelProperty(value = "重量")
    private BigDecimal weight;

      /**
       * 供应商URL
       */
      @ExcelProperty(value = "供应商URL")
    private String supplierUrl;

      /**
       * 参考URL
       */
      @ExcelProperty(value = "参考URL")
    private String url;

      /**
       * 默认货位
       */
      @ExcelProperty(value = "默认货位")
    private String positionName;

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
       * 货主名字
       */
      @ExcelProperty(value = "货主名字")
    private String consignorName;

      /**
       * 产品型号
       */
      @ExcelProperty(value = "产品型号")
    private String productBarCode;

      /**
       * 批次属性
       */
      @ExcelProperty(value = "批次属性")
    private String batchAttribute;

      /**
       * 保质期（年）
       */
      @ExcelProperty(value = "保质期", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "年=")
    private BigDecimal shelfLifeYear;

      /**
       * 保质期（月）
       */
      @ExcelProperty(value = "保质期", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "月=")
    private BigDecimal shelfLifeApril;

      /**
       * 保质期天数
       */
      @ExcelProperty(value = "保质期天数")
    private BigDecimal shelfLifeDay;

      /**
       * 长（毫米）
       */
      @ExcelProperty(value = "长", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "毫=米")
    private BigDecimal length;

      /**
       * 宽（毫米）
       */
      @ExcelProperty(value = "宽", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "毫=米")
    private BigDecimal width;

      /**
       * 高（毫米）
       */
      @ExcelProperty(value = "高", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "毫=米")
    private BigDecimal height;

      /**
       * 净重（克）
       */
      @ExcelProperty(value = "净重", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "克=")
    private BigDecimal netWeight;

      /**
       * 质检方案
       */
      @ExcelProperty(value = "质检方案")
    private String qualityPlan;

      /**
       * 包装件数
       */
      @ExcelProperty(value = "包装件数")
    private Long packageNumber;

      /**
       * 是否需效期管理
       */
      @ExcelProperty(value = "是否需效期管理")
    private Byte isNeedPeriod;

      /**
       * 质检比例
       */
      @ExcelProperty(value = "质检比例")
    private BigDecimal qualityProportion;

      /**
       * 海关备案号
       */
      @ExcelProperty(value = "海关备案号")
    private String customsCode;

      /**
       * 运输限制信息
       */
      @ExcelProperty(value = "运输限制信息")
    private String trafficLimitation;

      /**
       * 按批次分拣
       */
      @ExcelProperty(value = "按批次分拣")
    private Byte isBatchNumberSorting;

      /**
       * 关联码
       */
      @ExcelProperty(value = "关联码")
    private String relationCode;

      /**
       * 单位体积
       */
      @ExcelProperty(value = "单位体积")
    private BigDecimal unitCube;

      /**
       * 商品别名
       */
      @ExcelProperty(value = "商品别名")
    private String aliasName;

      /**
       * 用途
       */
      @ExcelProperty(value = "用途")
    private String usefulness;

      /**
       * 原产地
       */
      @ExcelProperty(value = "原产地")
    private String originPlace;

      /**
       * 成分
       */
      @ExcelProperty(value = "成分")
    private String composition;

      /**
       * 功能
       */
      @ExcelProperty(value = "功能")
    private String functions;

      /**
       * 税率
       */
      @ExcelProperty(value = "税率")
    private BigDecimal rate;

      /**
       * 生产商
       */
      @ExcelProperty(value = "生产商")
    private String manufacturer;

      /**
       * 按规格分拣
       */
      @ExcelProperty(value = "按规格分拣")
    private Byte isSpecSorting;

      /**
       * 中单位
       */
      @ExcelProperty(value = "中单位")
    private String middleUnit;

      /**
       * 中单位换算关系
       */
      @ExcelProperty(value = "中单位换算关系")
    private BigDecimal middleUnitConvert;

      /**
       * 分拣方式
       */
      @ExcelProperty(value = "分拣方式")
    private Byte sortingType;

      /**
       * 关联码2
       */
      @ExcelProperty(value = "关联码2")
    private String relationCode2;

      /**
       * 关联码3
       */
      @ExcelProperty(value = "关联码3")
    private String relationCode3;

      /**
       * 关联码4
       */
      @ExcelProperty(value = "关联码4")
    private String relationCode4;

      /**
       * 关联码5
       */
      @ExcelProperty(value = "关联码5")
    private String relationCode5;

      /**
       * 禁收效期系数%
       */
      @ExcelProperty(value = "禁收效期系数%")
    private BigDecimal noReceivingRate;

      /**
       * middle_barcode
       */
      @ExcelProperty(value = "middle_barcode")
    private String middleBarcode;

      /**
       * 停售天数
       */
      @ExcelProperty(value = "停售天数")
    private String stopSellDays;

      /**
       * 大包装条码
       */
      @ExcelProperty(value = "大包装条码")
    private String bigBarcode;

      /**
       * 税价
       */
      @ExcelProperty(value = "税价")
    private BigDecimal ratePrice;

      /**
       * 市场价
       */
      @ExcelProperty(value = "市场价")
    private BigDecimal marketPrice;

      /**
       * 币种
       */
      @ExcelProperty(value = "币种")
    private String currency;

      /**
       * 行政税号
       */
      @ExcelProperty(value = "行政税号")
    private String administrativeCode;

      /**
       * 行邮税号
       */
      @ExcelProperty(value = "行邮税号")
    private String postMailCode;

      /**
       * 申报单位
       */
      @ExcelProperty(value = "申报单位")
    private String declareUnit;

      /**
       * 申报数量
       */
      @ExcelProperty(value = "申报数量")
    private BigDecimal declareQuantityOrder;

      /**
       * 打包配置类型
       */
      @ExcelProperty(value = "打包配置类型")
    private String unitPackageType;

      /**
       * 托盘单位数
       */
      @ExcelProperty(value = "托盘单位数")
    private BigDecimal unitPackage;

      /**
       * 图片
       */
      @ExcelProperty(value = "图片")
    private String images;

      /**
       * 展示类型
       */
      @ExcelProperty(value = "展示类型")
    private String showType;

      /**
       * 销量
       */
      @ExcelProperty(value = "销量")
    private Long buyNum;

      /**
       * 收藏量
       */
      @ExcelProperty(value = "收藏量")
    private Long collectionNum;

      /**
       * 规格JSON参数
       */
      @ExcelProperty(value = "规格JSON参数")
    private String specJson;

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
       * 创建人ID
       */
      @ExcelProperty(value = "创建人ID")
    private Long createBy;

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
       * 修改人ID
       */
      @ExcelProperty(value = "修改人ID")
    private Long updateBy;

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
       * 租户编号
       */
      @ExcelProperty(value = "租户编号")
    private String tenantId;

      /**
       * 管理SN
       */
      @ExcelProperty(value = "管理SN")
    private Byte isManageSn;

      /**
       * 商品英文名
       */
      @ExcelProperty(value = "商品英文名")
    private String productNameEn;

      /**
       * 整托数量
       */
      @ExcelProperty(value = "整托数量")
    private BigDecimal plateQty;

      /**
       * 满足数量为整托分拣
       */
      @ExcelProperty(value = "满足数量为整托分拣")
    private BigDecimal ceilPlateQty;

      /**
       * 是否重货
       */
      @ExcelProperty(value = "是否重货")
    private Byte isHeavyCargo;

      /**
       * 单位体积重
       */
      @ExcelProperty(value = "单位体积重")
    private BigDecimal cubeWeight;

      /**
       * 温层
       */
      @ExcelProperty(value = "温层")
    private String thermocLine;

      /**
       * 停售提前时长（天）
       */
      @ExcelProperty(value = "停售提前时长", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "天=")
    private Long stopSaleDay;

      /**
       * 是否扫码费
       */
      @ExcelProperty(value = "是否扫码费")
    private Byte isScanCost;

      /**
       * 物流重量(吨)
       */
      @ExcelProperty(value = "物流重量(吨)")
    private BigDecimal logisticsWeightTon;

      /**
       * 仓库Id
       */
      @ExcelProperty(value = "仓库Id")
    private Long storageId;

      /**
       * 仓库名称
       */
      @ExcelProperty(value = "仓库名称")
    private String storageName;

  
}
