package com.yiruantong.basic.domain.storage.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.basic.domain.storage.BasePlate;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 容器管理视图对象 base_plate
 *
 * @author YiRuanTong
 * @date 2024-03-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlate.class)
public class BasePlateVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器ID
   */
  @ExcelProperty(value = "容器ID")
  private Long plateId;

  /**
   * 容器编号
   */
  @ExcelProperty(value = "容器编号")
  private String plateCode;

  /**
   * 容器类别
   */
  @ExcelProperty(value = "容器类别")
  private String plateType;

  /**
   * 容器名称
   */
  @ExcelProperty(value = "容器名称")
  private String plateName;

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
   * 已打印
   */
  @ExcelProperty(value = "已打印")
  private Long isPrinted;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Long enable;

  /**
   * 是否使用
   */
  @ExcelProperty(value = "是否使用")
  private Long isUse;

  /**
   * 容器状态
   */
  @ExcelProperty(value = "容器状态")
  private String plateState;

  /**
   * 打印状态
   */
  @ExcelProperty(value = "打印状态")
  private String printState;

  /**
   * 打印次数
   */
  @ExcelProperty(value = "打印次数")
  private Long pringQuantity;

  /**
   * 已使用
   */
  @ExcelProperty(value = "已使用")
  private Long isUsing;

  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;

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
   * 容器规格
   */
  @ExcelProperty(value = "容器规格")
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


}
