package com.yiruantong.inbound.domain.service.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inbound.domain.service.InReturn;
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
 * 退货单视图对象 in_return
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InReturn.class)
public class InReturnVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 退货ID
   */
  @ExcelProperty(value = "退货ID")
  private Long returnId;

  /**
   * 退货编号
   */
  @ExcelProperty(value = "退货编号")
  private String returnCode;

  /**
   * 采购单ID
   */
  @ExcelProperty(value = "采购单ID")
  private Long orderId;

  /**
   * 采购单编号
   */
  @ExcelProperty(value = "采购单编号")
  private String orderCode;

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
   * 申请日期
   */
  @ExcelProperty(value = "申请日期")
  private Date applyDate;

  /**
   * 采购商ID
   */
  @ExcelProperty(value = "采购商ID")
  private Long providerId;

  /**
   * 采购商编号
   */
  @ExcelProperty(value = "采购商编号")
  private String providerCode;

  /**
   * 采购商名称
   */
  @ExcelProperty(value = "采购商名称")
  private String providerShortName;

  /**
   * 合计数量
   */
  @ExcelProperty(value = "合计数量")
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  @ExcelProperty(value = "合计金额")
  private BigDecimal totalAmount;

  /**
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

  /**
   * 合计价税
   */
  @ExcelProperty(value = "合计价税")
  private BigDecimal totalRateAmount;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String returnStatus;

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
   * 审核人
   */
  @ExcelProperty(value = "审核人")
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
   * 退货数量
   */
  @ExcelProperty(value = "退货数量")
  private BigDecimal totalReturnQuantity;

  /**
   * 退货金额
   */
  @ExcelProperty(value = "退货金额")
  private BigDecimal returnAmount;

  /**
   * 发货人
   */
  @ExcelProperty(value = "发货人")
  private String shippingName;

  /**
   * 发货人地址
   */
  @ExcelProperty(value = "发货人地址")
  private String shippingAddress;

  /**
   * 手机
   */
  @ExcelProperty(value = "手机")
  private String mobile;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 单据类型
   */
  @ExcelProperty(value = "单据类型")
  private String orderType;

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
   * 合计净重
   */
  @ExcelProperty(value = "合计净重")
  private BigDecimal totalNetWeight;

  /**
   * 合计上架数量
   */
  @ExcelProperty(value = "合计上架数量")
  private BigDecimal totalShelvedQuantity;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQtyTotal;

  /**
   * 快递类别
   */
  @ExcelProperty(value = "快递类别")
  private Byte expressCorpType;

  /**
   * 快递ID
   */
  @ExcelProperty(value = "快递ID")
  private Long expressCorpId;

  /**
   * 快递名称
   */
  @ExcelProperty(value = "快递名称")
  private String expressCorpName;

  /**
   * 快递单号
   */
  @ExcelProperty(value = "快递单号")
  private String expressCode;

  /**
   * 运费
   */
  @ExcelProperty(value = "运费")
  private BigDecimal shippingAmount;

  /**
   * 发货方式
   */
  @ExcelProperty(value = "发货方式")
  private String shippingMethod;

  /**
   * 配送类型
   */
  @ExcelProperty(value = "配送类型")
  private String distributionType;

  /**
   * 线路名称
   */
  @ExcelProperty(value = "线路名称")
  private String lineName;

  /**
   * 线路编号
   */
  @ExcelProperty(value = "线路编号")
  private String lineCode;

  /**
   * 线路ID
   */
  @ExcelProperty(value = "线路ID")
  private Long lineId;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;


}
