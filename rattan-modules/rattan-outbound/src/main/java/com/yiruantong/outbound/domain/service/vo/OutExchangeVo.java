package com.yiruantong.outbound.domain.service.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.outbound.domain.service.OutExchange;
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
 * 换货管理视图对象 out_exchange
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutExchange.class)
public class OutExchangeVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 换货单ID
   */
  @ExcelProperty(value = "换货单ID")
  private Long exchangeId;

  /**
   * 换货单号
   */
  @ExcelProperty(value = "换货单号")
  private String exchangeCode;

  /**
   * 出库仓库ID
   */
  @ExcelProperty(value = "出库仓库ID")
  private Long storageId;

  /**
   * 出库仓库
   */
  @ExcelProperty(value = "出库仓库")
  private String storageName;

  /**
   * 入库仓库ID
   */
  @ExcelProperty(value = "入库仓库ID")
  private Long storageIdIn;

  /**
   * 入库仓库
   */
  @ExcelProperty(value = "入库仓库")
  private String storageNameIn;

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
   * 部门ID
   */
  @ExcelProperty(value = "部门ID")
  private Long deptId;

  /**
   * 部门名称
   */
  @ExcelProperty(value = "部门名称")
  private String deptName;

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
   * 换货日期
   */
  @ExcelProperty(value = "换货日期")
  private Date applyDate;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 入库合计数量
   */
  @ExcelProperty(value = "入库合计数量")
  private BigDecimal totalEnterQuantity;

  /**
   * 入库合计金额
   */
  @ExcelProperty(value = "入库合计金额")
  private BigDecimal totalEnterAmount;

  /**
   * 出库合计数量
   */
  @ExcelProperty(value = "出库合计数量")
  private BigDecimal totalOuterQuantity;

  /**
   * 出库合计金额
   */
  @ExcelProperty(value = "出库合计金额")
  private BigDecimal totalOuterAmount;

  /**
   * 换货差额
   */
  @ExcelProperty(value = "换货差额")
  private BigDecimal differenceAmount;

  /**
   * 优惠额度
   */
  @ExcelProperty(value = "优惠额度")
  private BigDecimal favourAmount;

  /**
   * 优惠后金额
   */
  @ExcelProperty(value = "优惠后金额")
  private BigDecimal factAmount;

  /**
   * 实收金额
   */
  @ExcelProperty(value = "实收金额")
  private BigDecimal receiveAmount;

  /**
   * 分拣日期
   */
  @ExcelProperty(value = "分拣日期")
  private Date sortingDate;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Long sortingStatus;

  /**
   * 换货状态
   */
  @ExcelProperty(value = "换货状态")
  private String exchangeStatus;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String auditor;

  /**
   * 审核
   */
  @ExcelProperty(value = "审核")
  private Long auditing;

  /**
   * 审核日期
   */
  @ExcelProperty(value = "审核日期")
  private Date auditDate;

  /**
   * 货主ID
   */
  @ExcelProperty(value = "货主ID")
  private Long consignorId;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String consignorCode;

  /**
   *
   */
  @ExcelProperty(value = "")
  private String consignorName;

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
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源ID
   */
  @ExcelProperty(value = "来源ID")
  private String sourceId;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;


}
