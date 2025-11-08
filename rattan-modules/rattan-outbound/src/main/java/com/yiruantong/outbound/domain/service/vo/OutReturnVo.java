package com.yiruantong.outbound.domain.service.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.outbound.domain.service.OutReturn;
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
 * 出库退货单视图对象 out_return
 *
 * @author YiRuanTong
 * @date 2025-02-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutReturn.class)
public class OutReturnVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 退货单ID
   */
  @ExcelProperty(value = "退货单ID")
  private Long returnId;

  /**
   * 退货单编号
   */
  @ExcelProperty(value = "退货单编号")
  private String returnCode;

  /**
   * 出库单ID
   */
  @ExcelProperty(value = "出库单ID")
  private Long orderId;

  /**
   * 出库单编号
   */
  @ExcelProperty(value = "出库单编号")
  private String orderCode;

  /**
   * 入库仓库ID
   */
  @ExcelProperty(value = "入库仓库ID")
  private Long storageId;

  /**
   * 入库仓库名称
   */
  @ExcelProperty(value = "入库仓库名称")
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
   * 部门
   */
  @ExcelProperty(value = "部门")
  private String deptName;

  /**
   * 入库日期
   */
  @ExcelProperty(value = "入库日期")
  private Date applyDate;

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
   * 退货数量
   */
  @ExcelProperty(value = "退货数量")
  private BigDecimal totalReturnQuantity;

  /**
   * 退货金额
   */
  @ExcelProperty(value = "退货金额")
  private BigDecimal totalSaleAmount;

  /**
   * 已退金额
   */
  @ExcelProperty(value = "已退金额")
  private BigDecimal totalRefundAmount;

  /**
   * 未退金额
   */
  @ExcelProperty(value = "未退金额")
  private BigDecimal totalUnrefundAmount;

  /**
   * 已收金额
   */
  @ExcelProperty(value = "已收金额")
  private BigDecimal totalPaid;

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
   * 退款状态
   */
  @ExcelProperty(value = "退款状态")
  private String refundStatus;

  /**
   * 驾驶员姓名
   */
  @ExcelProperty(value = "驾驶员姓名")
  private String driveName;

  /**
   * 车牌号
   */
  @ExcelProperty(value = "车牌号")
  private String truckNo;

  /**
   * 物品重量
   */
  @ExcelProperty(value = "物品重量")
  private BigDecimal weight;

  /**
   * 店铺订单编号
   */
  @ExcelProperty(value = "店铺订单编号")
  private String storeOrderCode;

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
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
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
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigOtyTotal;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 保修
   */
  @ExcelProperty(value = "保修")
  private String guarantee;

  /**
   * 售后类型
   */
  @ExcelProperty(value = "售后类型")
  private String returnType;

  /**
   * 问题归属
   */
  @ExcelProperty(value = "问题归属")
  private String ascription;

  /**
   * 物流名称
   */
  @ExcelProperty(value = "物流名称")
  private String expressCorpName;

  /**
   * 物流类别
   */
  @ExcelProperty(value = "物流类别")
  private Byte expressCorpType;

  /**
   * 物流单号
   */
  @ExcelProperty(value = "物流单号")
  private String expressCode;

  /**
   * 物流ID
   */
  @ExcelProperty(value = "物流ID")
  private Long expressCorpId;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;

  /**
   * 仓管状态
   */
  @ExcelProperty(value = "仓管状态")
  private String warehouseStatus;

  /**
   * 质检状态
   */
  @ExcelProperty(value = "质检状态")
  private String checkingStatus;


}
