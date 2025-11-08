package com.yiruantong.outbound.domain.out;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
  

import java.io.Serial;

/**
 * 打包单明细对象 out_package_detail
 *
 * @author YRT
 * @date 2025-04-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_package_detail", autoResultMap = true)
public class OutPackageDetail extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 出库明细ID
   */
    @TableId(value = "package_detail_id")
  private Long packageDetailId;

  /**
   * 出库单ID
   */
  private Long packageId;

  /**
   * 销售单ID
   */
  private Long orderId;

  /**
   * 销售明细ID
   */
  private Long orderDetailId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 条形码
   */
  private String productModel;

  /**
   * 商品规格
   */
  private String productSpec;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 打包数量
   */
  private BigDecimal packageQuantity;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位换算
   */
  private String unitConvertText;

  /**
   * 采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 销售价
   */
  private BigDecimal salePrice;

  /**
   * 折扣金额
   */
  private BigDecimal discountAmount;

  /**
   * 应收总额
   */
  private BigDecimal saleAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 税价
   */
  private BigDecimal ratePrice;

  /**
   * 价税合计
   */
  private BigDecimal rateAmount;

  /**
   * SN码
   */
  private String singleSignCode;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 出库单号
   */
  private String orderCode;

  /**
   * 箱号
   */
  private String caseNumber;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
    @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源主表ID
   */
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  private String sourceDetailId;

  /**
   * 类别编号
   */
  private Long typeId;

  /**
   * 类别名称
   */
  private String typeName;

  /**
   * 产品型号
   */
  private String productBarCode;

  /**
   * 品牌ID
   */
  private Long brandId;

  /**
   * 品牌名
   */
  private String brandName;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 大单位数量
   */
  private BigDecimal bigQty;

  /**
   * 合计重量(吨)
   */
  private BigDecimal rowWeightTon;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 小计包裹数
   */
  private BigDecimal rowPackage;

  /**
   * 区域id
   */
  private Long consignorIdSale;

  /**
   * 区域
   */
  private String consignorNameSale;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 拍号
   */
  private String plateCode;


}
