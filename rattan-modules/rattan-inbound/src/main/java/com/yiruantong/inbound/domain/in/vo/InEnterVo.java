package com.yiruantong.inbound.domain.in.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yiruantong.inbound.domain.in.InEnter;
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
 * 入库管理视图对象 in_enter
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InEnter.class)
public class InEnterVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 入库单ID
   */
  @ExcelProperty(value = "入库单ID")
  private Long enterId;

  /**
   * 入库单编号
   */
  @ExcelProperty(value = "入库单编号")
  private String enterCode;

  /**
   * 预到货单ID
   */
  @ExcelProperty(value = "预到货单ID")
  private Long orderId;

  /**
   * 预到货单号
   */
  @ExcelProperty(value = "预到货单号")
  private String orderCode;

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
   * 入库日期
   */
  @ExcelProperty(value = "入库日期")
  private Date applyDate;

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
  private BigDecimal totalEnterQuantity;

  /**
   * 合计金额
   */
  @ExcelProperty(value = "合计金额")
  private BigDecimal totalAmount;

  /**
   * 合计优惠金额
   */
  @ExcelProperty(value = "合计优惠金额")
  private BigDecimal totalFavourAmount;

  /**
   * 合计实付金额
   */
  @ExcelProperty(value = "合计实付金额")
  private BigDecimal totalFactAmount;

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
   * 付款期限
   */
  @ExcelProperty(value = "付款期限")
  private Date payLimitDate;

  /**
   * 入库状态
   */
  @ExcelProperty(value = "入库状态")
  private String enterStatus;

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
   * 已结算金额
   */
  @ExcelProperty(value = "已结算金额")
  private BigDecimal settleAmount;

  /**
   * 未已结算金额
   */
  @ExcelProperty(value = "未已结算金额")
  private BigDecimal unSettleAmount;

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
   * 是否已打印
   */
  @ExcelProperty(value = "是否已打印")
  private Long isPrint;

  /**
   * 订单类型
   */
  @ExcelProperty(value = "订单类型")
  private String orderType;

  /**
   * IPN编号
   */
  @ExcelProperty(value = "IPN编号")
  private String lpnCode;

  /**
   * 合计重量
   */
  @ExcelProperty(value = "合计重量")
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  @ExcelProperty(value = "集装箱号")
  private String containerNos;

  /**
   * 装卸道口
   */
  @ExcelProperty(value = "装卸道口")
  private String dockCrossing;

  /**
   * 铅封号
   */
  @ExcelProperty(value = "铅封号")
  private String sealNos;

  /**
   * 测温温度
   */
  @ExcelProperty(value = "测温温度")
  private String surveyDegree;

  /**
   * 储存温度
   */
  @ExcelProperty(value = "储存温度")
  private String storageDegree;

  /**
   * 是否使用装卸
   */
  @ExcelProperty(value = "是否使用装卸")
  private String isDock;

  /**
   * 异常
   */
  @ExcelProperty(value = "异常")
  private String abnormal;

  /**
   * 异常件数
   */
  @ExcelProperty(value = "异常件数")
  private String abnormalPieces;

  /**
   * 其他服务
   */
  @ExcelProperty(value = "其他服务")
  private String otherServices;

  /**
   * 其他服务
   */
  @ExcelProperty(value = "其他服务")
  private String truckNo;

  /**
   * 联系方式
   */
  @ExcelProperty(value = "联系方式")
  private String mobile;

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
   * 跟踪单号
   */
  @ExcelProperty(value = "跟踪单号")
  private String trackingNumber;

  /**
   * 扫描类型
   */
  @ExcelProperty(value = "扫描类型")
  private String scanInType;

  /**
   * 是否已上架
   */
  @ExcelProperty(value = "是否已上架")
  private String shelveStatus;

  /**
   * 上传文件
   */
  @ExcelProperty(value = "上传文件")
  private String uploadFile;

  /**
   * 合计件数
   */
  @ExcelProperty(value = "合计件数")
  private BigDecimal totalPackage;

  /**
   * 大单位数量
   */
  @ExcelProperty(value = "大单位数量")
  private BigDecimal bigQtyTotal;

  /**
   * 合计体积
   */
  @ExcelProperty(value = "合计体积")
  private BigDecimal totalCube;

  /**
   * 费用项ID
   */
  @ExcelProperty(value = "费用项ID")
  private String feeItemIds;

  /**
   * 仓库编号
   */
  @ExcelProperty(value = "仓库编号")
  private String storageCode;


}
