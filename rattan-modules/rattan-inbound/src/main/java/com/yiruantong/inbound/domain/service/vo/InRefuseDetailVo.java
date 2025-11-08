package com.yiruantong.inbound.domain.service.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.service.InRefuseDetail;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 拒收单明细视图对象 in_refuse_detail
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InRefuseDetail.class)
public class InRefuseDetailVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 拒收单明细ID
   */
  @ExcelProperty(value = "拒收单明细ID")
  private Long refuseDetailId;

  /**
   * 拒收单ID
   */
  @ExcelProperty(value = "拒收单ID")
  private Long refuseId;

  /**
   * 采购单明细ID
   */
  @ExcelProperty(value = "采购单明细ID")
  private Long orderDetailId;

  /**
   * 采购单ID
   */
  @ExcelProperty(value = "采购单ID")
  private Long orderId;

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
   * 货位名称
   */
  @ExcelProperty(value = "货位名称")
  private String positionName;

  /**
   * 拍号
   */
  @ExcelProperty(value = "拍号")
  private String plateCode;

  /**
   * 明细状态
   */
  @ExcelProperty(value = "明细状态")
  private String refuseStatus;

  /**
   * 拒收数量
   */
  @ExcelProperty(value = "拒收数量")
  private BigDecimal refuseQuantity;

  /**
   * 上架数量
   */
  @ExcelProperty(value = "上架数量")
  private BigDecimal shelveQuantity;

  /**
   * 单价
   */
  @ExcelProperty(value = "单价")
  private BigDecimal purchasePrice;

  /**
   * 货款金额
   */
  @ExcelProperty(value = "货款金额")
  private BigDecimal purchaseAmount;

  /**
   * 批次号
   */
  @ExcelProperty(value = "批次号")
  private String batchNumber;

  /**
   * 生产日期
   */
  @ExcelProperty(value = "生产日期")
  private Date produceDate;

  /**
   * 到期日期
   */
  @ExcelProperty(value = "到期日期")
  private Date limitDate;

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
   * 原产地
   */
  @ExcelProperty(value = "原产地")
  private String originPlace;

  /**
   * SN码
   */
  @ExcelProperty(value = "SN码")
  private String singleSignCode;

  /**
   * 总件数
   */
  @ExcelProperty(value = "总件数")
  private BigDecimal packageQty;

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


}
