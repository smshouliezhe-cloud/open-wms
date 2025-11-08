package com.yiruantong.inventory.domain.plate.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inventory.domain.plate.BasePlateOutDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 容器借出明细视图对象 base_plate_out_detail
 *
 * @author YRT
 * @date 2024-03-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = BasePlateOutDetail.class)
public class BasePlateOutDetailComposeVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;
  /**
   * 借出编号
   */
  private String outCode;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 类型
   */
  private String plateType;

  /**
   * 状态
   */
  private String statusText;

  /**
   * 合计借出数量
   */
  private BigDecimal totalOutQty;

  /**
   * 借出日期
   */
  private Date outDate;

  /**
   * 备注
   */
  private String remark;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

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
   * 客户Id
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户名称
   */
  private String clientShortName;

  /**
   * 仓库Id
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 来源id
   */
  private Long sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 来源类型
   */
  private String sourceType;

  /**
   * 销售组织
   */
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  private Long consignorIdSale;

  /**
   * 已归还数量
   */
  private BigDecimal totalReturnedQty;

  /**
   * 未归还数量
   */
  private BigDecimal totalUnreturnedQty;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 容器借出明细Id
   */
  @ExcelProperty(value = "容器借出明细Id")
  private Long outDetailId;

  /**
   * 容器借出Id
   */
  @ExcelProperty(value = "容器借出Id")
  private Long outId;
  /**
   * 现借出数量
   */
  @ExcelProperty(value = "现借出数量")
  private BigDecimal nowOutQty;

  /**
   * 当前借出数量
   */
  @ExcelProperty(value = "当前借出数量")
  private BigDecimal currentOutQty;

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
   * 已归还数量
   */
  @ExcelProperty(value = "已归还数量")
  private BigDecimal returnedQty;

  /**
   * 未归还数量
   */
  @ExcelProperty(value = "未归还数量")
  private BigDecimal unreturnedQty;

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


}
