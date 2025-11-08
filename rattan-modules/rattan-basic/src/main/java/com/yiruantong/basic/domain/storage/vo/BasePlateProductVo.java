package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BasePlateProduct;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商品容器管理视图对象 base_plate_product
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateProduct.class)
public class BasePlateProductVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 商品容器id
   */
  @ExcelProperty(value = "商品容器id")
  private Long plateProductId;

  /**
   * 商品id
   */
  @ExcelProperty(value = "商品id")
  private Long productId;

  /**
   * 商品名称
   */
  @ExcelProperty(value = "商品名称")
  private String productName;

  /**
   * 商品编号
   */
  @ExcelProperty(value = "商品编号")
  private String productCode;

  /**
   * 商品规格
   */
  @ExcelProperty(value = "商品规格")
  private String productSpec;

  /**
   * 商品条码
   */
  @ExcelProperty(value = "商品条码")
  private String productModel;

  /**
   * 容器id
   */
  @ExcelProperty(value = "容器id")
  private Long plateId;

  /**
   * 容器编号
   */
  @ExcelProperty(value = "容器编号")
  private String plateCode;

  /**
   * 容器类型
   */
  @ExcelProperty(value = "容器类型")
  private String plateType;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 仓库id
   */
  @ExcelProperty(value = "仓库id")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

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
   * 容器名称（规格）
   */
  @ExcelProperty(value = "容器名称", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "规=格")
  private String plateSpec;

  /**
   * 单位重量
   */
  @ExcelProperty(value = "单位重量")
  private BigDecimal weight;

  /**
   * 单位体积
   */
  @ExcelProperty(value = "单位体积")
  private BigDecimal unitCube;

  /**
   * 容器名称
   */
  @ExcelProperty(value = "容器名称")
  private String plateName;


}
