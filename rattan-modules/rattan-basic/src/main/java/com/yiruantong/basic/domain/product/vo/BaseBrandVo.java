package com.yiruantong.basic.domain.product.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.product.BaseBrand;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 品牌管理视图对象 base_brand
 *
 * @author YiRuanTong
 * @date 2023-11-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseBrand.class)
public class BaseBrandVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 品牌ID
   */
  @ExcelProperty(value = "品牌ID")
  private Long brandId;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 大类别ID
   */
  @ExcelProperty(value = "大类别ID")
  private Long bigTypeId;

  /**
   * 大类别名称
   */
  @ExcelProperty(value = "大类别名称")
  private String bigTypeName;

  /**
   * 类型
   */
  @ExcelProperty(value = "类型")
  private String modeType;

  /**
   * 品牌名
   */
  @ExcelProperty(value = "品牌名")
  private String brandName;

  /**
   * 品牌英文名
   */
  @ExcelProperty(value = "品牌英文名")
  private String brandNameEn;

  /**
   * 地址
   */
  @ExcelProperty(value = "地址")
  private String address;

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
   * 供应商简称
   */
  @ExcelProperty(value = "供应商简称")
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
   * 周期(天)
   */
  @ExcelProperty(value = "周期(天)")
  private Long cycle;

  /**
   * logo
   */
  @ExcelProperty(value = "logo")
  private String logo;

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
   * 质检计划
   */
  @ExcelProperty(value = "质检计划")
  private String qualityPlan;

  /**
   * 质检比例
   */
  @ExcelProperty(value = "质检比例")
  private BigDecimal qualityProportion;

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
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;


}
