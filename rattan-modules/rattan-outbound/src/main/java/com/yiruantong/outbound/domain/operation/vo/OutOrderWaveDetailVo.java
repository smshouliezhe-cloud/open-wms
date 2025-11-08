package com.yiruantong.outbound.domain.operation.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.outbound.domain.operation.OutOrderWaveDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 出库单波次明细视图对象 out_order_wave_detail
 *
 * @author YRT
 * @date 2024-09-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OutOrderWaveDetail.class)
public class OutOrderWaveDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 波次明细ID
   */
  @ExcelProperty(value = "波次明细ID")
  private Long orderWaveDetailId;

  /**
   * 波次单ID
   */
  @ExcelProperty(value = "波次单ID")
  private Long orderWaveId;

  /**
   * 分拣占位ID
   */
  @ExcelProperty(value = "分拣占位ID")
  private Long holderId;

  /**
   * 配货位
   */
  @ExcelProperty(value = "配货位")
  private String allotPositionName;

  /**
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 订单ID
   */
  @ExcelProperty(value = "订单ID")
  private Long orderId;

  /**
   * 订单明细编号
   */
  @ExcelProperty(value = "订单明细编号")
  private Long orderDetailId;

  /**
   * 订单编号
   */
  @ExcelProperty(value = "订单编号")
  private String orderCode;

  /**
   * 店铺订单编号
   */
  @ExcelProperty(value = "店铺订单编号")
  private String storeOrderCode;

  /**
   * 产品ID
   */
  @ExcelProperty(value = "产品ID")
  private Long productId;

  /**
   * 产品编号
   */
  @ExcelProperty(value = "产品编号")
  private String productCode;

  /**
   * 产品名称
   */
  @ExcelProperty(value = "产品名称")
  private String productName;

  /**
   * 条形码
   */
  @ExcelProperty(value = "条形码")
  private String productModel;

  /**
   * 商品规格
   */
  @ExcelProperty(value = "商品规格")
  private String productSpec;

  /**
   * 订单数量
   */
  @ExcelProperty(value = "订单数量")
  private BigDecimal quantityOrder;

  /**
   * 打包出库数量
   */
  @ExcelProperty(value = "打包出库数量")
  private BigDecimal quantityOuted;

  /**
   * 配货数量
   */
  @ExcelProperty(value = "配货数量")
  private BigDecimal matchedQuantity;

  /**
   * 冻结数量
   */
  @ExcelProperty(value = "冻结数量")
  private BigDecimal freezeQuantity;

  /**
   * SN号
   */
  @ExcelProperty(value = "SN号")
  private String singleSignCode;

  /**
   * 快递ID
   */
  @ExcelProperty(value = "快递ID")
  private Long expressCorpId;

  /**
   * 快递公司名称
   */
  @ExcelProperty(value = "快递公司名称")
  private String expressCorpName;

  /**
   * 快递公司编号
   */
  @ExcelProperty(value = "快递公司编号")
  private String expressCode;

  /**
   * 拣货数量
   */
  @ExcelProperty(value = "拣货数量")
  private BigDecimal pickQuantity;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 生成日期
   */
  @ExcelProperty(value = "生成日期")
  private Date produceDate;

  /**
   * 托盘号
   */
  @ExcelProperty(value = "托盘号")
  private String plateCode;

  /**
   * 关联码
   */
  @ExcelProperty(value = "关联码")
  private String relationCode;

  /**
   * 整拣单
   */
  @ExcelProperty(value = "整拣单")
  private Byte isFullContainerLoad;

  /**
   * 原始订单数量
   */
  @ExcelProperty(value = "原始订单数量")
  private BigDecimal quantityOrderOrigin;

  /**
   * 单位毛重
   */
  @ExcelProperty(value = "单位毛重")
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  @ExcelProperty(value = "小计毛重")
  private BigDecimal rowWeight;

  /**
   * 货位类型
   */
  @ExcelProperty(value = "货位类型")
  private Byte positionType;

  /**
   * 子波次单号
   */
  @ExcelProperty(value = "子波次单号")
  private String subOrderWaveCode;

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
   * 单位净重
   */
  @ExcelProperty(value = "单位净重")
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  @ExcelProperty(value = "小计净重")
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  @ExcelProperty(value = "来源类别")
  private String sourceType;

  /**
   * 来源主表ID
   */
  @ExcelProperty(value = "来源主表ID")
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  @ExcelProperty(value = "来源明细ID")
  private String sourceDetailId;

  /**
   * 线路Id
   */
  @ExcelProperty(value = "线路Id")
  private Long lineId;

  /**
   * 线路编号
   */
  @ExcelProperty(value = "线路编号")
  private String lineCode;

  /**
   * 线路名称
   */
  @ExcelProperty(value = "线路名称")
  private String lineName;

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
   * 已发货数量
   */
  @ExcelProperty(value = "已发货数量")
  private BigDecimal quantityShipped;

  /**
   * 项目号
   */
  @ExcelProperty(value = "项目号")
  private String projectCode;

  /**
   * 箱号
   */
  @ExcelProperty(value = "箱号")
  private String caseNumber;


}
