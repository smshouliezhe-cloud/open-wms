package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateInDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 容器归还明细视图对象 base_plate_in_detail
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateInDetail.class)
public class BasePlateInDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器归还明细Id
   */
  @ExcelProperty(value = "容器归还明细Id")
  private Long inDetailId;

  /**
   * 容器归还Id
   */
  @ExcelProperty(value = "容器归还Id")
  private Long inId;

  /**
   * 类型
   */
  @ExcelProperty(value = "类型")
  private String plateType;

  /**
   * 现借出数量
   */
  @ExcelProperty(value = "现借出数量")
  private BigDecimal nowOutQty;

  /**
   * 归还数量
   */
  @ExcelProperty(value = "归还数量")
  private BigDecimal returnQty;

  /**
   * 剩余借出数量
   */
  @ExcelProperty(value = "剩余借出数量")
  private BigDecimal surplusOutQty;

  /**
   * SN
   */
  @ExcelProperty(value = "SN")
  private String singleSignCode;

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
   * 容器id
   */
  @ExcelProperty(value = "容器id")
  private Long plateId;

  /**
   * 容器名称
   */
  @ExcelProperty(value = "容器名称")
  private String plateName;

  /**
   * 容器编号
   */
  @ExcelProperty(value = "容器编号")
  private String plateCode;

  /**
   * 容器规格
   */
  @ExcelProperty(value = "容器规格")
  private String plateSpec;

  /**
   * 小计运费
   */
  @ExcelProperty(value = "小计运费")
  private BigDecimal subFreight;

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
   * 小计重量
   */
  @ExcelProperty(value = "小计重量")
  private BigDecimal rowWeight;

  /**
   * 小计体积
   */
  @ExcelProperty(value = "小计体积")
  private BigDecimal rowCube;

  /**
   * 运费单价
   */
  @ExcelProperty(value = "运费单价")
  private BigDecimal price;

  /**
   * 来源主表id
   */
  @ExcelProperty(value = "来源主表id")
  private Long sourceMainId;

  /**
   * 来源明细id
   */
  @ExcelProperty(value = "来源明细id")
  private Long sourceDetailId;

  /**
   * 容器属性
   */
  @ExcelProperty(value = "容器属性")
  private String plateAttribute;


}
