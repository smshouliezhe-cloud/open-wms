package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateFlow;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 容器流水记录视图对象 base_plate_flow
 *
 * @author YRT
 * @date 2024-03-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateFlow.class)
public class BasePlateFlowVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 流水ID
   */
  @ExcelProperty(value = "流水ID")
  private Long flowId;

  /**
   * 流水单号
   */
  @ExcelProperty(value = "流水单号")
  private String flowCode;

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

  /**
   * 客户Id
   */
  @ExcelProperty(value = "客户Id")
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
   * 容器类别
   */
  @ExcelProperty(value = "容器类别")
  private String plateType;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 归还数量
   */
  @ExcelProperty(value = "归还数量")
  private BigDecimal returnQty;

  /**
   * 借出数量
   */
  @ExcelProperty(value = "借出数量")
  private BigDecimal outerQty;

  /**
   * 总借出数量
   */
  @ExcelProperty(value = "总借出数量")
  private BigDecimal totalouterQty;

  /**
   * 操作前数量
   */
  @ExcelProperty(value = "操作前数量")
  private BigDecimal beforeBalanceQty;

  /**
   * 操作后数量
   */
  @ExcelProperty(value = "操作后数量")
  private BigDecimal balanceQty;

  /**
   * 经手人ID
   */
  @ExcelProperty(value = "经手人ID")
  private Long userId;

  /**
   * 经手人
   */
  @ExcelProperty(value = "经手人")
  private String nickName;

  /**
   * 部门Id
   */
  @ExcelProperty(value = "部门Id")
  private Long deptId;

  /**
   * 部门
   */
  @ExcelProperty(value = "部门")
  private String deptName;

  /**
   * 来源类型
   */
  @ExcelProperty(value = "来源类型")
  private String sourceType;

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
   * 容器id
   */
  @ExcelProperty(value = "容器id")
  private Long plateId;

  /**
   * 销售组织
   */
  @ExcelProperty(value = "销售组织")
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  @ExcelProperty(value = "销售组织编号")
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  @ExcelProperty(value = "销售组织ID")
  private Long consignorIdSale;

  /**
   * 容器规格
   */
  @ExcelProperty(value = "容器规格")
  private String plateSpec;


}
