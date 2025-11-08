package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 拣货下架视图对象 out_order_wave_detail
 *
 * @author YRT
 * @date 2023-11-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderWaveDetail.class)
public class OutOrderWaveDetailPickingVo extends OutOrderWaveDetail implements Serializable {
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
   * 货主编号
   */
  @ExcelProperty(value = "货主编号")
  private String consignorName;

  /**
   * 快递公司ID
   */
  @ExcelProperty(value = "快递公司ID")
  private Long expressCorpId;

  /**
   * 快递公司名称
   */
  @ExcelProperty(value = "快递公司名称")
  private String expressCorpName;

  /**
   * 关联码
   */
  private String relationCode;

  /**
   * middle_barcode
   */
  private String middleBarcode;

  /**
   * 关联码2
   */
  private String relationCode2;

  /**
   * 关联码3
   */
  private String relationCode3;

  /**
   * 关联码4
   */
  private String relationCode4;

  /**
   * 关联码5
   */
  private String relationCode5;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 中单位
   */
  private String middleUnit;

  /**
   * 中单位换算关系
   */
  private BigDecimal middleUnitConvert;

  /**
   * 小单位
   */
  private String smallUnit;

  /**
   * 大单位
   */
  private String bigUnit;

  /**
   * 换算关系
   */
  private BigDecimal unitConvert;

  /**
   * 单位换算描述
   */
  private String unitConvertText;

  /**
   * 采购价
   */
  private BigDecimal purchasePrice;

  /**
   * 大包装条码
   */
  private String bigBarcode;

  /**
   * 客户ID
   */
  @ExcelProperty(value = "客户ID")
  private Long clientId;

  /**
   * 客户编号
   */
  @ExcelProperty(value = "客户编号")
  private String clientCode;

  /**
   * 客户名称
   */
  @ExcelProperty(value = "客户名称")
  private String clientShortName;

  /**
   * 管理SN
   */
  @ExcelProperty(value = "管理SN")
  private Byte isManageSn;
}
