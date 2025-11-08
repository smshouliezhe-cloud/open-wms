package com.yiruantong.inbound.domain.service.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.inbound.domain.service.InExchange;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 换货单视图对象 in_exchange
 *
 * @author YiRuanTong
 * @date 2023-10-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = InExchange.class)
public class InExchangeVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 换货单ID
   */
  @ExcelProperty(value = "换货单ID")
  private Long exchangeId;

  /**
   * 换货编号
   */
  @ExcelProperty(value = "换货编号")
  private String exchangeCode;

  /**
   * 出货仓库ID
   */
  @ExcelProperty(value = "出货仓库ID")
  private Long storageId;

  /**
   * 出货仓库名称
   */
  @ExcelProperty(value = "出货仓库名称")
  private String storageName;

  /**
   * 入货仓库ID
   */
  @ExcelProperty(value = "入货仓库ID")
  private Long storageIdIn;

  /**
   * 入货仓库名称
   */
  @ExcelProperty(value = "入货仓库名称")
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
   * 部门名称额
   */
  @ExcelProperty(value = "部门名称额")
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
   * 税率
   */
  @ExcelProperty(value = "税率")
  private BigDecimal rate;

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
   * 实付金额
   */
  @ExcelProperty(value = "实付金额")
  private BigDecimal factAmount;

  /**
   * 支付金额
   */
  @ExcelProperty(value = "支付金额")
  private BigDecimal payAmount;

  /**
   * 分拣状态
   */
  @ExcelProperty(value = "分拣状态")
  private Long sortingStatus;

  /**
   * 分拣日期
   */
  @ExcelProperty(value = "分拣日期")
  private Date sortingDate;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private String exchangeStatus;

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


}
