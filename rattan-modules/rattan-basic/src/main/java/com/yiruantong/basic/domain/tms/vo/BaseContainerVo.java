package com.yiruantong.basic.domain.tms.vo;

  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.basic.domain.tms.BaseContainer;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 集装箱信息视图对象 base_container
 *
 * @author YRT
 * @date 2025-01-21
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BaseContainer.class)
public class BaseContainerVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 集装箱ID
   */
  @ExcelProperty(value = "集装箱ID")
  private Long containerId;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNo;

  /**
   * 集装箱类型
   */
  @ExcelProperty(value = "集装箱类型")
  private String containerType;

  /**
   * 尺寸
   */
  @ExcelProperty(value = "尺寸")
  private String size;

  /**
   * 交易编号
   */
  @ExcelProperty(value = "交易编号")
  private String containerSpellMode;

  /**
   * 铅封号
   */
  @ExcelProperty(value = "铅封号")
  private String sealNo;

  /**
   * 预订舱长宽高(mm)
   */
  @ExcelProperty(value = "预订舱长宽高(mm)")
  private String cabinLwhBooking;

  /**
   * 实际订舱长宽高(mm)
   */
  @ExcelProperty(value = "实际订舱长宽高(mm)")
  private String cabinLwhActual;

  /**
   * 装箱后实际长宽高(mm)
   */
  @ExcelProperty(value = "装箱后实际长宽高(mm)")
  private String cabinLwhAfter;

  /**
   * 体积
   */
  @ExcelProperty(value = "体积")
  private BigDecimal volume;

  /**
   * 自重(KGS)
   */
  @ExcelProperty(value = "自重(KGS)")
  private BigDecimal weight;

  /**
   * 运输ID
   */
  @ExcelProperty(value = "运输ID")
  private Long transportId;

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
   * 运输类型
   */
  @ExcelProperty(value = "运输类型")
  private String transportationType;


}
